package com.deepexi.user.domain.dto;

import javax.ws.rs.QueryParam;
import java.io.Serializable;
import java.util.List;

/**
 * @Description 部门子账号DTO
 * @Author lizhongbao
 * @Date 2019/9/27
 */
public class SubAccountQueryDto implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * 主账号ID
     */
    private String primaryAccountId;

    /**
     * 账号ID集合
     */
    private List<String> ids;

    /**
     * 账号平台类型：1=内部账号，2=C端账号，3=B端账号
     */
    private Integer platformType;

    /**
     * 用户名
     */
    @QueryParam("username")
    private String username;

    /**
     * 手机号
     */
    @QueryParam("phone")
    private String phone;

    /**
     * 账号状态：0=启用，1=禁用
     */
    @QueryParam("status")
    private Integer status;

    /**
     * 平台类型：0=运营，1=商户，2=推广，3=商户h5，4=推广h5
     */
    @QueryParam("type")
    private String type;

    public String getPrimaryAccountId() {
        return primaryAccountId;
    }

    public void setPrimaryAccountId(String primaryAccountId) {
        this.primaryAccountId = primaryAccountId;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }
}
