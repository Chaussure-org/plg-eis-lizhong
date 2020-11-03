package com.prolog.eis.dto.inventory;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/3 17:15
 * 半成品箱库容器任务dto
 */
public class RickerInfoDto {

    @ApiModelProperty("巷道")
    private String areaNo;

    @ApiModelProperty("任务数")
    private int taskCount;

    @ApiModelProperty("空闲货位")
    private int storeCount;

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public int getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(int storeCount) {
        this.storeCount = storeCount;
    }
}
