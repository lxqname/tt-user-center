package com.deepexi.user.enums;

/**
 * @author honghvungen
 * @date 2019/10/14 14:26
 */
public enum SendTemplateEnum {

    /**
     * 租户注册，平台审核后，租户如果填了邮箱就会发送审核通过，已开通租户的邮件信息给客户。
     */
    PASS_APPROVAL("pass_approval", "没有参数"),
    /**
     * 发送邮箱注册验证码
     */
    EMAIL_VERIFY_CODE("email_verify_code", "两个参数，1：验证码，2：有效时间"),

    /**
     * 发送邮箱验证链接
     */
    EMAIL_LINK_VERIFYTION_CODE("email_verify_link_code", "两个参数，1：验证链接，2：有效时间"),
    /**
     * 发送手机注册验证码
     */
    PHONE_VERIFY_CODE("phone_verify_code", "两个参数，1：验证码，2：有效时间");

    private String code;
    private String paramDescribe;

    SendTemplateEnum(String code, String paramDescribe) {
        this.code = code;
        this.paramDescribe = paramDescribe;
    }

    public String getCode() {
        return code;
    }

    public String getParamDescribe() {
        return paramDescribe;
    }
}
