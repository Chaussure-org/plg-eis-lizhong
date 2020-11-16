package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/13 10:27
 * 盘点计划展示dto
 */
@Data
public class InventoryInfoDto {
    @ApiModelProperty("盘点计划id")
    private int id;
    @ApiModelProperty("单据编号")
    private String billNo;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品图号")
    private String ownerDrawnNo;

    @ApiModelProperty("计划数量")
    private int  taskCount;

    @ApiModelProperty("出库数量")
    private int outboundCount;

    @ApiModelProperty("商品类别")
    private String goodsType;

    @ApiModelProperty("行号")
    private String seqNo;

    @ApiModelProperty("单据日期")
    private Date billDate;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
