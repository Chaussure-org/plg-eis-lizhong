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
@ApiModel ("订单绑定订单框表")
@Table ("order_binding_order_box")
public class OrderBindingOrderBox {

  @Column("order_bill_id")
  @ApiModelProperty("订单ID")
  private Integer orderBillId;

  @Column("order_box_id")
  @ApiModelProperty("订单框ID")
  private Integer orderBoxId;

  @Column("grid_no")
  @ApiModelProperty("格子号")
  private Integer gridNo;

  public Integer getOrderBillId() {
    return orderBillId;
  }

  public void setOrderBillId(Integer orderBillId) {
    this.orderBillId = orderBillId;
  }

  public Integer getOrderBoxId() {
    return orderBoxId;
  }

  public void setOrderBoxId(Integer orderBoxId) {
    this.orderBoxId = orderBoxId;
  }

  public Integer getGridNo() {
    return gridNo;
  }

  public void setGridNo(Integer gridNo) {
    this.gridNo = gridNo;
  }

}
