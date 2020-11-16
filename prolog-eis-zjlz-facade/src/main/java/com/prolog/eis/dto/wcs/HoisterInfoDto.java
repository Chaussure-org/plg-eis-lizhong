package com.prolog.eis.dto.wcs;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author LuoLi
 * @version 1.0
 * @date 2020/7/13 20:06
 */
public class HoisterInfoDto implements Serializable {
    @ApiModelProperty("提升机编号")
    private String hoist;

    @ApiModelProperty("提升机状态")
    private int status;//0:正常 1：可用
    @ApiModelProperty("编码：0状态码正常")
    private int code;

    public String getHoist() {
        return hoist;
    }

    public void setHoist(String hoist) {
        this.hoist = hoist;
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
