package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/18 10:36
 */
@Data
public class OpenDiskFinishDto {

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("时间")
    private String time;

    @ApiModelProperty("拆码盘机编号")
    private String deviceId;

    @ApiModelProperty("拆盘机后托盘是否到位 0：托盘未到位 1：托盘到位")
    private String isArrive;
}
