package com.deepexi.user.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.deepexi.user.extension.AppRuntimeEnv;
import com.deepexi.user.service.AccountService;
import com.deepexi.util.StringUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: mybatis-plus公共字段自动填充，https://baomidou.oschina.io/mybatis-plus-doc/#/auto-fill
 * @Author: lizhongbao
 * @Date: 2019-9-19 11:06:02
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    public static final String CREATED_AT = "createdAt";
    public static final String TENANT_CODE = "tenantCode";
    public static final String UPDATED_AT = "updatedAt";
    public static final String CREATED_BY = "createdBy";
    public static final String UPDATED_BY = "updatedBy";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppRuntimeEnv appRuntimeEnv;

    /**
     * 插入方法实体填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName(CREATED_AT, new Date(), metaObject);
        setFieldValByName(TENANT_CODE, appRuntimeEnv.getTenantId(), metaObject);
        String token = appRuntimeEnv.getToken();
        if (StringUtil.isNotBlank(token)) {
            try {
                setFieldValByName(CREATED_BY, accountService.getLoginAccountIdByToken(token), metaObject);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Token格式不正确" + e.getMessage());
            }
        }
    }

    /**
     * 更新方法实体填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName(UPDATED_AT, new Date(), metaObject);
        String token = appRuntimeEnv.getToken();
        if (StringUtil.isNotBlank(token)) {
            try {
                setFieldValByName(UPDATED_BY, accountService.getLoginAccountIdByToken(token), metaObject);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Token格式不正确" + e.getMessage());
            }
        }
    }
}