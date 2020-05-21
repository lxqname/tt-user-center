package com.deepexi.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.user.service.UserAccountRelationService;
import com.deepexi.user.domain.eo.UserAccountRelation;
import com.deepexi.user.domain.dto.UserAccountRelationDto;
import com.deepexi.user.extension.AppRuntimeEnv;
import com.deepexi.user.mapper.UserAccountRelationMapper;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.deepexi.util.BeanPowerHelper;

import java.util.List;


@Component
@Service(version = "${demo.service.version}")
public class UserAccountRelationServiceImpl implements UserAccountRelationService {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountRelationServiceImpl.class);

    @Autowired
    private UserAccountRelationMapper userAccountRelationMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Override
    public PageBean findPage(UserAccountRelationDto eo, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<UserAccountRelation> list = userAccountRelationMapper.findList(eo);
        return new PageBean<>(list);
    }

    @Override
    public List<UserAccountRelation> findAll(UserAccountRelationDto eo) {
        List<UserAccountRelation> list = userAccountRelationMapper.findList(eo);
        return list;
    }

    @Override
    public UserAccountRelation detail(String pk) {
        return userAccountRelationMapper.selectById(pk);
    }

    @Override
    public Boolean create(UserAccountRelation eo) {
        int result = userAccountRelationMapper.insert(eo);
        return result > 0;
    }

    @Override
    public Boolean update(String pk, UserAccountRelation eo) {
        eo.setId(pk);
        int result = userAccountRelationMapper.updateById(eo);
        return result > 0;
    }

    @Override
    public Boolean delete(String... pk) {
        int result = userAccountRelationMapper.deleteByIds(pk);
        return result > 0;
    }

    @Override
    public UserAccountRelation getByDto(UserAccountRelationDto dto) {
        return userAccountRelationMapper.getByDto(dto, appRuntimeEnv.getTenantId());
    }

}