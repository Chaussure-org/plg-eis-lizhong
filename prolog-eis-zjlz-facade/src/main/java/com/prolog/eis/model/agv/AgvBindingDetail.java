package com.prolog.eis.model.agv;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
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

    @Column("goodsId")
    @ApiModelProperty("商品id")
    private Integer goodsId;

    @Column("binding_num")
    @ApiModelProperty("订单优先级")
    private Integer bindingNum;

    @Column("order_priority")
    @ApiModelProperty("订单优先级")
    private Integer orderPriority;

    @Column("update_time")
    @ApiModelProperty("订单明细id")
    private Date updateTime;

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
