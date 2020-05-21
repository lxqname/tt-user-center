package com.deepexi.user.service;

import com.deepexi.user.domain.dto.AccountDto;
import com.deepexi.user.domain.dto.LoginDto;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.user.domain.eo.UserAccountRelation;
import com.deepexi.user.domain.dto.UserAccountRelationDto;

import java.util.*;

public interface UserAccountRelationService {

    PageBean<UserAccountRelation> findPage(UserAccountRelationDto eo, Integer page, Integer size);

    List<UserAccountRelation> findAll(UserAccountRelationDto eo);

    UserAccountRelation detail(String pk);

    Boolean update(String pk, UserAccountRelation eo);

    Boolean create(UserAccountRelation eo);

    Boolean delete(String... pk);

    /**
     * 获取用户-账号关联实体
     *
     * @param dto
     * @return
     */
    UserAccountRelation getByDto(UserAccountRelationDto dto);
}