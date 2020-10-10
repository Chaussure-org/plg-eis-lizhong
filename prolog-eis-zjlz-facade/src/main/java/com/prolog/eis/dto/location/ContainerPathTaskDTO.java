package com.prolog.eis.dto.location;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: wuxl
 * @create: 2020-09-28 14:53
 * @Version: V1.0
 */
@Data
public class ContainerPathTaskDTO implements Serializable {
    private static final long serialVersionUID = 3094924352086024465L;

    private Integer id;

    /**
     *  载具编号
     */
    private String palletNo;

    /**
     *  托盘号
     */
    private String containerNo;

    /**
     *  起点区域
     */
    private String sourceArea;

    /**
     *  起点货位
     */
    private String sourceLocation;

    /**
     *  终点区域
     */
    private String targetArea;

    /**
     *  终点货位
     */
    private String targetLocation;

    /**
     *  容器高度
     */
    private Integer actualHeight;

    /**
     *  回传标识
     */
    private Integer callBack;

    /**
     *  任务类型
     */
    private Integer taskType;

    /**
     *  任务状态
     */
    private Integer taskState;

    /**
     *  创建时间
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date updateTime;

    /**
     *  x点
     */
    private Integer x;

    /**
     *  y点
     */
    private Integer y;

    /**
     *  步长
     */
    private double step;
}
