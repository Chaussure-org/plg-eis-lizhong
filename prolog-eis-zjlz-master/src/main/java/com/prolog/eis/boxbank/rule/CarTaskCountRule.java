/*
package com.prolog.eis.boxbank.rule;

import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dao.enginee.EngineLxChuKuMapper;
import com.prolog.eis.dao.store.SxLayerMapper;
import com.prolog.eis.dao.test.RgvMapper;
import com.prolog.eis.dto.enginee.CengLxTaskDto;
import com.prolog.eis.dto.store.StoreTaskDto;
import com.prolog.eis.model.store.SxCeng;
import com.prolog.eis.service.store.IStoreService;
import com.prolog.eis.util.TaskUtils;
import com.prolog.eis.wcs.common.Constant;
import com.prolog.eis.wcs.dto.CarInfoDTO;
import com.prolog.eis.wcs.model.WCSTask;
import com.prolog.eis.wcs.service.IWCSCommandService;
import com.prolog.eis.wcs.service.IWCSService;
import com.prolog.eis.wcs.service.IWCSTaskService;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CarTaskCountRule implements IRule {

    private static final Logger logger = LoggerFactory.getLogger(CarTaskCountRule.class);
    @Autowired
    private EngineLxChuKuMapper engineLxChuKuMapper;
    @Autowired
    private IWCSService wcsService;
    @Autowired
    private EisProperties properties;
    @Autowired
    private SxLayerMapper sxLayerMapper;
    @Autowired
    private IStoreService storeService;
    @Autowired
    private IWCSTaskService taskService;
    @Autowired
    private IWCSCommandService commandService;
    @Autowired
    private RgvMapper rgvMapper;

    @Override
    public boolean execute(StoreLocationDTO location,Function<StoreLocationDTO,Boolean> func) throws Exception {
        int layer = location.getLayer();
        CengLxTaskDto cengLxTaskDto = engineLxChuKuMapper.findTotalTaskByCeng(layer);
        List<CarInfoDTO> cars = wcsService.getCarInfo();
        //List<CarInfoDTO> cars = rgvMapper.findAll();
        // 小车故障后需锁层
        List<CarInfoDTO> collectWrong = cars.stream().filter(x -> x.getStatus() == 4||x.getStatus()==3).collect(Collectors.toList());
        List<CarInfoDTO> collectRight = new ArrayList<>();
        collectRight.addAll(cars);
        collectRight.removeAll(collectWrong);
        if (collectRight.size() != 0){
            this.unLockLayer(collectRight);
        }
        if (collectWrong.size() != 0){
            this.lockLayer(collectWrong);
        }
        // 考虑换层
        //checkAutoCrossLayer(layer,cars,cengLxTaskDto);
        return true;
    }

    // 当某层无车,且有任务时
    public synchronized void checkAutoCrossLayer(int layer, List<CarInfoDTO> cars, CengLxTaskDto cengLxTaskDto) throws Exception {
        // 判断是否有换层任务
        List<WCSTask> wcsTasks = taskService.getTaskByMap(MapUtils.put("wcsType", Constant.COMMAND_TYPE_HC).getMap());
        if (wcsTasks.size()>0) {
            throw new Exception("已存在一个换层任务");
        }
        // 查找所有信息对应的层
        List<Integer> layers = cars.stream().map(CarInfoDTO::getLayer).distinct().collect(Collectors.toList());
        //若所有层没有包含任务层,说明该层有任务,且没有车,需要换层
        if (!layers.contains(layer)){
            // 寻找无出库任务,并且最近的层
            int sourceLayer = layerRule(layer,cars);
            if(sourceLayer != 0){
                // 可以换层,生成任务,然后调用换层接口
                    doCrossLayer(sourceLayer,layer);
            }
        }else{
            long carCount = cars.stream().filter(x -> x.getLayer() == layer && x.getStatus() != 3 && x.getStatus() != 4).count();
            if (cengLxTaskDto.getTaskCount() > (carCount * properties.getMaxTaskCountPerCar())) {
                throw new RuntimeException("任务数超过了小车最大任务数");
            }
        }
    }

    private void doCrossLayer(int sourceLayer, int layer) throws Exception {
        // 锁层
        SxCeng layer1 = new SxCeng();
        layer1.setLayer(layer);
        layer1.setLockState(1);
        sxLayerMapper.updateByLayer(layer1);
        SxCeng layer2 = new SxCeng();
        layer2.setLayer(sourceLayer);
        layer2.setLockState(1);
        sxLayerMapper.updateByLayer(layer2);
        // 记录并发起换层任务
        String taskId = TaskUtils.gerenateTaskId();
        WCSTask task = new WCSTask();
        task.setId(taskId);
        task.setEisType(Constant.TASK_TYPE_HC);
        task.setWcsType(Constant.COMMAND_TYPE_HC);
        task.setStatus(WCSTask.STATUS_WAITTING);
        task.setAddress(String.valueOf(sourceLayer));
        task.setTarget(String.valueOf(layer));
        task.setGmtCreateTime(new Date());
        taskService.add(task);
        boolean b = commandService.sendHCCommand(taskId, sourceLayer, layer);
        if (!b){
            throw new Exception("换层任务发送不成功");
        }
    }

    private int layerRule(int layer,List<CarInfoDTO> cars) {
        List<CarInfoDTO> collect = cars.stream().filter(x -> x.getStatus() == 1 || x.getStatus() == 2).collect(Collectors.toList());
        List<Integer> layers = collect.stream().map(CarInfoDTO::getLayer).collect(Collectors.toList());
        int sourceLayer = 0;
        List<StoreTaskDto> storeTasks = storeService.findStoreTasks();
        if (storeTasks.size()>0){
            Map<Integer, List<StoreTaskDto>> taskMap = com.prolog.eis.util.CollectionUtils.mapList(storeTasks, task -> task.getLayer(), task->task.getTaskType()!=0);
            for (Map.Entry<Integer, List<StoreTaskDto>> listEntry : taskMap.entrySet()) {
                layers.remove(listEntry.getKey());
            }
            if (layers.size()>0){
                sourceLayer= compute(sourceLayer, layer, layers);
                return sourceLayer;
            }else {
                return 0;
            }
        }else {
            sourceLayer = compute(sourceLayer,layer,layers);
            return sourceLayer;
        }

    }

    private int compute(int sourceLayer,int layer,List<Integer> layers){
        int diffNum = Math.abs(layers.get(0) - layer);
        for (Integer integer : layers){
            int diffNumTemp = Math.abs(integer - layer);
            if (diffNumTemp <= diffNum) {
                diffNum = diffNumTemp;
                sourceLayer = integer;
            }
        }
        return sourceLayer;
    }

    //锁层
    private void lockLayer(List<CarInfoDTO> list){
        for (CarInfoDTO car : list) {
            List<SxCeng> layer1 = sxLayerMapper.findByMap(MapUtils.put("layer", car.getLayer()).getMap(), SxCeng.class);
            if (layer1.size()>0 && layer1.get(0).getLockState()==0){
                layer1.get(0).setLockState(1);
                sxLayerMapper.updateByLayer(layer1.get(0));
                logger.info(car.getRgvId()+"号车出现故障,所在层"+layer1.get(0).getLayer()+"已封锁");
            }
        }
    }

    //解锁
    private void unLockLayer(List<CarInfoDTO> list){
        for (CarInfoDTO car : list) {
            List<SxCeng> layer1 = sxLayerMapper.findByMap(MapUtils.put("layer", car.getLayer()).getMap(), SxCeng.class);
            if (layer1.size()>0 && layer1.get(0).getLockState()==1){
                SxCeng sxCeng = layer1.get(0);
                sxCeng.setLockState(0);
                sxLayerMapper.updateByLayer(sxCeng);
                logger.info(car.getRgvId()+"号车恢复正常,所在层"+layer1.get(0).getLayer()+"已解锁");
            }
        }
    }


}
*/
