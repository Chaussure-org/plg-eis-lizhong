package com.prolog.eis.station.service.impl;

import com.prolog.eis.dto.inventory.StationTaskDto;
import com.prolog.eis.dto.station.ContainerTaskDto;
import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.station.dao.StationMapper;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.util.IPUtils;
import com.prolog.framework.utils.MapUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public List<ContainerTaskDto> getTaskByContainerNo(String containerNo) {
        return stationMapper.getTaskByContainerNo(containerNo);
    }

    @Override
    public void changeFinishStationStatus(int isLock) throws Exception {
        List<Station> stations =
                stationMapper.findByMap(MapUtils.put("stationType", Station.STATION_TYPE_FINISHEDPROD).getMap(),
                        Station.class);
        if (stations.size() != 1){
            throw new Exception("成品库站台配置有问题");
        }
        stationMapper.updateStationLock(isLock,stations.get(0).getId());

    }

    @Override
    public void changeStationIsLock(int stationId, int flag) throws Exception {
        List<Station> stations = stationMapper.findByMap(MapUtils.put("id", stationId).put("stationType", Station.STATION_TYPE_UNFINISHEDPROD).getMap(), Station.class);
        if (stations.size() == 0){
            throw new Exception("站台【"+stationId+"】不存在");
        }
        Station station = stations.get(0);
        if (station.getStationTaskType() != Station.TASK_TYPE_SEED){
            throw new Exception("站台【"+stationId+"】不存在");
        }
        station.setIsLock(flag);
        stationMapper.update(station);
    }

    @Override
    public int getStationId(String stationIp) throws Exception {
        List<Station> stations = stationMapper.findByMap(MapUtils.put("stationIp", stationIp).getMap(), Station.class);
        if (stations.size() > 1 || stations.size() < 1){
            throw new Exception("站台配置有问题请检查站台配置");
        }
        return stations.get(0).getId();
    }

    @Override
    public List<StationTaskDto> getStationTask() {
        return stationMapper.getStationInfo();
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
