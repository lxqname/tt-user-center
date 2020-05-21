package com.deepexi.user.domain.dto;

import javax.ws.rs.QueryParam;
import java.io.Serializable;

/**
 * @Description 账号查询DTO
 * @Author lizhongbao
 * @Date 2019/9/20 18:58
 **/
public class AccountQueryDto implements Serializable {

    private static final long serialVersionUID = -1;

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
     * 账号状态：0=启用，1=禁用
     */
    @QueryParam("status")
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
