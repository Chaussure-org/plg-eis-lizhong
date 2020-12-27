package com.prolog.eis.configuration;

public class WCSProperties {
    private String host;
    private int port;
    private int bankId = 1;

    /**
     * 查询出库货位是否空闲,此接口同步返回
     */
    private String checkPosition = "/WcsApi/ OutboundRequest";


    /**
     * 输送线行走
     */
    private String lineMoveUrl = "/eis/lineMove";


    /**
     * 拆盘机入口托盘到位
     *
     * @return
     */
    private String trayArriveUrl = "/WcsApi/AgvArrive";


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

    public String getTrayArriveUrl() {
        return trayArriveUrl;
    }

    public void setTrayArriveUrl(String trayArriveUrl) {
        this.trayArriveUrl = trayArriveUrl;
    }

    public String getCheckPosition() {
        return checkPosition;
    }

    public void setCheckPosition(String checkPosition) {
        this.checkPosition = checkPosition;
    }
}
