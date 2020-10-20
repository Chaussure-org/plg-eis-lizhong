package com.prolog.eis.enums;

public class ConstantEnum {

    //任务状态 wcs -> eis
    public static final short TASK_STATUS_START =1; //任务开始
    public static final short TASK_STATUS_FINISH =2;//任务结束
    public static final short TASK_STATUS_EXCEPTION =3;//任务异常

    //任务类型 wcs -> eis
    //1：入库 2：出库 3:移库 4:小车换层 5:输送线行走
    public static final short TASK_TYPE_RK =1; //入库
    public static final short TASK_TYPE_CK =2; //出库
    public static final short TASK_TYPE_YK =3; //移库
    public static final short TASK_TYPE_HC =4; //小车换层
    public static final short TASK_TYPE_XZ =5; //输送线行走

    public static final short TASK_TYPE_LXJZ =6; //料箱进站
    public static final short TASK_TYPE_DDKJZ =7; //订单框进站
    public static final short TASK_TYPE_SHAPE_INSPECT =8; //外形检测
    public static final short TASK_TYPE_RKK =9; //入库口

    public static final short TASK_TYPE_DDK_ARRIVE =10; //订单框到位
    public static final short TASK_TYPE_LX_ARRIVE =11; //料箱到位

    //命令类型 eis -> wcs
    public static final short COMMAND_TYPE_CK=1;//出库 同步
    public static final short COMMAND_TYPE_RK=2;//入库 异步
    public static final short COMMAND_TYPE_XZ=3;//行走 异步
    public static final short COMMAND_TYPE_REQUEST_ORDER_BOX=4;//订单框请求 异步
    public static final short COMMAND_TYPE_LIGHT=5;//灯光 同步
    public static final short COMMAND_TYPE_HC=6;

    public static final short TASK_TYPE_TO_AGV = 111;
    public static final short TASK_TYPE_BORROW = 112;
}
