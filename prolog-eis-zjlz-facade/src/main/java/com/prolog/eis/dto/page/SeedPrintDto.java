package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/12 12:02
 */
@Data
public class SeedPrintDto {
    @NotNull
    @ApiModelProperty("派工单")
    private String orderNo;

    @NotNull
    @ApiModelProperty("容器号")
    private String orderTrayNo;
}
