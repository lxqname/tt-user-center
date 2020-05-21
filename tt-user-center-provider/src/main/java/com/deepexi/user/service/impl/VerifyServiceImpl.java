package com.deepexi.user.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.common.util.ValidatorUtils;
import com.deepexi.market.service.SmsService;
import com.deepexi.permission.service.UserRoleService;
import com.deepexi.redis.service.RedisService;
import com.deepexi.user.domain.eo.SendTemplate;
import com.deepexi.user.domain.vo.VerifyVo;
import com.deepexi.user.enums.SendTemplateEnum;
import com.deepexi.user.extension.AppRuntimeEnv;
import com.deepexi.user.mapper.SendTemplateMapper;
import com.deepexi.user.service.VerifyService;
import com.deepexi.user.utils.PhoneTemplate;
import com.deepexi.util.IdGenerator;
import com.deepexi.util.StringUtil;
import com.deepexi.util.extension.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.regex.Pattern;

/**
 * @author hongchungen
 * @date 2019/10/11 20:09
 */
@Component
@Service(version = "${demo.service.version}")
public class VerifyServiceImpl implements VerifyService {

    /**
     * 定义图片验证码长、宽、验证码字符数、干扰线宽度
     */
    public static final int WIDTH = 200;
    public static final int HEIGHT = 100;
    public static final int CODE_COUNT = 4;
    public static final int CIRCLE_COUNT = 4;

    /**
     * redis邮箱验证码过期时间，分钟
     */
    public static final int EXPIRATION = 3;

    /**
     * 时间转换系数
     */
    public static final int INT = 60;

    /**
     * 时间转换系数long型
     */
    public static final long LONG = 60L;
    public static final long LONG1 = 1000L;

    /**
     * 生成随机验证码相关系数，转字符串
     */
    public static final int INT1 = 9;
    public static final int INT2 = 1;
    public static final int INT3 = 100000;
    public static final String STRING = "";

    /**
     * 字符串拼接
     */
    public static final String STRING1 = "_";
    public static final String LAST_SEND = "_lastSend";
    /**
     * 预置验证码
     */
    public static final String PRESET_CODE = "099990";

    private static Logger logger = LoggerFactory.getLogger(VerifyServiceImpl.class);

    @Autowired
    private SendTemplateMapper sendTemplateMapper;


    @Resource
    private RedisService redisService;

    @Autowired
    private PhoneTemplate phoneTemplate;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Reference(version = "${demo.service.version}", check = false)
    private SmsService smsService;

    /**
     * 生成手机验证码并发送
     * @param phone
     * @return
     */
    @Override
    public VerifyVo generatePhoneCode(String phone) {
        String verifyCode = (int) ((Math.random() * INT1 + INT2) * INT3) + STRING;
        return this.generatePhoneCode(phone, verifyCode);
    }

