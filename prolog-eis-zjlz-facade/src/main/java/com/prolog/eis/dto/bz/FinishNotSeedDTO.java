package com.prolog.eis.dto.bz;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/18 20:38
 */
public class FinishNotSeedDTO {

    @ApiModelProperty("未出库订单总数")
    private Integer notOutOrderCount;

    @ApiModelProperty("未出库数量")
    private Integer notOutOrderDetailCount;

    @ApiModelProperty("索取订单 0开始索取，1结束索取")
    private Integer isLock;


    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getNotOutOrderCount() {
        return notOutOrderCount;
    }

    public void setNotOutOrderCount(Integer notOutOrderCount) {
        this.notOutOrderCount = notOutOrderCount;
    }

    public Integer getNotOutOrderDetailCount() {
        return notOutOrderDetailCount;
    }

    public void setNotOutOrderDetailCount(Integer notOutOrderDetailCount) {
        this.notOutOrderDetailCount = notOutOrderDetailCount;
    }
}
