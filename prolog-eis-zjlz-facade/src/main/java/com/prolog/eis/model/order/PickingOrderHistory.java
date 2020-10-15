package com.prolog.eis.model.order;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-10-15 
 */
@ApiModel ("拣选单历史表")
@Table ("picking_order_history")
public class PickingOrderHistory {

  @Column("id")
  @Id
  @ApiModelProperty("拣选单ID")
  private Integer id;

  @Column("station_id")
  @ApiModelProperty("站台ID")
  private Integer stationId;

  @Column("picking_order_no")
  @ApiModelProperty("拣选单编号")
  private String pickingOrderNo;

  @Column("order_state")
  @ApiModelProperty("作业状态")
  private Integer orderState;

  @Column("order_start_time")
  @ApiModelProperty("订单开始时间")
  private java.util.Date orderStartTime;

  @Column("pick_start_time")
  @ApiModelProperty("拣选开始时间")
  private java.util.Date pickStartTime;

  @Column("pick_complete_time")
  @ApiModelProperty("拣选完成时间")
  private java.util.Date pickCompleteTime;

  @Column("current_seed_box_no")
  @ApiModelProperty("当前播种料箱号")
  private String currentSeedBoxNo;

  @Column("seed_start_time")
  @ApiModelProperty("播种开始时间")
  private java.util.Date seedStartTime;

  @Column("seed_complete_time")
  @ApiModelProperty("播种完成时间")
  private java.util.Date seedCompleteTime;

  @Column("is_all_arrived")
  @ApiModelProperty("是否全部到达（0:否 ,1:是）")
  private Integer isAllArrived;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("update_time")
  @ApiModelProperty("修改时间")
  private java.util.Date updateTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getStationId() {
    return stationId;
  }

  public void setStationId(Integer stationId) {
    this.stationId = stationId;
  }

  public String getPickingOrderNo() {
    return pickingOrderNo;
  }

  public void setPickingOrderNo(String pickingOrderNo) {
    this.pickingOrderNo = pickingOrderNo;
  }

  public Integer getOrderState() {
    return orderState;
  }

  public void setOrderState(Integer orderState) {
    this.orderState = orderState;
  }

  public java.util.Date getOrderStartTime() {
    return orderStartTime;
  }

  public void setOrderStartTime(java.util.Date orderStartTime) {
    this.orderStartTime = orderStartTime;
  }

  public java.util.Date getPickStartTime() {
    return pickStartTime;
  }

  public void setPickStartTime(java.util.Date pickStartTime) {
    this.pickStartTime = pickStartTime;
  }

  public java.util.Date getPickCompleteTime() {
    return pickCompleteTime;
  }

  public void setPickCompleteTime(java.util.Date pickCompleteTime) {
    this.pickCompleteTime = pickCompleteTime;
  }

  public String getCurrentSeedBoxNo() {
    return currentSeedBoxNo;
  }

  public void setCurrentSeedBoxNo(String currentSeedBoxNo) {
    this.currentSeedBoxNo = currentSeedBoxNo;
  }

  public java.util.Date getSeedStartTime() {
    return seedStartTime;
  }

  public void setSeedStartTime(java.util.Date seedStartTime) {
    this.seedStartTime = seedStartTime;
  }

  public java.util.Date getSeedCompleteTime() {
    return seedCompleteTime;
  }

  public void setSeedCompleteTime(java.util.Date seedCompleteTime) {
    this.seedCompleteTime = seedCompleteTime;
  }

  public Integer getIsAllArrived() {
    return isAllArrived;
  }

  public void setIsAllArrived(Integer isAllArrived) {
    this.isAllArrived = isAllArrived;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

  public java.util.Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.util.Date updateTime) {
    this.updateTime = updateTime;
  }

}
