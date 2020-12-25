package com.prolog.eis.model.agv;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Ignore;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


/**
 * @Description
 * @Author SunPP
 * @Date 2020-09-29
 */
@ApiModel("null")
@Table("agv_binding_detail")
public class AgvBindingDetail {

    @Column("container_no")
    @ApiModelProperty("容器编号")
    private String containerNo;

    @Column("order_bill_id")
    @ApiModelProperty("订单明细id")
    private Integer orderBillId;


    @Column("order_mx_id")
    @ApiModelProperty("订单明细id")
    private Integer orderMxId;

    @Column("goods_id")
    @ApiModelProperty("商品id")
    private Integer goodsId;

    @Column("binding_num")
    @ApiModelProperty("绑定数量")
    private Integer bindingNum;

    @Column("order_priority")
    @ApiModelProperty("订单优先级")
    private Integer orderPriority;


    @Column("wms_order_priority")
    @ApiModelProperty("wms订单优先级")
    private Integer wmsOrderPriority;


    @Column("detail_status")
    @ApiModelProperty("agv绑定明细状态")
    private Integer detailStatus;

    @Column("update_time")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("1-是 2-不是")
    @Ignore
    private int ironTray;

    public int getIronTray() {
        return ironTray;
    }

    public void setIronTray(int ironTray) {
        this.ironTray = ironTray;
    }


    public Integer getWmsOrderPriority() {
        return wmsOrderPriority;
    }

    public Integer getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(Integer detailStatus) {
        this.detailStatus = detailStatus;
    }

    public void setWmsOrderPriority(Integer wmsOrderPriority) {
        this.wmsOrderPriority = wmsOrderPriority;
    }

    public Integer getBindingNum() {
        return bindingNum;
    }

    public void setBindingNum(Integer bindingNum) {
        this.bindingNum = bindingNum;
    }

    public Integer getOrderBillId() {
        return orderBillId;
    }

    public void setOrderBillId(Integer orderBillId) {
        this.orderBillId = orderBillId;
    }

    public Integer getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(Integer orderPriority) {
        this.orderPriority = orderPriority;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public Integer getOrderMxId() {
        return orderMxId;
    }

    public void setOrderMxId(Integer orderMxId) {
        this.orderMxId = orderMxId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

}
