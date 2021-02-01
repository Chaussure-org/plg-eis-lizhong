package com.prolog.eis.model.line;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description
 * @Author SunPP
 * @Date 2020-10-09
 */
@ApiModel("null")
@Table("line_binding_detail")
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

    @Column("goods_id")
    @ApiModelProperty("商品id")
    private Integer goodsId;


    @Column("binding_num")
    @ApiModelProperty("绑定数量")
    private Integer bindingNum;

    @Column("order_priority")
    @ApiModelProperty("eis优先级")
    private Integer orderPriority;



    @Column("wms_order_priority")
    @ApiModelProperty("wms优先级")
    private Integer wmsOrderPriority;

    @Column("detail_status")
    @ApiModelProperty("明细状态")
    private Integer detailStatus;

    @Column("update_time")
    @ApiModelProperty("更新时间")
    private java.util.Date updateTime;

    @Column("dept_num")
    @ApiModelProperty("移位数")
    private int deptNum;


    public int getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(int deptNum) {
        this.deptNum = deptNum;
    }

    public Integer getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(Integer orderPriority) {
        this.orderPriority = orderPriority;
    }

    public Integer getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(Integer detailStatus) {
        this.detailStatus = detailStatus;
    }

    public Integer getWmsOrderPriority() {
        return wmsOrderPriority;
    }

    public void setWmsOrderPriority(Integer wmsOrderPriority) {
        this.wmsOrderPriority = wmsOrderPriority;
    }



    public Integer getBindingNum() {
        return bindingNum;
    }

    public void setBindingNum(Integer bindingNum) {
        this.bindingNum = bindingNum;
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


    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

}
