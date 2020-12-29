package com.prolog.eis.dto.wms;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author wangkang
 * @Description 出库任务回告实体
 * @CreateTime 2020-10-14 11:06
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility= JsonAutoDetect.Visibility.NONE)
public class WmsOutboundCallBackDto {


    @ApiModelProperty("任务id")
    @JsonProperty(value = "TASKID")
    private String TASKID;

    @ApiModelProperty("单据编号")
    @JsonProperty(value = "BILLNO")
    private String BILLNO;

    @ApiModelProperty("单据时间")
    @JsonProperty(value = "BILLDATE")
    private Date BILLDATE;

    @ApiModelProperty("状态")
    @JsonProperty(value = "STATUS")
    private Integer STATUS;

    @ApiModelProperty("时间戳")
    @JsonProperty(value = "SJC")
    private Date SJC;

    @ApiModelProperty("单据类型")
    @JsonProperty(value = "BILLTYPE")
    private String BILLTYPE;

    @ApiModelProperty("库区")
    @JsonProperty(value = "BRANCHAREA")
    private String BRANCHAREA;

    @ApiModelProperty("商品id")
    @JsonProperty(value = "ITEMID")
    private String ITEMID;

    @ApiModelProperty("批次id")
    @JsonProperty(value = "LOTID")
    private String LOTID;

    @ApiModelProperty("容器号")
    @JsonProperty(value = "CONTAINERNO")
    private String CONTAINERNO;

    @ApiModelProperty("实际数量")
    @JsonProperty(value = "QTY")
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

    public String getBILLTYPE() {
        return BILLTYPE;
    }

    public void setBILLTYPE(String BILLTYPE) {
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
