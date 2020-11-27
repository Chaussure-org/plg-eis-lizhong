package com.prolog.eis.pick.service.impl;

import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.engin.service.TrayOutEnginService;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.model.wcs.OpenDisk;
import com.prolog.eis.pick.service.IOrderTrayService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.wcs.service.IOpenDiskService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.ldap.PagedResultsControl;
import java.util.Comparator;
import java.util.Date;
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
    @Autowired
    private IOpenDiskService openDiskService;

    private static final Logger logger = LoggerFactory.getLogger(OrderTrayServiceImpl.class);
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void  requestOrderTray() throws Exception {

        //检测拆盘机出口是否有料箱
        List<OpenDisk> openDisks = openDiskService.findOpenDiskByMap(MapUtils.put("openDiskId", OpenDisk.OPEN_DISK_OUT).put("taskStatus", OpenDisk.TASK_STATUS_ARRIVE).getMap());
        if (openDisks.size() == 0){
            return;
        }

        //找寻播种站台且索取订单的 个数
        List<Integer> stations = this.getStation();
        if (stations.size() == 0){
            return;
        }

        //寻找一个最合适的站台输送订单框
        String storeArea = "OD01";
        List<StationTrayDTO> stationTrayDTOS = agvLocationService.findTrayTaskStation(storeArea, stations);
        //找订单托最少，有空闲区域的站台
        List<StationTrayDTO> collect = stationTrayDTOS.stream().sorted(Comparator.comparing(StationTrayDTO::getUseCount)).filter(x ->x.getEmptyCount() != 0).collect(Collectors.toList());
        if (collect.size() == 0){
            //未找到需要订单拖的站台
            return;
        }else {
            //去往对应站台
            int targetStationId = collect.get(0).getStationId();
            List<String> usableStore = agvLocationService.getUsableStore(storeArea,targetStationId);
            if (usableStore.size() == 0){
                throw new Exception("站台【"+targetStationId+"】没找到可用区域");
            }
            String containerNo = "TD"+System.currentTimeMillis();
            AgvStoragelocation agvStore = agvLocationService.findByMap(MapUtils.put("areaNo", OpenDisk.OPEN_DISK_OUT).getMap()).get(0);
            if (agvStore == null){
                throw new Exception("接驳口【"+OpenDisk.OPEN_DISK_OUT+"】不存在");
            }
            pathSchedulingService.inboundTask(containerNo,containerNo,agvStore.getAreaNo(),agvStore.getLocationNo(),OpenDisk.OPEN_DISK_OUT);
            pathSchedulingService.containerMoveTask(containerNo,storeArea,usableStore.get(0));
            OpenDisk openDisk = openDisks.get(0);
            openDisk.setTaskStatus(OpenDisk.TASK_STATUS_NOT);
            openDiskService.updateOpenDisk(openDisk);
        }
    }



    @Override
    public void orderTrayOut() throws Exception {
        /**
         * 检查拆盘机入口是否有订单托盘，没有则调度一个空订单托盘出库
         */
        //拆盘机入口
        List<OpenDisk> openDisks = openDiskService.findOpenDiskByMap(MapUtils.put("openDiskId", OpenDisk.OPEN_DISK_IN).put("taskStatus", 0).getMap());
        if (openDisks.size() == 0){
            return;
        }else {
            List<ContainerPathTask> containerPathTasks = containerPathTaskService.findByMap(MapUtils.put("targetArea", OpenDisk.OPEN_DISK_IN).getMap());
            if (containerPathTasks.size() > 0){
                return;
            }
        }
        String bkArea = "BK01";
        List<ContainerPathTask> containerByPaths = containerPathTaskService.getContainerByPath(bkArea);
        if (containerByPaths.size() != 0){
            return;
        }

        //todo:调度托盘出库
        //空订单拖id
        int goodsId = -2;
        List<OutContainerDto> outContainerDtos = trayOutEnginService.outByGoodsId(goodsId, 1);
        OutContainerDto outContainerDto = outContainerDtos.get(0);
        pathSchedulingService.containerMoveTask(outContainerDto.getContainerNo(),bkArea,null);
        //修改拆盘机入口状态
        OpenDisk openDisk = openDisks.get(0);
        openDisk.setTaskStatus(1);
        openDiskService.updateOpenDisk(openDisk);

    }

    /**
     * 铁笼站台请求订单拖，
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void requestIronTray() throws Exception {
        //找站台
        List<Station> stations = stationService.findStationByMap(MapUtils.put("stationTaskType", Station.TASK_TYPE_SEED)
                .put("isLock", Station.UN_LOCK).put("stationType", Station.STATION_TYPE_IRON).getMap());
        if (stations.size() == 0){
            return;
        }
        List<Integer> ids = stations.stream().map(Station::getId).collect(Collectors.toList());
        //寻找一个最合适的站台输送订单框
        String storeArea = "OD01";
        List<StationTrayDTO> stationTrayDTOS = agvLocationService.findTrayTaskStation(storeArea, ids);
        //找订单托最少，有空闲区域的站台
        List<StationTrayDTO> collect = stationTrayDTOS.stream().sorted(Comparator.comparing(StationTrayDTO::getUseCount)).filter(x ->x.getEmptyCount() != 0).collect(Collectors.toList());
        if (collect.size() == 0) {
            //未找到需要订单拖的站台
            return;
        }else {
            //找铁笼
            List<String> ironTray = agvLocationService.getIronTray(StoreArea.IT01);
            if (ironTray.size() == 0){
                logger.info("铁笼区无可用铁笼调度去拣选站");
                return;
            }
            //去往对应站台
            int targetStationId = collect.get(0).getStationId();
            List<String> usableStore = agvLocationService.getUsableStore(storeArea,targetStationId);
            if (usableStore.size() == 0){
                throw new Exception("站台【"+targetStationId+"】没找到可用区域");
            }
            pathSchedulingService.containerMoveTask(ironTray.get(0),storeArea,usableStore.get(0));

        }
    }


    private List<Integer> getStation(){
        List<Integer> stations = stationService.findStationByMap(MapUtils.put("stationTaskType", Station.TASK_TYPE_SEED)
                .put("isLock", Station.UN_LOCK).put("stationType",Station.STATION_TYPE_UNFINISHEDPROD).getMap())
                .stream().map(Station::getId).collect(Collectors.toList());

        return stations;
    }





}
