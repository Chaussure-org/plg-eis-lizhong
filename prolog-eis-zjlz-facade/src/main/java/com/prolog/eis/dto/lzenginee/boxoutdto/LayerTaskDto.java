package com.prolog.eis.dto.lzenginee.boxoutdto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:LayerTaskDto
 * Package:com.prolog.eis.dto.lzenginee.boxoutdto
 * Description:
 *
 * @date:2020/10/10 10:07
 * @author:SunPP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LayerTaskDto {
    @ApiModelProperty("层")
    private int layer;
    @ApiModelProperty("出库任务数")
    private int outCount;
    @ApiModelProperty("入库任务数")
    private int inCount;
}
