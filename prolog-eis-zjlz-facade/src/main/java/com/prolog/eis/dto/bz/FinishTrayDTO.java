package com.prolog.eis.dto.bz;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/19 0:10
 * 成品拖拣选信息dto
 */

public class FinishTrayDTO {

    @ApiModelProperty("出库单号")
    private String orderNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品编码")
    private String goodsNo;

    @ApiModelProperty("播种数量")
    private int seedCount;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public int getSeedCount() {
        return seedCount;
    }

    public void setSeedCount(int seedCount) {
        this.seedCount = seedCount;
    }
}
