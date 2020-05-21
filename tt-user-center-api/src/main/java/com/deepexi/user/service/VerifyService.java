package com.deepexi.user.service;

import com.deepexi.user.domain.vo.VerifyVo;

/**
 * @author hongchungen
 * @date 2019/10/11 19:37
 */
public interface VerifyService {
    /**
     * 生成手机验证码并发送
     * @param phone
     * @return
     */
    VerifyVo generatePhoneCode(String phone);

    /**
     * 生成手机验证码并发送
     * @param phone
     * @param code
     * @return
     */
    VerifyVo generatePhoneCode(String phone, String code);

    /**
     * 校验手机验证码
     * @param phone
     * @param code
     * @return
     */
    Boolean checkPhoneCode(String phone, String code);

    /**
     * 生成图片验证码并返回当前用户标识
     * @return
     */
    VerifyVo generateImgCode();

    /**
     * 检验图片验证码
     * @param verifyId
     * @param verifyCode
     * @return
     */
    boolean checkImgCode(String verifyId, String verifyCode);

}
