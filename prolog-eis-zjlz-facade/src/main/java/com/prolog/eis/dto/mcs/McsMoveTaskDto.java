package com.prolog.eis.dto.mcs;

import com.prolog.eis.dto.sas.SasMoveTaskDto;
import com.prolog.eis.dto.wcs.SasMoveCarDto;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author wangkang
 * @Description mcs出入库实体
 * @CreateTime 2020-10-18 20:56
 */
public class McsMoveTaskDto implements Serializable {

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("任务类型")
    private int type;

    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("源地址")
    private String address;

    @ApiModelProperty("目标地址")
    private String target;

    @ApiModelProperty("重量")
    private String weight;

    @ApiModelProperty("优先级")
    private String priority;

    @ApiModelProperty("状态")
    private int status;

    @ApiModelProperty("任务类型")
    private int bankId;

    public McsMoveTaskDto(){};

    public McsMoveTaskDto(String taskId, int type, String containerNo, String address, String target, String weight, String priority, int status, int bankId) {
        this.taskId = taskId;
        this.type = type;
        this.containerNo = containerNo;
        this.address = address;
        this.target = target;
        this.weight = weight;
        this.priority = priority;
        this.status = status;
        this.bankId = bankId;
    }

    @Override
    public String toString() {
        return "McsMoveTaskDto{" +
                "taskId='" + taskId + '\'' +
                ", type=" + type +
                ", containerNo='" + containerNo + '\'' +
                ", address='" + address + '\'' +
                ", target='" + target + '\'' +
                ", weight='" + weight + '\'' +
                ", priority='" + priority + '\'' +
                ", status=" + status +
                ", bankId=" + bankId +
                '}';
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





}
