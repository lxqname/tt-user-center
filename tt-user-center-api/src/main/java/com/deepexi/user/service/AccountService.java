package com.deepexi.user.service;

import com.deepexi.user.domain.dto.AccountQueryDto;
import com.deepexi.user.domain.dto.LoginDto;
import com.deepexi.user.domain.vo.AccountVerifyVo;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.user.domain.eo.Account;
import com.deepexi.user.domain.dto.AccountDto;

import java.util.*;

public interface AccountService {

    PageBean<Account> findPage(AccountQueryDto dto, Integer page, Integer size);

    List<Account> findAll(AccountQueryDto dto);

    Account detail(String pk);

    Boolean update(String pk, AccountDto eo, String tenantId);

    Boolean delete(String... pk);

    /**
     * 登录
     *
     * @param dto
     * @param tenantId
     * @return
     */
    AccountDto login(LoginDto dto, String tenantId);

    /**
     * 注册
     *
     * @param dto
     * @param tenantId
     * @return
     */
    Account register(AccountDto dto, String tenantId);

    /**
     * 根据token获取登录用户信息
     *
     * @param token
     * @return
     */
    Map getLoginUserByToken(String token);

    /**
     * 根据token获取登录账号ID
     *
     * @param token
     * @return
     */
    String getLoginAccountIdByToken(String token);

    /**
     * 根据token获取登录账号信息
     *
     * @param token
     * @return
     */
    Account getLoginAccountByToken(String token);

    /**
     * @Description 根据accountId获取username
     * @Author lizhongbao
     * @Date 2019/9/24
     * @Params [id]
     * @Return java.lang.String
     */
    String getUsernameById(String id);

    /**
     * @Description 校验账号信息，排除手机号
     * @Author lizhongbao
     * @Date 2019/9/27
     * @Params [dto]
     * @Return com.deepexi.user.domain.vo.AccountVerifyVo
     */
    AccountVerifyVo saveVerifyExcludePhone(AccountDto dto);

    /**
     * @Description 修改账号状态
     * @Author lizhongbao
     * @Date 2019/9/29
     * @Params [id, status]
     * @Return int
     */
    Boolean updateStatus(String id, Integer status);

    /**
     * @Description 主账号-分页查询
     * @Author lizhongbao
     * @Date 2019/10/12
     * @Params [dto, page, size]
     * @Return com.deepexi.util.pageHelper.PageBean<com.deepexi.user.domain.eo.Account>
     */
    PageBean<Account> pagePrimaryAccount(AccountQueryDto dto, Integer page, Integer size);
}