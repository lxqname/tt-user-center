package com.deepexi.user.domain.vo;

import java.io.Serializable;

/**
 * @author: lizhongbao
 * @Description: 校验VO
 * @date: 2019-9-21 16:50:57
 */
public class VerifyVo implements Serializable {

    private static final long serialVersionUID = -7162920228029663149L;

    /**
     * 是否保存到redis
     */
    private boolean isSave;

    /**
     * 是否发送成功
     */
    private boolean isSend;

    /**
     * 用户获取图片验证码标识
     */
    private String verifyId;

    /**
     * 图片字符串
     */
    private String imgString;

    /**
     * 验证链接
     */
    private String link;

    public boolean getIsSave() {
        return isSave;
    }

    public void setIsSave(boolean isSave) {
        this.isSave = isSave;
    }

    public boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(boolean isSend) {
        this.isSend = isSend;
    }

    public String getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(String verifyId) {
        this.verifyId = verifyId;
    }

    public String getImgString() {
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
