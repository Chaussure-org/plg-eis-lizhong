package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-25 16:37
 */
public class WMSInboundTaskDto {

    @ApiModelProperty("行号")
    private String lineId;

    @ApiModelProperty("仓库id")
    private String branchCode;

    @ApiModelProperty("单据编号")
    private String billNo;

    @ApiModelProperty("库别")
    private Integer branchType;

    @ApiModelProperty("关联但据编号")
    private String refBillNo;

    @ApiModelProperty("供应商代码")
    private String vendorCode;

    @ApiModelProperty("供应商名称")
    private String vendorName;

    @ApiModelProperty("入库日期")
    private Date billDate;

    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("上级容器号")
    private String parentContainerNo;

    @ApiModelProperty("部门编号")
    private String purOrgId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("单据行号")
    private String seqNo;

    @ApiModelProperty("商品编码")
    private String itemCode;

    @ApiModelProperty("商品名称")
    private String itemName;

    @ApiModelProperty("包装数量")
    private Integer jzs;

    @NotNull
    @ApiModelProperty("数量")
    private Integer qty;

    @ApiModelProperty("件数")
    private Integer pcs;

    @ApiModelProperty("零散数")
    private Integer odd;

    @ApiModelProperty("批次号")
    private String pch;

    @ApiModelProperty("单价")
    private Double netPrice;

    @ApiModelProperty("提货方式")
    private String arrivalMode;

    @ApiModelProperty("送货截至日期")
    private Date closeDate;

    @ApiModelProperty("生产日期")
    private Date pDateFrom;

    @ApiModelProperty("时间戳")
    private Date sjc;

    @ApiModelProperty("是否订单托")
    private Integer special;

    @ApiModelProperty("麦头")
    private String lotNo;

    @ApiModelProperty("拓展字段1")
    private String exsattr1;

    @ApiModelProperty("拓展字段2")
    private String exsattr2;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Integer getBranchType() {
        return branchType;
    }

    public void setBranchType(Integer branchType) {
        this.branchType = branchType;
    }

    public String getRefBillNo() {
        return refBillNo;
    }

    public void setRefBillNo(String refBillNo) {
        this.refBillNo = refBillNo;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getParentContainerNo() {
        return parentContainerNo;
    }

    public void setParentContainerNo(String parentContainerNo) {
        this.parentContainerNo = parentContainerNo;
    }

    public String getPurOrgId() {
        return purOrgId;
    }

    public void setPurOrgId(String purOrgId) {
        this.purOrgId = purOrgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getJzs() {
        return jzs;
    }

    public void setJzs(Integer jzs) {
        this.jzs = jzs;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getPcs() {
        return pcs;
    }

    public void setPcs(Integer pcs) {
        this.pcs = pcs;
    }

    public Integer getOdd() {
        return odd;
    }

    public void setOdd(Integer odd) {
        this.odd = odd;
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch;
    }

    public Double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(Double netPrice) {
        this.netPrice = netPrice;
    }

    public String getArrivalMode() {
        return arrivalMode;
    }

    public void setArrivalMode(String arrivalMode) {
        this.arrivalMode = arrivalMode;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Date getpDateFrom() {
        return pDateFrom;
    }

    public void setpDateFrom(Date pDateFrom) {
        this.pDateFrom = pDateFrom;
    }

    public Date getSjc() {
        return sjc;
    }

    public void setSjc(Date sjc) {
        this.sjc = sjc;
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

    public String getExsattr1() {
        return exsattr1;
    }

    public void setExsattr1(String exsattr1) {
        this.exsattr1 = exsattr1;
    }

    public String getExsattr2() {
        return exsattr2;
    }

    public void setExsattr2(String exsattr2) {
        this.exsattr2 = exsattr2;
    }

    @Override
    public String toString() {
        return "WMSInboundTaskDto{" +
                "lineId='" + lineId + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", billNo='" + billNo + '\'' +
                ", branchType=" + branchType +
                ", refBillNo='" + refBillNo + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", billDate=" + billDate +
                ", containerNo='" + containerNo + '\'' +
                ", parentContainerNo='" + parentContainerNo + '\'' +
                ", purOrgId='" + purOrgId + '\'' +
                ", remark='" + remark + '\'' +
                ", seqNo='" + seqNo + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", jzs=" + jzs +
                ", qty=" + qty +
                ", pcs=" + pcs +
                ", odd=" + odd +
                ", pch='" + pch + '\'' +
                ", netPrice=" + netPrice +
                ", arrivalMode='" + arrivalMode + '\'' +
                ", closeDate=" + closeDate +
                ", pDateFrom=" + pDateFrom +
                ", sjc=" + sjc +
                ", special=" + special +
                ", lotNo='" + lotNo + '\'' +
                ", exsattr1='" + exsattr1 + '\'' +
                ", exsattr2='" + exsattr2 + '\'' +
                '}';
    }
}
