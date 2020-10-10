package com.prolog.eis.dto.location;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: wuxl
 * @create: 2020-09-09 10:49
 * @Version: V1.0
 */
@Data
public class ContainerPathTaskDetailDTO implements Serializable {
    private static final long serialVersionUID = -3625793335598422104L;

    /**
     * id
     */
    private Integer id;

    /**
     * 载具编号
     */
    private String palletNo;

    /**
     * 托盘号
     */
    private String containerNo;

    /**
     * 起点区域
     */
    private String sourceArea;

    /**
     * 起点
     */
    private String sourceLocation;

    /**
     * 下一个点位区域
     */
    private String nextArea;

    /**
     * 下一个点位
     */
    private String nextLocation;

    /**
     * 路径状态-1未开始  0到位 10申请载具 20申请载具开始 30载具到位 40开始绑定新载具 50给设备发送移动指令 60移动开始
     */
    private Integer taskState;

    /**
     * 设备任务Id
     */
    private String taskId;

    /**
     * 执行设备编号
     */
    private String deviceNo;

    /**
     * 任务序号 第一条为 1 后续递增
     */
    private Integer sortIndex;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 到位时间
     */
    private Date arriveTime;

    /**
     * 申请载具时间
     */
    private Date applyTime;

    /**
     * 申请载具开始时间
     */
    private Date applyStartTime;

    /**
     * 载具到位时间
     */
    private Date palletArriveTime;

    /**
     * 绑定新载具时间
     */
    private Date bindingPalletTime;

    /**
     * 绑定开始时间
     */
    private Date bindingStartTime;

    /**
     * 给设备发送移动指令时间
     */
    private Date sendTime;

    /**
     * 移动开始时间
     */
    private Date moveTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 起点区域设备系统
     */
    private String sourceDeviceSystem;

    /**
     * 下一个点位区域设备系统
     */
    private String nextDeviceSystem;

    /**
     * X原点
     */
    private Integer originX;

    /**
     * Y原点
     */
    private Integer originY;

    /**
     * 步长
     */
    private Double step;
}
