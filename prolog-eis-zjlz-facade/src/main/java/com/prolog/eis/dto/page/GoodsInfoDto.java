package com.prolog.eis.dto.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prolog.framework.core.annotation.Column;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/11 11:31
 * 商品资料展示
 */
@Data
public class GoodsInfoDto {

    @ApiModelProperty("商品id")
    private Integer goodsId;


    @ApiModelProperty("商品编号")
    private String goodsNo;
    
    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("大类")
    private String goodsOneType;

    @ApiModelProperty("客户图号")
    private String ownerDrawnNo;
    @ApiModelProperty("表面处理")
    private String surfaceDeal;

    @ApiModelProperty("商品类别")
    private String goodsType;

    @ApiModelProperty("重量（克：g）")
    private BigDecimal weight;

    @ApiModelProperty("包装数量")
    private Integer packageNumber;

    @ApiModelProperty("贴标标识(0:未贴标，1：贴标)")
    private Integer pastLabelFlg;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private java.util.Date createTime;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private java.util.Date updateTime;


}
