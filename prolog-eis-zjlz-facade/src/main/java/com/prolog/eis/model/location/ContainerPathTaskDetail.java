package com.prolog.eis.model.location;

import com.prolog.framework.core.annotation.Column;
import com.prolog.framework.core.annotation.Id;
import com.prolog.framework.core.annotation.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出库在途容器表(ContainerPathTaskDetail)实体类
 *
 * @author wuxl
 * @since 2020-08-28 14:50:38
 */
@Data
@Table("container_path_task_detail")
public class ContainerPathTaskDetail implements Serializable {
    private static final long serialVersionUID = 304018533535420737L;

    @Id
    @Column("ID")
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

    @Column("task_state")
    @ApiModelProperty("路径状态-1未开始  0到位 10申请载具 20申请载具开始 30载具到位 40开始绑定新载具 50给设备发送移动指令 60移动开始")
    private Integer taskState;

    @Column("task_id")
    @ApiModelProperty("设备任务Id")
    private String taskId;

    @Column("device_no")
    @ApiModelProperty("执行设备编号")
    private String deviceNo;

    @Column("sort_index")
    @ApiModelProperty("任务序号 第一条为 1 后续递增")
    private Integer sortIndex;

    @Column(value = "create_time",jdbcType = Column.JdbcType_DATE)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @Column(value = "arrive_time",jdbcType = Column.JdbcType_DATE)
    @ApiModelProperty("到位时间")
    private Date arriveTime;

    @Column(value = "apply_time",jdbcType = Column.JdbcType_DATE)
    @ApiModelProperty("申请载具时间")
    private Date applyTime;

    @Column(value = "apply_start_time",jdbcType = Column.JdbcType_DATE)
    @ApiModelProperty("申请载具开始时间")
    private Date applyStartTime;

    @Column(value = "pallet_arrive_time",jdbcType = Column.JdbcType_DATE)
    @ApiModelProperty("载具到位时间")
    private Date palletArriveTime;

    @Column(value = "binding_pallet_time",jdbcType = Column.JdbcType_DATE)
    @ApiModelProperty("绑定新载具时间")
    private Date bindingPalletTime;

    @Column(value = "binding_start_time",jdbcType = Column.JdbcType_DATE)
    @ApiModelProperty("绑定开始时间")
    private Date bindingStartTime;

    @Column(value = "send_time",jdbcType = Column.JdbcType_DATE)
    @ApiModelProperty("给设备发送移动指令时间")
    private Date sendTime;

    @Column(value = "move_time",jdbcType = Column.JdbcType_DATE)
    @ApiModelProperty("移动开始时间")
    private Date moveTime;

    @Column(value = "update_time",jdbcType = Column.JdbcType_DATE)
    @ApiModelProperty("修改时间")
    private Date updateTime;

}