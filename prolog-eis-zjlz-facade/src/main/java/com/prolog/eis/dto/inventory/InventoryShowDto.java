package com.prolog.eis.dto.inventory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/3 9:23
 * 盘点商品展示dto
 */
@Data
public class InventoryShowDto {

    @ApiModelProperty("商品条码")
    private String goodsNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品数量")
    private Integer goodsNum;

    @ApiModelProperty("单据编号")
    private String billNo;

    @ApiModelProperty("批号")
    private String lotId;
}
