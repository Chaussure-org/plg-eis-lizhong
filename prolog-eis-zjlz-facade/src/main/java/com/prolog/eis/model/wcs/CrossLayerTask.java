package com.prolog.eis.model.wcs;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @Description  
 * @Author  SunPP
 * @Date 2020-10-26 
 */
@ApiModel ("null")
@Table ("cross_layer_task")
public class CrossLayerTask {

  @Column("task_id")
  @Id
  @ApiModelProperty("task_id")
  private String taskId;

  @Column("carNo")
  @ApiModelProperty("小车编号")
  private String carNo;

  @Column("source_layer")
  @ApiModelProperty("起始层")
  private Integer sourceLayer;

  @Column("target_layer")
  @ApiModelProperty("目标层")
  private Integer targetLayer;

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getCarNo() {
    return carNo;
  }

  public void setCarNo(String carNo) {
    this.carNo = carNo;
  }

  public Integer getSourceLayer() {
    return sourceLayer;
  }

  public void setSourceLayer(Integer sourceLayer) {
    this.sourceLayer = sourceLayer;
  }

  public Integer getTargetLayer() {
    return targetLayer;
  }

  public void setTargetLayer(Integer targetLayer) {
    this.targetLayer = targetLayer;
  }

}
