package com.prolog.eis.enums;

public class ConstantEnum {

    //任务状态 wcs -> eis
    public static final short TASK_STATUS_START =1; //任务开始
    public static final short TASK_STATUS_FINISH =2;//任务结束
    public static final short TASK_STATUS_EXCEPTION =3;//任务异常

    //任务类型 wcs -> eis
    /** 请求类型 6-料箱进站 7-订单框进站 8-体积检测 9-入库口
     * 入库
     */
    public static final int TYPE_RK = 1;

    /**
     * 进站
     */
    public static final int TYPE_IN = 2;

    /**
     * 移动
     */
    public static final int TYPE_MOVE = 3;

    /**
     * 借道
     */
    public static final int TYPE_BORROW = 4;


    /**
     * 箱库入库bcr
     */
    public static final int BCR_TYPE_XKRK = 1;

    /**
     * 立库入库bcr
     */
    public static final int BCR_TYPE_LKRK = 4;
}
