package com.deepexi.user.service.kong;

import com.deepexi.user.domain.dto.KongConsumer;
import com.deepexi.user.domain.vo.JwtVo;

import java.util.Map;

/**
 * @Description 提供Kong服务的接口
 * @Author shaowin
 * @Date 2018-11-29 14:03
 **/
public interface KongService {

    /**
     * 是否存在消费者
     *
     * @param customId
     * @return
     */
    JwtVo isExistConsumer(String customId);

    /**
     * 创建消费者
     *
     * @param customId
     * @return
     */
    KongConsumer createConsumer(String customId);

    /**
     * 获取所有JWT凭证
     *
     * @param id
     * @return
     */
    JwtVo getTokens(String id);

    /**
     * 删除JWT凭证
     *
     * @param customId
     * @param id
     */
    void deleteByCustomIdAndId(String customId, String id);

    /**
     * 创建JWT凭证
     *
     * @param id
     * @return
     */
    Map<String, String> createToken(String id);

    /**
     * 0.11.2之后新增的接口
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    Map<String, String> getUserByToken(String token);
}
