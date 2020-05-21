package com.deepexi.user.controller;

import com.deepexi.common.annotation.Token;
import com.deepexi.user.domain.dto.AccountShareRelationDto;
import com.deepexi.user.extension.AppRuntimeEnv;
import com.deepexi.user.service.AccountShareRelationService;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;

/**
 * @Description 账号分享关系 Controller
 * @Author lizhongbao
 * @Date 2019/11/7
 */
@Service
@Path("/api/v1/accountShareRelations")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class AccountShareRelationController {

    @Autowired
    private AccountShareRelationService accountShareRelationService;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    /**
     * @Description 创建账号分享关系
     * @Author lizhongbao
     * @Date 2019/11/7
     * @Params [dto]
     * @Return com.deepexi.util.config.Payload
     */
    @POST
    @Path("/")
    @Token(require = false)
    public Payload register(@Valid @NotNull AccountShareRelationDto dto) {
        return new Payload(accountShareRelationService.create(dto));
    }

}
