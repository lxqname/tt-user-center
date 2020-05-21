package com.deepexi.user.service;

import com.deepexi.user.domain.dto.AccountShareRelationDto;
import com.deepexi.user.domain.eo.AccountShareRelation;

/**
 * @Description 账号分享关系 服务
 * @Author lizhongbao
 * @Date 2019/11/7
 */
public interface AccountShareRelationService {

    /**
     * @Description 新增账号分享关系
     * @Author lizhongbao
     * @Date 2019/11/7
     * @Params [dto]
     * @Return java.lang.Boolean
     */
    Boolean create(AccountShareRelationDto dto);

    /**
     * @Description 检查是否首次记录关系
     * @Author lizhongbao
     * @Date 2019/11/7
     * @Params [targetAccountId]
     * @Return java.lang.Boolean
     */
    Boolean checkFirstRelation(String targetAccountId);

    /**
     * @Description 检查是否首次记录关系
     * @Author lizhongbao
     * @Date 2019/11/7
     * @Params [targetAccountId]
     * @Return java.lang.Boolean
     */
    AccountShareRelation getByDto(AccountShareRelationDto dto);
}