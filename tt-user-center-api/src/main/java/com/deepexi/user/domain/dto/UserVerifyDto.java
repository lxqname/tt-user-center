package com.deepexi.user.domain.dto;

import java.io.Serializable;

/**
 * @Description 用户校验DTO
 * @Author lizhongbao
 * @Date 2019-9-11 15:36:30
 * @Version 1.0
 **/
public class UserVerifyDto implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * 编号
     */
    private String code;

    /**
     * 排除ID
     */
    private String excludeId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExcludeId() {
        return excludeId;
    }

    public void setExcludeId(String excludeId) {
        this.excludeId = excludeId;
    }
}
