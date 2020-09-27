package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 14:39
 */
public class InboundCallBackDto {

    @ApiModelProperty("行号")
    private String lineId;

    @ApiModelProperty("单据编号")
    private String billNo;

    @ApiModelProperty("关联单据编号")
    private String refBillNo;

    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("单据行号")
    private String seqNo;

    @ApiModelProperty("商品编码")
    private String itemCode;

    @ApiModelProperty("商品名称")
    private String itemName;

    @ApiModelProperty("是否订单托")
    private Integer special;

    @ApiModelProperty("麦头")
    private String lotNo;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getRefBillNo() {
        return refBillNo;
    }

    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    @Override
    public String toString() {
        return "InboundCallBackDto{" +
                "lineId='" + lineId + '\'' +
                ", billNo='" + billNo + '\'' +
                ", refBillNo='" + refBillNo + '\'' +
                ", containerNo='" + containerNo + '\'' +
                ", seqNo='" + seqNo + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", special=" + special +
                ", lotNo='" + lotNo + '\'' +
                '}';
    }
}
