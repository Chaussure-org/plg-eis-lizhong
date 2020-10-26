package com.prolog.eis.dto.wcs;

import java.io.Serializable;

public class CarInfoDTO implements Serializable {


    private String rgvId;
    private int layer;
    //1,2 正常 3跨层 4故障
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
