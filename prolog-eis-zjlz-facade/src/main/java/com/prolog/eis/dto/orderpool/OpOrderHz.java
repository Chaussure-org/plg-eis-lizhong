package com.prolog.eis.dto.orderpool;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author panteng
 * @description: 订单池出库订单
 * @date 2020/4/17 15:09
 */
@Table("order_hz")
public class OpOrderHz {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("拣选单Id")
    private Integer pickingOrderId;

    @ApiModelProperty("出库单编号")
    private String outboundCode;

    @ApiModelProperty("时效（YYYY-MM-DD HH:MM:SS）")
    private Date expectTime;

    @Column("priority")
    @ApiModelProperty("优先级")
    private Integer priority;

    @Column("order_box_no")
    @ApiModelProperty("订单箱编号")
    private String orderBoxNo;

    @ApiModelProperty("订单明细集合")
    private List<OpOrderMx> mxList;

    public List<OpOrderMx> getMxList() {
        return mxList;
    }

    public void setMxList(List<OpOrderMx> mxList) {
        this.mxList = mxList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPickingOrderId() {
        return pickingOrderId;
    }

    public void setPickingOrderId(Integer pickingOrderId) {
        this.pickingOrderId = pickingOrderId;
    }

    public String getOutboundCode() {
        return outboundCode;
    }

    public void setOutboundCode(String outboundCode) {
        this.outboundCode = outboundCode;
    }

    public Date getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Date expectTime) {
        this.expectTime = expectTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getOrderBoxNo() {
        return orderBoxNo;
    }

    public void setOrderBoxNo(String orderBoxNo) {
        this.orderBoxNo = orderBoxNo;
    }
}
