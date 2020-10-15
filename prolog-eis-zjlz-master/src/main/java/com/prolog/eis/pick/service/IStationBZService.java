package com.prolog.eis.pick.service;

import com.prolog.eis.dto.bz.BCPPcikingDTO;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.model.station.Station;

import java.util.List;

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
    BCPPcikingDTO startBZPicking(int stationId, String containerNo, String orderBoxNo) throws Exception;

    /**
     * 拣选确认
     */
    void pickingConfirm(int stationId,String containerNo,String orderBoxNo,int completeNum) throws Exception;
    /**
     * 检查容器是否是当前拣选站所需
     */
    boolean checkContainerToStation(String containerNo,int stationId);
    /**
     * 校验托盘或料箱是否在拣选站
     */
    boolean checkContainerExist(String containerNo,int stationId);

    /**
     * 订单转历史
      * @param orderBillId   订单汇总id
     */
    void orderToHistory(int orderBillId) throws Exception;

    /**
     * 物料容器离开
     * @param containerNo
     * @throws Exception
     */
    void  containerNoLeave(String containerNo,int stationId) throws Exception;

    /**
     * 订单拖放行
     * @param orderTrayNo
     * @param stationId
     * @param orderBillId
     * @throws Exception
     */
    void orderTrayLeave(String orderTrayNo,int stationId,int orderBillId) throws Exception;

    /**
     * 计算尾拖
     * @param containerNo
     * @return
     */
    boolean computeLastTray(String containerNo) throws Exception;

    /**
     * 计算绑定料箱去最近的一个站台
     * @param stationIds
     * @param sourceStation
     * @return
     */
    int computeTargetStation(List<Integer> stationIds,int sourceStation);


    /**
     * 切换拣选单
     * @param station
     */
    void changePickingOrder(Station station) throws Exception;

    void seedToWms(ContainerBindingDetail containerBindingDetail);
}
