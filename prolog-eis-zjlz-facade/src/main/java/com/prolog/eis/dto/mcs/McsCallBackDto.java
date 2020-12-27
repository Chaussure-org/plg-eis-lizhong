package com.prolog.eis.dto.mcs;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author wangkang
 * @Description Mcs回告
 * @CreateTime 2020-10-22 9:05
 */
public class McsCallBackDto implements Serializable {
    public static final int STATUS_START=1;
    public static final int STATUS_FINISH=2;

    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("状态 1-开始 2-完成")
    private short status;
    @ApiModelProperty("任务类型1：入库 2：出库 3:移库 ")
    private short type;
    @ApiModelProperty("料箱编号")
    private String containerNo;

    @ApiModelProperty("小车编号")
    private String stackerId;

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



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "McsCallBackDto{" +
                "taskId='" + taskId + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", containerNo='" + containerNo + '\'' +
                ", stackerId='" + stackerId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getStackerId() {
        return stackerId;
    }

    public void setStackerId(String stackerId) {
        this.stackerId = stackerId;
    }
}
