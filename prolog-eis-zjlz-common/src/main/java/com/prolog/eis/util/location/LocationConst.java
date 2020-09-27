package com.prolog.eis.util.location;

/**
 * @author: wuxl
 * @create: 2020-09-14 10:39
 * @Version: V1.0
 */
public class LocationConst {
    private LocationConst() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * RCS设备厂商
     */
    public static final String DEVICE_SYSTEM_RCS = "RCS";

    /**
     * MCS设备厂商
     */
    public static final String DEVICE_SYSTEM_MCS = "MCS";

    /**
     * RCS任务类型 厂内货架搬运
     */
    public static final String RCS_TASK_TYPE_TRANSPORT = "F01";

    /**
     * RCS任务类型 厂内货架空满交换
     */
    public static final String RCS_TASK_TYPE_EXCHANGE = "F02";

    /**
     * RCS任务类型 辊筒搬运接驳
     */
    public static final String RCS_TASK_TYPE_ACCESS = "F03";

    /**
     * RCS任务类型 厂内货架出库AGV待命
     */
    public static final String RCS_TASK_TYPE_AWAIT = "F04";

    /**
     * RCS任务类型 旋转货架
     */
    public static final String RCS_TASK_TYPE_WHIRL = "F05";

    /**
     * RCS任务类型回告 任务开始
     */
    public static final String RCS_TASK_METHOD_START = "start";

    /**
     * RCS任务类型回告 走出储位
     */
    public static final String RCS_TASK_METHOD_OUTBIN = "outbin";

    /**
     * RCS任务类型回告 任务结束
     */
    public static final String RCS_TASK_METHOD_END = "end";
    
    /**
     * MCS任务类型回告 任务开始
     */
    public static final int MCS_TASK_METHOD_START = 1;
    
    /**
     * MCS任务类型回告 任务结束
     */
    public static final int MCS_TASK_METHOD_END = 2;

    /**
     * 容器任务状态 未开始
     */
    public static final int PATH_TASK_STATE_NOTSTARTED = 0;

    /**
     * 容器任务状态 待发送任务
     */
    public static final int PATH_TASK_STATE_TOBESENT = 10;

    /**
     * 容器任务状态 已发送指令
     */
    public static final int PATH_TASK_STATE_SEND = 20;

    /**
     * 容器任务状态 设备回告开始
     */
    public static final int PATH_TASK_STATE_START = 30;

    /**
     * 容器任务状态 已经离开原位置
     */
    public static final int PATH_TASK_STATE_LEAVE = 40;

    /**
     * 容器任务明细状态 未开始
     */
    public static final int PATH_TASK_DETAIL_STATE_NOTSTARTED = -1;

    /**
     * 容器任务明细状态 到位
     */
    public static final int PATH_TASK_DETAIL_STATE_INPLACE = 0;

    /**
     * 容器任务明细状态 申请载具
     */
    public static final int PATH_TASK_DETAIL_STATE_APPLYPALLET = 10;

    /**
     * 容器任务明细状态 申请载具开始
     */
    public static final int PATH_TASK_DETAIL_STATE_APPLYPALLET_START = 20;

    /**
     * 容器任务明细状态 载具到位
     */
    public static final int PATH_TASK_DETAIL_STATE_PALLETINPLACE = 30;

    /**
     * 容器任务明细状态 开始绑定新载具
     */
    public static final int PATH_TASK_DETAIL_STATE_BINDPALLET = 40;

    /**
     * 容器任务明细状态 给设备发送指令
     */
    public static final int PATH_TASK_DETAIL_STATE_SEND = 50;

    /**
     * 容器任务明细状态 移动开始
     */
    public static final int PATH_TASK_DETAIL_STATE_START = 60;
    
    /**
     * 区域类型 区域
     */
    public static final int AREA_TYPE_AREA = 10;
    
    /**
     * 区域类型 接驳点
     */
    public static final int AREA_TYPE_POINT = 20;
}
