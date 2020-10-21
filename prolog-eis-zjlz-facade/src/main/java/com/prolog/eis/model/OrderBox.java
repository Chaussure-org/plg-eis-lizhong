package com.prolog.eis.model;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;


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

  @Column("tray_weigh")
  @ApiModelProperty("订单拖重量")
  private BigDecimal trayWeigh;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private Date createTime;


  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public BigDecimal getTrayWeigh() {
    return trayWeigh;
  }

  public void setTrayWeigh(BigDecimal trayWeigh) {
    this.trayWeigh = trayWeigh;
  }

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
