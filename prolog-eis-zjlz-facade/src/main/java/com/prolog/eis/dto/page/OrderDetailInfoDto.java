package com.prolog.eis.dto.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/12 17:03
 * 订单明细页面dto
 */
@Data
public class OrderDetailInfoDto {

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("图号")
    private String ownerDrawnNo;


    @ApiModelProperty("计划数量")
    private int planQty;

    @ApiModelProperty("拣选数量")
    private int hasPickQty;

    @ApiModelProperty("完成数量")
    private int completeQty;

    @ApiModelProperty("商品订单号")
    private String goodsOrderNo;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
}
