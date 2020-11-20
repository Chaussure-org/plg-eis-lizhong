package com.prolog.eis.dto.bz;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/20 11:56
 * 回告wms数据处理
 */
public class PickWmsDto {

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("单据日期")
    private Date billDate;

    @ApiModelProperty("订单类型")
    private Integer orderType;

    @ApiModelProperty("商品id")
    private Integer goodsId;

}
