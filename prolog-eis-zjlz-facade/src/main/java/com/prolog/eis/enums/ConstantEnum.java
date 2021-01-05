package com.prolog.eis.enums;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstantEnum {

    //任务状态 wcs -> eis
    //任务开始
    public static final short TASK_STATUS_START = 1;
    //任务结束
    public static final short TASK_STATUS_FINISH = 2;
    //任务异常
    public static final short TASK_STATUS_EXCEPTION = 3;

    public static List<String> secondInBcrs = Arrays.asList("BCR0205", "BCR0206", "BCR0207", "BCR0208");

    public static List<String> secondOutBcrs = Arrays.asList("BCR0201", "BCR0202", "BCR0203", "BCR0204");

    public static List<String> secondPoints = Arrays.asList("RTM0202", "RTM0201", "RTM0203", "RTM0204", "RTM0205", "RTM0206", "RTM0207", "RTM0208"
            , "MTR0201", "MTR0202", "MTR0203", "MTR0204", "MTR0205", "MTR0206", "MTR0207", "MTR0208");

    //任务类型 wcs -> eis

    /**
     * 1.箱库 1楼入库BCR
     * 2.
     */
    public static final int BCR_TYPE_XKRK = 10;


    /**
     * 立库入库bcr
     */
    public static final int BCR_TYPE_LKRK = 20;


    /**
     * 请求类型 6-料箱进站 7-订单框进站 8-体积检测 9-入库口
     * 入库
     */
    public static final int TYPE_IN = 1;

    /**
     * 出库
     */
    public static final int TYPE_OUT = 2;

    /**
     * 站台
     */
    public static final int TYPE_STATION = 3;

    /**
     * 借道
     */
    public static final int TYPE_BORROW = 4;


}
