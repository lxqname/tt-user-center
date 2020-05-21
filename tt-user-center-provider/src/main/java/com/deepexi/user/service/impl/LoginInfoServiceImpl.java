package com.deepexi.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.user.domain.dto.LoginDto;
import com.deepexi.user.service.LoginInfoService;
import com.deepexi.user.domain.eo.LoginInfo;
import com.deepexi.user.domain.dto.LoginInfoDto;
import com.deepexi.user.extension.AppRuntimeEnv;
import com.deepexi.user.mapper.LoginInfoMapper;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.deepexi.util.BeanPowerHelper;

import java.util.Date;
import java.util.List;


@Component
@Service(version = "${demo.service.version}")
public class LoginInfoServiceImpl implements LoginInfoService {

    private static final Logger logger = LoggerFactory.getLogger(LoginInfoServiceImpl.class);
    public static final String USER_AGENT = "User-Agent";

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Override
    public PageBean findPage(LoginInfoDto eo, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<LoginInfo> list = loginInfoMapper.findList(eo);
        return new PageBean<>(list);
    }

    @Override
    public List<LoginInfo> findAll(LoginInfoDto eo) {
        List<LoginInfo> list = loginInfoMapper.findList(eo);
        return list;
    }

    @Override
    public LoginInfo detail(String pk) {
        return loginInfoMapper.selectById(pk);
    }

    @Override
    public Boolean create(LoginInfoDto eo) {
        int result = loginInfoMapper.insert(BeanPowerHelper.mapPartOverrider(eo, LoginInfo.class));
        return result > 0;
    }

    @Override
    public Boolean update(String pk, LoginInfoDto eo) {
        eo.setId(pk);
        int result = loginInfoMapper.updateById(BeanPowerHelper.mapPartOverrider(eo, LoginInfo.class));
        return result > 0;
    }

    @Override
    public Boolean delete(String... pk) {
        int result = loginInfoMapper.deleteByIds(pk);
        return result > 0;
    }

    @Override
    public Boolean create(LoginDto dto) {
        LoginInfoDto loginInfoDto = new LoginInfoDto();
        loginInfoDto.setAccountId(dto.getAccountId());
        loginInfoDto.setAddress(dto.getAddress());
        loginInfoDto.setBrowser(dto.getBrowser());
        loginInfoDto.setSource(dto.getChannel());
        loginInfoDto.setLoginTime(new Date());
        return this.create(loginInfoDto);
    }

}