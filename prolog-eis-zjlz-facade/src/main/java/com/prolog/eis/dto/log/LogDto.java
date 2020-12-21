package com.prolog.eis.dto.log;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020/7/20 20:20
 */
public class LogDto {

    /**
     * wms系统
     */
    public static final int WMS = 1;

    /**
     * wcs系统
     */
    public static final int WCS = 2;

    /**
     * sas系统
     */
    public static final int SAS = 3;

    /**
     * mcs系统
     */
    public static final int MCS = 4;

    /**
     * rcs系统
     */
    public static final int RCS = 5;
    /**
     * wms入库任务下发至eis
     */
    public static final int WMS_TYPE_SEND_INBOUND_TASK = 1;

    /**
     * wms出库任务下发至eis
     */
    public static final int WMS_TYPE_SEND_OUTBOUND_TASK = 2;

    /**
     * wms修改优先级至eis
     */
    public static final int WMS_TYPE_UPDATE_PRIORITY = 3;

    /**
     * eis入库任务完成回告wms
     */
    public static final int WMS_TYPE_INBOUND_CALLBACK = 4;

    /**
     * eis出库任务完成回告wms
     */
    public static final int WMS_TYPE_OUTBOUND_CALLBACK = 5;

    /**
     * wms同步商品到eis
     */
    public static final int WMS_TYPE_GOODS_SYNC = 6;

    /**
     * wms盘点任务下发
     */
    public static final int WMS_TYPE_SEND_INVENTORY_TAKS = 7;

    /**
     * eis盘点任务完成回告
     */
    public static final int WMS_TYPE_INVENTORY_CALLBACK = 8;


    /**
     * eis拣货开始回告wms
     */
    public static final int WMS_TYPE_START_ORDER = 9;

    /**
     * wcs输送线任务回告eis
     */
    public static final int WCS_TYPE_TASK_CALLBACK = 1;

    /**
     * wcsBCR回告eis
     */
    public static final int WCS_TYPE_BCR_REQUEST = 2;

    /**
     * eis下发输送线行走至wcs
     */
    public static final int WCS_TYPE_LINE_MOVE = 3;

    /**
     * wcs拆盘机入口回告
     */
    public static final int WCS_TYPE_OPEN_DISK_IN = 4;


    /**
     * wcs拆盘机出口回告
     */
    public static final int WCS_TYPE_OPEN_DISK_OUT = 5;


    /**
     * wcs容器放行回告
     */
    public static final int WCS_TYPE_CONTAINER_LEAVE = 6;


    /**
     * eis托盘到位回告wcs
     */
    public static final int WCS_TYPE_TARY_ARRIVE = 7;

    /**
     * sas入库任务回告eis
     */
    public static final int SAS_TYPE_SEND_INBOUND_TASK_CALLBACK = 1;

    /**
     * sas出库任务回告eis
     */
    public static final int SAS_TYPE_SEND_OUTBOUND_TASK_CALLBACK = 2;

    /**
     * sas换层任务回告eis
     */
    public static final int SAS_TYPE_CHANGE_LAYER_CALLBACK = 3;

    /**
     * eis下发换层指令sas
     */
    public static final int SAS_TYPE_CHANGE_LAYER = 4;

    /**
     * eis下发出入库指令sas
     */
    public static final int SAS_TYPE_SEND_TASK = 5;

    /**
     * eis请求小车信息sas
     */
    public static final int SAS_TYPE_GET_CARINFO = 6;

    /**
     * eis请求提升机信息sas
     */
    public static final int SAS_TYPE_GET_HOISTERINFO = 7;

    /**
     * rcs任务回告eis
     */
    public static final int RCS_TYPE_CALLBACK = 1;

    /**
     * eis下发agv移动任务
     */
    public static final int RCS_TYPE_SEND_TASK = 2;

    /**
     * mcs任务回告eis
     */
    public static final int MCS_TYPE_CALLBACK = 1;

    /**
     * eis下发堆垛机移动指令mcs
     */
    public static final int MCS_TYPE_CONTIANER_MOVE = 2;

    /**
     * eis查询堆垛机信息
     */
    public static final int MCS_TYPE_GETCATINFO = 3;


    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("接口描述")
    private String descri;

    @ApiModelProperty("方法流向")
    private String direct;

    @ApiModelProperty("任务类型")
    private Integer type;

    @ApiModelProperty("方法名")
    private String methodName;

    @ApiModelProperty("参数")
    private String params;

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("异常信息")
    private String exception;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("系统类型")
    private int systemType;

    @ApiModelProperty("本机地址")
    private String hostPort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getSystemType() {
        return systemType;
    }

    public void setSystemType(Integer systemType) {
        this.systemType = systemType;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    @Override
    public String toString() {
        return "LogDto{" +
                "id=" + id +
                ", descri='" + descri + '\'' +
                ", direct='" + direct + '\'' +
                ", type=" + type +
                ", methodName='" + methodName + '\'' +
                ", params='" + params + '\'' +
                ", success=" + success +
                ", exception='" + exception + '\'' +
                ", createTime=" + createTime +
                ", systemType=" + systemType +
                ", hostPort=" + hostPort +
                '}';
    }
}
