package com.prolog.eis.model.inventory;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-11-13 
 */
@ApiModel ("盘点明细")
@Table ("inventory_task_detail_history")
public class InventoryTaskDetailHistory {

  @Column("id")
  @Id
  @ApiModelProperty("盘点明细id")
  private Integer id;

  @Column("inventory_task_id")
  @ApiModelProperty("盘点计划id")
  private Integer inventoryTaskId;

  @Column("container_no")
  @ApiModelProperty("容器号")
  private String containerNo;

  @Column("goods_no")
  @ApiModelProperty("商品条码")
  private String goodsNo;

  @Column("goods_name")
  @ApiModelProperty("商品名称")
  private String goodsName;

  @Column("original_count")
  @ApiModelProperty("原始数量")
  private Integer originalCount;

  @Column("modify_count")
  @ApiModelProperty("修改数量")
  private Integer modifyCount;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("task_state")
  @ApiModelProperty("任务状态 0新建 10下发 20已出库 30进行中 40已完成")
  private Integer taskState;

  @Column("publish_time")
  @ApiModelProperty("下发时间")
  private java.util.Date publishTime;

  @Column("outbound_time")
  @ApiModelProperty("出库时间")
  private java.util.Date outboundTime;

  @Column("end_time")
  @ApiModelProperty("盘点结束时间")
  private java.util.Date endTime;

  @Column("finish_reason")
  @ApiModelProperty("结束原因")
  private String finishReason;

  @Column("pd_type")
  @ApiModelProperty("盘点类型")
  private Integer pdType;

  @Column("station_id")
  @ApiModelProperty("站台id")
  private Integer stationId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getInventoryTaskId() {
    return inventoryTaskId;
  }

  public void setInventoryTaskId(Integer inventoryTaskId) {
    this.inventoryTaskId = inventoryTaskId;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getGoodsNo() {
    return goodsNo;
  }

  public void setGoodsNo(String goodsNo) {
    this.goodsNo = goodsNo;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public Integer getOriginalCount() {
    return originalCount;
  }

  public void setOriginalCount(Integer originalCount) {
    this.originalCount = originalCount;
  }

  public Integer getModifyCount() {
    return modifyCount;
  }

  public void setModifyCount(Integer modifyCount) {
    this.modifyCount = modifyCount;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

  public Integer getTaskState() {
    return taskState;
  }

  public void setTaskState(Integer taskState) {
    this.taskState = taskState;
  }

  public java.util.Date getPublishTime() {
    return publishTime;
  }

  public void setPublishTime(java.util.Date publishTime) {
    this.publishTime = publishTime;
  }

  public java.util.Date getOutboundTime() {
    return outboundTime;
  }

  public void setOutboundTime(java.util.Date outboundTime) {
    this.outboundTime = outboundTime;
  }

  public java.util.Date getEndTime() {
    return endTime;
  }

  public void setEndTime(java.util.Date endTime) {
    this.endTime = endTime;
  }

  public String getFinishReason() {
    return finishReason;
  }

  public void setFinishReason(String finishReason) {
    this.finishReason = finishReason;
  }

  public Integer getPdType() {
    return pdType;
  }

  public void setPdType(Integer pdType) {
    this.pdType = pdType;
  }

  public Integer getStationId() {
    return stationId;
  }

  public void setStationId(Integer stationId) {
    this.stationId = stationId;
  }

}
