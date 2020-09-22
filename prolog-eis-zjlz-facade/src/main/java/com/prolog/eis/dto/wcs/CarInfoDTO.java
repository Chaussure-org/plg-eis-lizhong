package com.prolog.eis.dto.wcs;

public class CarInfoDTO {

    private String rgvId;
    private int layer;
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
}
