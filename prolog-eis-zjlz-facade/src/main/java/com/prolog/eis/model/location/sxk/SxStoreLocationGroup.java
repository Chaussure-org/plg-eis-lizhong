package com.prolog.eis.model.location.sxk;

import com.prolog.framework.core.annotation.AutoKey;
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
@ApiModel("货位组表")
@Table ("sx_store_location_group")
public class SxStoreLocationGroup {

  @Column("id")
  @Id
  @AutoKey(type = AutoKey.TYPE_IDENTITY)
  @ApiModelProperty("货位组ID")
  private Integer id;

  @Column("group_no")
  @ApiModelProperty("货位组编号")
  private String groupNo;

  @Column("entrance")
  @ApiModelProperty("入口类型：1、仅入口1，2、仅入口2，3、入口1+入口2")
  private Integer entrance;

  @Column("in_out_num")
  @ApiModelProperty("出入口数量")
  private Integer inOutNum;

  @Column("is_lock")
  @ApiModelProperty("是否锁定")
  private Integer isLock;

  @Column("ascent_lock_state")
  @ApiModelProperty("货位组升位锁")
  private Integer ascentLockState;

  @Column("ready_out_lock")
  @ApiModelProperty("待出库锁")
  private Integer readyOutLock;

  @Column("layer")
  @ApiModelProperty("层")
  private Integer layer;

  @Column("x")
  @ApiModelProperty("X轴")
  private Integer X;

  @Column("y")
  @ApiModelProperty("Y轴")
  private Integer Y;

  @Column("location_num")
  @ApiModelProperty("货位数量")
  private Integer locationNum;

  @Column("entrance1_property1")
  @ApiModelProperty("入口1的属性1(无入口则值为'none')")
  private String entrance1Property1;

  @Column("entrance1_property2")
  @ApiModelProperty("入口1的属性2(无入口则值为'none')")
  private String entrance1Property2;

  @Column("entrance2_property1")
  @ApiModelProperty("入口2的属性1(无入口则值为'none')")
  private String entrance2Property1;

  @Column("entrance2_property2")
  @ApiModelProperty("入口2的属性2(无入口则值为'none')")
  private String entrance2Property2;

  @Column("reserved_location")
  @ApiModelProperty("预留货位1.空托盘预留货位、2.理货预留货位、3.不用预留货位")
  private Integer reservedLocation;

  @Column("belong_area")
  @ApiModelProperty("所属区域")
  private Integer belongArea;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getGroupNo() {
    return groupNo;
  }

  public void setGroupNo(String groupNo) {
    this.groupNo = groupNo;
  }

  public Integer getEntrance() {
    return entrance;
  }

  public void setEntrance(Integer entrance) {
    this.entrance = entrance;
  }

  public Integer getInOutNum() {
    return inOutNum;
  }

  public void setInOutNum(Integer inOutNum) {
    this.inOutNum = inOutNum;
  }

  public Integer getIsLock() {
    return isLock;
  }

  public void setIsLock(Integer isLock) {
    this.isLock = isLock;
  }

  public Integer getAscentLockState() {
    return ascentLockState;
  }

  public void setAscentLockState(Integer ascentLockState) {
    this.ascentLockState = ascentLockState;
  }

  public Integer getReadyOutLock() {
    return readyOutLock;
  }

  public void setReadyOutLock(Integer readyOutLock) {
    this.readyOutLock = readyOutLock;
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

  public Integer getLocationNum() {
    return locationNum;
  }

  public void setLocationNum(Integer locationNum) {
    this.locationNum = locationNum;
  }

  public String getEntrance1Property1() {
    return entrance1Property1;
  }

  public void setEntrance1Property1(String entrance1Property1) {
    this.entrance1Property1 = entrance1Property1;
  }

  public String getEntrance1Property2() {
    return entrance1Property2;
  }

  public void setEntrance1Property2(String entrance1Property2) {
    this.entrance1Property2 = entrance1Property2;
  }

  public String getEntrance2Property1() {
    return entrance2Property1;
  }

  public void setEntrance2Property1(String entrance2Property1) {
    this.entrance2Property1 = entrance2Property1;
  }

  public String getEntrance2Property2() {
    return entrance2Property2;
  }

  public void setEntrance2Property2(String entrance2Property2) {
    this.entrance2Property2 = entrance2Property2;
  }

  public Integer getReservedLocation() {
    return reservedLocation;
  }

  public void setReservedLocation(Integer reservedLocation) {
    this.reservedLocation = reservedLocation;
  }

  public Integer getBelongArea() {
    return belongArea;
  }

  public void setBelongArea(Integer belongArea) {
    this.belongArea = belongArea;
  }

  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }
}
