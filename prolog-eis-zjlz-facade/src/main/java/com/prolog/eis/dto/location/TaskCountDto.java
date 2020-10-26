package com.prolog.eis.dto.location;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-26 13:30
 */
public class TaskCountDto {

    @ApiModelProperty("堆垛机区域")
    private String areaNo;

    @ApiModelProperty("任务数")
    private Integer taskCount;

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    @Override
    public String toString() {
        return "TaskCountDto{" +
                "areaNo='" + areaNo + '\'' +
                ", taskCount=" + taskCount +
                '}';
    }
}
