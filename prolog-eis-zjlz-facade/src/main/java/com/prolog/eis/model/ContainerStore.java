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
@ApiModel ("托盘库存表")
@Table ("container_store")
public class ContainerStore {


  /**
   * 出库
   */
  public static final Integer TASK_TYPE_OUTBOUND = 20;
  @Column("id")
  @Id
  @ApiModelProperty("托盘库存ID")
  private Integer id;

  @Column("container_no")
  @ApiModelProperty("托盘号")
  private String containerNo;

  @Column("container_type")
  @ApiModelProperty("托盘类型(-1空托剁;1任务托)")
  private String containerType;

  @Column("task_type")
  @ApiModelProperty("任务类型(0无业务任务;10;入库;11补货入库;12移库入库;20出库;21盘点出库;22移库出库)")
  private Integer taskType;

  @Column("work_count")
  @ApiModelProperty("作业次数")
  private Integer workCount;

  @Column("owner_id")
  @ApiModelProperty("业主")
  private String ownerId;

  @Column("goods_id")
  @ApiModelProperty("商品ID")
  private Integer goodsId;

  @Column("lot_id")
  @ApiModelProperty("批次")
  private String lotId;

  @Column("goods_order_no")
  @ApiModelProperty("商品订单号")
  private String goodsOrderNo;

  @Column("qty")
  @ApiModelProperty("库存数量")
  private Integer qty;

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

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getContainerType() {
    return containerType;
  }

  public void setContainerType(String containerType) {
    this.containerType = containerType;
  }

  public Integer getTaskType() {
    return taskType;
  }

  public void setTaskType(Integer taskType) {
    this.taskType = taskType;
  }

  public Integer getWorkCount() {
    return workCount;
  }

  public void setWorkCount(Integer workCount) {
    this.workCount = workCount;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

  public String getLotId() {
    return lotId;
  }

  public void setLotId(String lotId) {
    this.lotId = lotId;
  }

  public String getGoodsOrderNo() {
    return goodsOrderNo;
  }

  public void setGoodsOrderNo(String goodsOrderNo) {
    this.goodsOrderNo = goodsOrderNo;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
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
