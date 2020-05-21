package com.deepexi.user.controller;

import com.deepexi.common.enums.StatusEnum;
import com.deepexi.user.domain.dto.AccountQueryDto;
import com.deepexi.user.domain.dto.LoginDto;
import com.deepexi.user.extension.AppRuntimeEnv;
import com.deepexi.user.service.AccountService;
import com.deepexi.user.domain.dto.AccountDto;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

@Service
@Path("/api/v1/accounts")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    /**
     * @Description 注册
     * @Author lizhongbao
     * @Date 2019/9/23
     * @Params [accountDto]
     * @Return com.deepexi.util.config.Payload
     */
    @POST
    @Path("/register")
    public Payload register(AccountDto accountDto) {
        return new Payload(accountService.register(accountDto, appRuntimeEnv.getTenantId()));
    }

    /**
     * @Description 登录
     * @Author lizhongbao
     * @Date 2019/9/23
     * @Params [loginDto]
     * @Return com.deepexi.util.config.Payload
     */
    @POST
    @Path("/login")
    public Payload login(LoginDto loginDto) {
        return new Payload(accountService.login(loginDto, appRuntimeEnv.getTenantId()));
    }

    @GET
    @Path("/")
    public Payload findPage(@BeanParam AccountQueryDto dto,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload(accountService.findPage(dto, page, size));
    }

    @GET
    @Path("/list")
    public Payload findAll(@BeanParam AccountQueryDto dto) {
        return new Payload(accountService.findAll(dto));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload(accountService.detail(pk));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String pk, AccountDto eo) {
        return new Payload(accountService.update(pk, eo, appRuntimeEnv.getTenantId()));
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("id") String pk) {
        return new Payload(accountService.delete(pk.split(",")));
    }

    @DELETE
    @Path("/")
    public Payload delete(String[] pks) {
        return new Payload(accountService.delete(pks));
    }

    /**
     * @Description 启用账号
     * @Author lizhongbao
     * @Date 2019/9/29
     * @Params [pk]
     * @Return com.deepexi.util.config.Payload
     */
    @PUT
    @Path("/enable/{id:[a-zA-Z0-9]+}")
    public Payload enable(@PathParam("id") String pk) {
        return new Payload(accountService.updateStatus(pk, StatusEnum.ENABLE.getValue()));
    }

    /**
     * @Description 禁用账号
     * @Author lizhongbao
     * @Date 2019/9/29
     * @Params [pk]
     * @Return com.deepexi.util.config.Payload
     */
    @PUT
    @Path("/disable/{id:[a-zA-Z0-9]+}")
    public Payload disable(@PathParam("id") String pk) {
        return new Payload(accountService.updateStatus(pk, StatusEnum.DISABLE.getValue()));
    }
}
