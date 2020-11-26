package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/11 11:23
 * 商品资料查询条件dto
 */
@Data
public class GoodsQueryPageDto {
    @ApiModelProperty("商品id")
    private Integer goodsId;
    @ApiModelProperty("商品大类")
    private String goodsOneType;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("图号")
    private String ownerDrawnNo;
    @ApiModelProperty("商品类别")
    private String goodsType;
    @ApiModelProperty("贴标标识(0:未贴标，1：贴标)")
    private Integer pastLabelFlg;
    @NotNull
    @ApiModelProperty("当前页码")
    private int pageNum;
    @NotNull
    @ApiModelProperty("每页数量")
    private int pageSize;
}
