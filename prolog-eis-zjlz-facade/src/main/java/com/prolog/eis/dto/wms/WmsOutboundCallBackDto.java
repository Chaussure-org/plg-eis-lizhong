package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author wangkang
 * @Description 出库任务回告实体
 * @CreateTime 2020-10-14 11:06
 */
public class WmsOutboundCallBackDto {


    @ApiModelProperty("任务id")
    private String TASKID;

    @ApiModelProperty("单据编号")
    private String BILLNO;

    @ApiModelProperty("单据时间")
    private Date BILLDATE;

    @ApiModelProperty("状态")
    private Integer STATUS;

    @ApiModelProperty("时间戳")
    private Date SJC;

    @ApiModelProperty("单据类型")
    private Integer BILLTYPE;

    @ApiModelProperty("库区")
    private String BRANCHAREA;

    @ApiModelProperty("商品id")
    private String ITEMID;

    @ApiModelProperty("批次id")
    private String LOTID;

    @ApiModelProperty("容器号")
    private String CONTAINERNO;

    @ApiModelProperty("实际数量")
    private Double QTY;

    public String getTASKID() {
        return TASKID;
    }

    public void setTASKID(String TASKID) {
        this.TASKID = TASKID;
    }

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public Date getBILLDATE() {
        return BILLDATE;
    }

    public void setBILLDATE(Date BILLDATE) {
        this.BILLDATE = BILLDATE;
    }

    public Integer getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(Integer STATUS) {
        this.STATUS = STATUS;
    }

    public Date getSJC() {
        return SJC;
    }

    public void setSJC(Date SJC) {
        this.SJC = SJC;
    }

    public Integer getBILLTYPE() {
        return BILLTYPE;
    }

    public void setBILLTYPE(Integer BILLTYPE) {
        this.BILLTYPE = BILLTYPE;
    }

    public String getBRANCHAREA() {
        return BRANCHAREA;
    }

    public void setBRANCHAREA(String BRANCHAREA) {
        this.BRANCHAREA = BRANCHAREA;
    }

    public String getITEMID() {
        return ITEMID;
    }

    public void setITEMID(String ITEMID) {
        this.ITEMID = ITEMID;
    }

    public String getLOTID() {
        return LOTID;
    }

    public void setLOTID(String LOTID) {
        this.LOTID = LOTID;
    }

    public String getCONTAINERNO() {
        return CONTAINERNO;
    }

    public void setCONTAINERNO(String CONTAINERNO) {
        this.CONTAINERNO = CONTAINERNO;
    }

    public Double getQTY() {
        return QTY;
    }

    public void setQTY(Double QTY) {
        this.QTY = QTY;
    }

    @Override
    public String toString() {
        return "WmsOutboundCallBackDto{" +
                "TASKID='" + TASKID + '\'' +
                ", BILLNO='" + BILLNO + '\'' +
                ", BILLDATE=" + BILLDATE +
                ", STATUS=" + STATUS +
                ", SJC=" + SJC +
                ", BILLTYPE=" + BILLTYPE +
                ", BRANCHAREA='" + BRANCHAREA + '\'' +
                ", ITEMID='" + ITEMID + '\'' +
                ", LOTID='" + LOTID + '\'' +
                ", CONTAINERNO='" + CONTAINERNO + '\'' +
                ", QTY=" + QTY +
                '}';
    }
}
