package com.prolog.eis.dto.location;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: wuxl
 * @create: 2020-09-08 17:36
 * @Version: V1.0
 */
@Data
public class AgvStoragelocationDTO implements Serializable {
    private static final long serialVersionUID = 1692798578697870507L;

    private Integer id;

    /**
     * 区域编号
     */
    private String areaNo;

    /**
     * 点位编号
     */
    private String locationNo;

    /**
     * 入库楼层
     */
    private Integer ceng;

    /**
     * 坐标x
     */
    private Integer x;

    /**
     * 坐标y
     */
    private Integer y;

    /**
     * 位置类型 1存储位 2 输送线 3托盘作业位
     */
    private Integer locationType;

    /**
     * wms货位
     */
    private String tallyCode;

    /**
     * 任务锁  0空闲 1锁定
     */
    private Integer taskLock;

    /**
     * 1锁定  0不锁定
     */
    private Integer storageLock;

    /**
     * 设备编号
     */
    private String deviceNo;

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
