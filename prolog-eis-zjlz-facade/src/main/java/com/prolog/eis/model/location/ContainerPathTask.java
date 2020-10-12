package com.prolog.eis.model.location;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出库在途容器表(ContainerPathTask)实体类
 *
 * @author wuxl
 * @since 2020-08-28 14:58:58
 */
@Data
@Table("container_path_task")
public class ContainerPathTask implements Serializable {
  private static final long serialVersionUID = 8099230143022035155L;

  @Id
  @Column("id")
  @ApiModelProperty("id")
  private Integer id;

  @Column("pallet_no")
  @ApiModelProperty("载具编号")
  private String palletNo;

  @Column("container_no")
  @ApiModelProperty("托盘号")
  private String containerNo;

  @Column("source_area")
  @ApiModelProperty("起点区域")
  private String sourceArea;

  @Column("source_location")
  @ApiModelProperty("起点货位")
  private String sourceLocation;

  @Column("target_area")
  @ApiModelProperty("终点区域")
  private String targetArea;

  @Column("target_location")
  @ApiModelProperty("终点货位")
  private String targetLocation;

  @Column("actual_height")
  @ApiModelProperty("容器高度")
  private Integer actualHeight;

  @Column("call_back")
  @ApiModelProperty("回传标识")
  private Integer callBack;

  @Column("task_type")
  @ApiModelProperty("任务类型")
  private Integer taskType;

  @Column("task_state")
  @ApiModelProperty("任务状态")
  private Integer taskState;

  @Column("create_time")
  @ApiModelProperty("创建时间")
  private Date createTime;

  @Column("update_time")
  @ApiModelProperty("更新时间")
  private Date updateTime;

}
