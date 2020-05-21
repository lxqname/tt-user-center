package com.deepexi.user.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.util.*;
import java.io.Serializable;
// import io.swagger.annotations.ApiModel;
// import io.swagger.annotations.ApiModelProperty;


/**
 * @author lizhongbao
 * @desc user
 */
// @ApiModel(description = "用户表")
@TableName("usc_user")
public class User extends SuperEntity {

    // @ApiModelProperty(value = "员工编号")
    private String code;
    // @ApiModelProperty(value = "用户名")
    private String username;
    // @ApiModelProperty(value = "姓名")
    private String name;
    // @ApiModelProperty(value = "英文名")
    private String englishName;
    // @ApiModelProperty(value = "手机号")
    private String phone;
    // @ApiModelProperty(value = "邮箱")
    private String email;
    // @ApiModelProperty(value = "性别：1=男, 2=女, 0=未知")
    private Integer sex;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}

