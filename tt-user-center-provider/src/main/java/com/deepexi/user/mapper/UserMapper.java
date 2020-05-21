package com.deepexi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.user.domain.dto.UserDto;
import com.deepexi.user.domain.dto.UserVerifyDto;
import com.deepexi.user.domain.eo.User;
import com.deepexi.user.domain.vo.UserListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<UserListVo> findList(@Param("eo") UserDto eo, @Param("tenantCode") String tenantCode);

    int deleteByIds(@Param("pks") String... pks);

    int batchInsert(@Param("eo") List<UserDto> eo);

    int batchUpdate(@Param("eo") List<UserDto> eo);

    /**
     * 根据code查询user
     *
     * @param dto
     * @param tenantCode
     * @return
     */
    User getByDto(@Param("dto") UserVerifyDto dto, @Param("tenantCode") String tenantCode);
}
