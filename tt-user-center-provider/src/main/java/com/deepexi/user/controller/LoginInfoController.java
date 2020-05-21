package com.deepexi.user.controller;

import com.deepexi.user.service.LoginInfoService;
import com.deepexi.user.domain.dto.LoginInfoDto;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

@Service
@Path("/api/v1/loginInfos")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class LoginInfoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginInfoService loginInfoService;

    @GET
    @Path("/")
    public Payload findPage(@BeanParam LoginInfoDto eo,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload(loginInfoService.findPage(eo, page, size));
    }

    @GET
    @Path("/list")
    public Payload findAll(@BeanParam LoginInfoDto eo) {
        return new Payload(loginInfoService.findAll(eo));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload(loginInfoService.detail(pk));
    }

    @POST
    @Path("/")
    public Payload create(LoginInfoDto eo) {
        return new Payload(loginInfoService.create(eo));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String pk, LoginInfoDto eo) {
        return new Payload(loginInfoService.update(pk, eo));
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("id") String pk) {
        return new Payload(loginInfoService.delete(pk.split(",")));
    }

    @DELETE
    @Path("/")
    public Payload delete(String[] pks) {
        return new Payload(loginInfoService.delete(pks));
    }
}
