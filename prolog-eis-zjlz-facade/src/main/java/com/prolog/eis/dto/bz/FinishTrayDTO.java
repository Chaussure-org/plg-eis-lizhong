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
    private String ownerDrawnNo;

    @ApiModelProperty("播种数量")
    private Integer seedCount;

    @ApiModelProperty("订单id")
    private Integer orderBillId;


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

    public String getOwnerDrawnNo() {
        return ownerDrawnNo;
    }

    public void setOwnerDrawnNo(String ownerDrawnNo) {
        this.ownerDrawnNo = ownerDrawnNo;
    }

    public Integer getSeedCount() {
        return seedCount;
    }

    public void setSeedCount(Integer seedCount) {
        this.seedCount = seedCount;
    }

    public Integer getOrderBillId() {
        return orderBillId;
    }

    public void setOrderBillId(Integer orderBillId) {
        this.orderBillId = orderBillId;
    }
}
