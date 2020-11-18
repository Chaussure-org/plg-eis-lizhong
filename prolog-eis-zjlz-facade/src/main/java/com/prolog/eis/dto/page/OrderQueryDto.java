package com.prolog.eis.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/12 14:50
 */
@Data
public class OrderQueryDto {
    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("订单类型 1、生产出库2、销售出库3、移库")
    private Integer orderType;

    @ApiModelProperty("订单任务进度（0创建 10 开始出库 2出库中 30出库完成）")
    private Integer orderTaskState;

    @ApiModelProperty("出库时间   结束时间")
    private Date endTime;

    @ApiModelProperty("出库时间    开始时间")
    private Date startTime;

    @NotNull
    @ApiModelProperty("当前页码")
    private int pageNum;
    @NotNull
    @ApiModelProperty("每页数量")
    private int pageSize;
}
