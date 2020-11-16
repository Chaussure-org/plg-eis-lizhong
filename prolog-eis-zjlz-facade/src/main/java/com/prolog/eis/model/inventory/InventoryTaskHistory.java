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
@ApiModel ("盘点计划表历史表")
@Table ("inventory_task_history")
public class InventoryTaskHistory {

  @Column("id")
  @Id
  @ApiModelProperty("盘点计划id")
  private Integer id;

  @Column("bill_no")
  @ApiModelProperty("单据编号")
  private String billNo;

  @Column("seq_no")
  @ApiModelProperty("行号")
  private String seqNo;

  @Column("goods_id")
  @ApiModelProperty("商品id")
  private Integer goodsId;

  @Column("goods_type")
  @ApiModelProperty("商品类别")
  private String goodsType;

  @Column("bill_date")
  @ApiModelProperty("单据日期")
  private java.util.Date billDate;

  @Column("inventory_state")
  @ApiModelProperty("盘点状态 10:已创建 20:已下发 30:已完成 40:上传完成")
  private Integer inventoryState;

  @Column("inventory_type")
  @ApiModelProperty("盘点类型 1eis内部盘点  2wms盘点")
  private Integer inventoryType;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("issue_time")
  @ApiModelProperty("下发时间")
  private java.util.Date issueTime;

  @Column("start_time")
  @ApiModelProperty("开始时间")
  private java.util.Date startTime;

  @Column("end_time")
  @ApiModelProperty("任务完成时间")
  private java.util.Date endTime;

  @Column("task_id")
  @ApiModelProperty("wms任务id")
  private String taskId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getBillNo() {
    return billNo;
  }

  public void setBillNo(String billNo) {
    this.billNo = billNo;
  }

  public String getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(String seqNo) {
    this.seqNo = seqNo;
  }

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

  public String getGoodsType() {
    return goodsType;
  }

  public void setGoodsType(String goodsType) {
    this.goodsType = goodsType;
  }

  public java.util.Date getBillDate() {
    return billDate;
  }

  public void setBillDate(java.util.Date billDate) {
    this.billDate = billDate;
  }

  public Integer getInventoryState() {
    return inventoryState;
  }

  public void setInventoryState(Integer inventoryState) {
    this.inventoryState = inventoryState;
  }

  public Integer getInventoryType() {
    return inventoryType;
  }

  public void setInventoryType(Integer inventoryType) {
    this.inventoryType = inventoryType;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

  public java.util.Date getIssueTime() {
    return issueTime;
  }

  public void setIssueTime(java.util.Date issueTime) {
    this.issueTime = issueTime;
  }

  public java.util.Date getStartTime() {
    return startTime;
  }

  public void setStartTime(java.util.Date startTime) {
    this.startTime = startTime;
  }

  public java.util.Date getEndTime() {
    return endTime;
  }

  public void setEndTime(java.util.Date endTime) {
    this.endTime = endTime;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

}
