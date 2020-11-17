package com.prolog.eis.model.order;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-11-17
 * 成品订单
 */
@ApiModel ("成品订单(展示用)")
@Table ("order_finish")
public class OrderFinish {

  @Column("id")
  @Id
  @ApiModelProperty("主键id")
  private Integer id;

  @Column("order_bill_id")
  @ApiModelProperty("订单id")
  private Integer orderBillId;

  @Column("goods_id")
  @ApiModelProperty("商品id")
  private Integer goodsId;

  @Column("goods_name")
  @ApiModelProperty("产品名称")
  private String goodsName;

  @Column("plan_qty")
  @ApiModelProperty("派工数量")
  private Integer planQty;

  @Column("product_type")
  @ApiModelProperty("产品类型")
  private String productType;

  @Column("client_mark")
  @ApiModelProperty("客户号")
  private String clientMark;

  @Column("client_name")
  @ApiModelProperty("客户名称")
  private String clientName;

  @Column("order_delivery")
  @ApiModelProperty("订单交期")
  private java.util.Date orderDelivery;

  @Column("client_contract")
  @ApiModelProperty("客户合同号")
  private String clientContract;

  @Column("creat_time")
  @ApiModelProperty("创建时间")
  private java.util.Date creatTime;

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

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public Integer getPlanQty() {
    return planQty;
  }

  public void setPlanQty(Integer planQty) {
    this.planQty = planQty;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getClientMark() {
    return clientMark;
  }

  public void setClientMark(String clientMark) {
    this.clientMark = clientMark;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public java.util.Date getOrderDelivery() {
    return orderDelivery;
  }

  public void setOrderDelivery(java.util.Date orderDelivery) {
    this.orderDelivery = orderDelivery;
  }

  public String getClientContract() {
    return clientContract;
  }

  public void setClientContract(String clientContract) {
    this.clientContract = clientContract;
  }

  public java.util.Date getCreatTime() {
    return creatTime;
  }

  public void setCreatTime(java.util.Date creatTime) {
    this.creatTime = creatTime;
  }

}
