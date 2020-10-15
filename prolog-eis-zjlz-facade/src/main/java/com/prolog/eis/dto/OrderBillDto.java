package com.prolog.eis.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

/**
 * @Author wangkang
 * @Description 包装实体
 * @CreateTime 2020-10-14 17:29
 */
public class OrderBillDto {

    @ApiModelProperty("订单号")
    private Integer orderBillId;

    @ApiModelProperty("优先级")
    private Integer wmsOrderPriority;

    @ApiModelProperty("明细条数")
    private Integer count;


    public Integer getOrderBillId() {
        return orderBillId;
    }

    public void setOrderBillId(Integer orderBillId) {
        this.orderBillId = orderBillId;
    }

    public Integer getWmsOrderPriority() {
        return wmsOrderPriority;
    }

    public void setWmsOrderPriority(Integer wmsOrderPriority) {
        this.wmsOrderPriority = wmsOrderPriority;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderBillDto{" +
                "orderBillId=" + orderBillId +
                ", wmsOrderPriority=" + wmsOrderPriority +
                ", count=" + count +
                '}';
    }
}
