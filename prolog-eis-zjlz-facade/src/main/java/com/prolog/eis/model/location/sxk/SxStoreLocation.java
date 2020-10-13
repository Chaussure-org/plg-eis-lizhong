package com.prolog.eis.model.location.sxk;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-10-10 
 */
@ApiModel("货位表")
@Table ("sx_store_location")
public class SxStoreLocation {

  @Column("id")
  @Id
  @ApiModelProperty("货位id")
  private Integer id;

  @Column("store_no")
  @ApiModelProperty("货位编号-wcs货位坐标")
  private String storeNo;

  @Column("store_location_group_id")
  @ApiModelProperty("货位组id")
  private Integer storeLocationGroupId;

  @Column("area_no")
  @ApiModelProperty("区域编号")
  private String areaNo;

  @Column("layer")
  @ApiModelProperty("层")
  private Integer layer;

  @Column("x")
  @ApiModelProperty("巷道")
  private Integer X;

  @Column("y")
  @ApiModelProperty("y轴（列）")
  private Integer Y;

  @Column("store_location_id1")
  @ApiModelProperty("相邻货位id1")
  private Integer storeLocationId1;

  @Column("store_location_id2")
  @ApiModelProperty("相邻货位id2")
  private Integer storeLocationId2;

  @Column("ascent_lock_state")
  @ApiModelProperty("货位升位锁")
  private Integer ascentLockState;

  @Column("location_index")
  @ApiModelProperty("货位组位置索引(从上到下、从左到右)")
  private Integer locationIndex;

  @Column("depth")
  @ApiModelProperty("货位深度")
  private Integer depth;

  @Column("dept_num")
  @ApiModelProperty("移位数")
  private Integer deptNum;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("vertical_location_group_id")
  @ApiModelProperty("垂直货位组Id")
  private Integer verticalLocationGroupId;

  @Column("actual_weight")
  @ApiModelProperty("实际重量")
  private Double actualWeight;

  @Column("limit_weight")
  @ApiModelProperty("限重")
  private Double limitWeight;

  @Column("is_inBound_location")
  @ApiModelProperty("是否为入库货位(0.否、1、是)")
  private Integer isInBoundLocation;

  @Column("wms_store_no")
  @ApiModelProperty("Wms货位编号")
  private String wmsStoreNo;

  @Column("is_exception")
  @ApiModelProperty("是否为异常箱位")
  private Integer isException;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getStoreNo() {
    return storeNo;
  }

  public void setStoreNo(String storeNo) {
    this.storeNo = storeNo;
  }

  public Integer getStoreLocationGroupId() {
    return storeLocationGroupId;
  }

  public void setStoreLocationGroupId(Integer storeLocationGroupId) {
    this.storeLocationGroupId = storeLocationGroupId;
  }

  public String getAreaNo() {
    return areaNo;
  }

  public void setAreaNo(String areaNo) {
    this.areaNo = areaNo;
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

  public Integer getStoreLocationId1() {
    return storeLocationId1;
  }

  public void setStoreLocationId1(Integer storeLocationId1) {
    this.storeLocationId1 = storeLocationId1;
  }

  public Integer getStoreLocationId2() {
    return storeLocationId2;
  }

  public void setStoreLocationId2(Integer storeLocationId2) {
    this.storeLocationId2 = storeLocationId2;
  }

  public Integer getAscentLockState() {
    return ascentLockState;
  }

  public void setAscentLockState(Integer ascentLockState) {
    this.ascentLockState = ascentLockState;
  }

  public Integer getLocationIndex() {
    return locationIndex;
  }

  public void setLocationIndex(Integer locationIndex) {
    this.locationIndex = locationIndex;
  }

  public Integer getDepth() {
    return depth;
  }

  public void setDepth(Integer depth) {
    this.depth = depth;
  }

  public Integer getDeptNum() {
    return deptNum;
  }

  public void setDeptNum(Integer deptNum) {
    this.deptNum = deptNum;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

  public Integer getVerticalLocationGroupId() {
    return verticalLocationGroupId;
  }

  public void setVerticalLocationGroupId(Integer verticalLocationGroupId) {
    this.verticalLocationGroupId = verticalLocationGroupId;
  }

  public Double getActualWeight() {
    return actualWeight;
  }

  public void setActualWeight(Double actualWeight) {
    this.actualWeight = actualWeight;
  }

  public Double getLimitWeight() {
    return limitWeight;
  }

  public void setLimitWeight(Double limitWeight) {
    this.limitWeight = limitWeight;
  }

  public Integer getIsInBoundLocation() {
    return isInBoundLocation;
  }

  public void setIsInBoundLocation(Integer isInBoundLocation) {
    this.isInBoundLocation = isInBoundLocation;
  }

  public String getWmsStoreNo() {
    return wmsStoreNo;
  }

  public void setWmsStoreNo(String wmsStoreNo) {
    this.wmsStoreNo = wmsStoreNo;
  }

  public Integer getIsException() {
    return isException;
  }

  public void setIsException(Integer isException) {
    this.isException = isException;
  }

}
