package com.prolog.eis.dto.wms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-25 16:37
 */

public class WmsInboundTaskDto implements Serializable {

    @ApiModelProperty("行号")
    @JsonProperty(value = "LINEID")
    private String LINEID;

    @ApiModelProperty("仓库id")
    @JsonProperty(value = "BRANCHCODE")
    private String BRANCHCODE;

    @ApiModelProperty("单据编号")
    @JsonProperty(value = "BILLNO")
    private String BILLNO;

    @ApiModelProperty("库别")
    @JsonProperty(value = "BRANCHTYPE")
    private String BRANCHTYPE;

    @ApiModelProperty("关联单据编号")
    @JsonProperty(value = "REFBILLNO")
    private String REFBILLNO;

    @ApiModelProperty("单据类型")
    @JsonProperty(value = "BILLTYPE")
    private String BILLTYPE;

    @ApiModelProperty("入库日期")
    @JsonProperty(value = "BILLDATE")
    private Date BILLDATE;

    @ApiModelProperty("容器号")
    @JsonProperty(value = "CONTAINERNO")
    private String CONTAINERNO;

    @ApiModelProperty("上级容器号")
    @JsonProperty(value = "PARENTCONTAINERNO")
    private String PARENTCONTAINERNO;

    @ApiModelProperty("备注")
    @JsonProperty(value = "REMARK")
    private String REMARK;

    @ApiModelProperty("单据行号")
    @JsonProperty(value = "SEQNO")
    private String SEQNO;

    @ApiModelProperty("商品ID")
    @JsonProperty(value = "ITEMID")
    private String ITEMID;

    @ApiModelProperty("商品名称")
    @JsonProperty(value = "ITEMNAME")
    private String ITEMNAME;

    @ApiModelProperty("包装数量")
    @JsonProperty(value = "JZS")
    private Integer JZS;

    @NotNull
    @ApiModelProperty("数量")
    @JsonProperty(value = "QTY")
    private Double QTY;

    @ApiModelProperty("批号ID")
    @JsonProperty(value = "PCH")
    private String PCH;

    @ApiModelProperty("生产日期")
    @JsonProperty(value = "PDATEFROM")
    private Date PDATEFROM;

    @ApiModelProperty("时间戳")
    @JsonProperty(value = "SJC")
    private Date SJC;

    @ApiModelProperty("是否订单托")
    @JsonProperty(value = "SPECIAL")
    private Integer SPECIAL;

    @ApiModelProperty("麦头")
    @JsonProperty(value = "LOTNO")
    private String LOTNO;

    @ApiModelProperty("拓展字段1")
    @JsonProperty(value = "EXSATTR1")
    private String EXSATTR1;

    @ApiModelProperty("拓展字段2")
    @JsonProperty(value = "EXSATTR2")
    private String EXSATTR2;

    @ApiModelProperty("拓展字段3")
    @JsonProperty(value = "EXSATTR3")
    private String EXSATTR3;

    @ApiModelProperty("拓展字段4")
    private String EXSATTR4;

    @ApiModelProperty("拓展字段5")
    private String EXSATTR5;

    public String getLINEID() {
        return LINEID;
    }

    public void setLINEID(String LINEID) {
        this.LINEID = LINEID;
    }

    public String getBRANCHCODE() {
        return BRANCHCODE;
    }

    public void setBRANCHCODE(String BRANCHCODE) {
        this.BRANCHCODE = BRANCHCODE;
    }

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getBRANCHTYPE() {
        return BRANCHTYPE;
    }

    public void setBRANCHTYPE(String BRANCHTYPE) {
        this.BRANCHTYPE = BRANCHTYPE;
    }

    public String getREFBILLNO() {
        return REFBILLNO;
    }

    public void setREFBILLNO(String REFBILLNO) {
        this.REFBILLNO = REFBILLNO;
    }

    public String getBILLTYPE() {
        return BILLTYPE;
    }

    public void setBILLTYPE(String BILLTYPE) {
        this.BILLTYPE = BILLTYPE;
    }

    public Date getBILLDATE() {
        return BILLDATE;
    }

    public void setBILLDATE(Date BILLDATE) {
        this.BILLDATE = BILLDATE;
    }

