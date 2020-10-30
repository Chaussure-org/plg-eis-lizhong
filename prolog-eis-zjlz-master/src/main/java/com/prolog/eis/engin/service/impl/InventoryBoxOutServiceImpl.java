package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.inventory.InventoryOutDto;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.engin.service.IInventoryBoxOutService;
import com.prolog.eis.inventory.dao.InventoryTaskDetailMapper;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.sas.service.ISASService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
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
    private ISASService sasService;

    /**
     * 1、初始箱库盘点任务
     * 2、优先找有车层、任务最少、移位最少、创建时间最早的容器出库
     * 3、有车层任务做完找其他层出库
     * @throws IOException
     */
    @Override
    public synchronized void inventoryBoxOut() throws IOException {
        //查询可播种站台
        List<Station> stations = stationService.findStationByMap(MapUtils.put("stationTaskType", Station.TASK_TYPE_INVENTORY).
                put("isLock", Station.UN_LOCK).getMap());
        if (stations.size() == 0){
            return;
        }
        //初始化盘点数据
        List<InventoryOutDto> inventoryOutDtos = this.initInventoryStore();
        if (inventoryOutDtos.size() == 0){
            return;
        }
        //获取可用小车信息小车信息
        List<CarInfoDTO> carInfo = sasService.getCarInfo().stream().filter(x ->x.getStatus() == 1 || x.getStatus() == 2).collect(Collectors.toList());
        if (carInfo.size() == 0){
            return;
        }



    }


    /**
     * 箱库查询
     * @return
     */
    private List<InventoryOutDto> initInventoryStore() {
        String area = "SAS01";
        List<InventoryOutDto> inventoryStore = inventoryTaskDetailMapper.getInventoryStore(area);
        return inventoryStore;
    }


}
