package com.deepexi.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.permission.service.UserRoleService;
import com.deepexi.user.domain.dto.*;
import com.deepexi.user.domain.eo.*;
import com.deepexi.user.domain.vo.SubAccountVo;
import com.deepexi.user.service.*;
import com.deepexi.user.extension.AppRuntimeEnv;
import com.deepexi.user.mapper.AccountTreeMapper;
import com.deepexi.util.StringUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
@Service(version = "${demo.service.version}")
public class AccountTreeServiceImpl implements AccountTreeService {

    private static final Logger logger = LoggerFactory.getLogger(AccountTreeServiceImpl.class);

    @Reference(version = "${demo.service.version}", check = false)
    private UserRoleService userRoleService;

    @Autowired
    private AccountTreeMapper accountTreeMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Autowired
    private AccountService accountService;

    @Override
    public PageBean findPage(AccountTreeDto eo, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<AccountTree> list = accountTreeMapper.findList(eo);
        return new PageBean<>(list);
    }

    @Override
    public List<AccountTree> findAll(AccountTreeDto eo) {
        List<AccountTree> list = accountTreeMapper.findList(eo);
        return list;
    }

    @Override
    public AccountTree detail(String pk) {
        return accountTreeMapper.selectById(pk);
    }

    @Override
    public Boolean create(AccountTree eo) {
        int result = accountTreeMapper.insert(eo);
        return result > 0;
    }

    @Override
    public Boolean update(String pk, AccountTree eo) {
        eo.setId(pk);
        int result = accountTreeMapper.updateById(eo);
        return result > 0;
    }

    @Override
    public Boolean delete(String... pk) {
        if (accountService.delete(pk)) {
            int result = accountTreeMapper.deleteByIds(pk);
            return result > 0;
        }
        return false;
    }

    @Override
    public List<String> listSub(String id) {
        List<String> ids = new ArrayList<>();
        List<String> subIds = accountTreeMapper.listSub(id, appRuntimeEnv.getTenantId());
        if (CollectionUtil.isNotEmpty(subIds)) {
            ids.addAll(subIds);
            // 递归获取子节点
            Iterator<String> iter = subIds.iterator();
            while (iter.hasNext()) {
                List<String> subIds2 = listSub(iter.next());
                if (CollectionUtil.isNotEmpty(subIds2)) {
                    ids.addAll(subIds2);
                }
            }
        }
        return ids;
    }

    @Override
    public String saveSub(String pk, SubAccountDto dto) {
        saveSubVerify(pk, dto);

        AccountTree accountTree = new AccountTree();
        accountTree.setCode(dto.getCode());
        accountTree.setTopAccountId(dto.getPrimaryAccountId());
        accountTree.setpAccountId(dto.getParentAccountId());
        accountTree.setLevel(dto.getLevel());

        if (accountTree.getpAccountId().equals(pk)) {
            throw new ApplicationException(ResultEnum.PARAMETER_ERROR);
        }

        boolean isCreate = pk == null;
        if (isCreate) {
            // 新增
            Account account = accountService.register(dto, appRuntimeEnv.getTenantId());
            pk = account.getId();

            accountTree.setId(account.getId());
            this.create(accountTree);
        } else {
            // 修改
            accountService.update(pk, dto, appRuntimeEnv.getTenantId());
            this.update(pk, accountTree);
        }
        return pk;
    }

    @Override
    public void saveSubVerify(String id, SubAccountDto dto) {
        if (StringUtil.isNotBlank(dto.getCode())) {
            if (!this.verifyCode(id, dto.getPrimaryAccountId(), dto.getCode())) {
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "编号已存在"));
            }
        }
    }

    @Override
    public Boolean verifyCode(String id, String topAccountId, String code) {
        return accountTreeMapper.verifyCode(id, topAccountId, code, appRuntimeEnv.getTenantId()) < 1;
    }

    @Override
    public PageBean<SubAccountVo> pageSubVo(SubAccountQueryDto dto, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<SubAccountVo> list = accountTreeMapper.listSubVo(dto, appRuntimeEnv.getTenantId());
        this.getRole(list, dto.getType(), dto.getPrimaryAccountId());
        return new PageBean<>(list);
    }

    @Override
    public SubAccountVo getSubVoByAccountId(String accountId) {
        return accountTreeMapper.getSubVoByAccountId(accountId);
    }

    /**
     * @Description 获取角色
     * @Author lizhongbao
     * @Date 2019/9/29
     * @Params [subAccountVoList, type]
     * @Return void
     */
    private void getRole(List<SubAccountVo> subAccountVoList, String type, String primaryAccountId) {
        if (CollectionUtil.isNotEmpty(subAccountVoList)) {
            for (SubAccountVo subAccountVo : subAccountVoList) {
                try {
                    if (type == null || subAccountVo.getId().equals(primaryAccountId)) {
                        subAccountVo.setRoles(userRoleService.getRoleListByUserId(subAccountVo.getId()));
                    } else {
                        subAccountVo.setRoles(userRoleService.getRoleListByUserIdAndType(subAccountVo.getId(), type));
                    }
                } catch (RpcException e) {
                    logger.error(ResultEnum.RPC_ERROR.getMsg(), e);
                }
            }
        }
    }

}