    public String getCONTAINERNO() {
        return CONTAINERNO;
    }

    public void setCONTAINERNO(String CONTAINERNO) {
        this.CONTAINERNO = CONTAINERNO;
    }

    public String getPARENTCONTAINERNO() {
        return PARENTCONTAINERNO;
    }

    public void setPARENTCONTAINERNO(String PARENTCONTAINERNO) {
        this.PARENTCONTAINERNO = PARENTCONTAINERNO;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
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

    public String getITEMNAME() {
        return ITEMNAME;
    }

    public void setITEMNAME(String ITEMNAME) {
        this.ITEMNAME = ITEMNAME;
    }

    public Integer getJZS() {
        return JZS;
    }

    public void setJZS(Integer JZS) {
        this.JZS = JZS;
    }

    public Double getQTY() {
        return QTY;
    }

    public void setQTY(Double QTY) {
        this.QTY = QTY;
    }

    public String getPCH() {
        return PCH;
    }

    public void setPCH(String PCH) {
        this.PCH = PCH;
    }

    public Date getPDATEFROM() {
        return PDATEFROM;
    }

    public void setPDATEFROM(Date PDATEFROM) {
        this.PDATEFROM = PDATEFROM;
    }

    public Date getSJC() {
        return SJC;
    }

    public void setSJC(Date SJC) {
        this.SJC = SJC;
    }

    public Integer getSPECIAL() {
        return SPECIAL;
    }

    public void setSPECIAL(Integer SPECIAL) {
        this.SPECIAL = SPECIAL;
    }

    public String getLOTNO() {
        return LOTNO;
    }

    public void setLOTNO(String LOTNO) {
        this.LOTNO = LOTNO;
    }

    public String getEXSATTR1() {
        return EXSATTR1;
    }

    public void setEXSATTR1(String EXSATTR1) {
        this.EXSATTR1 = EXSATTR1;
    }

    public String getEXSATTR2() {
        return EXSATTR2;
    }

    public void setEXSATTR2(String EXSATTR2) {
        this.EXSATTR2 = EXSATTR2;
    }

    public String getEXSATTR3() {
        return EXSATTR3;
    }

    public void setEXSATTR3(String EXSATTR3) {
        this.EXSATTR3 = EXSATTR3;
    }

    public String getEXSATTR4() {
        return EXSATTR4;
    }

    public void setEXSATTR4(String EXSATTR4) {
        this.EXSATTR4 = EXSATTR4;
    }

    public String getEXSATTR5() {
        return EXSATTR5;
    }

    public void setEXSATTR5(String EXSATTR5) {
        this.EXSATTR5 = EXSATTR5;
    }

    @Override
    public String toString() {
        return "WMSInboundTaskDto{" +
                "LINEID='" + LINEID + '\'' +
                ", BRANCHCODE='" + BRANCHCODE + '\'' +
                ", BILLNO='" + BILLNO + '\'' +
                ", BRANCHTYPE=" + BRANCHTYPE +
                ", REFBILLNO='" + REFBILLNO + '\'' +
                ", BILLTYPE='" + BILLTYPE + '\'' +
                ", BILLDATE=" + BILLDATE +
                ", CONTAINERNO='" + CONTAINERNO + '\'' +
                ", PARENTCONTAINERNO='" + PARENTCONTAINERNO + '\'' +
                ", REMARK='" + REMARK + '\'' +
                ", SEQNO='" + SEQNO + '\'' +
                ", ITEMID='" + ITEMID + '\'' +
                ", ITEMNAME='" + ITEMNAME + '\'' +
                ", JZS=" + JZS +
                ", QTY=" + QTY +
                ", PCH='" + PCH + '\'' +
                ", PDATEFROM=" + PDATEFROM +
                ", SJC=" + SJC +
                ", SPECIAL=" + SPECIAL +
                ", LOTNO='" + LOTNO + '\'' +
                ", EXSATTR1='" + EXSATTR1 + '\'' +
                ", EXSATTR2='" + EXSATTR2 + '\'' +
                ", EXSATTR3='" + EXSATTR3 + '\'' +
                ", EXSATTR4='" + EXSATTR4 + '\'' +
                ", EXSATTR5='" + EXSATTR5 + '\'' +
                '}';
    }
}
