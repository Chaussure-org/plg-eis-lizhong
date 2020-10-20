package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TaskCallbackDTO {


    public static final int STATUS_START=1;
    public static final int STATUS_FINISH=2;

    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("状态 1-开始 2-完成")
    private short status;
    @ApiModelProperty("任务类型1：入库 2：出库 3:移库 4:小车换层 5:输送线行走")
    private short type;
    @ApiModelProperty("料箱编号")
    private String containerNo;
    @ApiModelProperty("小车编号")
    private String rgvId;
    @ApiModelProperty("当前坐标")
    private String address;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getRgvId() {
        return rgvId;
    }

    public void setRgvId(String rgvId) {
        this.rgvId = rgvId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "TaskCallbackDTO{" +
                "taskId='" + taskId + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", containerNo='" + containerNo + '\'' +
                ", rgvId='" + rgvId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
