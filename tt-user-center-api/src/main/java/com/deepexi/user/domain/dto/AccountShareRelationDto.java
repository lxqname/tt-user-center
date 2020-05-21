package com.deepexi.user.domain.dto;


import com.deepexi.common.domain.eo.SuperEntity;

import javax.validation.constraints.NotNull;


/**
 * @Description 账号分享关系DTO
 * @Author lizhongbao
 * @Date 2019/11/7
 */
public class AccountShareRelationDto extends SuperEntity {

    /**
     * 来源账号ID
     */
    @NotNull(message = "来源账号ID不能为空")
    private String sourceAccountId;
    /**
     * 分享账号ID
     */
    @NotNull(message = "分享账号ID不能为空")
    private String shareAccountId;
    /**
     * 目标账号ID
     */
    @NotNull(message = "目标账号ID不能为空")
    private String targetAccountId;

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getShareAccountId() {
        return shareAccountId;
    }

    public void setShareAccountId(String shareAccountId) {
        this.shareAccountId = shareAccountId;
    }

    public String getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

}

