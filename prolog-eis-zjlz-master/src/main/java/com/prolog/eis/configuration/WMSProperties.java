package com.prolog.eis.configuration;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:50
 */
public class WMSProperties {
    private String host;

    private String inPort;

    private String outPort;

    private String pdPort;

    /**
     * 入库上架回告 url 地址
     */
    private String wmsInboundUrl = "/inTransferTask/eisTaskConfirm/v1.0";

    /**
     * 开始拣选回告
     */
    private String wmsStartSeedUrl = "/TaskDispatch/eisUpPrioritylevel/v1.0";

    /**
     * 播种完成回告
     */
    private String wmsSeedEndUrl = "/TaskDispatch/returnDo/v1.0";

    /**
     * 盘点完成回告
     */
    private String wmsInventoryEndUrl = "/stockcheck/returnPD/v1.0";


//======================================== 目前 eis--> wms 使用 IP 回告方式 add sunpp
    /**
     * 入库任务回告wms服务名
     */
    private String inboundServiceName = "lizhong-wms-bc-entry";

    /**
     * 拣选回告wms服务名(开始拣选任务、拣选完成回告)
     */
    private String seedServiceName = "lizhong-wms-bc-taskcenter";


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getInPort() {
        return inPort;
    }

    public void setInPort(String inPort) {
        this.inPort = inPort;
    }

    public String getOutPort() {
        return outPort;
    }

    public void setOutPort(String outPort) {
        this.outPort = outPort;
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

    public String getInboundServiceName() {
        return inboundServiceName;
    }

    public void setInboundServiceName(String inboundServiceName) {
        this.inboundServiceName = inboundServiceName;
    }

    public String getSeedServiceName() {
        return seedServiceName;
    }

    public void setSeedServiceName(String seedServiceName) {
        this.seedServiceName = seedServiceName;
    }

    public String getWmsInventoryEndUrl() {
        return wmsInventoryEndUrl;
    }

    public void setWmsInventoryEndUrl(String wmsInventoryEndUrl) {
        this.wmsInventoryEndUrl = wmsInventoryEndUrl;
    }

    public String getPdPort() {
        return pdPort;
    }

    public void setPdPort(String pdPort) {
        this.pdPort = pdPort;
    }
}
