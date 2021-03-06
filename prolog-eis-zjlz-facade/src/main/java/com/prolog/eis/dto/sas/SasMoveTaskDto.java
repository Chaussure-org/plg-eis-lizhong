package com.prolog.eis.dto.sas;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author wangkang
 * @Description 出入库任务实体
 * @CreateTime 2020-10-18 17:21
 */
public class SasMoveTaskDto implements Serializable {

    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("任务类型：1：入库:2：出库 3：同层移库")
    int type;
    @ApiModelProperty("库编号 默认为1")
    int bankId;
    @ApiModelProperty("容器号")
    String containerNo;
    @ApiModelProperty("源地址")
    String address;
    @ApiModelProperty("目标地址")
    String target;
    @ApiModelProperty("重量")
    String weight;
    @ApiModelProperty("优先级")
    String priority;
    @ApiModelProperty("任务状态 0- 正常 1-异常")
    int status;

    public SasMoveTaskDto() {
    }

    public SasMoveTaskDto(String taskId, int type, int bankId, String containerNo, String address, String target, String weight, String priority, int status) {
        this.taskId = taskId;
        this.type = type;
        this.bankId = bankId;
        this.containerNo = containerNo;
        this.address = address;
        this.target = target;
        this.weight = weight;
        this.priority = priority;
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    @Override
    public String toString() {
        return "SasMoveTaskDto{" +
                "taskId='" + taskId + '\'' +
                ", type=" + type +
                ", bankId=" + bankId +
                ", containerNo='" + containerNo + '\'' +
                ", address='" + address + '\'' +
                ", target='" + target + '\'' +
                ", weight='" + weight + '\'' +
                ", priority='" + priority + '\'' +
                ", status=" + status +
                '}';
    }
}
