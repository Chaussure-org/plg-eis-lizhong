package com.prolog.eis.dto.wms;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-14 12:17
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility= JsonAutoDetect.Visibility.NONE)
public class WmsInventoryCallBackDto {

    @ApiModelProperty("任务id")
    @JsonProperty(value = "TASKID")
    private String TASKID;

    @ApiModelProperty("单据编号")
    @JsonProperty(value = "BILLNO")
    private String BILLNO;

    @ApiModelProperty("单据日期")
    @JsonProperty(value = "BILLDATE")
    private Date BILLDATE;

    @ApiModelProperty("仓库编号")
    @JsonProperty(value = "BRANCHCODE")
    private String BRANCHCODE;

    @ApiModelProperty("库区")
    @JsonProperty(value = "BRANCHAREA")
    private String BRANCHAREA;

    @ApiModelProperty("行号")
    @JsonProperty(value = "SEQNO")
    private String SEQNO;

    @ApiModelProperty("商品id")
    @JsonProperty(value = "ITEMID")
    private String ITEMID;

    @ApiModelProperty("商品类别")
    @JsonProperty(value = "ITEMTYPE")
    private String ITEMTYPE;

    @ApiModelProperty("容器号")
    @JsonProperty(value = "CONTAINERNO")
    private String CONTAINERNO;

    @ApiModelProperty("批号id")
    @JsonProperty(value = "PCH")
    private String PCH;

    @ApiModelProperty("计划盘点日期")
    @JsonProperty(value = "PLANDATE")
    private Date PLANDATE;

    @ApiModelProperty("时间戳")
    @JsonProperty(value = "SJZ")
    private Date SJZ;

    @ApiModelProperty("差异数")
    @JsonProperty(value = "AFFQTY")
    private Double AFFQTY;

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

    public String getBRANCHCODE() {
        return BRANCHCODE;
    }

    public void setBRANCHCODE(String BRANCHCODE) {
        this.BRANCHCODE = BRANCHCODE;
    }

    public String getBRANCHAREA() {
        return BRANCHAREA;
    }

    public void setBRANCHAREA(String BRANCHAREA) {
        this.BRANCHAREA = BRANCHAREA;
    }

    public String getSEQNO() {
        return SEQNO;
    }

    public void setSEQNO(String SEQNO) {
        this.SEQNO = SEQNO;
    }

    public String getITEMID() {
        return ITEMID;
    }

    public void setITEMID(String ITEMID) {
        this.ITEMID = ITEMID;
    }

    public String getITEMTYPE() {
        return ITEMTYPE;
    }

    public void setITEMTYPE(String ITEMTYPE) {
        this.ITEMTYPE = ITEMTYPE;
    }

    public String getCONTAINERNO() {
        return CONTAINERNO;
    }

    public void setCONTAINERNO(String CONTAINERNO) {
        this.CONTAINERNO = CONTAINERNO;
    }

    public String getPCH() {
        return PCH;
    }

    public void setPCH(String PCH) {
        this.PCH = PCH;
    }

    public Date getPLANDATE() {
        return PLANDATE;
    }

    public void setPLANDATE(Date PLANDATE) {
        this.PLANDATE = PLANDATE;
    }

    public Date getSJZ() {
        return SJZ;
    }

    public void setSJZ(Date SJZ) {
        this.SJZ = SJZ;
    }

    public Double getAFFQTY() {
        return AFFQTY;
    }

    public void setAFFQTY(Double AFFQTY) {
        this.AFFQTY = AFFQTY;
    }

    @Override
    public String toString() {
        return "WmsInventoryCallBackDto{" +
                "TASKID='" + TASKID + '\'' +
                ", BILLNO='" + BILLNO + '\'' +
                ", BILLDATE=" + BILLDATE +
                ", BRANCHCODE='" + BRANCHCODE + '\'' +
                ", BRANCHAREA='" + BRANCHAREA + '\'' +
                ", SEQNO='" + SEQNO + '\'' +
                ", ITEMID='" + ITEMID + '\'' +
                ", ITEMTYPE='" + ITEMTYPE + '\'' +
                ", CONTAINERNO='" + CONTAINERNO + '\'' +
                ", PCH='" + PCH + '\'' +
                ", PLANDATE=" + PLANDATE +
                ", SJZ=" + SJZ +
                ", AFFQTY=" + AFFQTY +
                '}';
    }
}
