package com.deepexi.user.domain.vo;

import com.deepexi.user.domain.dto.KongConsumer;

import java.util.List;

/**
 * @author: lizhongbao
 * @Description: jwt VO
 * @date: 2019-9-21 16:50:57
 */
public class JwtVo {

    private Integer total;

    private List<KongConsumer> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<KongConsumer> getData() {
        return data;
    }

    public void setData(List<KongConsumer> data) {
        this.data = data;
    }
}
