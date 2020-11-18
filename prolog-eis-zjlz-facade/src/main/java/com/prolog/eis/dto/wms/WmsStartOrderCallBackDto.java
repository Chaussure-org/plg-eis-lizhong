package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/17 15:43
 * WMS激活拣货任务dto
 */

public class WmsStartOrderCallBackDto {

    @ApiModelProperty("订单编号")
    private String BILLNO;

    @ApiModelProperty("状态")
    private String STATUS;

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    @Override
    public String toString() {
        return "WmsStartOrderCallBackDto{" +
                "BILLNO='" + BILLNO + '\'' +
                ", STATUS='" + STATUS + '\'' +
                '}';
    }
}
