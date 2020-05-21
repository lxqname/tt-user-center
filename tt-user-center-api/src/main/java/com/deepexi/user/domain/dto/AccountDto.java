package com.deepexi.user.domain.dto;

import com.deepexi.user.domain.eo.Account;

/**
 * @Description 账号传输DTO
 * @Author lizhongbao
 * @Date  2019/9/21
 */
public class AccountDto extends Account {

    private String channel;

    /**
     * 第三方平台用户id
     */
    private String platformUserId;

    /**
     * 第三方平台访问token
     */
    private String accessToken;

    /**
     * 平台类型，0:未知,1:wechat,2:qq,3:aliPay,4:weibo,5:facebook,6:google,7:twitter
     */
    private String type;

    private String token;

    private String verifyCode;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(String platformUserId) {
        this.platformUserId = platformUserId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

}
