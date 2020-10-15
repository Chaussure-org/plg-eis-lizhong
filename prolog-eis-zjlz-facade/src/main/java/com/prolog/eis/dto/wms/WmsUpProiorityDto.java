package com.prolog.eis.dto.wms;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 15:06
 */
public class WmsUpProiorityDto {

    @ApiModelProperty("单据号")
    private String BILLNO;

    @ApiModelProperty("状态")
    private Integer STATUS;

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public Integer getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(Integer STATUS) {
        this.STATUS = STATUS;
    }

    @Override
    public String toString() {
        return "UpProiorityDto{" +
                "BILLNO='" + BILLNO + '\'' +
                ", STATUS=" + STATUS +
                '}';
    }
}
