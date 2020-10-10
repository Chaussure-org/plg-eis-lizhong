package com.prolog.eis.model.base;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


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

  @Column("specification")
  @ApiModelProperty("规格")
  private String specification;

  @Column("owner_drawn_no")
  @ApiModelProperty("客户图号")
  private String ownerDrawnNo;

  @Column("surface_deal")
  @ApiModelProperty("表面处理")
  private String surfaceDeal;

  @Column("goods_type")
  @ApiModelProperty("商品类别")
  private Integer goodsType;

  @Column("weight")
  @ApiModelProperty("重量（克：g）")
  private Double weight;

  @Column("package_number")
  @ApiModelProperty("包装数量")
  private Integer packageNumber;

  @Column("past_lable_flg")
  @ApiModelProperty("贴标标识")
  private Integer pastLableFlg;

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
  @ApiModelProperty("重量容错率")
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

  public String getSpecification() {
    return specification;
  }

  public void setSpecification(String specification) {
    this.specification = specification;
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

  public Integer getGoodsType() {
    return goodsType;
  }

  public void setGoodsType(Integer goodsType) {
    this.goodsType = goodsType;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  public Integer getPackageNumber() {
    return packageNumber;
  }

  public void setPackageNumber(Integer packageNumber) {
    this.packageNumber = packageNumber;
  }

  public Integer getPastLableFlg() {
    return pastLableFlg;
  }

  public void setPastLableFlg(Integer pastLableFlg) {
    this.pastLableFlg = pastLableFlg;
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
