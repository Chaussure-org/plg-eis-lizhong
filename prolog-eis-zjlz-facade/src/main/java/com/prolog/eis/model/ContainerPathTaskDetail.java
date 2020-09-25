package com.prolog.eis.model;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-09-25 
 */
@ApiModel ("出库在途容器表")
@Table ("container_path_task_detail")
public class ContainerPathTaskDetail {

  @Column("ID")
  @Id
  private Integer id;

  @Column("pallet_no")
  @ApiModelProperty("载具编号")
  private String palletNo;

  @Column("container_no")
  @ApiModelProperty("托盘号")
  private String containerNo;

  @Column("source_area")
  @ApiModelProperty("起点区域")
  private String sourceArea;

  @Column("source_location")
  @ApiModelProperty("起点")
  private String sourceLocation;

  @Column("next_area")
  @ApiModelProperty("下一个点位区域")
  private String nextArea;

  @Column("next_location")
  @ApiModelProperty("下一个点位")
  private String nextLocation;

  @Column("task_state")
  @ApiModelProperty("路径状态-1未开始  0到位 10申请载具 20申请载具开始 30载具到位 40开始绑定新载具 50给设备发送移动指令 60移动开始")
  private Integer taskState;

  @Column("task_id")
  @ApiModelProperty("设备任务Id")
  private String taskId;

  @Column("device_no")
  @ApiModelProperty("执行设备编号")
  private String deviceNo;

  @Column("sort_index")
  @ApiModelProperty("任务序号 第一条为 1 后续递增")
  private Integer sortIndex;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("arrive_time")
  @ApiModelProperty("到位时间")
  private java.util.Date arriveTime;

  @Column("apply_time")
  @ApiModelProperty("申请载具时间")
  private java.util.Date applyTime;

  @Column("apply_start_time")
  @ApiModelProperty("申请载具开始时间")
  private java.util.Date applyStartTime;

  @Column("pallet_arrive_time")
  @ApiModelProperty("载具到位时间")
  private java.util.Date palletArriveTime;

  @Column("binding_pallet_time")
  @ApiModelProperty("绑定新载具时间")
  private java.util.Date bindingPalletTime;

  @Column("binding_start_time")
  @ApiModelProperty("绑定开始时间")
  private java.util.Date bindingStartTime;

  @Column("update_time")
  @ApiModelProperty("修改时间")
  private java.util.Date updateTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPalletNo() {
    return palletNo;
  }

  public void setPalletNo(String palletNo) {
    this.palletNo = palletNo;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getSourceArea() {
    return sourceArea;
  }

  public void setSourceArea(String sourceArea) {
    this.sourceArea = sourceArea;
  }

  public String getSourceLocation() {
    return sourceLocation;
  }

  public void setSourceLocation(String sourceLocation) {
    this.sourceLocation = sourceLocation;
  }

  public String getNextArea() {
    return nextArea;
  }

  public void setNextArea(String nextArea) {
    this.nextArea = nextArea;
  }

  public String getNextLocation() {
    return nextLocation;
  }

  public void setNextLocation(String nextLocation) {
    this.nextLocation = nextLocation;
  }

  public Integer getTaskState() {
    return taskState;
  }

  public void setTaskState(Integer taskState) {
    this.taskState = taskState;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getDeviceNo() {
    return deviceNo;
  }

  public void setDeviceNo(String deviceNo) {
    this.deviceNo = deviceNo;
  }

  public Integer getSortIndex() {
    return sortIndex;
  }

  public void setSortIndex(Integer sortIndex) {
    this.sortIndex = sortIndex;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

  public java.util.Date getArriveTime() {
    return arriveTime;
  }

  public void setArriveTime(java.util.Date arriveTime) {
    this.arriveTime = arriveTime;
  }

  public java.util.Date getApplyTime() {
    return applyTime;
  }

  public void setApplyTime(java.util.Date applyTime) {
    this.applyTime = applyTime;
  }

  public java.util.Date getApplyStartTime() {
    return applyStartTime;
  }

  public void setApplyStartTime(java.util.Date applyStartTime) {
    this.applyStartTime = applyStartTime;
  }

  public java.util.Date getPalletArriveTime() {
    return palletArriveTime;
  }

  public void setPalletArriveTime(java.util.Date palletArriveTime) {
    this.palletArriveTime = palletArriveTime;
  }

  public java.util.Date getBindingPalletTime() {
    return bindingPalletTime;
  }

  public void setBindingPalletTime(java.util.Date bindingPalletTime) {
    this.bindingPalletTime = bindingPalletTime;
  }

  public java.util.Date getBindingStartTime() {
    return bindingStartTime;
  }

  public void setBindingStartTime(java.util.Date bindingStartTime) {
    this.bindingStartTime = bindingStartTime;
  }

  public java.util.Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.util.Date updateTime) {
    this.updateTime = updateTime;
  }

}
