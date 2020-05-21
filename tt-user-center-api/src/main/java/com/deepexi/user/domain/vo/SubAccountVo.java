package com.deepexi.user.domain.vo;

import com.deepexi.permission.domain.eo.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author: lizhongbao
 * @Description: 子账号VO
 * @date: 2019-9-21 16:50:57
 */
public class SubAccountVo implements Serializable {

    private static final long serialVersionUID = -1;

    /** 账号ID */
    private String id;
    /** 用户名 */
    private String username;
    /** 手机号码 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 姓名 */
    private String name;
    /** 性别：1=男, 2=女, 0=未知 */
    private Integer sex;
    /** 账号状态：0=启用，1=禁用 */
    private Integer status;
    /** 账号类型：1=主账号，2=子账号 */
    private Integer level;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 编号
     */
    private String code;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
