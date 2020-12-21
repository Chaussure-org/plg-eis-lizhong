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
    int type;

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
    @ApiModelProperty("状态")
    int status;

    public McsMoveTaskDto(String taskId, int type, String containerNo, String address, String target, String weight,
                          String priority, int status) {
        this.taskId = taskId;
        this.type = type;
        this.containerNo = containerNo;
        this.address = address;
        this.target = target;
        this.weight = weight;
        this.priority = priority;
        this.status = status;
    }

    public McsMoveTaskDto() {
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

    @Override
    public String toString() {
        return "MoveTaskDto{" +
                "taskId='" + taskId + '\'' +
                ", type=" + type +
                ", containerNo='" + containerNo + '\'' +
                ", address='" + address + '\'' +
                ", target='" + target + '\'' +
                ", weight='" + weight + '\'' +
                ", priority='" + priority + '\'' +
                ", status=" + status +
                '}';
    }

}
