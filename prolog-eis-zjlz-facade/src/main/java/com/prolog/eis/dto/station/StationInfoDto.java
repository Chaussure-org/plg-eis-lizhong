package com.prolog.eis.dto.station;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/9 14:20
 * 站台修改dto
 */
@Data
public class StationInfoDto {

    @NotNull
    @ApiModelProperty("站台id")
    private int stationId;

    @ApiModelProperty("锁定状态")
    private int isLock;

    @ApiModelProperty("站台ip")
    private String stationIp;

    @ApiModelProperty("站台作业类型")
    private int stationTaskType;
}
