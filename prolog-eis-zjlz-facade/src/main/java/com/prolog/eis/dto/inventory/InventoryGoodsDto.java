package com.prolog.eis.dto.inventory;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/27 12:05
 * 盘点商品容器详情
 */
public class InventoryGoodsDto {

    @ApiModelProperty("容器名称")
    private String containerNo;

    @ApiModelProperty("商品条码")
    private String goodsNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("原始数量")
    private Integer originalCount;

    @ApiModelProperty("商品图号")
    private String ownerDrawnNo;

    public String getOwnerDrawnNo() {
        return ownerDrawnNo;
    }

    public void setOwnerDrawnNo(String ownerDrawnNo) {
        this.ownerDrawnNo = ownerDrawnNo;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getOriginalCount() {
        return originalCount;
    }

    public void setOriginalCount(Integer originalCount) {
        this.originalCount = originalCount;
    }

    @Override
    public String toString() {
        return "InventoryGoodsDto{" +
                "containerNo='" + containerNo + '\'' +
                ", goodsNo='" + goodsNo + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", originalCount=" + originalCount +
                ", ownerDrawnNo='" + ownerDrawnNo + '\'' +
                '}';
    }
}
