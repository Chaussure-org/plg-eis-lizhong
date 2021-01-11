package com.prolog.eis.configuration;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:11
 */
public class SASProperties {
    private String host;
    private int port;

    private String getCarInfoUrl = "/Interface/SearchRgv";

    private String getHoisterInfoDtoUrl = "/Interface/SearchHoist";

    private String sendContainerTaskUrl = "/Interface/Request";

    private String crossLoyer="/Interface/CrossLayer";

    public String getCrossLoyer() {
        return crossLoyer;
    }

    public void setCrossLoyer(String crossLoyer) {
        this.crossLoyer = crossLoyer;
    }

    public String getGetCarInfoUrl() {
        return getCarInfoUrl;
    }

    public void setGetCarInfoUrl(String getCarInfoUrl) {
        this.getCarInfoUrl = getCarInfoUrl;
    }

    public String getGetHoisterInfoDtoUrl() {
        return getHoisterInfoDtoUrl;
    }

    public void setGetHoisterInfoDtoUrl(String getHoisterInfoDtoUrl) {
        this.getHoisterInfoDtoUrl = getHoisterInfoDtoUrl;
    }

    public String getSendContainerTaskUrl() {
        return sendContainerTaskUrl;
    }

    public void setSendContainerTaskUrl(String sendContainerTaskUrl) {
        this.sendContainerTaskUrl = sendContainerTaskUrl;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
