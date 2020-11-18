package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class CarInfoDTO implements Serializable {

    @ApiModelProperty("小车id")
    private String rgvId;
    @ApiModelProperty("层")
    private int layer;
    //1,2 正常 3跨层 4故障
    @ApiModelProperty("状态1-工作中 2-空闲 3-跨层中 4-故障中")
    private int status;

    public String getRgvId() {
        return rgvId;
    }

    public void setRgvId(String rgvId) {
        this.rgvId = rgvId;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CarInfoDTO{" +
                "rgvId='" + rgvId + '\'' +
                ", layer=" + layer +
                ", status=" + status +
                '}';
    }
}
