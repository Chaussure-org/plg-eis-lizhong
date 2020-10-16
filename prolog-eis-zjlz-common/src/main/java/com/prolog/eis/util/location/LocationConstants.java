package com.prolog.eis.util.location;

/**
 * @author: wuxl
 * @create: 2020-09-14 10:39
 * @Version: V1.0
 */
public final class LocationConstants {

    private LocationConstants() {
        throw new IllegalStateException("Utility class");
    }

    //----------------------------------下游WCS系统start----------------------------------//
    /**
     * RCS设备厂商
     */
    public static final String DEVICE_SYSTEM_RCS = "RCS";
    /**
     * MCS设备厂商
     */
    public static final String DEVICE_SYSTEM_MCS = "MCS";
    /**
     * SAS设备厂商
     */
    public static final String DEVICE_SYSTEM_SAS = "SAS";
    /**
     * WCS设备厂商
     */
    public static final String DEVICE_SYSTEM_WCS = "WCS";

    //----------------------------------下游WCS系统end----------------------------------//

    //----------------------------------RCS任务类型start----------------------------------//
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
    //----------------------------------RCS任务类型end----------------------------------//

    //----------------------------------RCS任务类型回告start----------------------------------//
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
    //----------------------------------RCS任务类型回告end----------------------------------//

    //----------------------------------MCS任务类型回告start----------------------------------//
    /**
     * MCS任务类型回告 任务开始
     */
    public static final int MCS_TASK_METHOD_START = 1;
    /**
     * MCS任务类型回告 任务结束
     */
    public static final int MCS_TASK_METHOD_END = 2;
    //----------------------------------MCS任务类型回告end----------------------------------//

    //----------------------------------容器任务状态start----------------------------------//
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
    //----------------------------------容器任务状态end----------------------------------//

    //----------------------------------容器任务明细状态start----------------------------------//
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
    //----------------------------------容器任务明细状态end----------------------------------//

    //----------------------------------区域类型start----------------------------------//
    /**
     * 区域类型 区域
     */
    public static final int AREA_TYPE_AREA = 10;
    /**
     * 区域类型 接驳点
     */
    public static final int AREA_TYPE_POINT = 20;
    //----------------------------------区域类型end----------------------------------//

    //----------------------------------路径任务类型start----------------------------------//
    /**
     * 路径任务类型 无任务
     */
    public static final int PATH_TASK_TYPE_NONE = 0;
    /**
     * 路径任务类型 入库任务
     */
    public static final int PATH_TASK_TYPE_INBOUND = 10;
    /**
     * 路径任务类型 出库任务
     */
    public static final int PATH_TASK_TYPE_OUTBOUND = 20;
    /**
     * 路径任务类型 待出库或待移动
     */
    public static final int PATH_TASK_TYPE_WAIT = 30;
    /**
     * 路径任务类型 内部移动
     */
    public static final int PATH_TASK_TYPE_MOVE = 40;
    //----------------------------------路径任务类型end----------------------------------//

    //----------------------------------路径任务是否回传start----------------------------------//
    /**
     * 是否回传 不回传
     */
    public static final int TASK_CALLBACK_NONE = 0;
    /**
     * 是否回传 回传
     */
    public static final int TASK_CALLBACK_CALLBACK = 10;
    //----------------------------------路径任务是否回传end----------------------------------//

    //----------------------------------货位组升位锁start----------------------------------//

    /**
     * 货位组升位锁 不锁定
     */
    public static final int GROUP_ASCENTLOCK_UNLOCK = 0;
    /**
     * 货位组升位锁 锁定
     */
    public static final int GROUP_ASCENTLOCK_LOCK = 1;
    //----------------------------------货位组升位锁end----------------------------------//

    //----------------------------------AGV任务锁start----------------------------------//
    /**
     * AGV任务锁 空闲
     */
    public static final int AGV_TASKLOCK_UNLOCK = 0;
    /**
     * AGV任务锁 锁定
     */
    public static final int AGV_TASKLOCK_LOCK = 1;
    //----------------------------------AGV任务锁end----------------------------------//

    //----------------------------------AGV货位锁start----------------------------------//
    /**
     * AGV货位锁 不锁定
     */
    public static final int AGV_STORAGELOCK_UNLOCK = 0;
    /**
     * AGV货位锁 锁定
     */
    public static final int AGV_STORAGELOCK_LOCK = 1;
    //----------------------------------AGV货位锁end----------------------------------//

    //----------------------------------AGV位置类型start----------------------------------//
    /**
     * AGV位置类型 存储位
     */
    public static final int AGV_LOCATION_TYPE_STORAGE = 1;
    /**
     * AGV位置类型 输送线
     */
    public static final int AGV_LOCATION_TYPE_TRANSMISSION = 2;
    /**
     * AGV位置类型 托盘作业位
     */
    public static final int AGV_LOCATION_TYPE_PALLET = 3;
    //----------------------------------AGV货位锁end----------------------------------//

    //----------------------------------区域货位预留类型start----------------------------------//
    /**
     * 区域预留类型 数量
     */
    public static final int AREA_RESERVE_TYPE_NUMBER = 1;
    /**
     * 区域预留类型 百分比
     */
    public static final int AREA_RESERVE_TYPE_PERCENTAGE = 2;
    //----------------------------------区域货位预留类型end----------------------------------//
}
