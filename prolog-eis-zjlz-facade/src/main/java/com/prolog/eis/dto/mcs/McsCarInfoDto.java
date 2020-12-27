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
    private String stackerId;

    @ApiModelProperty("编码：0状态码正常")
    private int code;

    @ApiModelProperty("堆垛机状态 //0:正常 1：可用")
    private int status;

    @Override
    public String toString() {
        return "McsCarInfoDto{" +
                "stackerId='" + stackerId + '\'' +
                ", status=" + status +
                ", code=" + code +
                '}';
    }

    public McsCarInfoDto() {
    }

    public McsCarInfoDto(String stackerId, int status, int code) {
        this.stackerId = stackerId;
        this.status = status;
        this.code = code;
    }



    public String getStackerId() {
        return stackerId;
    }

    public void setStackerId(String stackerId) {
        this.stackerId = stackerId;
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
}
