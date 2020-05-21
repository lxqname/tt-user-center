package com.deepexi.user.service.kong;

import com.deepexi.user.domain.eo.Account;

import java.util.Map;

/**
 * @author: lizhongbao
 * @Description: kong上登录/注册服务
 * @date: 2019-9-21 16:50:57
 */
public interface KongLoginService {

    /**
     * kong上生成token，返回kong的内容
     *
     * @param account 用户信息
     * @param channel 用户渠道
     * @return jwt凭证
     */
    Map commonGenerateToken(Account account, String channel);


    /**
     * kong上生成token
     *
     * @param account 用户信息
     * @param channel 用户渠道
     * @return jwt令牌
     */
    String generateToken(Account account, String channel);

    /**
     * 根据token获取用户相关信息
     *
     * @param token
     * @return
     */
    Map getByToken(String token);


}
