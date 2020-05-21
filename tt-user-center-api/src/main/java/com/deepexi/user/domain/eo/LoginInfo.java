package com.deepexi.user.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.util.*;
import java.io.Serializable;
// import io.swagger.annotations.ApiModel;
// import io.swagger.annotations.ApiModelProperty;


/**
 * @author lizhongbao
 * @desc login_info
 */
// @ApiModel(description = "登录日志表")
@TableName("usc_login_info")
public class LoginInfo extends SuperEntity {

    // @ApiModelProperty(value = "账号ID")
    private String accountId;
    // @ApiModelProperty(value = "登录的浏览器")
    private String browser;
    // @ApiModelProperty(value = "ip地址")
    private String address;
    // @ApiModelProperty(value = "来源")
    private String source;
    // @ApiModelProperty(value = "最后登录时间")
    private Date loginTime;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}

