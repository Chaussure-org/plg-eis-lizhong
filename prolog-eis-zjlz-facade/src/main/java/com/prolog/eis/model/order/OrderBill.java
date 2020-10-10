package com.prolog.eis.model.order;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description
 * @Author Hunter
 * @Date 2020-09-25
 */
@ApiModel("订单汇总表")
@Table("order_bill")
public class OrderBill {

    public static final int FIRST_PRIORITY = 1;
    public static final int SECOND_PRIORITY = 2;
    public static final int THIRD_PRIORITY = 3;
    public static final int ORDER_STATUS_START_OUT=10;
    public static final int ORDER_STATUS_OUTING=20;
    public static final int ORDER_STATUS_FINISH=30;
    @Column("id")
    @Id
    @ApiModelProperty("订单ID")
    private Integer id;

    @Column("picking_order_id")
    @ApiModelProperty("拣选单ID")
    private Integer pickingOrderId;

    @Column("order_no")
    @ApiModelProperty("订单编号")
    private String orderNo;

    @Column("order_type")
    @ApiModelProperty("订单类型）")
    private String orderType;

    @Column("order_priority")
    @ApiModelProperty("订单优先级")
    private Integer orderPriority;

    @Column("order_task_state")
    @ApiModelProperty("订单任务进度（0创建 10 开始出库 20 出库中 30 完成）")
    private Integer orderTaskState;

    @Column("receiver")
    @ApiModelProperty("收货人")
    private String receiver;

    @Column("start_time")
    @ApiModelProperty("开始入库时间")
    private java.util.Date startTime;

    @Column("create_time")
    @ApiModelProperty("创建时间")
    private java.util.Date createTime;

    @Column("complete_time")
    @ApiModelProperty("完成时间")
    private java.util.Date completeTime;

    @Column("is_add_pool")
    @ApiModelProperty("是否加入订单池 0 未加入  1 已加入")
    private Integer isAddPool;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPickingOrderId() {
        return pickingOrderId;
    }

    public void setPickingOrderId(Integer pickingOrderId) {
        this.pickingOrderId = pickingOrderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(Integer orderPriority) {
        this.orderPriority = orderPriority;
    }

    public Integer getOrderTaskState() {
        return orderTaskState;
    }

    public void setOrderTaskState(Integer orderTaskState) {
        this.orderTaskState = orderTaskState;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public java.util.Date getStartTime() {
        return startTime;
    }

    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(java.util.Date completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getIsAddPool() {
        return isAddPool;
    }

    public void setIsAddPool(Integer isAddPool) {
        this.isAddPool = isAddPool;
    }

}
