package com.deepexi.user.domain.dto;

import java.io.Serializable;

/**
 * @Description 账号校验DTO
 * @Author lizhongbao
 * @Date 2019-9-11 11:13:15
 * @Version 1.0
 **/
public class AccountVerifyDto implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * 账号平台类型：1=内部账号，2=C端账号，3=B端账号
     */
    private Integer platformType;

    /**
     * 用户名/手机号/邮箱
     */
    private String username;

    /**
     * 排除ID
     */
    private String excludeId;

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExcludeId() {
        return excludeId;
    }

    public void setExcludeId(String excludeId) {
        this.excludeId = excludeId;
    }
}
