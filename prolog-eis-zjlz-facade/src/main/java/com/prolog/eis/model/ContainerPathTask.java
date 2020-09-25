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
@ApiModel ("托盘路径表")
@Table ("container_path_task")
public class ContainerPathTask {

  @Column("id")
  @Id
  @ApiModelProperty("托盘路径ID")
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
  @ApiModelProperty("起点货位")
  private String sourceLocation;

  @Column("target_area")
  @ApiModelProperty("终点区域")
  private String targetArea;

  @Column("target_location")
  @ApiModelProperty("终点货位")
  private String targetLocation;

  @Column("actual_height")
  @ApiModelProperty("容器高度")
  private Integer actualHeight;

  @Column("call_back")
  @ApiModelProperty("回传标识(0无需回传;1需要回传上报)")
  private Integer callBack;

  @Column("task_type")
  @ApiModelProperty("任务类型(0无任务;10 入库;20出库;30待出库;40内部移位)")
  private Integer taskType;

  @Column("task_state")
  @ApiModelProperty("任务状态(0未开始;10待发送任务;20已发送指令;30设备回告开始;40已经离开原位置)")
  private Integer taskState;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("update_time")
  @ApiModelProperty("更新时间")
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

  public String getTargetArea() {
    return targetArea;
  }

  public void setTargetArea(String targetArea) {
    this.targetArea = targetArea;
  }

  public String getTargetLocation() {
    return targetLocation;
  }

  public void setTargetLocation(String targetLocation) {
    this.targetLocation = targetLocation;
  }

  public Integer getActualHeight() {
    return actualHeight;
  }

  public void setActualHeight(Integer actualHeight) {
    this.actualHeight = actualHeight;
  }

  public Integer getCallBack() {
    return callBack;
  }

  public void setCallBack(Integer callBack) {
    this.callBack = callBack;
  }

  public Integer getTaskType() {
    return taskType;
  }

  public void setTaskType(Integer taskType) {
    this.taskType = taskType;
  }

  public Integer getTaskState() {
    return taskState;
  }

  public void setTaskState(Integer taskState) {
    this.taskState = taskState;
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
