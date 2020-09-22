package com.prolog.eis.dto.wcs;

/**
 * @author LuoLi
 * @version 1.0
 * @date 2020/7/13 20:06
 */
public class HoisterInfoDto {

    private String hoist;

    private int status;//0:正常 1：可用

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
