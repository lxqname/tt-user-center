package com.deepexi.user.domain.dto;

import java.util.Date;
import java.io.Serializable;

/**
 * @author lizhongbao
 * @desc account_tree
 */
public class AccountTreeDto implements Serializable {

    private static final long serialVersionUID = -1;

    private String id;

    private String pAccountId;

    private String topAccountId;

    private String code;

    private Integer level;

    private String tenantCode;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;

    private Integer version;

    private Boolean dr;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setPAccountId(String pAccountId) {
        this.pAccountId = pAccountId;
    }

    public String getPAccountId() {
        return this.pAccountId;
    }

    public void setTopAccountId(String topAccountId) {
        this.topAccountId = topAccountId;
    }

    public String getTopAccountId() {
        return this.topAccountId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getTenantCode() {
        return this.tenantCode;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setDr(Boolean dr) {
        this.dr = dr;
    }

    public Boolean getDr() {
        return this.dr;
    }
}

