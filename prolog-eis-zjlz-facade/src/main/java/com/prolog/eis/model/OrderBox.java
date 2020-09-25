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
@ApiModel ("订单框表")
@Table ("order_box")
public class OrderBox {

  @Column("id")
  @Id
  @ApiModelProperty("订单框ID")
  private Integer id;

  @Column("order_box_no")
  @ApiModelProperty("订单框编号")
  private String orderBoxNo;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getOrderBoxNo() {
    return orderBoxNo;
  }

  public void setOrderBoxNo(String orderBoxNo) {
    this.orderBoxNo = orderBoxNo;
  }

}
