package com.prolog.eis.station.service.impl;

import com.prolog.eis.dto.inventory.StationTaskDto;
import com.prolog.eis.dto.station.ContainerTaskDto;
import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.station.dao.StationMapper;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.IPUtils;
import com.prolog.eis.vo.station.StationInfoVo;
import com.prolog.framework.utils.MapUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
    @Autowired
    private IContainerStoreService containerStoreService;

    @Override
    public Station findById(int stationId) throws Exception {
        return stationMapper.findById(stationId, Station.class);
    }

    @Override
    public boolean checkStationStatus() throws Exception {
        List<Station> stations =
                stationMapper.findByMap(MapUtils.put("stationType", Station.STATION_TYPE_FINISHEDPROD).getMap(),
                        Station.class);
        if (stations == null) {
            throw new Exception("没有找到成品库站台配置");
        }
        if (stations.size() > 1 || stations.size() < 1) {
            throw new Exception("成品库站台数量有问题，请检查配置");
        }
        if (stations.get(0).getIsLock() == 1) {
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
        if (stations.size() != 1) {
            throw new Exception("成品库站台配置有问题");
        }
        Station station = stations.get(0);
        station.setUpdateTime(new Date());
        station.setIsLock(isLock);
        stationMapper.update(station);

    }

    @Override
    public void changeStationIsLock(int stationId, int flag) throws Exception {
        List<Station> stations = stationMapper.findByMap(MapUtils.put("id", stationId).put("stationType", Station.STATION_TYPE_UNFINISHEDPROD).getMap(), Station.class);
        if (stations.size() == 0) {
            throw new Exception("站台【" + stationId + "】不存在");
        }
        Station station = stations.get(0);
        if (station.getStationTaskType() != Station.TASK_TYPE_SEED) {
            throw new Exception("站台【" + stationId + "】不存在");
        }
        station.setIsLock(flag);
        stationMapper.update(station);
    }

    @Override
    public Station getStationId(String stationIp) throws Exception {
        List<Station> stations = stationMapper.findByMap(MapUtils.put("stationIp", stationIp).getMap(), Station.class);
        if (stations.size() > 1 || stations.size() < 1) {
            throw new Exception("站台配置有问题请检查站台配置");
        }
        return stations.get(0);
    }

    @Override
    public List<StationTaskDto> getStationTask() {
        return stationMapper.getStationInfo();
    }

    /**
     * 切换站台1、先修改站台不索取任务
     * 2、站台不锁取任务且当前站台无作业任务则才能修改
     * <p>
     * 索取订单状态，不能修改作业类型可修改索取状态和ip
     * 不索取订单状态，当前站台无作业可修改站台类型，否则只能修改索取状态和ip
     *
     * @param stationInfoDto
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStationTaskType(StationInfoDto stationInfoDto) throws Exception {
        Station station = stationMapper.findById(stationInfoDto.getStationId(), Station.class);
        if (null == station) {
            throw new Exception("站台【" + stationInfoDto.getStationId() + "】不存在");
        }
        if (station.getIsLock() == 0) {
            if (stationInfoDto.getStationTaskType() == station.getStationTaskType()) {
                station.setIsLock(stationInfoDto.getIsLock());
                station.setStationIp(stationInfoDto.getStationIp());
                station.setStationTaskType(stationInfoDto.getStationTaskType());
                station.setUpdateTime(new Date());
                stationMapper.update(station);
            } else {
                throw new Exception("站台需要先修改为锁定状态");
            }
        } else {
            if (station.getStationTaskType() == stationInfoDto.getStationTaskType()) {
                station.setIsLock(stationInfoDto.getIsLock());
                station.setStationIp(stationInfoDto.getStationIp());
                station.setStationTaskType(stationInfoDto.getStationTaskType());
                station.setUpdateTime(new Date());
                stationMapper.update(station);
            } else {
                //可修改作业类型
                long count = 0;
                String flag = null;
                switch (station.getStationTaskType()) {
                    //盘点
                    case Station.TASK_TYPE_INVENTORY:
                        count = containerStoreService.findByMap(MapUtils.put("taskType", ContainerStore.TASK_TYPE_INVENTORY_OUTBOUND).getMap()).size();
                        flag = "盘点";
                        break;
                    //播种
                    case Station.TASK_TYPE_SEED:
                        count = containerStoreService.findByMap(MapUtils.put("taskType", ContainerStore.TASK_TYPE_OUTBOUND).getMap()).size();
                        flag = "播种";
                        break;

                    default:
                        break;
                }
                if (count > 0) {
                    throw new Exception("当前【" + flag + "】任务未完成无法修改");
                }
                station.setIsLock(stationInfoDto.getIsLock());
                station.setStationIp(stationInfoDto.getStationIp());
                station.setStationTaskType(stationInfoDto.getStationTaskType());
                station.setUpdateTime(new Date());
                stationMapper.update(station);
            }

        }
    }

    @Override
    public List<StationInfoVo> queryAll() {
        return stationMapper.queryAll();
    }

    @Override
    public StationInfoVo queryById(int stationId) {
        return stationMapper.queryById(stationId);
    }

    @Override
    public boolean checkSeedFinish(String containerNo, int stationId) {
        return false;
    }

    @Override
    public void updateStation(Station station) throws Exception {
        stationMapper.update(station);
    }

    @Override
    public List<Station> findStationByMap(Map map) {
        return stationMapper.findByMap(map, Station.class);
    }

    @Override
    public List<Integer> findPickingOrderBillId(int stationId) {
        return stationMapper.getStationOrderBillId(stationId);
    }


}
