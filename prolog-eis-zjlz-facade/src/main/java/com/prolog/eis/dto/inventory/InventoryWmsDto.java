package com.prolog.eis.dto.inventory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/23 11:28
 */
@Data
public class InventoryWmsDto {
    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("单据编号")
    private String billNo;

    @ApiModelProperty("单据日期")
    private Date billDate;
    

    @ApiModelProperty("库区")
    private String branchType;

    @ApiModelProperty("行号")
    private String seqNo;

    @ApiModelProperty("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品类别")
    private String goodsType;

    @ApiModelProperty("差异数")
    private Double affQty;
}
