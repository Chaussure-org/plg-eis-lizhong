package com.prolog.eis.inventory.service.impl;

import com.prolog.eis.dto.inventory.InventoryShowDto;
import com.prolog.eis.dto.inventory.RickerInfoDto;
import com.prolog.eis.dto.inventory.RickerTaskDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.dto.wms.WmsInventoryCallBackDto;
import com.prolog.eis.engin.service.IInventoryTrayOutService;
import com.prolog.eis.inventory.service.IInventoryHistoryService;
import com.prolog.eis.inventory.service.IInventoryJobService;
import com.prolog.eis.inventory.service.IInventoryTaskDetailService;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.IPointLocationService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.PointLocation;
import com.prolog.eis.model.inventory.InventoryTaskDetail;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.wcs.service.IWcsService;
import com.prolog.eis.wms.service.IWmsService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/2 11:25
 */
@Service
public class InventoryJobServiceImpl implements IInventoryJobService {
    @Autowired
    private IInventoryTaskDetailService taskDetailService;
    @Autowired
    private ContainerPathTaskService containerPathTaskService;
    @Autowired
    private IStationService stationService;
    @Autowired
    private IContainerStoreService containerStoreService;
    @Autowired
    private PathSchedulingService pathSchedulingService;
    @Autowired
    private IInventoryTrayOutService trayOutService;
    @Autowired
    private IWcsService wcsService;
    @Autowired
    private IPointLocationService pointLocationService;

    @Autowired
    private IInventoryHistoryService inventoryHistoryService;

    @Override
    public InventoryShowDto findInventoryDetail(String containerNo) throws Exception {
        if (StringUtils.isBlank(containerNo)) {
            throw new Exception("容器号不能为空");
        }
        //校验
        inventoryCheck(containerNo);

        List<InventoryShowDto> inventoryInfo = taskDetailService.findInventoryInfo(containerNo);

        return inventoryInfo.get(0);
    }

    @Override
    public void doInventoryTask(String containerNo, int goodsNum, String lotId) throws Exception {
        if (StringUtils.isBlank(containerNo)) {
            throw new Exception("容器号不能为空");
        }

        //开始盘点
        List<ContainerStore> containerStores = containerStoreService.findByMap(MapUtils.put("containerNo", containerNo).getMap());
        if (containerStores.size() == 0) {
            throw new Exception("容器【" + containerNo + "】无库存");
        }
        ContainerStore containerStore = containerStores.get(0);
        //校验批次号
        if (!StringUtils.isBlank(lotId)) {
            if (!containerStore.getLotId().equals(lotId)) {
                throw new Exception("容器【" + containerNo + "】批次号错误");
            }
        }
        //校验容器是否有盘点任务
        List<InventoryTaskDetail> details = taskDetailService.findByMap(MapUtils.put("containerNo", containerNo).put("taskState", InventoryTaskDetail.TASK_STATE_OUT).getMap());
        if (details.size() == 0) {
            throw new Exception("容器【" + containerNo + "】无盘点任务");

        }
        //校验容器是否已经到达
        InventoryTaskDetail inventoryTaskDetail = details.get(0);
        List<Station> stations = stationService.findStationByMap(MapUtils.put("containerNo", containerNo).getMap());
        List<ContainerPathTask> containerPathTasks = containerPathTaskService.findByMap(MapUtils.put("containerNo", containerNo)
                .put("sourceArea", StoreArea.RCS01).put("targetArea", StoreArea.RCS01).getMap());
        if (stations.size() == 0 && containerPathTasks.size() == 0) {
            throw new Exception("容器【" + containerNo + "】不在盘点区域");
        }



        containerStore.setUpdateTime(new Date());
        //修改盘点计划
        inventoryTaskDetail.setModifyCount(goodsNum);
        inventoryTaskDetail.setTaskState(InventoryTaskDetail.TASK_STATE_FINISH);
        inventoryTaskDetail.setEndTime(new Date());
        if (stations.size() > 1) {
            inventoryTaskDetail.setStationId(stations.get(0).getId());
        } else {
            //agv区盘点站台id默认为0
            inventoryTaskDetail.setStationId(0);
        }
        taskDetailService.updateInventoryDetail(inventoryTaskDetail);
        //盘点数量和实际数量不符则修改库存并回告wms
        if (!containerStore.getQty().equals(goodsNum)) {
            containerStore.setQty(goodsNum);
            //修改库存
            containerStoreService.updateContainerStore(containerStore);
        }
        //容器放行
        if (stations.size() > 0) {
            //上层输送线放行回箱库
            containerLeaveByStation(stations.get(0).getId(), PointLocation.POINT_ID_LXHK, containerNo);
        } else {
            //下层agv放行回立库找堆垛机任务最少的巷道
            RickerInfoDto rickerInfoDto = computeAreaNo();
            pathSchedulingService.containerMoveTask(containerNo, rickerInfoDto.getAreaNo(), null);
        }
        //转历史
        inventoryHistoryService.inventoryToHistory(containerNo);
    }

