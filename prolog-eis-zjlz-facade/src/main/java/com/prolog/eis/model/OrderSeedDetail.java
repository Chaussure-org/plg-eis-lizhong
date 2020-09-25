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
@ApiModel ("订单播种明细表")
@Table ("order_seed_detail")
public class OrderSeedDetail {

  @Column("id")
  @Id
  @ApiModelProperty("订单播种明细ID")
  private Integer id;

  @Column("order_mx_id")
  @ApiModelProperty("订单明细ID")
  private Integer orderMxId;

  @Column("grid_no")
  @ApiModelProperty("格子号")
  private Integer gridNo;

  @Column("order_box_no")
  @ApiModelProperty("订单箱号")
  private String orderBoxNo;

  @Column("user_id")
  @ApiModelProperty("播种人ID")
  private String userId;

  @Column("user_name")
  @ApiModelProperty("播种人名称")
  private String userName;

  @Column("owner_id")
  @ApiModelProperty("业主")
  private String ownerId;

  @Column("lot_id")
  @ApiModelProperty("批次号")
  private String lotId;

  @Column("goods_id")
  @ApiModelProperty("商品ID")
  private String goodsId;

  @Column("goods_order_no")
  @ApiModelProperty("商品订单号")
  private String goodsOrderNo;

  @Column("seed_qty")
  @ApiModelProperty("播种数量")
  private Integer seedQty;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getOrderMxId() {
    return orderMxId;
  }

  public void setOrderMxId(Integer orderMxId) {
    this.orderMxId = orderMxId;
  }

  public Integer getGridNo() {
    return gridNo;
  }

  public void setGridNo(Integer gridNo) {
    this.gridNo = gridNo;
  }

  public String getOrderBoxNo() {
    return orderBoxNo;
  }

  public void setOrderBoxNo(String orderBoxNo) {
    this.orderBoxNo = orderBoxNo;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(String goodsId) {
    this.goodsId = goodsId;
  }

  public String getGoodsOrderNo() {
    return goodsOrderNo;
  }

  public void setGoodsOrderNo(String goodsOrderNo) {
    this.goodsOrderNo = goodsOrderNo;
  }

  public Integer getSeedQty() {
    return seedQty;
  }

  public void setSeedQty(Integer seedQty) {
    this.seedQty = seedQty;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

}
