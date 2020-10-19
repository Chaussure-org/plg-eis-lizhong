package com.prolog.eis.dto.rcs;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author wangkang
 * @Description rcs任务实体
 * @CreateTime 2020-10-18 20:27
 */
public class RcsTaskDto {

    @ApiModelProperty("任务编号")
    private String reqCode;

    @ApiModelProperty("容器编号")
    private String containerNo;

    @ApiModelProperty("起点位置")
    private String startPosition;

    @ApiModelProperty("终点位置")
    private String endPosition;

    @ApiModelProperty("任务模板")
    private String taskType;

    @ApiModelProperty("优先级")
    private String priority;

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public RcsTaskDto(String reqCode, String containerNo, String startPosition, String endPosition, String taskType,
                      String priority) {
        this.reqCode = reqCode;
        this.containerNo = containerNo;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.taskType = taskType;
        this.priority = priority;
    }

    public RcsTaskDto() {
    }

    @Override
    public String toString() {
        return "RcsTaskDto{" +
                "reqCode='" + reqCode + '\'' +
                ", containerNo='" + containerNo + '\'' +
                ", startPosition='" + startPosition + '\'' +
                ", endPosition='" + endPosition + '\'' +
                ", taskType='" + taskType + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
