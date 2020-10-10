package com.prolog.eis.model.line;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  SunPP
 * @Date 2020-10-09 
 */
@ApiModel ("null")
@Table ("line_binding_detail")
public class LineBindingDetail {

  @Column("container_no")
  @ApiModelProperty("容器编号")
  private String containerNo;

  @Column("order_bill_id")
  @ApiModelProperty("订单id")
  private Integer orderBillId;

  @Column("order_mx_id")
  @ApiModelProperty("订单明细id")
  private Integer orderMxId;

  @Column("goodsId")
  @ApiModelProperty("商品id")
  private Integer goodsId;

  @Column("priority")
  @ApiModelProperty("优先级")
  private Integer priority;

  @Column("update_time")
  @ApiModelProperty("更新时间")
  private java.util.Date updateTime;

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

  public Integer getOrderMxId() {
    return orderMxId;
  }

  public void setOrderMxId(Integer orderMxId) {
    this.orderMxId = orderMxId;
  }

  public Integer getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public java.util.Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.util.Date updateTime) {
    this.updateTime = updateTime;
  }

}
