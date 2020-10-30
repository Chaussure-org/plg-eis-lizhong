package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.inventory.InventoryOutDto;
import com.prolog.eis.dto.inventory.RickerTaskDto;
import com.prolog.eis.engin.dao.InventoryTrayOutMapper;
import com.prolog.eis.engin.service.IInventoryTrayOutService;
import com.prolog.eis.inventory.dao.InventoryTaskDetailMapper;
import com.prolog.eis.inventory.service.IInventoryTaskDetailService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.inventory.InventoryTaskDetail;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/28 12:12
 */
public class InventoryTrayOutServiceImpl implements IInventoryTrayOutService {
    @Autowired
    private InventoryTaskDetailMapper inventoryTaskDetailMapper;
    @Autowired
    private InventoryTrayOutMapper trayOutMapper;
    @Autowired
    private IStationService stationService;
    @Autowired
    private PathSchedulingService pathSchedulingService;
    @Autowired
    private IContainerStoreService containerStoreService;
    @Autowired
    private IInventoryTaskDetailService inventoryTaskDetailService;



    /**
     * 1、初始化盘点容器
     * 2、任务数最少堆垛机找容器
     * 3、移位数最少容器
     * 4、创建时间最早的容器
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void inventoryTrayOut() throws Exception {
        //查播种站台
        List<Station> stations = stationService.findStationByMap(MapUtils.put("stationTaskType", Station.TASK_TYPE_INVENTORY).
                put("isLock", Station.UN_LOCK).getMap());
        if (stations.size() == 0){
            return;
        }

        InventoryOutDto outInventory = null;
        //初始化数据
        List<InventoryOutDto> inventoryOutDtos = this.initInventoryStore();
        if (inventoryOutDtos.size() == 0){
            return;
        }
        //查找agv区货位
        int emptyStore = getEmptyStore();
        if (emptyStore <= 0){
            return;
        }

        //巷道任务数
        List<RickerTaskDto> rickerTaskDtos = computeRickerTask();
        //按巷道任务数排序
        List<RickerTaskDto> collect = rickerTaskDtos.stream().sorted(Comparator.comparing(RickerTaskDto::getTaskCount)).collect(Collectors.toList());
        for (RickerTaskDto rickerTaskDto : collect) {
            //过滤获取任务最少巷道的可盘点任务
            List<InventoryOutDto> inventory = inventoryOutDtos.stream().
                    filter(x -> x.getAreaNo().equals(rickerTaskDto.getAlleyWay())).collect(Collectors.toList());
            if (inventory.size() == 0){
                continue;
            }else {
                //找寻一个可出库的盘点容器 移位数最少、创建时间最早进行排序
                List<InventoryOutDto> inventory1 = inventory.stream().
                        sorted(Comparator.comparing(InventoryOutDto::getDeptNum).
                                thenComparing(InventoryOutDto::getCreateTime)).collect(Collectors.toList());

                outInventory = inventory1.get(0);
                break;
            }
        }

        //出库
        pathSchedulingService.containerMoveTask(outInventory.getContainerNo(),"RCS01",null);
        //修改状态
        outUpdateStore(outInventory.getContainerNo());

    }

    @Override
    public List<RickerTaskDto> computeRickerTask() {
        List<RickerTaskDto> inRicker = trayOutMapper.getInTaskCount();
        List<RickerTaskDto> outRicker = trayOutMapper.getOutTaskCount();
        List<RickerTaskDto> rickerTask = new ArrayList<>();

        rickerTask.addAll(inRicker);
        rickerTask.addAll(outRicker);
        Map<String, RickerTaskDto> collect = rickerTask.stream().collect(Collectors.toMap(RickerTaskDto::getAlleyWay, Function.identity(), (k1, k2) -> {
            k1.setTaskCount(k1.getTaskCount() + k2.getTaskCount());
            return k1;
        }));

        rickerTask = collect.values().stream().collect(Collectors.toList());
        List<RickerTaskDto> mcs1 = rickerTask.stream().filter(x -> x.getAlleyWay().equals("MCS01")).collect(Collectors.toList());
        if (mcs1.size() == 0){
            rickerTask.add(new RickerTaskDto("MCS01",0));
        }
        List<RickerTaskDto> mcs2 = rickerTask.stream().filter(x -> x.getAlleyWay().equals("MCS02")).collect(Collectors.toList());
        if (mcs2.size() == 0){
            rickerTask.add(new RickerTaskDto("MCS02",0));
        }
        List<RickerTaskDto> mcs3 = rickerTask.stream().filter(x -> x.getAlleyWay().equals("MCS03")).collect(Collectors.toList());
        if (mcs3.size() == 0){
            rickerTask.add(new RickerTaskDto("MCS03",0));
        }
        List<RickerTaskDto> mcs4 = rickerTask.stream().filter(x -> x.getAlleyWay().equals("MCS04")).collect(Collectors.toList());
        if (mcs4.size() == 0){
            rickerTask.add(new RickerTaskDto("MCS04",0));
        }

        return rickerTask;

    }

    @Override
    public int getEmptyStore() {
        return trayOutMapper.getEmpty();
    }

    @Override
    public void outUpdateStore(String containerNo) throws Exception {
        containerStoreService.updateContainerStore(containerNo, ContainerStore.TASK_TYPE_INVENTORY_OUTBOUND,ContainerStore.TASK_STATUS_OUT);
        inventoryTaskDetailService.updateContainerTaskState(containerNo, InventoryTaskDetail.TASK_STATE_OUT);
    }


    private List<InventoryOutDto> initInventoryStore() {
        String area = "MCS01,MCS02,MCS03,MCS04";
        List<InventoryOutDto> inventoryStore = inventoryTaskDetailMapper.getInventoryStore(area);
        return inventoryStore;
    }


}
