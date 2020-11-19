package com.prolog.eis.model.wcs;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.omg.CORBA.PUBLIC_MEMBER;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-11-18 
 */
@ApiModel ("拆盘机")
@Table ("open_disk")
public class OpenDisk {
  /**
   * 拆盘机入口
   */
  public static final String OPEN_DISK_IN = "BK01";

  /**
   * 拆盘机出口
   */
  public static final String OPEN_DISK_OUT = "BK02";

  /**
   * 点位状态空闲
   */
  public static final int  TASK_STATUS_NOT = 0;

  /**
   * 点位状态载货 到达
   */
  public static final int  TASK_STATUS_ARRIVE = 1;

  
  @Column("open_disk_id")
  @ApiModelProperty("拆盘机点位id")
  @Id
  private String openDiskId;

  @Column("open_disk_name")
  @ApiModelProperty("拆盘机点位名称")
  private String openDiskName;

  @Column("task_status")
  @ApiModelProperty("点位状态（0：空闲 ，1：到位）")
  private Integer taskStatus;

  @Column("device_no")
  @ApiModelProperty("设备编号")
  private String deviceNo;

  public String getOpenDiskId() {
    return openDiskId;
  }

  public void setOpenDiskId(String openDiskId) {
    this.openDiskId = openDiskId;
  }

  public String getOpenDiskName() {
    return openDiskName;
  }

  public void setOpenDiskName(String openDiskName) {
    this.openDiskName = openDiskName;
  }

  public Integer getTaskStatus() {
    return taskStatus;
  }

  public void setTaskStatus(Integer taskStatus) {
    this.taskStatus = taskStatus;
  }

  public String getDeviceNo() {
    return deviceNo;
  }

  public void setDeviceNo(String deviceNo) {
    this.deviceNo = deviceNo;
  }

}
