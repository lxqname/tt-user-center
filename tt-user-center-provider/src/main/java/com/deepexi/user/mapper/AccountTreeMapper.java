package com.deepexi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.user.domain.dto.AccountTreeDto;
import com.deepexi.user.domain.dto.SubAccountQueryDto;
import com.deepexi.user.domain.eo.AccountTree;
import com.deepexi.user.domain.vo.SubAccountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface AccountTreeMapper extends BaseMapper<AccountTree> {

    List<AccountTree> findList(@Param("eo") AccountTreeDto eo);

    int deleteByIds(@Param("pks") String... pks);

    int batchInsert(@Param("eo") List<AccountTreeDto> eo);

    int batchUpdate(@Param("eo") List<AccountTreeDto> eo);

    List<AccountTree> listSubByPId(@Param("eo") AccountTreeDto eo);

    /**
     * @Description 获取子节点ID
     * @Author lizhongbao
     * @Date 2019/9/27
     * @Params [id, tenantCode]
     * @Return java.util.List<java.lang.String>
     */
    List<String> listSub(@Param("id") String id, @Param("tenantCode") String tenantCode);

    /**
     * @Description 获取子节点ID
     * @Author lizhongbao
     * @Date 2019/9/27
     * @Params [id, tenantCode]
     * @Return java.util.List<java.lang.String>
     */
    List<SubAccountVo> listSubVo(@Param("dto") SubAccountQueryDto dto, @Param("tenantCode") String tenantCode);

    /**
     * @Description 获取账号VO
     * @Author lizhongbao
     * @Date 2019/9/29
     * @Params [accountId]
     * @Return com.deepexi.user.domain.vo.SubAccountVo
     */
    SubAccountVo getSubVoByAccountId(@Param("accountId") String accountId);

    /**
     * @Description 账号编码是否重复校验
     * @Author lizhongbao
     * @Date 2019/10/28
     * @Params [id, topAccountId, code, tenantCode]
     * @Return java.lang.Integer
     */
    Integer verifyCode(@Param("id") String id, @Param("topAccountId") String topAccountId, @Param("code") String code, @Param("tenantCode") String tenantCode);
}
