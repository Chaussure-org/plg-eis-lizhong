package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-25 16:37
 */
public class WMSOutboundTaskDto {

    @ApiModelProperty("业主内码")
    private String consignor;

    @ApiModelProperty("仓库id")
    private String branchCode;

    @ApiModelProperty("单据编号")
    private String billNo;

    @ApiModelProperty("单据类型")
    private String billType;

    @ApiModelProperty("关联采购单订单号")
    private String custPoNo;

    @ApiModelProperty("单位内码")
    private String customerCode;

    @ApiModelProperty("单位名称")
    private String customerName;

    @ApiModelProperty("单据日期")
    private Date billDate;

    @ApiModelProperty("新店标识")
    private String xdbs;

    @ApiModelProperty("开店日期")
    private Date kdDate;

    @ApiModelProperty("提货方式")
    private String deliverType;

    @ApiModelProperty("部门内码")
    private String purOrgId;

    @ApiModelProperty("客户备注")
    private String remark;

    @ApiModelProperty("承运商")
    private String carror;

    @ApiModelProperty("配送线路")
    private String shipLineId;

    @ApiModelProperty("省")
    private String saveNo;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String dist;

    @ApiModelProperty("收获地址")
    private String shipToAddress;

    @ApiModelProperty("收货人")
    private String deliverLxr;

    @ApiModelProperty("收货人电话")
    private String deliverTel;

    @ApiModelProperty("送货时间")
    private Date shippedDate;

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

    @ApiModelProperty("单价")
    private Double netPrice;

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

    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getCustPoNo() {
        return custPoNo;
    }

    public void setCustPoNo(String custPoNo) {
        this.custPoNo = custPoNo;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getXdbs() {
        return xdbs;
    }

    public void setXdbs(String xdbs) {
        this.xdbs = xdbs;
    }

    public Date getKdDate() {
        return kdDate;
    }

    public void setKdDate(Date kdDate) {
        this.kdDate = kdDate;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
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

    public String getCarror() {
        return carror;
    }

    public void setCarror(String carror) {
        this.carror = carror;
    }

    public String getShipLineId() {
        return shipLineId;
    }

    public void setShipLineId(String shipLineId) {
        this.shipLineId = shipLineId;
    }

    public String getSaveNo() {
        return saveNo;
    }

    public void setSaveNo(String saveNo) {
        this.saveNo = saveNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public String getDeliverLxr() {
        return deliverLxr;
    }

    public void setDeliverLxr(String deliverLxr) {
        this.deliverLxr = deliverLxr;
    }

    public String getDeliverTel() {
        return deliverTel;
    }

    public void setDeliverTel(String deliverTel) {
        this.deliverTel = deliverTel;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
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

    public Double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(Double netPrice) {
        this.netPrice = netPrice;
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
        return "WMSOutboundTaskDto{" +
                "consignor='" + consignor + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", billNo='" + billNo + '\'' +
                ", billType='" + billType + '\'' +
                ", custPoNo='" + custPoNo + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", billDate=" + billDate +
                ", xdbs='" + xdbs + '\'' +
                ", kdDate=" + kdDate +
                ", deliverType='" + deliverType + '\'' +
                ", purOrgId='" + purOrgId + '\'' +
                ", remark='" + remark + '\'' +
                ", carror='" + carror + '\'' +
                ", shipLineId='" + shipLineId + '\'' +
                ", saveNo='" + saveNo + '\'' +
                ", city='" + city + '\'' +
                ", dist='" + dist + '\'' +
                ", shipToAddress='" + shipToAddress + '\'' +
                ", deliverLxr='" + deliverLxr + '\'' +
                ", deliverTel='" + deliverTel + '\'' +
                ", shippedDate=" + shippedDate +
                ", seqNo='" + seqNo + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", jzs=" + jzs +
                ", qty=" + qty +
                ", pcs=" + pcs +
                ", odd=" + odd +
                ", netPrice=" + netPrice +
                ", sjc=" + sjc +
                ", special=" + special +
                ", lotNo='" + lotNo + '\'' +
                ", exsattr1='" + exsattr1 + '\'' +
                ", exsattr2='" + exsattr2 + '\'' +
                '}';
    }
}
