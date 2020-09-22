package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel()
public class BoxCompletedDTO {
    @ApiModelProperty("料箱箱号")
    private String containerNo;
    @ApiModelProperty("当前位置坐标")
    private String address;

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
