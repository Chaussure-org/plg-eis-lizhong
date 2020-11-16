package com.prolog.eis.dto.mcs;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 9:32
 */
public class McsCarInfoDto implements Serializable {

    @ApiModelProperty("堆垛机编号")
    private String rgvId;

    @ApiModelProperty("堆垛机状态")
    private int status;//0:正常 1：可用

    @ApiModelProperty("编码：0状态码正常")
    private int code;

    public McsCarInfoDto() {
    }

    public McsCarInfoDto(String rgvId, int status, int code) {
        this.rgvId = rgvId;
        this.status = status;
        this.code = code;
    }

    public String getRgvId() {
        return rgvId;
    }

    public void setRgvId(String rgvId) {
        this.rgvId = rgvId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "McsCarInfoDto{" +
                "rgvId='" + rgvId + '\'' +
                ", status=" + status +
                ", code=" + code +
                '}';
    }
}
