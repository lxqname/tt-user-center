package com.deepexi.user.extension;

import com.deepexi.util.config.Payload;
import com.deepexi.util.constant.ContentType;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ResteasyViolationException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

/**
 * @Description: 覆盖resteasy内置的异常映射器，将默认的ViolationReport输出为自定义格式
 * @author: lizhongbao
 * @date: 2019-9-21 16:50:57
 */
@Component
@Provider
public class ViolationExceptionMapper implements ExceptionMapper<ResteasyViolationException> {

    public static final String CODE_400 = "400";

    @Override
    public Response toResponse(ResteasyViolationException e) {
        // 获取校验失败的反馈信息
        String message = "参数不正确";
        for (List<ResteasyConstraintViolation> list : e.getViolationLists()) {
            if (list.size() == 0) {
                continue;
            }
            ResteasyConstraintViolation constraintViolation = list.get(0);
            message = constraintViolation.getMessage();
            break;
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new Payload(null, CODE_400, message)).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }
}