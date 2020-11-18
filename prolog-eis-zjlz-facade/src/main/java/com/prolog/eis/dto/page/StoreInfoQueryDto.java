package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/16 12:10
 * 库存查询条件dto
 */
@Data
public class StoreInfoQueryDto {

    @NotNull
    @ApiModelProperty("当前页码")
    private int pageNum;
    @NotNull
    @ApiModelProperty("每页数量")
    private int pageSize;

    @ApiModelProperty("货位编号")
    private String storeNo;

    @ApiModelProperty("层")
    private int layer;

    @ApiModelProperty("巷道(x)")
    private int x;

    @ApiModelProperty("排（y）")
    private int y;

    @ApiModelProperty("区域")
    private String areaNo;

    @ApiModelProperty("是否锁定 0 未锁定1锁定")
    private int isLock;

    @ApiModelProperty("容器编号")
    private String containerNo;
}
