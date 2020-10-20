package com.prolog.eis.dto.bz;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 17:19
 */
public class BCPGoodsInfoDTO {

    //订单编号
    private int orderNo;
    //图号
    private String graphNo;
    //商品名称
    private String goodsname;
    //商品编号
    private String goodsNo;

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

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    @Override
    public String toString() {
        return "BCPGoodsInfoDTO{" +
                "orderNo=" + orderNo +
                ", graphNo='" + graphNo + '\'' +
                ", goodsname='" + goodsname + '\'' +
                ", goodsNo='" + goodsNo + '\'' +
                '}';
    }
}
