package com.prolog.eis.bz.service;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/9/28 12:07
 */
public interface IStationBZService {
    /**
     * 开始拣选
     * @param stationId 站台id
     * @param containerNo 容器编号
     * @param orderBoxNo 订单框编号
     */
    void startBZPicking(int stationId,String containerNo,String orderBoxNo) throws Exception;

    /**
     * 拣选确认
     */
    void pickingConfirm(int stationId,String containerNo,String orderBoxNo);
    /**
     * 检查容器是否是当前拣选站所需
     */
    boolean checkContainerToStation(String containerNo,int stationId);
    /**
     * 校验托盘或料箱是否在拣选站
     */
    boolean checkContainerExist(String containerNo,int stationId);
}
