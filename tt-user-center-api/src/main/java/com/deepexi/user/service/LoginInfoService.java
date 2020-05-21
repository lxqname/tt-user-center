package com.deepexi.user.service;

import com.deepexi.user.domain.dto.LoginDto;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.user.domain.eo.LoginInfo;
import com.deepexi.user.domain.dto.LoginInfoDto;

import java.util.*;

public interface LoginInfoService {

    PageBean<LoginInfo> findPage(LoginInfoDto eo, Integer page, Integer size);

    List<LoginInfo> findAll(LoginInfoDto eo);

    LoginInfo detail(String pk);

    Boolean update(String pk, LoginInfoDto eo);

    Boolean create(LoginInfoDto eo);

    Boolean delete(String... pk);

    /**
     * @Description 创建登录日志
     * @Author lizhongbao
     * @Date 2019/10/30
     * @Params [accountId]
     * @Return java.lang.Boolean
     */
    Boolean create(LoginDto dto);
}