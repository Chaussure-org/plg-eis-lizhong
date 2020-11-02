package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.inventory.BoxLayerTaskDto;
import com.prolog.eis.dto.inventory.InventoryOutDto;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.engin.dao.InventoryBoxOutMapper;
import com.prolog.eis.engin.service.IInventoryBoxOutService;
import com.prolog.eis.engin.service.IInventoryTrayOutService;
import com.prolog.eis.inventory.dao.InventoryTaskDetailMapper;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.inventory.InventoryTaskDetail;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.sas.service.ISasService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/29 18:02
 */
public class InventoryBoxOutServiceImpl implements IInventoryBoxOutService {
    @Autowired
    private InventoryTaskDetailMapper inventoryTaskDetailMapper;
    @Autowired
    private IStationService stationService;
    @Autowired
    private ISasService sasService;
    @Autowired
    private InventoryBoxOutMapper boxOutMapper;
    @Autowired
    private PathSchedulingService pathSchedulingService;
    @Autowired
    private IInventoryTrayOutService iInventoryTrayOutService;

    /**
     * 1、初始箱库盘点任务
     * 2、优先找有车层、任务最少、移位最少、创建时间最早的容器出库
     * 3、有车层任务做完找其他层出库
     *
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void inventoryBoxOut() throws Exception {
        //查询可播种站台
        List<Station> stations = stationService.findStationByMap(MapUtils.put("stationTaskType", Station.TASK_TYPE_INVENTORY).
                put("isLock", Station.UN_LOCK).getMap());
        if (stations.size() == 0) {
            return;
        }
        //初始化盘点数据
        List<InventoryOutDto> inventoryOutDtos = this.initInventoryStore();
        if (inventoryOutDtos.size() == 0) {
            return;
        }
        //获取可用小车信息小车信息
        List<Integer> carLayers = sasService.getCarInfo().stream().filter(x -> x.getStatus() == 1 || x.getStatus() == 2)
                .map(CarInfoDTO::getLayer).collect(Collectors.toList());
        if (carLayers.size() == 0) {
            return;
        }
        InventoryOutDto outContainer = null;
        //获取层信息
        List<BoxLayerTaskDto> layerTaskInfo = boxOutMapper.getLayerTaskInfo();

        //找有小车层的盘点出库容器
        List<InventoryOutDto> collect = inventoryOutDtos.stream().filter(x -> carLayers.contains(x.getLayer())).collect(Collectors.toList());
        if (collect.size() > 0) {
            //找任务数最少且有车的层集合
            List<BoxLayerTaskDto> taskDtosSorted = layerTaskInfo.stream().filter(x -> carLayers.contains(x.getLayer())).
                    sorted(Comparator.comparing(BoxLayerTaskDto::getTaskCount)).collect(Collectors.toList());
            BoxLayerTaskDto boxLayerTaskDto = taskDtosSorted.get(0);

            List<InventoryOutDto> inventoryLayer = collect.stream().filter(x -> x.getLayer() == boxLayerTaskDto.getLayer()).
                    collect(Collectors.toList());
            outContainer = this.getOutContainer(inventoryLayer);
        } else {
            //无车找移位数最少的，创建时间最早的层
            outContainer = this.getOutContainer(inventoryOutDtos);
        }

        //出库
        pathSchedulingService.containerMoveTask(outContainer.getContainerNo(), StoreArea.WCS081,StoreArea.LXJZ01);
        //修改状态
        iInventoryTrayOutService.outUpdateStore(outContainer.getContainerNo());

    }

    @Override
    public void inventoryAllotStation() {

    }


    /**
     * 箱库查询
     *
     * @return
     */
    private List<InventoryOutDto> initInventoryStore() {
        String area = "SAS01";
        List<InventoryOutDto> inventoryStore = inventoryTaskDetailMapper.getInventoryStore(area);
        return inventoryStore;
    }

    /**
     * 找移位数最少的，创建时间最早的容器
     */
    private InventoryOutDto getOutContainer(List<InventoryOutDto> inventoryOutDtos) {
        List<InventoryOutDto> collect = inventoryOutDtos.stream().sorted(Comparator.comparing(InventoryOutDto::getDeptNum).
                thenComparing(InventoryOutDto::getCreateTime)).collect(Collectors.toList());
        return collect.get(0);
    }


}
