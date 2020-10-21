package com.prolog.eis.dto.bz;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/21 10:30
 * 订单拖称重检测dto
 */
public class OrderTrayWeighDTO {

    @ApiModelProperty("容器重量")
    private BigDecimal containerWeigh;

    @ApiModelProperty("称重重量")
    private BigDecimal weigh;

    @ApiModelProperty("标识符")
    private boolean flag;


    public BigDecimal getContainerWeigh() {
        return containerWeigh;
    }

    public void setContainerWeigh(BigDecimal containerWeigh) {
        this.containerWeigh = containerWeigh;
    }

    public BigDecimal getWeigh() {
        return weigh;
    }

    public void setWeigh(BigDecimal weigh) {
        this.weigh = weigh;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
