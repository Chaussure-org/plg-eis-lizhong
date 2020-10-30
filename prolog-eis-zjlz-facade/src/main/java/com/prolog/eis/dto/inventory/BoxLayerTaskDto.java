package com.prolog.eis.dto.inventory;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/30 11:15
 * 箱库层任务数dto
 */
public class BoxLayerTaskDto {

    @ApiModelProperty("层")
    private int layer;

    @ApiModelProperty("任务数")
    private int taskCount;


    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
}
