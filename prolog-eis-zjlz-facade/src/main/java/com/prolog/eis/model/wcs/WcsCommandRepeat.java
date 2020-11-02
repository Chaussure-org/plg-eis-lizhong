package com.prolog.eis.model.wcs;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  Hunter
 * @Date 2020-11-02 
 */
@ApiModel ("wcs指令失败重发表")
@Table ("wcs_command_repeat")
public class WcsCommandRepeat {

  @Column("task_id")
  @ApiModelProperty("任务id")
  private String taskId;

  @Column("address")
  @ApiModelProperty("源地址")
  private String address;

  @Column("target")
  @ApiModelProperty("目标地址")
  private String target;

  @Column("container_no")
  @ApiModelProperty("容器号")
  private String containerNo;

  @Column("type")
  @ApiModelProperty("任务类型")
  private Integer type;

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

}
