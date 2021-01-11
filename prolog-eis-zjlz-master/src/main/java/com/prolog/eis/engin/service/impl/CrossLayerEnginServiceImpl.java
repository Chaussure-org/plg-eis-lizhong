package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.LayerGoodsCountDto;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.dto.wcs.SasMoveCarDto;
import com.prolog.eis.engin.dao.CrossLayerTaskMapper;
import com.prolog.eis.engin.service.CrossLayerEnginService;
import com.prolog.eis.model.wcs.CrossLayerTask;
import com.prolog.eis.sas.service.ISasService;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.framework.common.message.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
    private ISasService ISasService;
    @Autowired
    private CrossLayerTaskMapper crossLayerTaskMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void findCrossLayerTask() throws Exception {
        //所有的出库任务( 此方法暂时改成 入库的自动跨层调度)
        List<LayerGoodsCountDto> outContainers = containerStoreMapper.findOutContainers();
        if (outContainers.size() == 0) {
            return;
        }
        //所有的车
        List<CarInfoDTO> collect = ISasService.getCarInfo().stream().filter(x -> Arrays.asList(1, 2).contains(x.getStatus())).collect(Collectors.toList());
        List<CarInfoDTO> cars = collect.stream().filter(x -> x.getLayer() != 0).collect(Collectors.toList());
        if (cars.size() == 0) {
            return;
        }
        //1.找车 首先找没有任务的车 2.找层 有任务没有车的层 3. 这一层没有正在执行跨层任务的
        List<Integer> taskLayers = outContainers.stream().map(LayerGoodsCountDto::getLayer).collect(Collectors.toList());
        List<Integer> carLayers = cars.stream().map(CarInfoDTO::getLayer).collect(Collectors.toList());
        //有任务没车的层
        List<LayerGoodsCountDto> tasksNoCars = outContainers.stream().filter(x -> !carLayers.contains(x.getLayer())).collect(Collectors.toList());
        if (tasksNoCars.size() == 0) {
            return;
        }
        List<CarInfoDTO> carNoTasks = cars.stream().filter(x -> !taskLayers.contains(x.getLayer())).collect(Collectors.toList());
        //没有空闲的车
        if (carNoTasks.size() == 0) {
            return;
        }
        //正在执行的跨层任务  1.有任务没车的 层 排除 已经生成跨层任务的任务层
        List<CrossLayerTask> crossTasks = crossLayerTaskMapper.findByMap(null, CrossLayerTask.class);
        if (crossTasks.size() > 0) {
            List<Integer> targets = crossTasks.stream().map(CrossLayerTask::getTargetLayer).collect(Collectors.toList());
            tasksNoCars.forEach(x -> {
                if (targets.contains(x.getLayer())) {
                    tasksNoCars.remove(x);
                }
            });
        }
        //单次调度只生成一条跨层任务 1.优先任务数最多的层先执行跨层
        tasksNoCars.stream().sorted(Comparator.comparing(LayerGoodsCountDto::getOutCount, Comparator.reverseOrder()));
        CarInfoDTO car = carNoTasks.get(0);
        this.sendCrossLayerTask(car.getLayer(), tasksNoCars.get(0).getLayer(), car.getRgvId());
    }

    @Override
    public void sendCrossLayerTask(int sourceLayer, int targetLayer, String rgvId) throws Exception {
        String taskId = PrologStringUtils.newGUID();
        SasMoveCarDto sasMoveCarDto = new SasMoveCarDto();
        sasMoveCarDto.setBankId(1);
        sasMoveCarDto.setSource(sourceLayer);
        sasMoveCarDto.setTarget(targetLayer);
        sasMoveCarDto.setTaskId(taskId);
        RestMessage<String> message = ISasService.moveCar(sasMoveCarDto);
        if (message.isSuccess()) {
            this.saveCrossLayerTask(sourceLayer, targetLayer, rgvId);
        }
    }

    @Override
    public void saveCrossLayerTask(int sourceLayer, int targetLayer, String rgvId) throws Exception {
        CrossLayerTask crossLayerTask = new CrossLayerTask();
        crossLayerTask.setSourceLayer(sourceLayer);
        crossLayerTask.setTargetLayer(targetLayer);
        crossLayerTask.setCarNo(rgvId);
        crossLayerTaskMapper.save(crossLayerTask);
    }
}