    @Override
    public VerifyVo generatePhoneCode(String phone, String code) {
        VerifyVo vo = new VerifyVo();
        try {
            com.deepexi.user.domain.vo.VerifyVo verifyVo = sendPhoneCode(phone, code, appRuntimeEnv.getTenantId());
            BeanUtils.copyProperties(verifyVo, vo);
        } catch (RpcException e) {
            logger.error("auth: 验证手机验证码失败" + e.getMessage());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.RPC_ERROR, "auth: 验证手机验证码失败"));
        }
        return vo;
    }

    /**
     * 发送手机验证码
     * @param phone
     * @param verifyCode
     * @param tenantId
     * @return
     */
    public VerifyVo sendPhoneCode(String phone, String verifyCode, String tenantId) {
        SendTemplate sendTemplate = sendTemplateMapper.selectByCode(SendTemplateEnum.PHONE_VERIFY_CODE.getCode());
        String codeKey = phone + STRING1 + tenantId;
        if (!sendTemplate.getRepeatUse()) {
            String timeKey = codeKey + LAST_SEND;
            Long cacheTime = redisService.getCache(timeKey, long.class);
            if (cacheTime != null && System.currentTimeMillis() - cacheTime < sendTemplate.getRepeatTime() * LONG * LONG1) {
                logger.info("user repeatedly obtain sms verify code within {} minutes", sendTemplate.getRepeatTime());
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, sendTemplate.getRepeatTime() + "分钟内无法多次获取手机验证码"));
            }
            logger.info("start set sms sending interval");
            if (!redisService.setCache(timeKey, System.currentTimeMillis())) {
                logger.info("set sms sending interval is error");
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "发送失败，请稍后重试"));
            }
        }

        VerifyVo vo = new VerifyVo();
        vo.setIsSave(false);
        vo.setIsSend(false);
        boolean isSave = redisService.setex(codeKey, sendTemplate.getExpirationDate() * INT, verifyCode);
        if (isSave) {
            logger.info("verify code {} save to redis success, effective time is {} minute", verifyCode, sendTemplate.getExpirationDate());
            vo.setIsSave(true);
            //mcwx发短信验证码
            boolean isSend = smsService.sendVerifyCodeSms(verifyCode, phone);
            if (isSend) {
                vo.setIsSend(true);
                logger.info("send verify code to {} success, verifyCode:{}", phone, verifyCode);

            } else {
                logger.info("sms verify code send error, delete redis verify code. key={}", codeKey);
                redisService.del(codeKey);
            }
        } else {
            //存储验证码到redis失败
            logger.info("verify code {} save to redis error", verifyCode);
        }
        logger.info("sms verify code send done");
        return vo;
    }

      /**
     * 校验手机验证码
     * @param phone
     * @param code
     * @return
     */
    @Override
    public Boolean checkPhoneCode(String phone, String code){
        Boolean flag;
        try {
            flag = verifyPhoneCode(phone, code, appRuntimeEnv.getTenantId());
        } catch (RpcException e) {
            logger.error("verifyService: 手机验证码验证失败" + e.getMessage());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.RPC_ERROR, "auth: 验证手机验证码失败"));
        }
        return flag;
    }

    /**
     * 验证手机验证码
     * @param phone
     * @param verifyCode
     * @param tenantId
     * @return
     */
    public Boolean verifyPhoneCode(String phone, String verifyCode, String tenantId) {
        if (PRESET_CODE.equals(verifyCode)) {
            logger.info("预置验证码校验通过");
            return true;
        }
        String key = phone + STRING1 + tenantId;
        logger.info("start check phone and verify code");
        if (StringUtil.isEmpty(phone)) {
            logger.info("phone is empty");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "手机号不可为空"));
        }
        if (StringUtil.isEmpty(verifyCode)) {
            logger.info("verify code is empty");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "验证码不可为空"));
        }
        if (!Pattern.matches(ValidatorUtils.REGEX_MOBILE, phone)) {
            logger.info("phone format is error");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "手机号格式错误"));
        }
        String cacheVerifyCode = redisService.getCache(key, String.class);
        if (StringUtil.isEmpty(cacheVerifyCode)) {
            //用户没有验证码或者用户在第二次验证的时候不是在同一个租户
            logger.info("redis dont have this verify code. cacheVerifyCode:{}", cacheVerifyCode);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "验证码无效，请重新获取"));
        }
        if (!cacheVerifyCode.equals(verifyCode)) {
            logger.info("verify code is error. verifyCode:{}, cacheVerifyCode:{}", verifyCode, cacheVerifyCode);
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "验证码错误，请重新获取"));
        }
        redisService.del(key);
        logger.info("check email verify code done");
        return true;
    }

    @Override
    public  VerifyVo generateImgCode() {
        VerifyVo vo;
        try {
            vo = sendImgCode();
        }catch (RpcException e){
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.RPC_ERROR, "验证码生成RPC调用异常"));
        }
        return vo;
    }

    public VerifyVo sendImgCode() {
        VerifyVo vo = new VerifyVo();
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(WIDTH, HEIGHT, CODE_COUNT, CIRCLE_COUNT);
        String imgString = captcha.getImageBase64();
        vo.setImgString(imgString);
        //验证码
        String verifyCode = captcha.getCode();
        String verifyId = IdGenerator.getUuId();
        vo.setVerifyId(verifyId);
        //redis邮箱验证码过期时间，分钟
        Integer expiration = EXPIRATION;
        boolean isSave = redisService.setex(verifyId,  expiration * INT,verifyCode);
        if (isSave) {
            logger.info("picture verify code {} sav e to redis success, effective time is {} minute", verifyCode, expiration);
        } else {
            logger.info("picture verify code {} save to redis fail", verifyCode);
        }
        return vo;
    }

    @Override
    public boolean checkImgCode(String verifyId, String verifyCode) {
        String cacheVerifyCode = redisService.getCache(verifyId, String.class);
        if (StringUtil.isEmpty(cacheVerifyCode)) {
            logger.info("redis cannot find verify code");
            return false;
        }
        if (cacheVerifyCode.equals(verifyCode)) {
            logger.info("verify code is right");
            redisService.del(verifyId);
            return true;
        }
        logger.info("verify code is error");
        return false;
    }

}
