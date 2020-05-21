package com.deepexi.user.domain.eo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author hongchungen
 * @date 2019/10/12 10:04
 */
@TableName("usc_send_template")
public class SendTemplate implements Serializable {
    private static final long serialVersionUID = 4137851623649874981L;

    private String id;

    private String code;

    private String subject;

    private String content;

    private String remark;

    private Integer expirationDate;

    private Integer repeatTime;

    private boolean repeatUse;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Integer expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(Integer repeatTime) {
        this.repeatTime = repeatTime;
    }

    public boolean getRepeatUse() {
        return repeatUse;
    }

    public void setRepeatUse(boolean repeatUse) {
        this.repeatUse = repeatUse;
    }

}
