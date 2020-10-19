package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author wangkang
 * @Description 输送线行走实体
 * @CreateTime 2020-10-18 19:17
 */
public class WcsLineMoveDto {

    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("源地址")
    private String address;
    @ApiModelProperty("目标地址")
    private String target;
    @ApiModelProperty("容器号")
    private String containerNo;
    @ApiModelProperty("任务类型")
    private int type;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public WcsLineMoveDto(String taskId, String address, String target, String containerNo, int type) {
        this.taskId = taskId;
        this.address = address;
        this.target = target;
        this.containerNo = containerNo;
        this.type = type;
    }

    public WcsLineMoveDto() {
    }

    @Override
    public String toString() {
        return "WmsLineMoveDto{" +
                "taskId='" + taskId + '\'' +
                ", address='" + address + '\'' +
                ", target='" + target + '\'' +
                ", containerNo='" + containerNo + '\'' +
                ", type=" + type +
                '}';
    }
}
