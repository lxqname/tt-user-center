package com.deepexi.user.service.kong.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.user.domain.dto.KongConsumer;
import com.deepexi.user.domain.eo.Account;
import com.deepexi.user.domain.vo.JwtVo;
import com.deepexi.user.service.kong.KongLoginService;
import com.deepexi.user.service.kong.KongService;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.StringUtil;
import com.deepexi.util.extension.ApplicationException;
import com.google.common.collect.Maps;
import com.nimbusds.jose.JWSObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: lizhongbao
 * @Description: kong上登录/注册服务
 * @date: 2019-9-21 16:50:57
 */
@Component
@Service(version = "${demo.service.version}")
@Transactional(rollbackFor = Exception.class)
public class KongLoginServiceImpl implements KongLoginService {

    public static final String KEY = "key";
    public static final String SECRET = "secret";
    public static final String TYP = "typ";
    public static final String JWT = "JWT";
    public static final String ALG = "alg";
    public static final String HS_256 = "HS256";
    public static final String ISS = "iss";
    public static final String ACCOUNT_ID = "account_id";
    public static final String TENANT_ID = "tenant_id";
    public static final String CHANNEL = "channel";
    public static final int AMOUNT = 2;
    public static final String JOINT = "_";
    private final Logger logger = LoggerFactory.getLogger(KongLoginService.class);

    @Autowired
    private KongService kongService;

    @Override
    public Map commonGenerateToken(Account account, String channel) {
        String customId = account.getId() + JOINT + account.getTenantCode() + JOINT + channel;
        logger.info("start generating token,custom_id:{}", customId);

        // 判断是否已存在consumer,不存在则创建
        JwtVo existConsumer = kongService.isExistConsumer(customId);
        String id;
        if (CollectionUtil.isEmpty(existConsumer.getData()) || existConsumer.getData().get(0).getId() == null) {
            // 创建consumer
            KongConsumer jwtConsumer = kongService.createConsumer(customId);
            id = jwtConsumer.getId();
        } else {
            id = existConsumer.getData().get(0).getId();
        }
        // 删除已有的token
        JwtVo jwtTokens = kongService.getTokens(id);
        if (null != jwtTokens) {
            List<KongConsumer> kongConsumers = jwtTokens.getData();
            if (kongConsumers != null) {
                kongConsumers.forEach(kongConsumer -> {
                    kongService.deleteByCustomIdAndId(id, kongConsumer.getId());
                });
            }
        }
        // 重新生成token
        Map<String, String> jwtToken = kongService.createToken(id);
        if (jwtToken == null || StringUtil.isEmpty(jwtToken.get(KEY))) {
            logger.error("generate token is error:{}", jwtToken);
        }
        return jwtToken;
    }

    @Override
    public String generateToken(Account account, String channel) {
        Map map = commonGenerateToken(account, channel);
        String key = (String) map.get(KEY);
        String secret = (String) map.get(SECRET);
        Map<String, Object> header = Maps.newHashMap();
        header.put(TYP, JWT);
        header.put(ALG, HS_256);
        Map<String, Object> claims = Maps.newHashMap();
        claims.put(ISS, key);
        claims.put(ACCOUNT_ID, account.getId());
        claims.put(TENANT_ID, account.getTenantCode());
        claims.put(CHANNEL, channel);
        Calendar nowTime = Calendar.getInstance();
        Date now = nowTime.getTime();
        nowTime.add(Calendar.HOUR, AMOUNT);
        Date expTime = nowTime.getTime();
        String token = Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expTime)
                .signWith(SignatureAlgorithm.HS256, new SecretKeySpec(DatatypeConverter.parseBase64Binary(secret), SignatureAlgorithm.HS256.getJcaName())).compact();
        logger.info("generate token is success:{}", token);
        return token;
    }

    @Override
    public Map<String, Object> getByToken(String token) {
        if (StringUtil.isEmpty(token)) {
            logger.info("token cannot be empty");

            throw new ApplicationException(ResultEnum.TOKEN_ERROR);
        }
        try {
            JSONObject jsonObject = JWSObject.parse(token).getPayload().toJSONObject();
            Map<String, Object> result = Maps.newHashMap();
            result.put(ACCOUNT_ID, jsonObject.get(ACCOUNT_ID));
            result.put(TENANT_ID, jsonObject.get(TENANT_ID));
            result.put(CHANNEL, jsonObject.get(CHANNEL));
            return result;
        } catch (ParseException ex) {
            logger.info("token format error");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.TOKEN_ERROR, "token格式错误token:" + token));
        }
    }

}
