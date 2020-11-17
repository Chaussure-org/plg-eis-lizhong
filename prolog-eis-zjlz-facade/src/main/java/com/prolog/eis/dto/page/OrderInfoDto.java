package com.prolog.eis.dto.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/12 14:02
 * 订单详情展示dto
 */
@Data
public class OrderInfoDto {
    @ApiModelProperty("订单id")
    private int orderId;
    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("订单类型 1、生产出库2、销售出库3、库内")
    private int orderType;

    @ApiModelProperty("订单明细数")
    private int orderCount;

    @ApiModelProperty("已完成数量")
    private int finishCount;
    
    @ApiModelProperty("wms任务有限级别")
    private int wmsOrderPriority;

    @ApiModelProperty("订单任务进度（0创建 10 开始出库 2出库中 30出库完成）")
    private int orderTaskState;

    @ApiModelProperty("单据日期")
    private Date billDate;

    @ApiModelProperty("出库截止时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    @ApiModelProperty("移库出库区")
    private String branchType;


    @ApiModelProperty("拣选站台")
    private int stationId;
    @ApiModelProperty("拣选单id")
    private int pickingOrderId;
    
}
