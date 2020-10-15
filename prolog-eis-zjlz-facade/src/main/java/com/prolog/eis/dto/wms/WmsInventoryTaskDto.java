package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-14 10:50
 */
public class WmsInventoryTaskDto {

    @ApiModelProperty("任务id")
    private String TASKID;

    @ApiModelProperty("单据编号")
    private String BILLNO;

    @ApiModelProperty("单据日期")
    private Date BILLDATE;

    @ApiModelProperty("仓库编号")
    private String BRANCHCODE;

    @ApiModelProperty("库区")
    private String BRANCHTYPE;

    @ApiModelProperty("容器号")
    private String CONTAINERNO;

    @ApiModelProperty("行号")
    private String SEQNO;

    @ApiModelProperty("商品类别")
    private String ITEMTYPE;

    @ApiModelProperty("盘点原因")
    private String REASONCODE;

    @ApiModelProperty("商品id")
    private String ITEMID;

    @ApiModelProperty("计划盘点时间")
    private Date PLANDATE;

    @ApiModelProperty("时间戳")
    private Date SJC;

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

    public String getBRANCHTYPE() {
        return BRANCHTYPE;
    }

    public void setBRANCHTYPE(String BRANCHTYPE) {
        this.BRANCHTYPE = BRANCHTYPE;
    }

    public String getCONTAINERNO() {
        return CONTAINERNO;
    }

    public void setCONTAINERNO(String CONTAINERNO) {
        this.CONTAINERNO = CONTAINERNO;
    }

    public String getSEQNO() {
        return SEQNO;
    }

    public void setSEQNO(String SEQNO) {
        this.SEQNO = SEQNO;
    }

    public String getITEMTYPE() {
        return ITEMTYPE;
    }

    public void setITEMTYPE(String ITEMTYPE) {
        this.ITEMTYPE = ITEMTYPE;
    }

    public String getREASONCODE() {
        return REASONCODE;
    }

    public void setREASONCODE(String REASONCODE) {
        this.REASONCODE = REASONCODE;
    }

    public String getITEMID() {
        return ITEMID;
    }

    public void setITEMID(String ITEMID) {
        this.ITEMID = ITEMID;
    }

    public Date getPLANDATE() {
        return PLANDATE;
    }

    public void setPLANDATE(Date PLANDATE) {
        this.PLANDATE = PLANDATE;
    }

    public Date getSJC() {
        return SJC;
    }

    public void setSJC(Date SJC) {
        this.SJC = SJC;
    }

    @Override
    public String toString() {
        return "WmsInventoryTaskDto{" +
                "TASKID='" + TASKID + '\'' +
                ", BILLNO='" + BILLNO + '\'' +
                ", BILLDATE=" + BILLDATE +
                ", BRANCHCODE='" + BRANCHCODE + '\'' +
                ", BRANCHTYPE='" + BRANCHTYPE + '\'' +
                ", CONTAINERNO='" + CONTAINERNO + '\'' +
                ", SEQNO='" + SEQNO + '\'' +
                ", ITEMTYPE='" + ITEMTYPE + '\'' +
                ", REASONCODE='" + REASONCODE + '\'' +
                ", ITEMID='" + ITEMID + '\'' +
                ", PLANDATE=" + PLANDATE +
                ", SJC=" + SJC +
                '}';
    }
}
