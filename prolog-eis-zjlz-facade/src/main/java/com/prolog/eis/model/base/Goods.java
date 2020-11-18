package com.prolog.eis.model.base;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-09-25 
 */
@ApiModel ("商品资料表")
@Table ("goods")
public class Goods {

  @Column("id")
  @Id
  @ApiModelProperty("id")
  private Integer id;

  @Column("goods_no")
  @ApiModelProperty("商品编号")
  private String goodsNo;

  @Column("goods_name")
  @ApiModelProperty("商品名称")
  private String goodsName;

  @Column("goods_one_type")
  @ApiModelProperty("大类")
  private String goodsOneType;

  @Column("owner_drawn_no")
  @ApiModelProperty("客户图号")
  private String ownerDrawnNo;

  @Column("surface_deal")
  @ApiModelProperty("表面处理")
  private String surfaceDeal;

  @Column("goods_type")
  @ApiModelProperty("商品类别")
  private String goodsType;

  @Column("weight")
  @ApiModelProperty("重量（克：g）")
  private BigDecimal weight;

  @Column("package_number")
  @ApiModelProperty("包装数量")
  private Integer packageNumber;

  @Column("past_label_flg")
  @ApiModelProperty("贴标标识")
  private Integer pastLabelFlg;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("update_time")
  @ApiModelProperty("修改时间")
  private java.util.Date updateTime;

  @Column("abc")
  @ApiModelProperty("ABC属性")
  private String abc;

  @Column("weight_fault_tolerance")
  @ApiModelProperty("重量容错率")
  private Double weightFaultTolerance;

  @Column("last_container_rate")
  @ApiModelProperty("尾拖比率")
  private Double lastContainerRate;

  public Double getLastContainerRate() {
    return lastContainerRate;
  }

  public void setLastContainerRate(Double lastContainerRate) {
    this.lastContainerRate = lastContainerRate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getGoodsNo() {
    return goodsNo;
  }

  public void setGoodsNo(String goodsNo) {
    this.goodsNo = goodsNo;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public String getGoodsOneType() {
    return goodsOneType;
  }

  public void setGoodsOneType(String goodsOneType) {
    this.goodsOneType = goodsOneType;
  }

  public String getOwnerDrawnNo() {
    return ownerDrawnNo;
  }

  public void setOwnerDrawnNo(String ownerDrawnNo) {
    this.ownerDrawnNo = ownerDrawnNo;
  }

  public String getSurfaceDeal() {
    return surfaceDeal;
  }

  public void setSurfaceDeal(String surfaceDeal) {
    this.surfaceDeal = surfaceDeal;
  }

  public String getGoodsType() {
    return goodsType;
  }

  public void setGoodsType(String goodsType) {
    this.goodsType = goodsType;
  }

  public BigDecimal getWeight() {
    return weight;
  }

  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

  public Integer getPackageNumber() {
    return packageNumber;
  }

  public void setPackageNumber(Integer packageNumber) {
    this.packageNumber = packageNumber;
  }

  public Integer getPastLabelFlg() {
    return pastLabelFlg;
  }

  public void setPastLabelFlg(Integer pastLabelFlg) {
    this.pastLabelFlg = pastLabelFlg;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

  public java.util.Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.util.Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getAbc() {
    return abc;
  }

  public void setAbc(String abc) {
    this.abc = abc;
  }

  public Double getWeightFaultTolerance() {
    return weightFaultTolerance;
  }

  public void setWeightFaultTolerance(Double weightFaultTolerance) {
    this.weightFaultTolerance = weightFaultTolerance;
  }

}
