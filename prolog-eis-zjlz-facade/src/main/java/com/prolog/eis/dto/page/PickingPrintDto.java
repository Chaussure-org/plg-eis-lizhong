package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/12 10:50
 * 派工单打印dto
 */
@Data
public class PickingPrintDto {

    @ApiModelProperty("派工单")
    private String orderNo;


    @ApiModelProperty("容器号")
    private String orderTrayNo;

    @ApiModelProperty("站台号")
    private int stationId;

    @ApiModelProperty("操作人")
    private String operator;
}
