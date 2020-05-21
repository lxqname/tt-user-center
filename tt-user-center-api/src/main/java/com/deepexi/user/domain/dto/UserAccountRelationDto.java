package com.deepexi.user.domain.dto;

import java.io.Serializable;

/**
 * @author lizhongbao
 * @desc user_account_relation
 */
public class UserAccountRelationDto implements Serializable {

    private static final long serialVersionUID = -1;

    private String userId;

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

    @Override
    public String toString() {
        return "UserAccountRelationDto{" +
                "userId='" + userId + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}

