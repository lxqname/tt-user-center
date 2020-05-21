package com.deepexi.user.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.permission.domain.eo.Role;

import java.io.Serializable;
import java.util.List;


/**
 * @author: lizhongbao
 * @Description: 用户列表VO
 * @date: 2019-9-21 16:50:57
 */
@TableName("usc_user")
public class UserListVo implements Serializable {

    /**
     * 员工编号
     */
    private String id;
    /**
     * 员工编号
     */
    private String code;
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 英文名
     */
    private String englishName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别：1=男, 2=女, 0=未知
     */
    private Integer sex;

    /**
     * 账号ID
     */
    private String accountId;

    /**
     * 角色列表
     */
    private List<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

