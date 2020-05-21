package com.deepexi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.user.domain.eo.SendTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * @author honchungen
 * @date 2019/20/12 10:08
 */
public interface SendTemplateMapper extends BaseMapper<SendTemplate> {
    /**
     * 根据code获取消息模板
     * @param code
     * @return
     */
    SendTemplate selectByCode(@Param("code") String code);
}
