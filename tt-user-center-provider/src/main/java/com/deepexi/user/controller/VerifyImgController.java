package com.deepexi.user.controller;

import com.deepexi.common.annotation.Token;
import com.deepexi.user.service.VerifyService;
import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

/**
 * @author hongchungen
 * @date 2019/10/14 19:45
 */
@Service
@Path("/api/v1/verifyImgs")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class VerifyImgController {

    @Autowired
    private VerifyService verifyService;

    @GET
    @Path("/generateImgCode")
    @Token(require = false)
    public Payload generateImgCode() {
        return new Payload(verifyService.generateImgCode());
    }

    @GET
    @Path("/generatePhoneCode")
    @Token(require = false)
    public Payload generatePhoneCode(@QueryParam("phone")  String phone) {
        return new Payload(verifyService.generatePhoneCode(phone));
    }

    @GET
    @Path("/checkImgCode")
    @Token(require = false)
    public Payload checkImgCode(@QueryParam("verifyId") String verifyId, @QueryParam("verifyCode") String verifyCode) {
        return new Payload(verifyService.checkImgCode(verifyId, verifyCode));
    }
}
