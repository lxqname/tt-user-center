package com.deepexi.user.service;

import com.deepexi.user.domain.dto.AccountDto;
import com.deepexi.user.domain.dto.LoginDto;
import com.deepexi.user.domain.vo.UserListVo;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.user.domain.eo.User;
import com.deepexi.user.domain.dto.UserDto;

import java.util.*;

public interface UserService {

    PageBean<UserListVo> findPage(UserDto eo, Integer page, Integer size);

    List<UserListVo> findAll(UserDto eo);

    User detail(String pk);

    Boolean update(String pk, UserDto eo);

    Boolean create(UserDto eo);

    Boolean delete(String... pk);

    /**
     * 运营端登录
     *
     * @param dto
     * @return
     */
    AccountDto login(LoginDto dto);
}