package com.prolog.eis.dto.bz;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/18 20:38
 */
public class FinishNotSeedDTO {

    @ApiModelProperty("未出库订单总数")
    private int notOutOrderCount;

    @ApiModelProperty("未出库数量")
    private int notOutOrderDetailCount;


    public int getNotOutOrderCount() {
        return notOutOrderCount;
    }

    public void setNotOutOrderCount(int notOutOrderCount) {
        this.notOutOrderCount = notOutOrderCount;
    }

    public int getNotOutOrderDetailCount() {
        return notOutOrderDetailCount;
    }

    public void setNotOutOrderDetailCount(int notOutOrderDetailCount) {
        this.notOutOrderDetailCount = notOutOrderDetailCount;
    }
}
