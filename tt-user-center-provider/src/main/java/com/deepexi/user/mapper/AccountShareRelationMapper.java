package com.deepexi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.user.domain.dto.AccountShareRelationDto;
import com.deepexi.user.domain.eo.AccountShareRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 账号分享关系 Mapper
 * @Author lizhongbao
 * @Date 2019/11/7
 */
@Mapper
public interface AccountShareRelationMapper extends BaseMapper<AccountShareRelation> {

    /**
     * @Description 检查是否首次记录关系
     * @Author lizhongbao
     * @Date 2019/11/7
     * @Params [targetAccountId]
     * @Return java.lang.Boolean
     */
    Integer getCountByTargetAccountId(@Param("targetAccountId") String targetAccountId);

    /**
     * @Description 检查是否首次记录关系
     * @Author lizhongbao
     * @Date 2019/11/7
     * @Params [targetAccountId]
     * @Return java.lang.Boolean
     */
    AccountShareRelation getByDto(@Param("dto") AccountShareRelationDto dto);

}
