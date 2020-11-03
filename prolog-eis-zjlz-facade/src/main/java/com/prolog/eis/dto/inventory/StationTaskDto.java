package com.prolog.eis.dto.inventory;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/2 14:45
 * 站台任务数
 */
public class StationTaskDto {

    @ApiModelProperty("站台id")
    private int stationId;

    @ApiModelProperty("任务数")
    private int taskCount;

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
}
