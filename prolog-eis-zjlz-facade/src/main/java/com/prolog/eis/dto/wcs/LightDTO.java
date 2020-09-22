package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class LightDTO {
    @ApiModelProperty("拣选站台编号")
    private String stationNo;
    @ApiModelProperty("灯编号")
    private String lightNo;

    public LightDTO() {
    }

    public LightDTO(String stationNo, String lightNo) {
        this.stationNo = stationNo;
        this.lightNo = lightNo;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getLightNo() {
        return lightNo;
    }

    public void setLightNo(String lightNo) {
        this.lightNo = lightNo;
    }
}
