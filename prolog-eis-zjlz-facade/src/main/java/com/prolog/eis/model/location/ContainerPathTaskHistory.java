package com.prolog.eis.model.location;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 容器路径任务历史表(ContainerPathTaskHistory)实体类
 *
 * @author wuxl
 * @since 2020-09-23 11:22:24
 */
@Data
@Table("CONTAINER_PATH_TASK_HISTORY")
public class ContainerPathTaskHistory implements Serializable {
    private static final long serialVersionUID = -71823674078947117L;

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
    @ApiModelProperty("起点")
    private String sourceLocation;

    @Column("next_area")
    @ApiModelProperty("下一个点位区域")
    private String nextArea;

    @Column("next_location")
    @ApiModelProperty("下一个点位")
    private String nextLocation;

    @Column("task_type")
    @ApiModelProperty("任务类型(0无任务;10 入库;20出库;30待出库;40内部移位)")
    private Integer taskType;

    @Column("task_id")
    @ApiModelProperty("设备任务Id")
    private String taskId;

    @Column("device_no")
    @ApiModelProperty("执行设备编号")
    private String deviceNo;

    @Column("sort_index")
    @ApiModelProperty("任务序号 第一条为 1 后续递增")
    private Integer sortIndex;

    @Column("create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column("arrive_time")
    @ApiModelProperty("到位时间")
    private Date arriveTime;

    @Column("apply_time")
    @ApiModelProperty("申请载具时间")
    private Date applyTime;

    @Column("apply_start_time")
    @ApiModelProperty("申请载具开始时间")
    private Date applyStartTime;

    @Column("pallet_arrive_time")
    @ApiModelProperty("载具到位时间")
    private Date palletArriveTime;

    @Column("binding_pallet_time")
    @ApiModelProperty("绑定新载具时间")
    private Date bindingPalletTime;

    @Column("binding_start_time")
    @ApiModelProperty("绑定开始时间")
    private Date bindingStartTime;

    @Column("send_time")
    @ApiModelProperty("给设备发送移动指令时间")
    private Date sendTime;

    @Column("move_time")
    @ApiModelProperty("移动开始时间")
    private Date moveTime;

    @Column("end_time")
    @ApiModelProperty("任务结束时间")
    private Date endTime;

    @Column("update_time")
    @ApiModelProperty("${column.comment}")
    private Date updateTime;


}