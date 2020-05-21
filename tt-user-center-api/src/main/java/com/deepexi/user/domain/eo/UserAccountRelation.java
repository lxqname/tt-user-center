package com.deepexi.user.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.util.*;
import java.io.Serializable;
// import io.swagger.annotations.ApiModel;
// import io.swagger.annotations.ApiModelProperty;


/**
 * @author lizhongbao
 * @desc user_account_relation
 */
// @ApiModel(description = "用户-账号关联表")
@TableName("usc_user_account_relation")
public class UserAccountRelation extends SuperEntity {

    // @ApiModelProperty(value = "用户ID")
    private String userId;
    // @ApiModelProperty(value = "账号ID")
    private String accountId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

