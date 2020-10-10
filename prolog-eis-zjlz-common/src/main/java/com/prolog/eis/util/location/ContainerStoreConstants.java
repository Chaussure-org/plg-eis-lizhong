package com.prolog.eis.util.location;

/**
 * @author: wuxl
 * @create: 2020-09-29 10:08
 * @Version: V1.0
 */
public class ContainerStoreConstants {
    private ContainerStoreConstants() {
        throw new IllegalStateException("Utility class");
    }

    //----------------------------------托盘类型start----------------------------------//
    /**
     * 托盘类型 空托剁
     */
    public static final int CONTAINER_TYPE_EMPTY = -1;
    /**
     * 托盘类型 任务托
     */
    public static final int CONTAINER_TYPE_TASK = 1;
    //----------------------------------托盘类型end----------------------------------//

    //----------------------------------任务类型start----------------------------------//
    /**
     * 任务类型 无业务任务
     */
    public static final int TASK_TYPE_NOTHING = 0;
    /**
     * 任务类型 入库
     */
    public static final int TASK_TYPE_INBOUND = 10;
    /**
     * 任务类型 补货入库
     */
    public static final int TASK_TYPE_INBOUND_SUPPLEMENT = 11;
    /**
     * 任务类型 移库入库
     */
    public static final int TASK_TYPE_INBOUND_MOVE = 12;
    /**
     * 任务类型 出库
     */
    public static final int TASK_TYPE_OUTBOUND = 20;
    /**
     * 任务类型 盘点出库
     */
    public static final int TASK_TYPE_OUTBOUND_CHECK = 21;
    /**
     * 任务类型 盘点出库
     */
    public static final int TASK_TYPE_OUTBOUND_MOVE = 22;
    /**
     * 任务类型 合托出库
     */
    public static final int TASK_TYPE_OUTBOUND_MERGE = 23;
    //----------------------------------任务类型end----------------------------------//
}
