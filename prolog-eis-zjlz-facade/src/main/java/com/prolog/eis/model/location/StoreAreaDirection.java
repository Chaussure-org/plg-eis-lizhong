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
@ApiModel ("库存区域方向表")
@Table ("store_area_direction")
public class StoreAreaDirection {

  @Column("id")
  @Id
  @ApiModelProperty("ID")
  private Integer id;

  @Column("source_area_no")
  @ApiModelProperty("起点区域")
  private String sourceAreaNo;

  @Column("target_area_no")
  @ApiModelProperty("终点区域")
  private String targetAreaNo;

  @Column("max_height")
  @ApiModelProperty("高度限制")
  private Integer maxHeight;

  @Column("path_step")
  @ApiModelProperty("路线长度")
  private Integer pathStep;

  @Column("path_power")
  @ApiModelProperty("路线权值")
  private Integer pathPower;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private java.util.Date createTime;

  @Column("update_time")
  @ApiModelProperty("修改时间")
  private java.util.Date updateTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSourceAreaNo() {
    return sourceAreaNo;
  }

  public void setSourceAreaNo(String sourceAreaNo) {
    this.sourceAreaNo = sourceAreaNo;
  }

  public String getTargetAreaNo() {
    return targetAreaNo;
  }

  public void setTargetAreaNo(String targetAreaNo) {
    this.targetAreaNo = targetAreaNo;
  }

  public Integer getMaxHeight() {
    return maxHeight;
  }

  public void setMaxHeight(Integer maxHeight) {
    this.maxHeight = maxHeight;
  }

  public Integer getPathStep() {
    return pathStep;
  }

  public void setPathStep(Integer pathStep) {
    this.pathStep = pathStep;
  }

  public Integer getPathPower() {
    return pathPower;
  }

  public void setPathPower(Integer pathPower) {
    this.pathPower = pathPower;
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
