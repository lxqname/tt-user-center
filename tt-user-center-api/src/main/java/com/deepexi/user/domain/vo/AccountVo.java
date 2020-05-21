package com.deepexi.user.domain.vo;

import com.deepexi.user.domain.eo.Account;


/**
 * @author: lizhongbao
 * @Description: 账号VO
 * @date: 2019-9-21 16:50:57
 */
public class AccountVo extends Account {

    /**
     * 第三方登录
     */
    private String nickname;

    private String avatar;

    //jwt生成的token
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
