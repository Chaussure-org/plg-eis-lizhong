package com.prolog.eis.util.location;

/**
 * 公共常量类
 */
public interface CommonConstants {
    static final int YES = 1;
    static final int NO = 0;
    static final String SUCCESS = "success";
    static final String WARN = "warn";
    static final String ERROR = "error";

    /**
     * 站台作业类型
     */
    static final int STATION_TYPE_SEEDING = 10; // 拣选播种
    static final int STATION_TYPE_INVENTORY = 20; // 盘点
    static final int STATION_TYPE_MERGE_CONTAINER = 30; // 合托
    static final int STATION_TYPE_MAINTAIN = 40; // 养护

    /**
     * 合托任务锁
     */
    static final String MERGE_CONTAINER_TASK = "MERGE_CONTAINER_TASK";


}
