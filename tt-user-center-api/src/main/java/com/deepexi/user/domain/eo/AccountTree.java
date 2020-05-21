package com.deepexi.user.domain.eo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.deepexi.common.domain.eo.SuperEntity;

import java.util.*;
import java.io.Serializable;
// import io.swagger.annotations.ApiModel;
// import io.swagger.annotations.ApiModelProperty;


/**
 * @author lizhongbao
 * @desc account_tree
 */
// @ApiModel(description = "账号树")
@TableName("usc_account_tree")
public class AccountTree extends SuperEntity {

    // @ApiModelProperty(value = "父账号ID")
    private String pAccountId;
    // @ApiModelProperty(value = "顶级账号ID")
    private String topAccountId;
    // @ApiModelProperty(value = "编码")
    private String code;
    // @ApiModelProperty(value = "级别")
    private Integer level;

    public String getpAccountId() {
        return pAccountId;
    }

    public void setpAccountId(String pAccountId) {
        this.pAccountId = pAccountId;
    }

    public String getTopAccountId() {
        return topAccountId;
    }

    public void setTopAccountId(String topAccountId) {
        this.topAccountId = topAccountId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}

