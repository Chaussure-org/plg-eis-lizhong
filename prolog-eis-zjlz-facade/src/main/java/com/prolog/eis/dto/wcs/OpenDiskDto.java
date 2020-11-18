package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/18 10:30
 * 拆盘机信号空闲dto
 */
@Data
public class OpenDiskDto {

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("时间")
    private String time;

    @ApiModelProperty("拆码盘机编号")
    private String deviceId;

    @ApiModelProperty("拆码盘机状态 0：拆码盘机空闲 1：拆码盘机运作")
    private String status;

}
