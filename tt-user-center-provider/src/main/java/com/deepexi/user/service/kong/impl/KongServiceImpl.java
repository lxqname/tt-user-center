package com.deepexi.user.service.kong.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.deepexi.user.domain.dto.KongConsumer;
import com.deepexi.user.domain.vo.JwtVo;
import com.deepexi.user.service.kong.KongService;
import com.deepexi.user.utils.HttpClient;
import com.deepexi.util.JsonUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;


/**
 * @author: lizhongbao
 * @Description: kong服务
 * @date: 2019-9-21 16:50:57
 */
@Component
@Service(version = "${demo.service.version}")
public class KongServiceImpl implements KongService {

    public static final String ID = "{id}";
    public static final String KEY = "{key}";
    public static final String CONSUMER_ID = "{consumerId}";
    public static final String CUSTOM_ID = "custom_id";
    private final Logger logger = LoggerFactory.getLogger(KongService.class);

    @Value("${kong.jwt.uri}")
    private String uri;

    @Value("${kong.jwt.consumerPath}")
    private String consumerPath;

    @Value("${kong.jwt.consumerListPath}")
    private String consumerListPath;

    @Value("${kong.jwt.tokenPath}")
    private String tokenPath;

    @Value("${kong.jwt.deleteToken}")
    private String deleteToken;

    @Value("${kong.jwt.tokenUser}")
    private String tokenUser;


    @PostConstruct
    public void init() {
        logger.info("---------------初始化kong配置---------------");
        logger.info("获取consumer地址:{}", uri + consumerPath);
        logger.info("获取consumer列表地址:{}", uri + consumerListPath);
        logger.info("获取token地址:{}", uri + tokenPath);
        logger.info("根据key-id删除token地址:{}", uri + deleteToken);
        logger.info("根据token获取用户信息:{}", uri + tokenUser);
    }

    @Override
    public JwtVo isExistConsumer(String customId) {
        logger.info("测试kong请求 customId[{}] uri[{}] ", customId, uri);
        String res = HttpUtil.get(this.uri + this.consumerPath.replace(ID, customId));
        return JsonUtil.json2Bean(res, JwtVo.class);
    }

    @Override
    public KongConsumer createConsumer(String customId) {
        JSONObject requestBody = new JSONObject();
        requestBody.put(CUSTOM_ID, customId);
        String res = HttpUtil.post(this.uri + this.consumerListPath, requestBody);
        logger.info("consumer create success res = {}", res);
        return JsonUtil.json2Bean(res, KongConsumer.class);
    }

    @Override
    public JwtVo getTokens(String id) {
        String res = HttpUtil.get(this.uri + this.tokenPath.replace(ID, id));
        if (res != null) {
            return JsonUtil.json2Bean(res, JwtVo.class);
        }
        return null;
    }

    @Override
    public void deleteByCustomIdAndId(String customId, String id) {
        HttpClient.delete(this.uri + this.deleteToken.replace(CONSUMER_ID, customId).replace(ID, id));
    }

    @Override
    public Map<String, String> createToken(String id) {
        String url = this.uri + this.tokenPath.replace(ID, id);
        logger.info("生成token 请求的 url{}", url);
        String res = HttpUtil.post(url, Maps.newHashMap());
        logger.info("生成token 返回的的 结果{}", res);
        return JsonUtil.json2Map(res);
    }

    @Override
    public Map<String, String> getUserByToken(String token) {
        String res = HttpUtil.get(this.uri + this.tokenUser.replace(KEY, token));
        return JsonUtil.json2Map(res);
    }


}
