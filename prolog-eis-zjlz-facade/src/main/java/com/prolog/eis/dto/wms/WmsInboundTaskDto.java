package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-25 16:37
 */
public class WmsInboundTaskDto {

    @ApiModelProperty("行号")
    private String LINEID;

    @ApiModelProperty("仓库id")
    private String BRANCHCODE;

    @ApiModelProperty("单据编号")
    private String BILLNO;

    @ApiModelProperty("库别")
    private Integer BRANCHTYPE;

    @ApiModelProperty("关联单据编号")
    private String REFBILLNO;

    @ApiModelProperty("单据类型")
    private String BILLTYPE;

    @ApiModelProperty("入库日期")
    private Date BILLDATE;

    @ApiModelProperty("容器号")
    private String CONTAINERNO;

    @ApiModelProperty("上级容器号")
    private String PARENTCONTAINERNO;

    @ApiModelProperty("备注")
    private String REMARK;

    @ApiModelProperty("单据行号")
    private String SEQNO;

    @ApiModelProperty("商品ID")
    private String ITEMID;

    @ApiModelProperty("商品名称")
    private String ITEMNAME;

    @ApiModelProperty("包装数量")
    private Integer JZS;

    @NotNull
    @ApiModelProperty("数量")
    private Double QTY;

    @ApiModelProperty("批号ID")
    private String PCH;

    @ApiModelProperty("生产日期")
    private Date PDATEFROM;

    @ApiModelProperty("时间戳")
    private Date SJC;

    @ApiModelProperty("是否订单托")
    private Integer SPECIAL;

    @ApiModelProperty("麦头")
    private String LOTNO;

    @ApiModelProperty("拓展字段1")
    private String EXSATTR1;

    @ApiModelProperty("拓展字段2")
    private String EXSATTR2;

    @ApiModelProperty("拓展字段3")
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

    public Integer getBRANCHTYPE() {
        return BRANCHTYPE;
    }

    public void setBRANCHTYPE(Integer BRANCHTYPE) {
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
