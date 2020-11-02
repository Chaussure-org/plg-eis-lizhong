package com.prolog.eis.model.order;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-10-26 
 */
@ApiModel ("订单汇总表")
@Table ("order_bill_history")
public class OrderBillHistory {

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
  @ApiModelProperty("订单类型")
  private String orderType;

  @Column("order_priority")
  @ApiModelProperty("订单优先级")
  private Integer orderPriority;

  @Column("wms_order_priority")
  @ApiModelProperty("wms任务优先级")
  private Integer wmsOrderPriority;

  @Column("order_task_state")
  @ApiModelProperty("订单任务进度（0创建 10 开始出库 2出库中 30出库完成）")
  private Integer orderTaskState;

  @Column("order_area")
  @ApiModelProperty("区域")
  private String orderArea;

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

  @Column("out_time")
  @ApiModelProperty("出库截止时间，越靠小越先出")
  private java.util.Date outTime;

  @Column("task_id")
  @ApiModelProperty("任务id")
  private String taskId;

  @Column("bill_date")
  @ApiModelProperty("单据日期")
  private java.util.Date billDate;

  @Column("branch_type")
  @ApiModelProperty("移库类型）")
  private String branchType;


  public String getBranchType() {
    return branchType;
  }

  public void setBranchType(String branchType) {
    this.branchType = branchType;
  }

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

  public Integer getWmsOrderPriority() {
    return wmsOrderPriority;
  }

  public void setWmsOrderPriority(Integer wmsOrderPriority) {
    this.wmsOrderPriority = wmsOrderPriority;
  }

  public Integer getOrderTaskState() {
    return orderTaskState;
  }

  public void setOrderTaskState(Integer orderTaskState) {
    this.orderTaskState = orderTaskState;
  }

  public String getOrderArea() {
    return orderArea;
  }

  public void setOrderArea(String orderArea) {
    this.orderArea = orderArea;
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

  public java.util.Date getOutTime() {
    return outTime;
  }

  public void setOutTime(java.util.Date outTime) {
    this.outTime = outTime;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public java.util.Date getBillDate() {
    return billDate;
  }

  public void setBillDate(java.util.Date billDate) {
    this.billDate = billDate;
  }

}
