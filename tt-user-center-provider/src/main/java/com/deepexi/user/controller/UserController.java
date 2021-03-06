package com.deepexi.user.controller;

import com.deepexi.common.annotation.Token;
import com.deepexi.user.domain.dto.LoginDto;
import com.deepexi.user.service.UserService;
import com.deepexi.user.domain.dto.UserDto;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;

@Service
@Path("/api/v1/users")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * @Description 渠道端登录
     * @Author lizhongbao
     * @Date 2019-9-10 17:38:44
     * @Params [dto]
     * @Return com.deepexi.util.config.Payload
     */
    @POST
    @Path("/login")
    @Token(require = false)
    public Payload login(@Valid @NotNull LoginDto dto) {

        return new Payload(userService.login(dto));
    }

    @GET
    @Path("/")
    public Payload findPage(@BeanParam UserDto eo,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload(userService.findPage(eo, page, size));
    }

    @GET
    @Path("/list")
    public Payload findAll(@BeanParam UserDto eo) {
        return new Payload(userService.findAll(eo));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload(userService.detail(pk));
    }

    @POST
    @Path("/")
    public Payload create(@Valid @NotNull UserDto eo) {
        return new Payload(userService.create(eo));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String pk, @Valid @NotNull UserDto eo) {
        return new Payload(userService.update(pk, eo));
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("id") String pk) {
        return new Payload(userService.delete(pk.split(",")));
    }

    @DELETE
    @Path("/")
    public Payload delete(String[] pks) {
        return new Payload(userService.delete(pks));
    }
}
