package com.prolog.eis.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = com.prolog.eis.configuration.EisProperties.Prefix)
public class EisProperties {
    public static final String Prefix = "prolog.eis";
    private int maxJxdOrderCount = 5; //拣选单最大订单数量
    private int maxSPCount=20; //拣选单最大商品数量，超过就开始进行收敛
    private int maxTaskCountPerCar = 5;//每个小车最大任务数量
    private int maxLxCountPerStation = 3;//每个站台到料箱bcr线体料箱容量
    private int maxInLxCountPerStation = 5;//每个站台，料箱入库线体容量

    private int commandSheduleThreadCount = 3;//wcs异步命令执行线程数
    private long commandExucteRate=800;//wcs异步命令调度执行频率ms

    private int basePointX = 11;
    private int basePointY = 64;

    private double limitWeight = 30000;

    private int maxOutTaskCountPerStation=6;//每个站台的最大出库数量

    private int clientPort = 9100;//eis客戶端端口

    private int bzInterval = 1500;//拍灯间隔
    private WCSProperties wcs = new WCSProperties();

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

    public int getMaxSPCount() {
        return maxSPCount;
    }

    public void setMaxSPCount(int maxSPCount) {
        this.maxSPCount = maxSPCount;
    }

    public WCSProperties getWcs() {
        return wcs;
    }

    public void setWcs(WCSProperties wcs) {
        this.wcs = wcs;
    }
}
