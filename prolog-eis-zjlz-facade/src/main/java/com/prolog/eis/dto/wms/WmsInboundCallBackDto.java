package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 14:39
 */
public class WmsInboundCallBackDto {

    @ApiModelProperty("行号")
    private String LINEID;

    @ApiModelProperty("单据编号")
    private String BILLNO;

    @ApiModelProperty("单据类型")
    private String BILLTYPE;

    @ApiModelProperty("容器号")
    private String CONTAINERID;

    @ApiModelProperty("单据行号")
    private String SEQNO;

    @ApiModelProperty("商品编码")
    private String ITEMID;

    @ApiModelProperty("商品名称")
    private String ITEMNAME;

    @ApiModelProperty("是否订单托")
    private Integer SPECIAL;

    @ApiModelProperty("麦头")
    private String LOTNO;

    public String getLINEID() {
        return LINEID;
    }

    public void setLINEID(String LINEID) {
        this.LINEID = LINEID;
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

    public String getCONTAINERID() {
        return CONTAINERID;
    }

    public void setCONTAINERID(String CONTAINERID) {
        this.CONTAINERID = CONTAINERID;
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

    @Override
    public String toString() {
        return "WmsInboundCallBackDto{" +
                "LINEID='" + LINEID + '\'' +
                ", BILLNO='" + BILLNO + '\'' +
                ", BILLTYPE='" + BILLTYPE + '\'' +
                ", CONTAINERID='" + CONTAINERID + '\'' +
                ", SEQNO='" + SEQNO + '\'' +
                ", ITEMID='" + ITEMID + '\'' +
                ", ITEMNAME='" + ITEMNAME + '\'' +
                ", SPECIAL=" + SPECIAL +
                ", LOTNO='" + LOTNO + '\'' +
                '}';
    }
}
