package com.prolog.eis.pick.service.impl;

import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.engin.service.TrayOutEnginService;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.pick.service.IOrderTrayService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/20 19:07
 */
@Service
public class OrderTrayServiceImpl implements IOrderTrayService {
    @Autowired
    private IStationService stationService;
    @Autowired
    private ContainerPathTaskService containerPathTaskService;
    @Autowired
    private AgvLocationService agvLocationService;
    @Autowired
    private PathSchedulingService pathSchedulingService;
    @Autowired
    private TrayOutEnginService trayOutEnginService;
    @Autowired
    private IContainerStoreService containerStoreService;
    @Override
    public void requestOrderTray() throws Exception {
        //检测拆盘机出口是否有料箱
        String bkArea = "BK02";
        List<ContainerPathTask> containerPathTasks = containerPathTaskService.getContainerByPath(bkArea);
        if (containerPathTasks.size() == 0){
            return;
        }else if (containerPathTasks.size() > 1){
            throw new Exception("拆盘机口有多个订单拖");
        }

        //找寻播种站台且索取订单的个数
        List<Integer> stations = this.getStation();
        if (stations.size() == 0){
            return;
        }

        //寻找一个最合适的站台输送订单框
        String storeArea = "OD01";
        List<StationTrayDTO> stationTrayDTOS = agvLocationService.findTrayTaskStation(storeArea, stations);
        List<StationTrayDTO> collect = stationTrayDTOS.stream().sorted(Comparator.comparing(StationTrayDTO::getCount).reversed()).collect(Collectors.toList());
        if (collect.get(0).getCount() == 0){
            //未找到需要订单拖的站台
            return;
        }else {
            //去往对应站台
            int targetStationId = collect.get(0).getStationId();
            List<String> usableStore = agvLocationService.getUsableStore(storeArea,targetStationId);
            if (usableStore.size() == 0){
                throw new Exception("站台【"+targetStationId+"】没找到可用区域");
            }
            pathSchedulingService.containerMoveTask(containerPathTasks.get(0).getContainerNo(),usableStore.get(0),usableStore.get(0));
        }
    }



    @Override
    public void orderTrayOut() throws Exception {
        /**
         * 检查拆盘机入口是否有订单托盘，没有则调度一个空订单托盘出库
         */
        //拆盘机入口
        String bkArea = "BK01";
        List<ContainerPathTask> containerByPaths = containerPathTaskService.getContainerByPath(bkArea);
        if (containerByPaths.size() != 0){
            return;
        }
        //查询空订单框
//        containerStoreService.findByMap(MapUtils.put())
        //todo:调度托盘出库
        //空订单拖id
        int goodsId = 1111;
        List<OutContainerDto> outContainerDtos = trayOutEnginService.outByGoodsId(goodsId, 1);
        OutContainerDto outContainerDto = outContainerDtos.get(0);
        pathSchedulingService.containerMoveTask(outContainerDto.getContainerNo(),bkArea,null);

    }


    private List<Integer> getStation(){
        List<Integer> stations = stationService.findStationByMap(MapUtils.put("stationTaskType", Station.TASK_TYPE_SEED)
                .put("isLock", Station.UN_LOCK).put("stationType",Station.STATION_TYPE_UNFINISHEDPROD).getMap())
                .stream().map(Station::getId).collect(Collectors.toList());

        return stations;
    }





}
