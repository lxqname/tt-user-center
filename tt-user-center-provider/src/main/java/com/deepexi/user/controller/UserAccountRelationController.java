package com.deepexi.user.controller;

import com.deepexi.user.domain.eo.UserAccountRelation;
import com.deepexi.user.service.UserAccountRelationService;
import com.deepexi.user.domain.dto.UserAccountRelationDto;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

@Service
@Path("/api/v1/userAccountRelations")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class UserAccountRelationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserAccountRelationService userAccountRelationService;

    @GET
    @Path("/")
    public Payload findPage(@BeanParam UserAccountRelationDto eo,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload(userAccountRelationService.findPage(eo, page, size));
    }

    @GET
    @Path("/list")
    public Payload findAll(@BeanParam UserAccountRelationDto eo) {
        return new Payload(userAccountRelationService.findAll(eo));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload(userAccountRelationService.detail(pk));
    }

    @POST
    @Path("/")
    public Payload create(UserAccountRelation eo) {
        return new Payload(userAccountRelationService.create(eo));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String pk, UserAccountRelation eo) {
        return new Payload(userAccountRelationService.update(pk, eo));
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("id") String pk) {
        return new Payload(userAccountRelationService.delete(pk.split(",")));
    }

    @DELETE
    @Path("/")
    public Payload delete(String[] pks) {
        return new Payload(userAccountRelationService.delete(pks));
    }
}
