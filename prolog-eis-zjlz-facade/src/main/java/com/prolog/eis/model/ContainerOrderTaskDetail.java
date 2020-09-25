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
@ApiModel ("托盘订单任务明细表")
@Table ("container_order_task_detail")
public class ContainerOrderTaskDetail {

  @Column("id")
  @Id
  @ApiModelProperty("托盘任务明细ID")
  private Integer id;

  @Column("order_detail_id")
  @ApiModelProperty("订单明细ID")
  private Integer orderDetailId;

  @Column("container_no")
  @ApiModelProperty("托盘号")
  private String containerNo;

  @Column("container_store_id")
  @ApiModelProperty("托盘库存ID")
  private Integer containerStoreId;

  @Column("order_detail_qty")
  @ApiModelProperty("订单明细数量")
  private Integer orderDetailQty;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getOrderDetailId() {
    return orderDetailId;
  }

  public void setOrderDetailId(Integer orderDetailId) {
    this.orderDetailId = orderDetailId;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public Integer getContainerStoreId() {
    return containerStoreId;
  }

  public void setContainerStoreId(Integer containerStoreId) {
    this.containerStoreId = containerStoreId;
  }

  public Integer getOrderDetailQty() {
    return orderDetailQty;
  }

  public void setOrderDetailQty(Integer orderDetailQty) {
    this.orderDetailQty = orderDetailQty;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

}
