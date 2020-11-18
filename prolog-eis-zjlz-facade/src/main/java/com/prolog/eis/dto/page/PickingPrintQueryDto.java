package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/12 11:09
 */
@Data
public class PickingPrintQueryDto {

    @ApiModelProperty("派工单")
    private String orderNo;


    @ApiModelProperty("容器号")
    private String orderTrayNo;

    @NotNull
    @ApiModelProperty("当前页码")
    private int pageNum;
    @NotNull
    @ApiModelProperty("每页数量")
    private int pageSize;
}
