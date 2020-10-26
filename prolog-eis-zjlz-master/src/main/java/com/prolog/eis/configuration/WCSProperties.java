package com.prolog.eis.configuration;

public class WCSProperties {
    private String host;
    private int port;
    private int bankId=1;
    private String lineMoveUrl="/eis/lineMove";//输送线行走

    public String getLineMoveUrl() {
        return lineMoveUrl;
    }

    public void setLineMoveUrl(String lineMoveUrl) {
        this.lineMoveUrl = lineMoveUrl;
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

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }
}
