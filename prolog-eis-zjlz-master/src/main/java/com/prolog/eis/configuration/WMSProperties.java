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
     * 入库任务回告wms服务名
     */
    private String inboundServiceName = "lizhong-wms-bc-entry";

    /**
     * 入库任务回告wms服务名
     */
    private String inboundServiceIp = "10.0.2.135";

    /**
     * 入库上架回告WMSIp
     */
    private String wmsInboundUrl="/inTransferTask/eisTaskConfirm/v1.0";

    private int wmsInboundPort = 20631;


    /**
     * 拣选回告wms服务名(开始拣选任务、拣选完成回告)
     */
    private String seedServiceName = "lizhong-wms-bc-taskcenter";
    /**
     * 入库上架回告WMSIp
     */
    private String seedServiceIp="10.0.2.135";

    private int seedServicePort = 20631;
    /**
     * 开始拣选回告
     */
    private String wmsStartSeedUrl = "/TaskDispatch/eisUpPrioritylevel/v1.0";

    /**
     * 播种完成回告
     */
    private String wmsSeedEndUrl = "/TaskDispatch/returnDo/v1.0";

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

    public String getWmsStartSeedUrl() {
        return wmsStartSeedUrl;
    }

    public void setWmsStartSeedUrl(String wmsStartSeedUrl) {
        this.wmsStartSeedUrl = wmsStartSeedUrl;
    }

    public String getWmsSeedEndUrl() {
        return wmsSeedEndUrl;
    }

    public void setWmsSeedEndUrl(String wmsSeedEndUrl) {
        this.wmsSeedEndUrl = wmsSeedEndUrl;
    }

    public String getSeedServiceName() {
        return seedServiceName;
    }

    public void setSeedServiceName(String seedServiceName) {
        this.seedServiceName = seedServiceName;
    }

    public String getInboundServiceName() {
        return inboundServiceName;
    }

    public void setInboundServiceName(String inboundServiceName) {
        this.inboundServiceName = inboundServiceName;
    }

    public String getInboundServiceIp() {
        return inboundServiceIp;
    }

    public void setInboundServiceIp(String inboundServiceIp) {
        this.inboundServiceIp = inboundServiceIp;
    }

    public int getWmsInboundPort() {
        return wmsInboundPort;
    }

    public void setWmsInboundPort(int wmsInboundPort) {
        this.wmsInboundPort = wmsInboundPort;
    }

    public String getSeedServiceIp() {
        return seedServiceIp;
    }

    public void setSeedServiceIp(String seedServiceIp) {
        this.seedServiceIp = seedServiceIp;
    }

    public int getSeedServicePort() {
        return seedServicePort;
    }

    public void setSeedServicePort(int seedServicePort) {
        this.seedServicePort = seedServicePort;
    }
}
