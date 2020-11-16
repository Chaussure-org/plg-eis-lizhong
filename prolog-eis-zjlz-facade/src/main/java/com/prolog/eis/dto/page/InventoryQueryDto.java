package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/13 10:42
 * 盘点查询条件
 */
@Data
public class InventoryQueryDto {

    @NotNull
    @ApiModelProperty("当前页码")
    private int pageNum;
    @NotNull
    @ApiModelProperty("每页数量")
    private int pageSize;

    @ApiModelProperty("单据编号")
    private String billNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品图号")
    private String ownerDrawnNo;

    @ApiModelProperty("商品类别")
    private String goodsType;
}
