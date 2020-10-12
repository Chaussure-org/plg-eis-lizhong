package com.prolog.eis.station.service;

import com.prolog.eis.model.Station;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 15:29
 */
public interface IStationService {

    /**
     * 根据id查询
     */
    Station findById(int stationId) throws Exception;

    /**
     * 根据id修改
     */
    void updateStation(Station station) throws Exception;
}
