package com.deepexi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.user.domain.dto.LoginInfoDto;
import com.deepexi.user.domain.eo.LoginInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface LoginInfoMapper extends BaseMapper<LoginInfo> {

    List<LoginInfo> findList(@Param("eo") LoginInfoDto eo);

    int deleteByIds(@Param("pks") String... pks);

    int batchInsert(@Param("eo") List<LoginInfoDto> eo);

    int batchUpdate(@Param("eo") List<LoginInfoDto> eo);
}
