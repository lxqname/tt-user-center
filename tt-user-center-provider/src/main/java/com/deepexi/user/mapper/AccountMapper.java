package com.deepexi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.user.domain.dto.AccountDto;
import com.deepexi.user.domain.dto.AccountQueryDto;
import com.deepexi.user.domain.dto.AccountVerifyDto;
import com.deepexi.user.domain.eo.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    List<Account> findList(@Param("dto") AccountQueryDto dto, @Param("tenantCode") String tenantCode);

    int deleteByIds(@Param("pks") String... pks);

    int batchInsert(@Param("eo") List<AccountDto> eo);

    int batchUpdate(@Param("eo") List<AccountDto> eo);

    /**
     * 根据用户名、企业id查询未删除用户
     *
     * @param dto
     * @param tenantCode
     * @return
     */
    Account getByDto(@Param("dto") AccountVerifyDto dto, @Param("tenantCode") String tenantCode);

    /**
     * 根据用户名、企业id查询未删除用户，不校验手机号
     *
     * @param dto
     * @param tenantCode
     * @return
     */
    Account getByDtoExcludePhone(@Param("dto") AccountVerifyDto dto, @Param("tenantCode") String tenantCode);

    /**
     * 更新用户最后登录时间
     *
     * @param id
     * @return
     */
    int updateLastLoginTime(@Param("id") String id);

    /**
     * @Description 根据accountId获取username
     * @Author lizhongbao
     * @Date 2019/9/24
     * @Params [id]
     * @Return java.lang.String
     */
    String getUsernameById(@Param("id") String id);

    /**
     * @Description 修改账号状态
     * @Author lizhongbao
     * @Date 2019/9/29
     * @Params [id, status]
     * @Return int
     */
    int updateStatus(@Param("id") String id, @Param("status") Integer status);

    /**
     * @Description 主账号-分页查询
     * @Author lizhongbao
     * @Date 2019/10/12
     * @Params [dto, tenantCode]
     * @Return java.util.List<com.deepexi.user.domain.eo.Account>
     */
    List<Account> listPrimaryAccount(@Param("dto") AccountQueryDto dto, @Param("tenantCode") String tenantCode);
}
