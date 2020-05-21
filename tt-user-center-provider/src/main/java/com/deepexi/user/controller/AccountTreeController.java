package com.deepexi.user.controller;

import com.deepexi.user.domain.eo.AccountTree;
import com.deepexi.user.service.AccountTreeService;
import com.deepexi.user.domain.dto.AccountTreeDto;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

@Service
@Path("/api/v1/accountTrees")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class AccountTreeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountTreeService accountTreeService;

    @GET
    @Path("/")
    public Payload findPage(@BeanParam AccountTreeDto eo,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("size") @DefaultValue("10") Integer size) {
        return new Payload(accountTreeService.findPage(eo, page, size));
    }

    @GET
    @Path("/list")
    public Payload findAll(@BeanParam AccountTreeDto eo) {
        return new Payload(accountTreeService.findAll(eo));
    }

    @GET
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload detail(@PathParam("id") String pk) {
        return new Payload(accountTreeService.detail(pk));
    }

    @POST
    @Path("/")
    public Payload create(AccountTree eo) {
        return new Payload(accountTreeService.create(eo));
    }

    @PUT
    @Path("/{id:[a-zA-Z0-9]+}")
    public Payload update(@PathParam("id") String pk, AccountTree eo) {
        return new Payload(accountTreeService.update(pk, eo));
    }

    @DELETE
    @Path("/{id:[a-zA-Z0-9,]+}")
    public Payload delete(@PathParam("id") String pk) {
        return new Payload(accountTreeService.delete(pk.split(",")));
    }

    @DELETE
    @Path("/")
    public Payload delete(String[] pks) {
        return new Payload(accountTreeService.delete(pks));
    }
}
