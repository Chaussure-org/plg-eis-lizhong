package com.prolog.eis.station.service.impl;

import com.prolog.eis.model.Station;
import com.prolog.eis.station.dao.StationMapper;
import com.prolog.eis.station.service.IStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 15:34
 */
@Service
public class StationServiceImpl implements IStationService {

    @Autowired
    private StationMapper stationMapper;
    @Override
    public Station findById(int stationId) throws Exception {
        return stationMapper.findById(stationId,Station.class);
    }

    @Override
    public void updateStation(Station station) throws Exception {
        stationMapper.update(station);
    }

    @Override
    public List<Station> findStationByMap(Map map) {
        return stationMapper.findByMap(map,Station.class);
    }

    @Override
    public List<Integer> findPickingOrderBillId(int stationId) {
        return stationMapper.getStationOrderBillId();
    }
}
