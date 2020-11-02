package com.prolog.eis.inventory.service.impl;

import com.prolog.eis.dto.inventory.RickerTaskDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.engin.service.IInventoryTrayOutService;
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
import com.prolog.eis.util.PrologTaskIdUtils;
import com.prolog.eis.wcs.service.IWcsService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/2 11:25
 */
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

    @Override
    public void doInventoryTask(String containerNo, int qty, String lotId) throws Exception {
        if(StringUtils.isBlank(containerNo)){
            throw new Exception("容器号不能为空");
        }
        //校验容器是否有盘点任务
        InventoryTaskDetail inventoryTaskDetail = inventoryCheck(containerNo);
        //开始盘点
        List<ContainerStore> containerStores = containerStoreService.findByMap(MapUtils.put("containerNo", containerNo).getMap());
        if (containerStores.size() == 0){
            throw new Exception("容器【"+containerNo+"】无库存");
        }
        ContainerStore containerStore = containerStores.get(0);
        //校验批次号
        if (!StringUtils.isBlank(lotId)){
            if (!containerStore.getLotId().equals(lotId)){
                throw new Exception("容器【"+containerNo+"】批次号错误");
            }
        }
        List<Station> stations = stationService.findStationByMap(MapUtils.put("containerNo", containerNo).getMap());
        List<ContainerPathTask> containerPathTasks = containerPathTaskService.findByMap(MapUtils.put("containerNo", containerNo)
                .put("sourceArea", StoreArea.RCS01).put("targetArea", StoreArea.RCS01).getMap());
        if (stations.size() == 0 && containerPathTasks.size() == 0){
            throw new Exception("容器【"+containerNo+"】不在盘点区域");
        }
        //修改数量和原始数量不同则修改库存
        if (!containerStore.getQty().equals(qty)){
            containerStore.setQty(qty);
            //todo:回告wms
        }
        containerStore.setUpdateTime(new Date());
        //修改盘点计划
        inventoryTaskDetail.setModifyCount(qty);
        inventoryTaskDetail.setTaskState(InventoryTaskDetail.TASK_STATE_FINISH);
        inventoryTaskDetail.setEndTime(new Date());
        if (stations.size() > 1){
            inventoryTaskDetail.setStationId(stations.get(0).getId());
        }else {
            //agv区盘点站台id默认为0
            inventoryTaskDetail.setStationId(0);
        }
        taskDetailService.updateInventoryDetail(inventoryTaskDetail);

        //容器放行
        if (stations.size() > 0){
            //上层输送线放行回箱库
            containerLeaveByStation(stations.get(0).getId(),PointLocation.POINT_ID_LXHK,containerNo);
        }else {
            //下层agv放行回立库找堆垛机任务最少的巷道
            List<RickerTaskDto> taskDtos= trayOutService.computeRickerTask();
            List<RickerTaskDto> collect = taskDtos.stream().sorted(Comparator.comparing(RickerTaskDto::getTaskCount)).collect(Collectors.toList());
            pathSchedulingService.containerMoveTask(containerNo,collect.get(0).getAlleyWay(),null);
        }
        //todo：转历史
    }

    @Override
    public void inventoryDetailToHistory(InventoryTaskDetail inventoryTaskDetail) {
        taskDetailService.deleteById(inventoryTaskDetail.getId());
    }

    @Override
    public void containerLeaveByStation(int stationId,String target,String containerNo) throws Exception {
        String taskId = PrologStringUtils.newGUID();
        PointLocation pointLocation = pointLocationService.getPointByStationId(stationId);
        WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId,pointLocation.getPointId(),target,containerNo,5);
        wcsService.lineMove(wcsLineMoveDto);
    }


    /**
     * 盘点校验
     * @param containerNo
     * @return
     * @throws Exception
     */
    private InventoryTaskDetail inventoryCheck(String containerNo) throws Exception {
        //校验容器是否有盘点任务
        List<InventoryTaskDetail> details = taskDetailService.findByMap(MapUtils.put("containerNo", containerNo).put("taskState",InventoryTaskDetail.TASK_STATE_OUT).getMap());
        if (details.size() == 0){
            throw new Exception("容器【"+containerNo+"】无盘点任务");
        }
        //校验容器是否在盘点位
        boolean b = this.checkArrive(containerNo);
        if (b){
            throw new Exception("容器【"+containerNo+"】不在盘点区域");
        }
        return details.get(0);


    }

    private boolean checkArrive(String containerNo) throws Exception {
        List<Station> stations = stationService.findStationByMap(MapUtils.put("containerNo", containerNo).getMap());
        if (stations.size() == 0){
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
