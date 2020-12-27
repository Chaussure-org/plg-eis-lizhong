package com.prolog.eis.configuration;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:11
 */
public class MCSProperties {
    private String host;
    private int port;

    ////出库（入库/同层移库）任务接口
    private String mcsContainerMoveUrl="/eis/mcsContainerMove";

    private String getMcsCarInfoUrl="/eis/getMcsCarInfo";

    public String getGetMcsCarInfoUrl() {
        return getMcsCarInfoUrl;
    }

    public void setGetMcsCarInfoUrl(String getMcsCarInfoUrl) {
        this.getMcsCarInfoUrl = getMcsCarInfoUrl;
    }

    public String getMcsContainerMoveUrl() {
        return mcsContainerMoveUrl;
    }

    public void setMcsContainerMoveUrl(String mcsContainerMoveUrl) {
        this.mcsContainerMoveUrl = mcsContainerMoveUrl;
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
