package com.prolog.eis.station.service.impl;

import com.prolog.eis.model.station.Station;
import com.prolog.eis.station.dao.StationMapper;
import com.prolog.eis.station.service.IStationService;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import java.util.List;

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
    public boolean checkStationStatus() throws Exception {
        List<Station> stations =
                stationMapper.findByMap(MapUtils.put("stationType", Station.STATION_TYPE_FINISHEDPROD).getMap(),
                        Station.class);
        if (stations == null){
            throw new Exception("没有找到成品库站台配置");
        }
        if (stations.size()>1 || stations.size()<1){
            throw new Exception("成品库站台数量有问题，请检查配置");
        }
        if (stations.get(0).getIsLock()==1) {
            return false;
        }
        return true;
    }

    @Override
    public void clearStationPickingOrder(int stationId) {
        stationMapper.updateStationPickingOrderId(stationId);
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
        return stationMapper.getStationOrderBillId(stationId);
    }
}
