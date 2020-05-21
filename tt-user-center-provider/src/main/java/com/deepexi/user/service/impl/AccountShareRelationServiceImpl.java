package com.deepexi.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.user.domain.dto.*;
import com.deepexi.user.domain.eo.AccountShareRelation;
import com.deepexi.user.extension.AppRuntimeEnv;
import com.deepexi.user.mapper.AccountShareRelationMapper;
import com.deepexi.user.service.AccountShareRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.klock.annotation.Klock;
import org.springframework.stereotype.Component;


/**
 * @Description 账号分享关系 服务实现
 * @Author lizhongbao
 * @Date 2019/11/7
 */
@Component
@Service(version = "${demo.service.version}")
public class AccountShareRelationServiceImpl implements AccountShareRelationService {

    @Autowired
    private AccountShareRelationMapper accountShareRelationMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Override
    @Klock(keys = {"#dto.targetAccountId"})
    public Boolean create(AccountShareRelationDto dto) {
        // 如果分享账号跟目标账号一样，则跳过关系关联
        if (dto.getTargetAccountId().equals(dto.getSourceAccountId()) || dto.getTargetAccountId().equals(dto.getShareAccountId())) {
            return false;
        }
        AccountShareRelation accountShareRelation = new AccountShareRelation();
        BeanUtil.copyProperties(dto, accountShareRelation);
        // 是否首次关联
        Boolean first = this.checkFirstRelation(accountShareRelation.getTargetAccountId());
        accountShareRelation.setFirstBinding(first);
        if (!first) {
            // 是否已存在相同的关系
            AccountShareRelation accountShareRelation1 = this.getByDto(dto);
            if (accountShareRelation1 != null) {
                return false;
            }
        }
        int insertCount = accountShareRelationMapper.insert(accountShareRelation);
        return insertCount > 0;
    }

    @Override
    public Boolean checkFirstRelation(String targetAccountId) {
        return accountShareRelationMapper.getCountByTargetAccountId(targetAccountId) < 1;
    }

    @Override
    public AccountShareRelation getByDto(AccountShareRelationDto dto) {
        return accountShareRelationMapper.getByDto(dto);
    }
}