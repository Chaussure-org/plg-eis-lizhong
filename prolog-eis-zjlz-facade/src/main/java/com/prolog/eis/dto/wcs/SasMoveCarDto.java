package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

/**
 * @Author wangkang
 * @Description 小车换乘实体
 * @CreateTime 2020-10-18 20:14
 */
public class SasMoveCarDto {

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("小车id")
    private Integer rgvId;

    @ApiModelProperty("源层")
    private Integer source;

    @ApiModelProperty("目标层")
    private Integer target;

    @ApiModelProperty("库别")
    private Integer bankId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getRgvId() {
        return rgvId;
    }

    public void setRgvId(Integer rgvId) {
        this.rgvId = rgvId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public SasMoveCarDto() {
    }

    @Override
    public String toString() {
        return "SasMoveCarDto{" +
                "taskId='" + taskId + '\'' +
                ", rgvId=" + rgvId +
                ", source=" + source +
                ", target=" + target +
                ", bankId=" + bankId +
                '}';
    }
}
