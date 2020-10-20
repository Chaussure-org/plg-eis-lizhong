package com.prolog.eis.station.service;

import com.prolog.eis.model.station.Station;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 15:29
 */
public interface IStationService {

    /**
     * 根据id查找
     *
     * @param stationId
     * @return
     * @throws Exception
     */
    Station findById(int stationId) throws Exception;

    /**
     * 修改对象
     *
     * @param station
     * @throws Exception
     */
    void updateStation(Station station) throws Exception;

    /**
     * 根据map查询
     *
     * @param map
     * @return
     */
    List<Station> findStationByMap(Map map);

    /**  获取当前站台的订单汇总id
     * @param stationId
     * @return
     */
    List<Integer> findPickingOrderBillId(int stationId);

    /**
     * 检查站台状态
     * @return
     */
    boolean checkStationStatus() throws Exception;

    /**
     * 清空站台拣选单id
     */
    void clearStationPickingOrder(int stationId);


    /**
     * 成品库站台索取订单
     * @throws Exception
     * @param isLock
     */
    void changeFinishStationStatus(int isLock) throws Exception;
}
