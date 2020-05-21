package com.deepexi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.user.domain.dto.UserAccountRelationDto;
import com.deepexi.user.domain.eo.UserAccountRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface UserAccountRelationMapper extends BaseMapper<UserAccountRelation> {

    List<UserAccountRelation> findList(@Param("eo") UserAccountRelationDto eo);

    int deleteByIds(@Param("pks") String... pks);

    int batchInsert(@Param("eo") List<UserAccountRelationDto> eo);

    int batchUpdate(@Param("eo") List<UserAccountRelationDto> eo);

    /**
     * 获取用户-账号关联实体
     *
     * @param dto
     * @return
     */
    UserAccountRelation getByDto(@Param("dto") UserAccountRelationDto dto, @Param("tenantCode") String tenantCode);
}
