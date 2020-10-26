package com.prolog.eis.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = com.prolog.eis.configuration.EisProperties.PREFIX)
public class EisProperties {
    public static final String PREFIX = "prolog.eis";
    /**
     *   拣选单最大订单数量
     */
    private int maxJxdOrderCount = 5;
    /**
     *  拣选单最大商品数量，超过就开始进行收敛
     */
    private int maxSpCount=20;
    /**
     * 每个小车最大任务数量
     */
    private int maxTaskCountPerCar = 5;
    /**
     * 每个站台到料箱bcr线体料箱容量
     */
    private int maxLxCountPerStation = 3;
    /**
     * 每个站台，料箱入库线体容量
     */
    private int maxInLxCountPerStation = 5;
    /**
     * wcs异步命令执行线程数
     */
    private int commandSheduleThreadCount = 3;
    /**
     * wcs异步命令调度执行频率ms
     */
    private long commandExucteRate=800;

    private int basePointX = 11;
    private int basePointY = 64;

    private double limitWeight = 30000;

    /**
     * 每个站台的最大出库数量
     */
    private int maxOutTaskCountPerStation=6;
    /**
     * eis客戶端端口
     */
    private int clientPort = 9100;

    /**
     * 拍灯间隔
     */
    private int bzInterval = 1500;

    /**
     * 称重误差率
     */
    private BigDecimal errorRate = new BigDecimal("0.05");
    private WCSProperties wcs = new WCSProperties();

    private RCSProperties rcs = new RCSProperties();

    private MCSProperties mcs = new MCSProperties();

    private SASProperties sas = new SASProperties();

    private WMSProperties wms = new WMSProperties();

    public int getBzInterval() {
        return bzInterval;
    }

    public void setBzInterval(int bzInterval) {
        this.bzInterval = bzInterval;
    }

    public int getMaxInLxCountPerStation() {
        return maxInLxCountPerStation;
    }

    public void setMaxInLxCountPerStation(int maxInLxCountPerStation) {
        this.maxInLxCountPerStation = maxInLxCountPerStation;
    }

    public int getMaxOutTaskCountPerStation() {
        return maxOutTaskCountPerStation;
    }

    public void setMaxOutTaskCountPerStation(int maxOutTaskCountPerStation) {
        this.maxOutTaskCountPerStation = maxOutTaskCountPerStation;
    }

    public double getLimitWeight() {
        return limitWeight;
    }

    public void setLimitWeight(double limitWeight) {
        this.limitWeight = limitWeight;
    }

    public int getBasePointX() {
        return basePointX;
    }

    public void setBasePointX(int basePointX) {
        this.basePointX = basePointX;
    }

    public int getBasePointY() {
        return basePointY;
    }

    public void setBasePointY(int basePointY) {
        this.basePointY = basePointY;
    }



    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }



    public long getCommandExucteRate() {
        return commandExucteRate;
    }

    public void setCommandExucteRate(long commandExucteRate) {
        this.commandExucteRate = commandExucteRate;
    }

    public int getCommandSheduleThreadCount() {
        return commandSheduleThreadCount;
    }

    public void setCommandSheduleThreadCount(int commandSheduleThreadCount) {
        this.commandSheduleThreadCount = commandSheduleThreadCount;
    }

    public int getMaxTaskCountPerCar() {
        return maxTaskCountPerCar;
    }

    public void setMaxTaskCountPerCar(int maxTaskCountPerCar) {
        this.maxTaskCountPerCar = maxTaskCountPerCar;
    }

    public int getMaxLxCountPerStation() {
        return maxLxCountPerStation;
    }

    public void setMaxLxCountPerStation(int maxLxCountPerStation) {
        this.maxLxCountPerStation = maxLxCountPerStation;
    }

    public int getMaxJxdOrderCount() {
        return maxJxdOrderCount;
    }

    public void setMaxJxdOrderCount(int maxJxdOrderCount) {
        this.maxJxdOrderCount = maxJxdOrderCount;
    }

    public int getMaxSpCount() {
        return maxSpCount;
    }

    public void setMaxSpCount(int maxSpCount) {
        this.maxSpCount = maxSpCount;
    }


    public WCSProperties getWcs() {
        return wcs;
    }

    public void setWcs(WCSProperties wcs) {
        this.wcs = wcs;
    }

    public RCSProperties getRcs() {
        return rcs;
    }

    public void setRcs(RCSProperties rcs) {
        this.rcs = rcs;
    }

    public MCSProperties getMcs() {
        return mcs;
    }

    public void setMcs(MCSProperties mcs) {
        this.mcs = mcs;
    }

    public SASProperties getSas() {
        return sas;
    }

    public void setSas(SASProperties sas) {
        this.sas = sas;
    }

    public WMSProperties getWms() {
        return wms;
    }

    public void setWms(WMSProperties wms) {
        this.wms = wms;
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public BigDecimal getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(BigDecimal errorRate) {
        this.errorRate = errorRate;
    }
}
