package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.crypto.Data;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/12/8 18:55
 * 拆盘机订单拖到位dto
 */
public class TrayCallbackDto {

    @ApiModelProperty("taskId ")
    private String taskId;

    @ApiModelProperty("发送时间")
    private Data time;

    @ApiModelProperty("对接滚筒编号")
    private String deviceId ;


    @ApiModelProperty("AGV是否到位 0：未到位 1：已到位")
    private String isArrive;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Data getTime() {
        return time;
    }

    public void setTime(Data time) {
        this.time = time;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIsArrive() {
        return isArrive;
    }

    public void setIsArrive(String isArrive) {
        this.isArrive = isArrive;
    }

    @Override
    public String toString() {
        return "TrayCallbackDto{" +
                "taskId='" + taskId + '\'' +
                ", time=" + time +
                ", deviceId='" + deviceId + '\'' +
                ", isArrive='" + isArrive + '\'' +
                '}';
    }
}
