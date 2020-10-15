package com.prolog.eis.station.service;

import com.prolog.eis.model.Station;

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
}
