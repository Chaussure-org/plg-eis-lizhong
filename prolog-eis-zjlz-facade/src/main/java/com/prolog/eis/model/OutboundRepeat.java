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
@ApiModel ("出库重发消息表")
@Table ("outbound_repeat")
public class OutboundRepeat {

  @Column("id")
  @Id
  @ApiModelProperty("id")
  private Integer id;

  @Column("msg_id")
  @ApiModelProperty("消息id")
  private String msgId;

  @Column("task_type")
  @ApiModelProperty("出库回告类型 10整托出库回告 20整件出库回告 30订单框订单完成回告 40 b2b拆零回告")
  private Integer taskType;

  @Column("track_no")
  @ApiModelProperty("跟踪号，tasktype = 10时为托盘号，tasktype = 20时为eis打印的标签码，tasktype = 30时为订单框号,tasktype = 40时为eis打印的标签码")
  private String trackNo;

  @Column("unique_code")
  @ApiModelProperty("tasktype = 10时为托盘号，tasktype = 20时为整件唯一码,tasktype = 30时为空字符串,tasktype = 40时为空字符串")
  private String uniqueCode;

  @Column("wms_detail_id")
  @ApiModelProperty("wms订单明细")
  private String wmsDetailId;

  @Column("task_id")
  @ApiModelProperty("wms出库任务号")
  private String taskId;

  @Column("order_no")
  @ApiModelProperty("订单号")
  private String orderNo;

  @Column("owner_id")
  @ApiModelProperty("业主")
  private String ownerId;

  @Column("item_id")
  @ApiModelProperty("商品编码")
  private String itemId;

  @Column("lot_id")
  @ApiModelProperty("批次号")
  private String lotId;

  @Column("item_order_no")
  @ApiModelProperty("商品订单号")
  private String itemOrderNo;

  @Column("qty")
  @ApiModelProperty("明细出库数量")
  private Integer qty;

  @Column("second_datails")
  @ApiModelProperty("第二层明细")
  private String secondDatails;

  @Column("send_state")
  @ApiModelProperty("发送状态0:待发送,1:发送失败,2:发送成功,3:标记失效")
  private Integer sendState;

  @Column("send_count")
  @ApiModelProperty("发送次数")
  private Integer sendCount;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("last_send_time")
  @ApiModelProperty("最后一次发送时间")
  private java.util.Date lastSendTime;

  @Column("error_msg")
  @ApiModelProperty("最后一次发送失败时返回的消息")
  private String errorMsg;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMsgId() {
    return msgId;
  }

  public void setMsgId(String msgId) {
    this.msgId = msgId;
  }

  public Integer getTaskType() {
    return taskType;
  }

  public void setTaskType(Integer taskType) {
    this.taskType = taskType;
  }

  public String getTrackNo() {
    return trackNo;
  }

  public void setTrackNo(String trackNo) {
    this.trackNo = trackNo;
  }

  public String getUniqueCode() {
    return uniqueCode;
  }

  public void setUniqueCode(String uniqueCode) {
    this.uniqueCode = uniqueCode;
  }

  public String getWmsDetailId() {
    return wmsDetailId;
  }

  public void setWmsDetailId(String wmsDetailId) {
    this.wmsDetailId = wmsDetailId;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getLotId() {
    return lotId;
  }

  public void setLotId(String lotId) {
    this.lotId = lotId;
  }

  public String getItemOrderNo() {
    return itemOrderNo;
  }

  public void setItemOrderNo(String itemOrderNo) {
    this.itemOrderNo = itemOrderNo;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }

  public String getSecondDatails() {
    return secondDatails;
  }

  public void setSecondDatails(String secondDatails) {
    this.secondDatails = secondDatails;
  }

  public Integer getSendState() {
    return sendState;
  }

  public void setSendState(Integer sendState) {
    this.sendState = sendState;
  }

  public Integer getSendCount() {
    return sendCount;
  }

  public void setSendCount(Integer sendCount) {
    this.sendCount = sendCount;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

  public java.util.Date getLastSendTime() {
    return lastSendTime;
  }

  public void setLastSendTime(java.util.Date lastSendTime) {
    this.lastSendTime = lastSendTime;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

}
