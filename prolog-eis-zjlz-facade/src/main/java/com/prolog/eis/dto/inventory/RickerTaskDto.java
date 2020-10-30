package com.prolog.eis.dto.inventory;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/29 10:49
 * 巷道任务数dto
 */
public class RickerTaskDto {

    @ApiModelProperty("巷道")
    private String alleyWay;

    @ApiModelProperty("任务数")
    private int taskCount;

    public RickerTaskDto() {
    }

    public RickerTaskDto(String alleyWay, int taskCount) {
        this.alleyWay = alleyWay;
        this.taskCount = taskCount;
    }

    public String getAlleyWay() {
        return alleyWay;
    }

    public void setAlleyWay(String alleyWay) {
        this.alleyWay = alleyWay;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
    
}
