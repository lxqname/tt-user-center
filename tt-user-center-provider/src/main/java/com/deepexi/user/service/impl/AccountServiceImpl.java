package com.deepexi.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.deepexi.common.enums.PlatformTypeEnum;
import com.deepexi.common.enums.ResultEnum;
import com.deepexi.common.enums.StatusEnum;
import com.deepexi.common.util.ValidatorUtils;
import com.deepexi.user.domain.dto.*;
import com.deepexi.user.domain.vo.AccountVerifyVo;
import com.deepexi.user.service.AccountService;
import com.deepexi.user.domain.eo.Account;
import com.deepexi.user.extension.AppRuntimeEnv;
import com.deepexi.user.mapper.AccountMapper;
import com.deepexi.user.service.LoginInfoService;
import com.deepexi.user.service.kong.KongLoginService;
import com.deepexi.user.service.kong.impl.KongLoginServiceImpl;
import com.deepexi.util.MD5Util;
import com.deepexi.util.StringUtil;
import com.deepexi.util.extension.ApplicationException;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.klock.annotation.Klock;
import org.springframework.stereotype.Component;
import com.deepexi.util.BeanPowerHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * @Description 账号 服务类
 * @Author lizhongbao
 * @Date 2019/10/12
 */
@Component
@Service(version = "${demo.service.version}")
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    @Autowired
    private KongLoginService kongLoginService;

    @Autowired
    private LoginInfoService loginInfoService;

    @Override
    public PageBean findPage(AccountQueryDto dto, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Account> list = accountMapper.findList(dto, appRuntimeEnv.getTenantId());
        return new PageBean<>(list);
    }

    @Override
    public List<Account> findAll(AccountQueryDto dto) {
        List<Account> list = accountMapper.findList(dto, appRuntimeEnv.getTenantId());
        return list;
    }

    @Override
    public Account detail(String pk) {
        return accountMapper.selectById(pk);
    }

    @Override
    public Boolean delete(String... pk) {
        int result = accountMapper.deleteByIds(pk);
        return result > 0;
    }

    @Override
    public AccountDto login(LoginDto dto, String tenantId) {
        logger.info("start login");
        if (dto == null) {
            logger.info("login information is empty");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "用户信息不可为空"));
        }
        AccountVerifyDto accountVerifyDto = new AccountVerifyDto();
        BeanUtil.copyProperties(dto, accountVerifyDto);

        Account account = null;
        // 用户名登录
        account = accountMapper.getByDto(accountVerifyDto, tenantId);
        if (null == account) {
            logger.info("tenant dont have this user");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "用户不存在"));
        } else if (!MD5Util.verify(dto.getPassword(), account.getPassword())) {
            logger.info("password is error");
            throw new ApplicationException(ResultEnum.PASSWORD_ERROR);
        } else {
            if (account.getStatus().equals(StatusEnum.DISABLE.getValue())) {
                logger.info("this user not activation");
                throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "该用户尚未激活"));
            }
            // 登录成功,更新最后一次登录时间
            accountMapper.updateLastLoginTime(account.getId());
            dto.setAccountId(account.getId());
            loginInfoService.create(dto);
            // 生成token并返回
            String token = kongLoginService.generateToken(account, dto.getChannel());
            AccountDto result = BeanPowerHelper.mapCompleteOverrider(account, AccountDto.class);
            result.setToken(token);
            logger.info("login success");
            return result;
        }
    }

    @Override
    @Klock
    public Account register(AccountDto dto, String tenantId) {
        logger.info("start register");

        saveVerify(dto, tenantId);

        // 注册
        Account account = BeanPowerHelper.mapCompleteOverrider(dto, Account.class);
        account.setTenantCode(tenantId);
        account.setPassword(MD5Util.generate(account.getPassword()));
        // 当前账户状态 默认可用
        account.setStatus(null == dto.getStatus() ? 0 : dto.getStatus());
        account.setRegisterTime(new Date());
        if (accountMapper.insert(account) == 0) {
            logger.error("register failed, insert db error");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "注册失败，请稍后重试"));
        }
        logger.info("register success");
        return account;
    }

    @Override
    public Map getLoginUserByToken(String token) {
        Map map = kongLoginService.getByToken(token);
        return map;
    }

    @Override
    public String getLoginAccountIdByToken(String token) {
        Map map = this.getLoginUserByToken(token);
        String accountId = (String) map.get(KongLoginServiceImpl.ACCOUNT_ID);
        return accountId;
    }

    @Override
    public Account getLoginAccountByToken(String token) {
        String accountId = this.getLoginAccountIdByToken(token);
        Account account = accountMapper.selectById(accountId);
        return account;
    }

    @Override
    public String getUsernameById(String id) {
        if (StringUtil.isBlank(id)) {
            return null;
        }
        return accountMapper.getUsernameById(id);
    }

    @Override
    public Boolean update(String pk, AccountDto dto, String tenantId) {
        dto.setId(pk);
        saveVerify(dto, tenantId);

        Account account = this.detail(pk);
        if (null == account) {
            throw new ApplicationException(ResultEnum.getParameterError("账号不存在"));
        }

        // 是否修改密码
        if (StringUtil.isNotBlank(dto.getPassword())) {
            account.setPassword(MD5Util.generate(dto.getPassword()));
        }

        account.setUsername(dto.getUsername());
        account.setPhone(dto.getPhone());
        account.setEmail(dto.getEmail());
        account.setName(dto.getName());
        account.setSex(dto.getSex());
        account.setStatus(dto.getStatus());

        int result = accountMapper.updateById(account);
        return result > 0;
    }

    @Override
    public AccountVerifyVo saveVerifyExcludePhone(AccountDto dto) {
        String tenantId = appRuntimeEnv.getTenantId();
        if (dto == null) {
            logger.info("user information cannot be empty");
            return new AccountVerifyVo(ResultEnum.PARAMETER_ERROR, "用户信息不可为空", false);
        }
        // 判断用户名/手机号/邮箱是否同时为空
        if (StringUtil.isEmpty(dto.getUsername()) && StringUtil.isEmpty(dto.getPhone()) && StringUtil.isEmpty(dto.getEmail())) {
            logger.info("register failed，username/phone/email cannot be empty at the same time");
            return new AccountVerifyVo(ResultEnum.PARAMETER_ERROR, "注册失败，用户名/手机号/邮箱不可同时为空", false);
        }
        // 手机号格式判断
        if (!StringUtil.isEmpty(dto.getPhone()) && !Pattern.matches(ValidatorUtils.REGEX_MOBILE, dto.getPhone())) {
            logger.info("phone format error. phone = {}", dto.getPhone());
            return new AccountVerifyVo(ResultEnum.PARAMETER_ERROR, "手机号格式错误", false);
        }
        // 邮箱格式判断
        if (!StringUtil.isEmpty(dto.getEmail()) && !Pattern.matches(ValidatorUtils.REGEX_EMAIL, dto.getEmail())) {
            logger.info("email format error. email = {}", dto.getEmail());
            return new AccountVerifyVo(ResultEnum.PARAMETER_ERROR, "邮箱格式错误", false);
        }
        // 进行用户名/手机号/邮箱存在校验
        AccountVerifyDto accountVerifyDto = new AccountVerifyDto();
        accountVerifyDto.setUsername(dto.getUsername());
        Account account = accountMapper.getByDtoExcludePhone(accountVerifyDto, tenantId);
        if (null != account && !account.getId().equals(dto.getId())) {
            logger.info("username already exists. username = {}", account.getUsername());
            return new AccountVerifyVo(ResultEnum.USER_EXIST, "用户名已存在", false);
        }
        accountVerifyDto.setUsername(dto.getPhone());
        account = accountMapper.getByDtoExcludePhone(accountVerifyDto, tenantId);
        if (null != account && !account.getId().equals(dto.getId())) {
            logger.info("phone already exists. phone = {}", account.getPhone());
            return new AccountVerifyVo(ResultEnum.PARAMETER_ERROR, "手机号已存在", false);
        }
        accountVerifyDto.setUsername(dto.getEmail());
        account = accountMapper.getByDtoExcludePhone(accountVerifyDto, tenantId);
        if (null != account && !account.getId().equals(dto.getId())) {
            logger.info("email already exists. email = {}", account.getEmail());
            return new AccountVerifyVo(ResultEnum.PARAMETER_ERROR, "邮箱已存在", false);
        }
        return new AccountVerifyVo(null, "校验通过", true);
    }

    @Override
    public Boolean updateStatus(String id, Integer status) {
        return accountMapper.updateStatus(id, status) > 0;
    }

    @Override
    public PageBean<Account> pagePrimaryAccount(AccountQueryDto dto, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Account> list = accountMapper.listPrimaryAccount(dto, appRuntimeEnv.getTenantId());
        return new PageBean<>(list);
    }

    /**
     * 保存用户校验
     *
     * @param dto
     * @param tenantId
     * @return
     */
    private void saveVerify(AccountDto dto, String tenantId) {

        if (dto == null) {
            logger.info("user information cannot be empty");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "用户信息不可为空"));
        }
        // 判断用户名/手机号/邮箱是否同时为空
        if (StringUtil.isEmpty(dto.getUsername()) && StringUtil.isEmpty(dto.getPhone()) && StringUtil.isEmpty(dto.getEmail())) {
            logger.info("register failed，username/phone/email cannot be empty at the same time");
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "注册失败，用户名/手机号/邮箱不可同时为空"));
        }
        // 手机号格式判断
        if (!StringUtil.isEmpty(dto.getPhone()) && !Pattern.matches(ValidatorUtils.REGEX_MOBILE, dto.getPhone())) {
            logger.info("phone format error. phone = {}", dto.getPhone());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "手机号格式错误"));
        }
        // 邮箱格式判断
        if (!StringUtil.isEmpty(dto.getEmail()) && !Pattern.matches(ValidatorUtils.REGEX_EMAIL, dto.getEmail())) {
            logger.info("email format error. email = {}", dto.getEmail());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "邮箱格式错误"));
        }
        // 进行用户名/手机号/邮箱存在校验
        AccountVerifyDto accountVerifyDto = new AccountVerifyDto();
        accountVerifyDto.setPlatformType(dto.getPlatformType());
        accountVerifyDto.setUsername(dto.getUsername());
        Account account = accountMapper.getByDto(accountVerifyDto, tenantId);
        if (null != account && !account.getId().equals(dto.getId())) {
            logger.info("username already exists. username = {}", account.getUsername());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.USER_EXIST, "用户名已存在"));
        }
        accountVerifyDto.setUsername(dto.getPhone());
        account = accountMapper.getByDto(accountVerifyDto, tenantId);
        if (null != account && !account.getId().equals(dto.getId())) {
            logger.info("phone already exists. phone = {}", account.getPhone());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "手机号已存在"));
        }
        accountVerifyDto.setUsername(dto.getEmail());
        account = accountMapper.getByDto(accountVerifyDto, tenantId);
        if (null != account && !account.getId().equals(dto.getId())) {
            logger.info("email already exists. email = {}", account.getEmail());
            throw new ApplicationException(ResultEnum.setMsg(ResultEnum.PARAMETER_ERROR, "邮箱已存在"));
        }
    }

}