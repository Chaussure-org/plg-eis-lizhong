package com.prolog.eis.model.order;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2021-01-28 
 */
@ApiModel ("订单明细表")
@Table ("order_detail_history")
public class OrderDetailHistory {

  @Column("id")
  @Id
  @ApiModelProperty("订单明细id")
  private Integer id;

  @Column("order_bill_id")
  @ApiModelProperty("订单id")
  private Integer orderBillId;

  @Column("wms_detail_id")
  @ApiModelProperty("wms明细id")
  private String wmsDetailId;

  @Column("owner_id")
  @ApiModelProperty("业主")
  private String ownerId;

  @Column("lot_id")
  @ApiModelProperty("批次号")
  private String lotId;

  @Column("goods_id")
  @ApiModelProperty("商品id")
  private Integer goodsId;

  @Column("area_no")
  @ApiModelProperty("区域")
  private String areaNo;

  @Column("goods_order_no")
  @ApiModelProperty("商品订单号")
  private String goodsOrderNo;

  @Column("plan_qty")
  @ApiModelProperty("计划数量")
  private Integer planQty;

  @Column("has_pick_qty")
  @ApiModelProperty("已拣选数量")
  private Integer hasPickQty;

  @Column("complete_qty")
  @ApiModelProperty("已完成数量")
  private Integer completeQty;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("update_time")
  @ApiModelProperty("修改时间")
  private java.util.Date updateTime;

  @Column("tray_plan_qty")
  @ApiModelProperty("堆垛机区域计划出库数量")
  private Integer trayPlanQty;

  @Column("out_qty")
  @ApiModelProperty("成品库出库数量")
  private Integer outQty;

  @Column("task_id")
  @ApiModelProperty("任务id")
  private String taskId;

  @Column("special")
  @ApiModelProperty("是否是订单拖（麦头）1是订单拖 2 不是")
  private Integer special;

  @Column("decals")
  @ApiModelProperty("是否贴标（1 贴标  2 非贴标）")
  private Integer decals;

  @Column("wheat_head")
  @ApiModelProperty("麦头")
  private String wheatHead;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getOrderBillId() {
    return orderBillId;
  }

  public void setOrderBillId(Integer orderBillId) {
    this.orderBillId = orderBillId;
  }

  public String getWmsDetailId() {
    return wmsDetailId;
  }

  public void setWmsDetailId(String wmsDetailId) {
    this.wmsDetailId = wmsDetailId;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getLotId() {
    return lotId;
  }

  public void setLotId(String lotId) {
    this.lotId = lotId;
  }

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

  public String getAreaNo() {
    return areaNo;
  }

  public void setAreaNo(String areaNo) {
    this.areaNo = areaNo;
  }

  public String getGoodsOrderNo() {
    return goodsOrderNo;
  }

  public void setGoodsOrderNo(String goodsOrderNo) {
    this.goodsOrderNo = goodsOrderNo;
  }

  public Integer getPlanQty() {
    return planQty;
  }

  public void setPlanQty(Integer planQty) {
    this.planQty = planQty;
  }

  public Integer getHasPickQty() {
    return hasPickQty;
  }

  public void setHasPickQty(Integer hasPickQty) {
    this.hasPickQty = hasPickQty;
  }

  public Integer getCompleteQty() {
    return completeQty;
  }

  public void setCompleteQty(Integer completeQty) {
    this.completeQty = completeQty;
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

  public Integer getTrayPlanQty() {
    return trayPlanQty;
  }

  public void setTrayPlanQty(Integer trayPlanQty) {
    this.trayPlanQty = trayPlanQty;
  }

  public Integer getOutQty() {
    return outQty;
  }

  public void setOutQty(Integer outQty) {
    this.outQty = outQty;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public Integer getSpecial() {
    return special;
  }

  public void setSpecial(Integer special) {
    this.special = special;
  }

  public Integer getDecals() {
    return decals;
  }

  public void setDecals(Integer decals) {
    this.decals = decals;
  }

  public String getWheatHead() {
    return wheatHead;
  }

  public void setWheatHead(String wheatHead) {
    this.wheatHead = wheatHead;
  }

}
