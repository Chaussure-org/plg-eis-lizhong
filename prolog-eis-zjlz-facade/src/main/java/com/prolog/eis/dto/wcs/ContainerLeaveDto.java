package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/12/8 16:04
 * 拣选站料箱放行dto
 */
public class ContainerLeaveDto {

    @ApiModelProperty("任务号id")
    private String taskId;

    @ApiModelProperty("发送时间")
    private String time;

    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("拣选站编号")
    private String deviceId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "ContainerLeaveDto{" +
                "taskId='" + taskId + '\'' +
                ", time='" + time + '\'' +
                ", containerNo='" + containerNo + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
