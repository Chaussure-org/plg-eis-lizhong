package com.prolog.eis.model;

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
@ApiModel ("托盘库存商品表")
@Table ("container_store_goods")
public class ContainerStoreGoods {

  @Column("id")
  @Id
  @ApiModelProperty("托盘库存商品ID")
  private Integer id;

  @Column("container_no")
  @ApiModelProperty("托盘号")
  private String containerNo;

  @Column("goods_barcode")
  @ApiModelProperty("商品条码")
  private String goodsBarcode;

  @Column("case_specs")
  @ApiModelProperty("箱规格，整数数值，每箱包含多少中盒")
  private String caseSpecs;

  @Column("box_specs")
  @ApiModelProperty("中盒规格，整数数值，每个中盒包含多少花盒")
  private String boxSpecs;

  @Column("abc_type")
  @ApiModelProperty("A B C品种类型")
  private String abcType;

  @Column("chinese_name")
  @ApiModelProperty("商品中文名称")
  private String chineseName;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("update_time")
  @ApiModelProperty("更新时间")
  private java.util.Date updateTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getGoodsBarcode() {
    return goodsBarcode;
  }

  public void setGoodsBarcode(String goodsBarcode) {
    this.goodsBarcode = goodsBarcode;
  }

  public String getCaseSpecs() {
    return caseSpecs;
  }

  public void setCaseSpecs(String caseSpecs) {
    this.caseSpecs = caseSpecs;
  }

  public String getBoxSpecs() {
    return boxSpecs;
  }

  public void setBoxSpecs(String boxSpecs) {
    this.boxSpecs = boxSpecs;
  }

  public String getAbcType() {
    return abcType;
  }

  public void setAbcType(String abcType) {
    this.abcType = abcType;
  }

  public String getChineseName() {
    return chineseName;
  }

  public void setChineseName(String chineseName) {
    this.chineseName = chineseName;
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

}
