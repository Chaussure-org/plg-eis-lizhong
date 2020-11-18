package com.prolog.eis.dto.wms;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "TASKID")
    private String TASKID;

    @ApiModelProperty("业主内码")
    @JsonProperty(value = "CONSIGNOR")
    private String CONSIGNOR;

    @ApiModelProperty("仓库编码")
    @JsonProperty(value = "BRANCHCODE")
    private String BRANCHCODE;

    @ApiModelProperty("单据编号")
    @JsonProperty(value = "BILLNO")
    private String BILLNO;

    @ApiModelProperty("单据类型")
    @JsonProperty(value = "BILLTYPE")
    private Integer BILLTYPE;

    @ApiModelProperty("关联采购单订单号")
    @JsonProperty(value = "CUSTPONO")
    private String CUSTPONO;

    @ApiModelProperty("单据日期")
    @JsonProperty(value = "BILLDATE")
    private Date BILLDATE;

    @ApiModelProperty("单据行号")
    @JsonProperty(value = "SEQNO")
    private String SEQNO;

    @ApiModelProperty("商品id")
    @JsonProperty(value = "ITEMID")
    private String ITEMID;

    @ApiModelProperty("商品名称")
    @JsonProperty(value = "ITMENAME")
    private String ITMENAME;

    @ApiModelProperty("包装数量")
    @JsonProperty(value = "JZS")
    private Integer JZS;

    @NotNull
    @ApiModelProperty("数量")
    @JsonProperty(value = "QTY")
    private Double QTY;

    @ApiModelProperty("贴标区")
    @JsonProperty(value = "SFTB")
    private String SFTB;

    @ApiModelProperty("时间戳")
    @JsonProperty(value = "SJC")
    private Date SJC;

    @ApiModelProperty("是否订单托")
    @JsonProperty(value = "SPECIAL")
    private Integer SPECIAL;

    @ApiModelProperty("麦头")
    @JsonProperty(value = "LOTNO")
    private String LOTNO;

    @ApiModelProperty("拓展字段1  是否成品")
    private String EXSATTR1;

    @ApiModelProperty("拓展字段2  成品用-产品类别")
    private String EXSATTR2;

    @ApiModelProperty("拓展字段3 成品用-客户号")
    private String EXSATTR3;

    @ApiModelProperty("拓展字段4 贴标区")
    private String EXSATTR4;

    @ApiModelProperty("拓展字段5 成品用-客户名称")
    private String EXSATTR5;

    @ApiModelProperty("拓展字段6 成品用-派工数量")
    private String EXSATTR6;

    @ApiModelProperty("拓展字段7 成品用-订单交期")
    private Date EXSATTR7;

    @ApiModelProperty("拓展字段8 成品用-客户合同号")
    private String EXSATTR8;

    @ApiModelProperty("拓展字段9 成品用-产品名称")
    private String EXSATTR9;

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

    public Integer getBILLTYPE() {
        return BILLTYPE;
    }

    public void setBILLTYPE(Integer BILLTYPE) {
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

    public String getEXSATTR6() {
        return EXSATTR6;
    }

    public void setEXSATTR6(String EXSATTR6) {
        this.EXSATTR6 = EXSATTR6;
    }

    public Date getEXSATTR7() {
        return EXSATTR7;
    }

    public void setEXSATTR7(Date EXSATTR7) {
        this.EXSATTR7 = EXSATTR7;
    }

    public String getEXSATTR8() {
        return EXSATTR8;
    }

    public void setEXSATTR8(String EXSATTR8) {
        this.EXSATTR8 = EXSATTR8;
    }

    public String getEXSATTR9() {
        return EXSATTR9;
    }

    public void setEXSATTR9(String EXSATTR9) {
        this.EXSATTR9 = EXSATTR9;
    }

    @Override
    public String toString() {
        return "WmsOutboundTaskDto{" +
                "TASKID='" + TASKID + '\'' +
                ", CONSIGNOR='" + CONSIGNOR + '\'' +
                ", BRANCHCODE='" + BRANCHCODE + '\'' +
                ", BILLNO='" + BILLNO + '\'' +
                ", BILLTYPE=" + BILLTYPE +
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
                ", EXSATTR6='" + EXSATTR6 + '\'' +
                ", EXSATTR7=" + EXSATTR7 +
                ", EXSATTR8='" + EXSATTR8 + '\'' +
                ", EXSATTR9='" + EXSATTR9 + '\'' +
                '}';
    }
}
