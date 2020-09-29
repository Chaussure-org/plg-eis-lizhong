package com.prolog.eis.model.agv;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  SunPP
 * @Date 2020-09-29 
 */
@ApiModel ("null")
@Table ("agv_binding_detail")
public class AgvBindingDetail {

  @Column("container_no")
  @ApiModelProperty("容器编号")
  private String containerNo;

  @Column("order_mx_id")
  @ApiModelProperty("订单明细id")
  private Integer orderMxId;

  @Column("goodsId")
  @ApiModelProperty("商品id")
  private Integer goodsId;

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
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

}
