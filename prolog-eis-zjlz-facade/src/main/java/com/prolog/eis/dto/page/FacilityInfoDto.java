package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/11 14:47
 * 设备状态查询dto
 */
@Data
public class FacilityInfoDto {

    @ApiModelProperty("设备名称")
    private String facilityName;

    @ApiModelProperty("设备编号")
    private String facilityNo;

    @ApiModelProperty("设备状态")
    private String facilityStatus;

    @ApiModelProperty("设备所在层")
    private int facilityLayer;

}
