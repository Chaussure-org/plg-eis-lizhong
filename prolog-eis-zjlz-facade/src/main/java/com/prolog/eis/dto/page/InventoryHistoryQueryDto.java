package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/13 12:26
 * 盘点历史查询条件dto
 */
@Data
public class InventoryHistoryQueryDto {
    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("商品图号")
    private String goodsNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("站台id")
    private Integer stationId;

    @ApiModelProperty("盘点完成时间---》结束时间")
    private Date endTime;

    @ApiModelProperty("盘点完成时间----》开始时间")
    private Date startTime;

    @ApiModelProperty("盘点数量与库存差异  1---》相同，2----》不相同")
    private int  different;

    @NotNull
    @ApiModelProperty("当前页码")
    private int pageNum;
    @NotNull
    @ApiModelProperty("每页数量")
    private int pageSize;
}
