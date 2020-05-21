package com.deepexi.user.domain.vo;


import com.deepexi.common.enums.ResultEnum;

import java.io.Serializable;


/**
 * @author: lizhongbao
 * @Description: 账号VO
 * @date: 2019-9-21 16:50:57
 */
public class AccountVerifyVo implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * 返回枚举
     */
    private ResultEnum resultEnum;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 是否校验成功，true代表成功
     */
    private Boolean success;

    public AccountVerifyVo(ResultEnum resultEnum, String msg, Boolean success) {
        this.resultEnum = resultEnum;
        this.msg = msg;
        this.success = success;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
