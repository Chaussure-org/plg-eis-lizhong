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
@ApiModel ("入库重发消息表")
@Table ("inbound_repeat")
public class InboundRepeat {

  @Column("id")
  @Id
  @ApiModelProperty("id")
  private Integer id;

  @Column("msg_id")
  @ApiModelProperty("消息id")
  private String msgId;

  @Column("task_id")
  @ApiModelProperty("任务id")
  private String taskId;

  @Column("container_no")
  @ApiModelProperty("托盘号")
  private String containerNo;

  @Column("qty")
  @ApiModelProperty("数量")
  private Integer qty;

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

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
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
