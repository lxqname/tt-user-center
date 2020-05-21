package com.deepexi.user.domain.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lizhongbao
 * @desc user
 */
public class UserDto implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * 员工编号
     */
    @NotNull(message = "员工编号不能为空")
    private String code;

    /**
     * 英文名
     */
    private String englishName;

    /**
     * 账号信息
     */
    private AccountDto account;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "code='" + code + '\'' +
                ", englishName='" + englishName + '\'' +
                ", account=" + account +
                '}';
    }
}

