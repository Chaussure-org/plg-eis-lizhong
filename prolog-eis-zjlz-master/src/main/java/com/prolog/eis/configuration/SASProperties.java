package com.prolog.eis.configuration;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:11
 */
public class SASProperties {
    private String host;
    private int port;

    private String getCarInfoUrl = "/eis/getCarInfo";

    private String getHoisterInfoDtoUrl = "/eis/getHoisterInfoDto";

    private String sendContainerTaskUrl = "/eis/sendContainerTask";

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
