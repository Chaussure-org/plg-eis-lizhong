package com.prolog.eis.configuration;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:11
 */
public class RCSProperties {
    private String host;
    private int port;

    private String agvmoveUrl = "/rcms/services/rest/hikRpcService/genAgvSchedulingTask";

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

    public String getAgvmoveUrl() {
        return agvmoveUrl;
    }

    public void setAgvmoveUrl(String agvmoveUrl) {
        this.agvmoveUrl = agvmoveUrl;
    }
}
