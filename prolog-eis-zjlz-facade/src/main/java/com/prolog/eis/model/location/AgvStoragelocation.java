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
@ApiModel ("agv区域货位表")
@Table ("agv_storagelocation")
public class AgvStoragelocation {

  @Column("id")
  @Id
  @ApiModelProperty("id")
  private Integer id;

  @Column("area_no")
  @ApiModelProperty("区域编号")
  private String areaNo;

  @Column("location_no")
  @ApiModelProperty("点位编号")
  private String locationNo;

  @Column("ceng")
  @ApiModelProperty("入库楼层")
  private Integer ceng;

  @Column("x")
  @ApiModelProperty("坐标x")
  private Integer x;

  @Column("y")
  @ApiModelProperty("坐标y")
  private Integer y;

  @Column("location_type")
  @ApiModelProperty("位置类型 1存储位 2 输送线 3托盘作业位")
  private Integer locationType;

  @Column("tally_code")
  @ApiModelProperty("wms货位 可空")
  private String tallyCode;

  @Column("task_lock")
  @ApiModelProperty("任务锁  0空闲 1锁定")
  private Integer taskLock;

  @Column("storage_lock")
  @ApiModelProperty("1锁定  0不锁定")
  private Integer storageLock;

  @Column("device_no")
  @ApiModelProperty("设备编号")
  private String deviceNo;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAreaNo() {
    return areaNo;
  }

  public void setAreaNo(String areaNo) {
    this.areaNo = areaNo;
  }

  public String getLocationNo() {
    return locationNo;
  }

  public void setLocationNo(String locationNo) {
    this.locationNo = locationNo;
  }

  public Integer getCeng() {
    return ceng;
  }

  public void setCeng(Integer ceng) {
    this.ceng = ceng;
  }

  public Integer getX() {
    return x;
  }

  public void setX(Integer x) {
    this.x = x;
  }

  public Integer getY() {
    return y;
  }

  public void setY(Integer y) {
    this.y = y;
  }

  public Integer getLocationType() {
    return locationType;
  }

  public void setLocationType(Integer locationType) {
    this.locationType = locationType;
  }

  public String getTallyCode() {
    return tallyCode;
  }

  public void setTallyCode(String tallyCode) {
    this.tallyCode = tallyCode;
  }

  public Integer getTaskLock() {
    return taskLock;
  }

  public void setTaskLock(Integer taskLock) {
    this.taskLock = taskLock;
  }

  public Integer getStorageLock() {
    return storageLock;
  }

  public void setStorageLock(Integer storageLock) {
    this.storageLock = storageLock;
  }

  public String getDeviceNo() {
    return deviceNo;
  }

  public void setDeviceNo(String deviceNo) {
    this.deviceNo = deviceNo;
  }

}
