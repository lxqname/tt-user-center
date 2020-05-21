package com.deepexi.user.domain.dto;

/**
 * @Description 子账号DTO
 * @Author lizhongbao
 * @Date  2019/9/27
 */
public class SubAccountDto extends AccountDto {

    /**
     * 主账号ID
     */
    private String primaryAccountId;

    /**
     * 父账号ID
     */
    private String parentAccountId;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 编号
     */
    private String code;

    public String getPrimaryAccountId() {
        return primaryAccountId;
    }

    public void setPrimaryAccountId(String primaryAccountId) {
        this.primaryAccountId = primaryAccountId;
    }

    public String getParentAccountId() {
        return parentAccountId;
    }

    public void setParentAccountId(String parentAccountId) {
        this.parentAccountId = parentAccountId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
