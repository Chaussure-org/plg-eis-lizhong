package com.prolog.eis.model.order;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-10-10 
 */
@ApiModel ("容器绑定明细")
@Table ("container_binding_detail")
public class ContainerBindingDetail {

  @Column("container_no")
  @ApiModelProperty("容器编号")
  private String containerNo;

  @Column("order_bill_id")
  @ApiModelProperty("订单ID")
  private Integer orderBillId;

  @Column("order_detail_id")
  @ApiModelProperty("订单明细ID")
  private Integer orderDetailId;

  @Column("container_store_id")
  @ApiModelProperty("容器库存Id")
  private Integer containerStoreId;

  @Column("binding_num")
  @ApiModelProperty("容器绑定数量")
  private Integer bindingNum;

  @Column("seed_num")
  @ApiModelProperty("容器播种数量")
  private Integer seedNum;

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

  public Integer getContainerStoreId() {
    return containerStoreId;
  }

  public void setContainerStoreId(Integer containerStoreId) {
    this.containerStoreId = containerStoreId;
  }

  public Integer getBindingNum() {
    return bindingNum;
  }

  public void setBindingNum(Integer bindingNum) {
    this.bindingNum = bindingNum;
  }

  public Integer getSeedNum() {
    return seedNum;
  }

  public void setSeedNum(Integer seedNum) {
    this.seedNum = seedNum;
  }

}
