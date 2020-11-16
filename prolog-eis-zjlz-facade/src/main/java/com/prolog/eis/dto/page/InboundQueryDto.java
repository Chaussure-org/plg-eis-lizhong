package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/13 9:05
 */
@Data
public class InboundQueryDto {

    @ApiModelProperty("仓库类型")
    private String branchType;

    @ApiModelProperty("单据编号")
    private String billNo;

    @ApiModelProperty("容器号")
    private String containerNo;

    @ApiModelProperty("商品中文名称")
    private String goodsName;

    @NotNull
    @ApiModelProperty("当前页码")
    private int pageNum;
    @NotNull
    @ApiModelProperty("每页数量")
    private int pageSize;

}
