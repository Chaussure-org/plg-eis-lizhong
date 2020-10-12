package com.prolog.eis.dto.bz;

import java.io.Serializable;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 14:13
 * <p>
 * 半成品拣选播种作业DTO
 */
public class BCPPcikingDTO {
    //订单id
    private int orderId;

    //订单编号
    private int orderNo;
    //图号
    private String graphNo;
    //商品id
    private int goodsId;
    //商品编号
    private String goodsNo;
    //商品拣选数量
    private int pickNum;
    //剩余拣选的商品条数
    private int surplusOrderDetailCount;


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getGraphNo() {
        return graphNo;
    }

    public void setGraphNo(String graphNo) {
        this.graphNo = graphNo;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public int getPickNum() {
        return pickNum;
    }

    public void setPickNum(int pickNum) {
        this.pickNum = pickNum;
    }

    public int getSurplusOrderDetailCount() {
        return surplusOrderDetailCount;
    }

    public void setSurplusOrderDetailCount(int surplusOrderDetailCount) {
        this.surplusOrderDetailCount = surplusOrderDetailCount;
    }
}
