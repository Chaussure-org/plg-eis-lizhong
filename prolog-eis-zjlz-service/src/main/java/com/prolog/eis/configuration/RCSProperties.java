package com.prolog.eis.configuration;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:11
 */
public class RCSProperties {
    private String host;
    private int port;

    private String sendTaskUrl="/cms/services/rest/hikRpcService/genAgvSchedulingTask";

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

    public String getSendTaskUrl() {
        return sendTaskUrl;
    }

    public void setSendTaskUrl(String sendTaskUrl) {
        this.sendTaskUrl = sendTaskUrl;
    }
}
