package com.prolog.eis.dto.wms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-13 19:20
 */
public class WmsGoodsDto {

    @ApiModelProperty("商品id")
    @JsonProperty(value = "LINEID")
    private String ITEMID;

    @ApiModelProperty("商品名称")
    @JsonProperty(value = "ITEMNAME")
    private String ITEMNAME;

    @ApiModelProperty("分包数")
    @JsonProperty(value = "JZS")
    private Double JZS;

    @ApiModelProperty("大类")
    @JsonProperty(value = "ITEMTYPE")
    private String ITEMTYPE;

    @ApiModelProperty("类别")
    @JsonProperty(value = "GATEGORYID")
    private String GATEGORYID;

    @ApiModelProperty("商品条码")
    @JsonProperty(value = "ITEMBARCODE")
    private String ITEMBARCODE;

    @ApiModelProperty("称重")
    @JsonProperty(value = "WEIGHT")
    private Double WEIGHT;

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
    @JsonProperty(value = "EXSATTR4")
    private String EXSATTR4;

    public Double getWEIGHT() {
        return WEIGHT;
    }

    public void setWEIGHT(Double WEIGHT) {
        this.WEIGHT = WEIGHT;
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

    public Double getJZS() {
        return JZS;
    }

    public void setJZS(Double JZS) {
        this.JZS = JZS;
    }

    public String getITEMTYPE() {
        return ITEMTYPE;
    }

    public void setITEMTYPE(String ITEMTYPE) {
        this.ITEMTYPE = ITEMTYPE;
    }

    public String getGATEGORYID() {
        return GATEGORYID;
    }

    public void setGATEGORYID(String GATEGORYID) {
        this.GATEGORYID = GATEGORYID;
    }

    public String getITEMBARCODE() {
        return ITEMBARCODE;
    }

    public void setITEMBARCODE(String ITEMBARCODE) {
        this.ITEMBARCODE = ITEMBARCODE;
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

    @Override
    public String toString() {
        return "WmsGoodsDto{" +
                "ITEMID='" + ITEMID + '\'' +
                ", ITEMNAME='" + ITEMNAME + '\'' +
                ", JZS=" + JZS +
                ", ITEMTYPE='" + ITEMTYPE + '\'' +
                ", GATEGORYID='" + GATEGORYID + '\'' +
                ", ITEMBARCODE='" + ITEMBARCODE + '\'' +
                ", EXSATTR1='" + EXSATTR1 + '\'' +
                ", EXSATTR2='" + EXSATTR2 + '\'' +
                ", EXSATTR3='" + EXSATTR3 + '\'' +
                ", EXSATTR4='" + EXSATTR4 + '\'' +
                '}';
    }
}
