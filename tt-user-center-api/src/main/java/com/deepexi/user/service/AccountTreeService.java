package com.deepexi.user.service;

import com.deepexi.user.domain.dto.SubAccountDto;
import com.deepexi.user.domain.dto.SubAccountQueryDto;
import com.deepexi.user.domain.vo.SubAccountVo;
import com.deepexi.util.pageHelper.PageBean;
import com.deepexi.user.domain.eo.AccountTree;
import com.deepexi.user.domain.dto.AccountTreeDto;

import java.util.*;

public interface AccountTreeService {

    PageBean<AccountTree> findPage(AccountTreeDto eo, Integer page, Integer size);

    List<AccountTree> findAll(AccountTreeDto eo);

    AccountTree detail(String pk);

    Boolean update(String pk, AccountTree eo);

    Boolean create(AccountTree eo);

    Boolean delete(String... pk);

    /**
     * @Description 获取子节点
     * @Author lizhongbao
     * @Date 2019/9/27
     * @Params [id]
     * @Return java.util.List<java.lang.String>
     */
    List<String> listSub(String id);

    /**
     * @Description 子账号保存
     * @Author lizhongbao
     * @Date 2019/9/27
     * @Params [pk, dto]
     * @Return java.lang.String
     */
    String saveSub(String pk, SubAccountDto dto);

    /**
     * @Description 保存子账号校验
     * @Author lizhongbao
     * @Date 2019/10/15
     * @Params [id, dto]
     * @Return void
     */
    void saveSubVerify(String id, SubAccountDto dto);

    /**
     * @Description 账号编码是否重复校验
     * @Author lizhongbao
     * @Date 2019/10/28
     * @Params [id, topAccountId, code]
     * @Return java.lang.Boolean
     */
    Boolean verifyCode(String id, String topAccountId, String code);

    /**
     * @Description 获取子账号列表-分页
     * @Author lizhongbao
     * @Date 2019/9/27
     * @Params [dto, page, size]
     * @Return com.deepexi.util.pageHelper.PageBean<com.deepexi.user.domain.vo.SubAccountVo>
     */
    PageBean<SubAccountVo> pageSubVo(SubAccountQueryDto dto, Integer page, Integer size);

    /**
     * @Description 获取账号VO
     * @Author lizhongbao
     * @Date 2019/9/29
     * @Params [accountId]
     * @Return com.deepexi.user.domain.vo.SubAccountVo
     */
    SubAccountVo getSubVoByAccountId(String accountId);
}