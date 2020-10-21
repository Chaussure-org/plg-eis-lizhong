package com.prolog.eis.model.order;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-10-13 
 */
@ApiModel ("播种详细作业表")
@Table ("seed_info")
public class SeedInfo {

  @Column("id")
  @Id
  private Integer id;

  @Column("container_no")
  @ApiModelProperty("容器号")
  private String containerNo;

  @Column("order_bill_id")
  @ApiModelProperty("订单汇总号")
  private Integer orderBillId;

  @Column("order_detail_id")
  @ApiModelProperty("订单明细号")
  private Integer orderDetailId;

  @Column("order_tray_no")
  @ApiModelProperty("订单拖号")
  private String orderTrayNo;

  @Column("station_id")
  @ApiModelProperty("播种站台")
  private Integer stationId;

  @Column("num")
  @ApiModelProperty("播种数量")
  private Integer num;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("goods_id")
  @ApiModelProperty("商品id")
  private Integer goodsId;

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

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

  public Integer getOrderBillId() {
    return orderBillId;
  }

  public void setOrderBillId(Integer orderBillId) {
    this.orderBillId = orderBillId;
  }

  public Integer getOrderDetailId() {
    return orderDetailId;
  }

  public void setOrderDetailId(Integer orderDetailId) {
    this.orderDetailId = orderDetailId;
  }

  public String getOrderTrayNo() {
    return orderTrayNo;
  }

  public void setOrderTrayNo(String orderTrayNo) {
    this.orderTrayNo = orderTrayNo;
  }

  public Integer getStationId() {
    return stationId;
  }

  public void setStationId(Integer stationId) {
    this.stationId = stationId;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

}