    @Override
    public void inventoryDetailToHistory(InventoryTaskDetail inventoryTaskDetail) {
        taskDetailService.deleteById(inventoryTaskDetail.getId());
    }

    @Override
    public void containerLeaveByStation(int stationId, String target, String containerNo) throws Exception {
        String taskId = PrologStringUtils.newGUID();
        PointLocation pointLocation = pointLocationService.getPointByStationId(stationId);
        WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId, pointLocation.getPointId(), target, containerNo, 5);
        wcsService.lineMove(wcsLineMoveDto, 0);
    }

    @Override
    public RickerInfoDto computeAreaNo() throws Exception {
        List<RickerTaskDto> taskDtos = trayOutService.computeRickerTask();
        List<RickerInfoDto> rickerInfos = trayOutService.getRickerInfos();
        for (RickerInfoDto rickerInfo : rickerInfos) {
            for (RickerTaskDto taskDto : taskDtos) {
                if (taskDto.getAlleyWay().equals(rickerInfo.getAreaNo())){
                    rickerInfo.setTaskCount(taskDto.getTaskCount());
                }
            }
        }
        List<RickerInfoDto> collect = rickerInfos.stream().sorted(Comparator.comparing(RickerInfoDto::getTaskCount).
                thenComparing(RickerInfoDto::getStoreCount).reversed()).collect(Collectors.toList());
        if (collect.get(0).getStoreCount() < 1){
            throw new Exception("回库找寻巷道失败【"+collect.get(0).getAreaNo()+"】");
        }
        //任务数最少、巷道容器最少

        return collect.get(0);
    }


    /**
     * 盘点校验
     *
     * @param containerNo
     * @return
     * @throws Exception
     */
    private void inventoryCheck(String containerNo) throws Exception {
        //校验容器是否有盘点任务
        List<InventoryTaskDetail> details = taskDetailService.findByMap(MapUtils.put("containerNo", containerNo).put("taskState", InventoryTaskDetail.TASK_STATE_OUT).getMap());
        if (details.size() == 0) {
            throw new Exception("容器【" + containerNo + "】无盘点任务");
        }
        //校验容器是否在盘点位
        boolean b = this.checkArrive(containerNo);
        if (b) {
            throw new Exception("容器【" + containerNo + "】不在盘点区域");
        }


    }

    private boolean checkArrive(String containerNo) throws Exception {
        List<Station> stations = stationService.findStationByMap(MapUtils.put("containerNo", containerNo).getMap());
        if (stations.size() == 0) {
            return true;
        }
        List<ContainerPathTask> containerPathTasks = containerPathTaskService.findByMap(MapUtils.put("containerNo", containerNo)
                .put("sourceArea", StoreArea.RCS01).put("targetArea", StoreArea.RCS01).getMap());
        if (containerPathTasks.size() == 0) {
            return true;
        }
        return false;
    }


}
