package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-25 16:37
 */
public class WmsOutboundTaskDto {

    @ApiModelProperty("任务号")
    private String TASKID;

    @ApiModelProperty("业主内码")
    private String CONSIGNOR;

    @ApiModelProperty("仓库id")
    private String BRANCHCODE;

    @ApiModelProperty("单据编号")
    private String BILLNO;

    @ApiModelProperty("单据类型")
    private String BILLTYPE;

    @ApiModelProperty("关联采购单订单号")
    private String CUSTPONO;

    @ApiModelProperty("单据日期")
    private Date BILLDATE;

    @ApiModelProperty("单据行号")
    private String SEQNO;

    @ApiModelProperty("商品编码")
    private String ITEMID;

    @ApiModelProperty("商品名称")
    private String ITMENAME;

    @ApiModelProperty("包装数量")
    private Integer JZS;

    @NotNull
    @ApiModelProperty("数量")
    private Double QTY;

    @ApiModelProperty("贴标区")
    private String SFTB;

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

    public String getTASKID() {
        return TASKID;
    }

    public void setTASKID(String TASKID) {
        this.TASKID = TASKID;
    }

    public String getCONSIGNOR() {
        return CONSIGNOR;
    }

    public void setCONSIGNOR(String CONSIGNOR) {
        this.CONSIGNOR = CONSIGNOR;
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

    public String getBILLTYPE() {
        return BILLTYPE;
    }

    public void setBILLTYPE(String BILLTYPE) {
        this.BILLTYPE = BILLTYPE;
    }

    public String getCUSTPONO() {
        return CUSTPONO;
    }

    public void setCUSTPONO(String CUSTPONO) {
        this.CUSTPONO = CUSTPONO;
    }

    public Date getBILLDATE() {
        return BILLDATE;
    }

    public void setBILLDATE(Date BILLDATE) {
        this.BILLDATE = BILLDATE;
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

    public String getITMENAME() {
        return ITMENAME;
    }

    public void setITMENAME(String ITMENAME) {
        this.ITMENAME = ITMENAME;
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

    public String getSFTB() {
        return SFTB;
    }

    public void setSFTB(String SFTB) {
        this.SFTB = SFTB;
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
        return "WMSOutboundTaskDto{" +
                "TASKID='" + TASKID + '\'' +
                ", CONSIGNOR='" + CONSIGNOR + '\'' +
                ", BRANCHCODE='" + BRANCHCODE + '\'' +
                ", BILLNO='" + BILLNO + '\'' +
                ", BILLTYPE='" + BILLTYPE + '\'' +
                ", CUSTPONO='" + CUSTPONO + '\'' +
                ", BILLDATE=" + BILLDATE +
                ", SEQNO='" + SEQNO + '\'' +
                ", ITEMID='" + ITEMID + '\'' +
                ", ITMENAME='" + ITMENAME + '\'' +
                ", JZS=" + JZS +
                ", QTY=" + QTY +
                ", SFTB='" + SFTB + '\'' +
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
