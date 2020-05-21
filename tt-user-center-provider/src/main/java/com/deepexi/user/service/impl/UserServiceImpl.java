package com.deepexi.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.deepexi.common.enums.PlatformTypeEnum;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.common.util.RequestUtils;
import com.deepexi.permission.service.UserRoleService;
import com.deepexi.user.domain.dto.*;
import com.deepexi.user.domain.eo.Account;
import com.deepexi.user.domain.eo.UserAccountRelation;
import com.deepexi.user.domain.vo.UserListVo;
import com.deepexi.user.service.AccountService;
import com.deepexi.user.service.UserAccountRelationService;
import com.deepexi.user.service.UserService;
import com.deepexi.user.domain.eo.User;
import com.deepexi.user.extension.AppRuntimeEnv;
import com.deepexi.user.mapper.UserMapper;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.StringUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Component
@Service(version = "${demo.service.version}")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserAccountRelationService userAccountRelationService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Reference(version = "${demo.service.version}", check = false)
    private UserRoleService userRoleService;

    @Override
    public PageBean findPage(UserDto eo, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<UserListVo> list = userMapper.findList(eo, appRuntimeEnv.getTenantId());
        this.getRole(list);
        PageBean<UserListVo> userListVoPageBean = new PageBean<>(list);
        return userListVoPageBean;
    }

    @Override
    public List<UserListVo> findAll(UserDto eo) {
        List<UserListVo> list = userMapper.findList(eo, appRuntimeEnv.getTenantId());
        this.getRole(list);
        return list;
    }

    @Override
    public User detail(String pk) {
        return userMapper.selectById(pk);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(UserDto eo) {
        return save(null, eo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(String pk, UserDto eo) {
        return save(pk, eo);
    }

    /**
     * @Description
     * @Author lizhongbao
     * @Date 2019/9/21
     * @Params [userListVoList]
     * @Return void
     */
    private void getRole(List<UserListVo> userListVoList) {
        if (CollectionUtil.isNotEmpty(userListVoList)) {
            for (UserListVo userListVo : userListVoList) {
                try {
                    userListVo.setRoles(userRoleService.getRoleListByUserId(userListVo.getAccountId()));
                } catch (RpcException e) {
                    logger.error(ResultEnum.RPC_ERROR.getMsg(), e);
                }
            }
        }
    }

    /**
     * 保存用户
     *
     * @param pk
     * @param dto
     * @return
     */
    public Boolean save(String pk, UserDto dto) {
        if (dto == null) {
            throw new ApplicationException(ResultEnum.PARAMETER_ERROR);
        }
        saveVerify(pk, dto);
        AccountDto accountDto = dto.getAccount();
        // 运营账号
        accountDto.setPlatformType(PlatformTypeEnum.INTERNAL_ACCOUNT.getValue());
        boolean isCreate = pk == null;
        if (isCreate) {
            // 新增账号
            Account account = accountService.register(accountDto, appRuntimeEnv.getTenantId());

            // 新增用户
            User user = new User();
            BeanUtil.copyProperties(dto, user);
            BeanUtil.copyProperties(dto.getAccount(), user);
            userMapper.insert(user);

            // 新增用户账号关联
            UserAccountRelation userAccountRelation = new UserAccountRelation();
            userAccountRelation.setAccountId(account.getId());
            userAccountRelation.setUserId(user.getId());
            userAccountRelationService.create(userAccountRelation);
        } else {
            // 获取用户-账号
            UserAccountRelationDto userAccountRelationDto = new UserAccountRelationDto();
            userAccountRelationDto.setUserId(pk);
            UserAccountRelation userAccountRelation = userAccountRelationService.getByDto(userAccountRelationDto);
            if (userAccountRelation == null) {
                throw new ApplicationException(ResultEnum.getParameterError("账号不存在"));
            }
            // 修改账号
            accountService.update(userAccountRelation.getAccountId(), accountDto, appRuntimeEnv.getTenantId());

            // 修改用户
            User user = new User();
            BeanUtil.copyProperties(dto, user);
            BeanUtil.copyProperties(dto.getAccount(), user);
            user.setId(pk);
            userMapper.updateById(user);
        }
        return true;
    }

    /**
     * 保存用户校验
     *
     * @param pk
     * @param dto
     * @return
     */
    private void saveVerify(String pk, UserDto dto) {

        if (StringUtil.isBlank(dto.getCode())) {
            throw new ApplicationException(ResultEnum.getParameterError("员工编号不能为空"));
        }

        UserVerifyDto userVerifyDto = new UserVerifyDto();
        userVerifyDto.setExcludeId(pk);
        userVerifyDto.setCode(dto.getCode());
        User user = userMapper.getByDto(userVerifyDto, appRuntimeEnv.getTenantId());
        if (null != user) {
            throw new ApplicationException(ResultEnum.getParameterError("员工编号已存在"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String... pk) {
        int result = userMapper.deleteByIds(pk);
        if (result > 0) {
            for (String id : pk) {
                // 获取用户-账号
                UserAccountRelationDto userAccountRelationDto = new UserAccountRelationDto();
                userAccountRelationDto.setUserId(id);
                UserAccountRelation userAccountRelation = userAccountRelationService.getByDto(userAccountRelationDto);
                if (userAccountRelation == null) {
                    throw new ApplicationException(ResultEnum.getParameterError("账号不存在"));
                }
                accountService.delete(userAccountRelation.getAccountId());
            }
        }
        return true;
    }

    @Override
    public AccountDto login(LoginDto dto) {
        dto.setPlatformType(PlatformTypeEnum.INTERNAL_ACCOUNT.getValue());
        dto.setAddress(RequestUtils.getIpAddr(httpServletRequest));
        dto.setBrowser(RequestUtils.getBrowserName(httpServletRequest));
        return accountService.login(dto, appRuntimeEnv.getTenantId());
    }

}