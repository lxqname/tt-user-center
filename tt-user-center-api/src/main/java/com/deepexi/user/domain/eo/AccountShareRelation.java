package com.deepexi.user.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;


/**
 * @Description 账号分享关系表
 * @Author lizhongbao
 * @Date 2019/11/7
 */
@TableName("usc_account_share_relation")
public class AccountShareRelation extends SuperEntity {

    /**
     * 来源账号ID
     */
    private String sourceAccountId;
    /**
     * 分享账号ID
     */
    private String shareAccountId;
    /**
     * 目标账号ID
     */
    private String targetAccountId;
    /**
     * 是否首次绑定
     */
    private Boolean firstBinding;

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

    public Boolean getFirstBinding() {
        return firstBinding;
    }

    public void setFirstBinding(Boolean firstBinding) {
        this.firstBinding = firstBinding;
    }
}

