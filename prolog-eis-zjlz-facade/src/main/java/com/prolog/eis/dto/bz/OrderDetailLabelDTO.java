package com.prolog.eis.dto.bz;

import io.swagger.models.auth.In;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/13 18:42
 *
 *
 * 订单拖商品贴标查询dto
 */
public class OrderDetailLabelDTO {

    private Integer goodsId;

    private Integer pastLabelFlg;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getPastLabelFlg() {
        return pastLabelFlg;
    }

    public void setPastLabelFlg(Integer pastLabelFlg) {
        this.pastLabelFlg = pastLabelFlg;
    }
}
