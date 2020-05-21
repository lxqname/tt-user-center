package com.deepexi.user.domain.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName LoginDto
 * @Description 登录DTO
 * @Author lizhongbao
 * @Date 2019/9/10 16:30
 * @Version 1.0
 **/
public class LoginDto implements Serializable {

    private static final long serialVersionUID = -1;

    @NotNull(message = "用户名不可为空")
    private String username;

    @NotNull(message = "密码不可为空")
    private String password;

    /**
     * 账号来源
     */
    private String channel;

    /**
     * 账号平台类型：1=内部账号，2=C端账号，3=B端账号
     */
    private Integer platformType;

    /**
     * 账号ID
     */
    private String accountId;

    /**
     * IP地址
     */
    private String address;

    /**
     * 登录的浏览器
     */
    private String browser;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
