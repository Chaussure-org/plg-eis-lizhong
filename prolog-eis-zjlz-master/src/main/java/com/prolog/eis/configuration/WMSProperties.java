package com.prolog.eis.configuration;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:50
 */
public class WMSProperties {
    private String host;
    private int port;

    /**
     * 入库上架回告
     */
    private String wmsInboundUrl="/inTransferTask/eisTaskConfirm/v1.0";

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

    public String getWmsInboundUrl() {
        return wmsInboundUrl;
    }

    public void setWmsInboundUrl(String wmsInboundUrl) {
        this.wmsInboundUrl = wmsInboundUrl;
    }
}
