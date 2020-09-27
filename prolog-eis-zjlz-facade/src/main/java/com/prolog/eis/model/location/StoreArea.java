package com.prolog.eis.model.location;

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
@ApiModel ("库存区域表")
@Table ("store_area")
public class StoreArea {

  @Column("area_no")
  @ApiModelProperty("区域编号")
  private String areaNo;

  @Column("area_type")
  @ApiModelProperty("10区域;20接驳点")
  private Integer areaType;

  @Column("device_system")
  @ApiModelProperty("下游wcs系统")
  private String deviceSystem;

  @Column("location_no")
  @ApiModelProperty("area_type=20时有值 点位编号")
  private String locationNo;

  @Column("layer")
  @ApiModelProperty("area_type=20时有值  层")
  private Integer layer;

  @Column("x")
  @ApiModelProperty("area_type=2时有值  X")
  private Integer X;

  @Column("y")
  @ApiModelProperty("area_type=2时有值  Y")
  private Integer Y;

  @Column("max_height")
  @ApiModelProperty("高度限制")
  private Integer maxHeight;

  @Column("temporary_area")
  @ApiModelProperty("0 不允许暂存 1允许暂存")
  private Integer temporaryArea;

  @Column("max_count")
  @ApiModelProperty("最大容器数")
  private Integer maxCount;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("update_time")
  @ApiModelProperty("修改时间")
  private java.util.Date updateTime;

  public String getAreaNo() {
    return areaNo;
  }

  public void setAreaNo(String areaNo) {
    this.areaNo = areaNo;
  }

  public Integer getAreaType() {
    return areaType;
  }

  public void setAreaType(Integer areaType) {
    this.areaType = areaType;
  }

  public String getDeviceSystem() {
    return deviceSystem;
  }

  public void setDeviceSystem(String deviceSystem) {
    this.deviceSystem = deviceSystem;
  }

  public String getLocationNo() {
    return locationNo;
  }

  public void setLocationNo(String locationNo) {
    this.locationNo = locationNo;
  }

  public Integer getLayer() {
    return layer;
  }

  public void setLayer(Integer layer) {
    this.layer = layer;
  }

  public Integer getX() {
    return X;
  }

  public void setX(Integer X) {
    this.X = X;
  }

  public Integer getY() {
    return Y;
  }

  public void setY(Integer Y) {
    this.Y = Y;
  }

  public Integer getMaxHeight() {
    return maxHeight;
  }

  public void setMaxHeight(Integer maxHeight) {
    this.maxHeight = maxHeight;
  }

  public Integer getTemporaryArea() {
    return temporaryArea;
  }

  public void setTemporaryArea(Integer temporaryArea) {
    this.temporaryArea = temporaryArea;
  }

  public Integer getMaxCount() {
    return maxCount;
  }

  public void setMaxCount(Integer maxCount) {
    this.maxCount = maxCount;
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
