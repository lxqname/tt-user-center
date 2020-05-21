package com.deepexi.user.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.util.*;
import java.io.Serializable;
// import io.swagger.annotations.ApiModel;
// import io.swagger.annotations.ApiModelProperty;


/**
 * @author lizhongbao
 * @desc account
 */
// @ApiModel(description = "账号表")
@TableName("usc_account")
public class Account extends SuperEntity {

    // @ApiModelProperty(value = "用户名")
    private String username;
    // @ApiModelProperty(value = "密码")
    private String password;
    // @ApiModelProperty(value = "手机号码")
    private String phone;
    // @ApiModelProperty(value = "邮箱")
    private String email;
    // @ApiModelProperty(value = "姓名")
    private String name;
    // @ApiModelProperty(value = "性别：1=男, 2=女, 0=未知")
    private Integer sex;
    // @ApiModelProperty(value = "注册时间")
    private Date registerTime;
    // @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;
    // @ApiModelProperty(value = "账号平台类型：1=内部账号，2=C端账号，3=B端账号")
    private Integer platformType;
    // @ApiModelProperty(value = "账号状态：0=启用，1=禁用")
    private Integer status;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

