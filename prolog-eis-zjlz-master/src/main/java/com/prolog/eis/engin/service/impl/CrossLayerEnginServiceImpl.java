package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.LayerGoodsCountDto;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.engin.service.CrossLayerEnginService;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.sas.service.ISASService;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author SunPP
 * Description:三十功名尘与土，八千里路云和月
 * @return
 * @date:2020/10/26 15:47
 */
@Service
public class CrossLayerEnginServiceImpl implements CrossLayerEnginService {

    @Autowired
    private ContainerStoreMapper containerStoreMapper;
    @Autowired
    private ISASService isasService;

    @Override
    public synchronized void findCrossLayerTask() throws Exception {
        //所有的出库任务
        List<LayerGoodsCountDto> outContainers = containerStoreMapper.findOutContainers();
        //所有的车
        List<CarInfoDTO> cars = isasService.getCarInfo().stream().filter(x -> Arrays.asList(1, 2).contains(x.getStatus())).collect(Collectors.toList());
        //1.找车 首先找没有任务的车 2.找层 有任务没有车的层 3. 这一层没有正在执行跨层任务的
        List<Integer> taskLayers = outContainers.stream().map(LayerGoodsCountDto::getLayer).collect(Collectors.toList());
        List<Integer> carLayers = cars.stream().map(CarInfoDTO::getLayer).collect(Collectors.toList());
        List<CarInfoDTO> carNoTasks = cars.stream().filter(x -> !taskLayers.contains(x.getLayer())).collect(Collectors.toList());
        List<LayerGoodsCountDto> tasksNoCars = outContainers.stream().filter(x -> !carLayers.contains(x.getLayer())).collect(Collectors.toList());

        //找到跨层任务

    }

    @Override
    public void sendCrossLayerTask() throws Exception {

    }

    @Override
    public void saveCrossLayerTask() throws Exception {

    }
}
