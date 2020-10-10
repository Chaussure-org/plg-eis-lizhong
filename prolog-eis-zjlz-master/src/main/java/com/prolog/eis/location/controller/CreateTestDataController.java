package com.prolog.eis.location.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.prolog.eis.base.dao.GoodsMapper;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.dao.StoreAreaMapper;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.base.Goods;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.location.CoordinateUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author: wuxl
 * @create: 2020-09-27 9:21
 * @Version: V1.0
 */
@RestController
@Api(tags = "造测试数据")
@RequestMapping("/api/v1/create")
public class CreateTestDataController {

    @Autowired
    private AgvStoragelocationMapper agvStoragelocationMapper;
    @Autowired
    private StoreAreaMapper storeAreaMapper;
    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;
    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;
    @Autowired
    private GoodsMapper goodsInfoMapper;
    @Autowired
    private ContainerStoreMapper containerStoreMapper;

    /**
     * 写死100个存储位，5个拣选站货位，每个拣选站2个货位，1个空托盘回收位。
     */
    @ApiOperation(value = "新增agv货位", notes = "新增agv货位")
    @PostMapping("/agvStorage")
    @Transactional(rollbackFor = Exception.class)
    public RestMessage<Long> createAgvStorage(){
        //存储位
        this.addStorage();
        //拣选站货位
        this.addPickingStation();
        //空托盘回收位
        this.addEmptyContainerStation();

        return RestMessage.newInstance(true, "保存成功");
    }

    /**
     * 造库存
     */
    @ApiOperation(value = "造库存", notes = "造库存")
    @PostMapping("/store")
    @Transactional(rollbackFor = Exception.class)
    public RestMessage<Long> createStore(){
        List<Goods> goodsInfoList = goodsInfoMapper.findByMap(Maps.newHashMap(), Goods.class);
        if(CollectionUtils.isEmpty(goodsInfoList)){
            RestMessage.newInstance(false, "保存失败");
        }
        List<ContainerStore> containerStoreList = Lists.newArrayList();
        Random random = new Random();
        goodsInfoList.forEach(goodsInfo -> {
            int count = random.nextInt(10) + 1;
            for (int i = 1; i <= count; i++) {
                int qty = (random.nextInt(101) + 80) * 100;
                ContainerStore containerStore = new ContainerStore();
                containerStore.setContainerNo(String.format("TP_%d_%s", goodsInfo.getId(), String.format("%02d", i)));
                //containerStore.setContainerType(1);
                containerStore.setTaskType(0);
                containerStore.setWorkCount(0);
                containerStore.setGoodsId(goodsInfo.getId());
                containerStore.setQty(qty);
                containerStore.setCreateTime(new Date());
                containerStoreList.add(containerStore);

                ContainerPathTask containerPathTask = containerPathTaskMapper.getRequestPallet(null);
                if(null == containerPathTask){
                    return;
                }
                List<ContainerPathTaskDetail> containerPathTaskDetailList = containerPathTaskDetailMapper.findByMap(
                        MapUtils.put("palletNo", containerPathTask.getPalletNo()).getMap()
                        , ContainerPathTaskDetail.class);
                if(CollectionUtils.isEmpty(containerPathTaskDetailList)){
                    return;
                }
                containerPathTaskMapper.updateMapById(containerPathTask.getId()
                        , MapUtils.put("containerNo", containerStore.getContainerNo()).getMap()
                        , ContainerPathTask.class);
                containerPathTaskDetailMapper.updateMapById(containerPathTaskDetailList.get(0).getId()
                        , MapUtils.put("containerNo", containerStore.getContainerNo()).getMap()
                        , ContainerPathTaskDetail.class);
            }
        });
        containerStoreMapper.saveBatch(containerStoreList);
        return RestMessage.newInstance(true, "保存成功");
    }


    @SneakyThrows
    private void addStorage(){
        int x = 1;
        int y = 1;
        List<AgvStoragelocation> agvStoragelocationList = Lists.newArrayList();
        List<ContainerPathTask> containerPathTaskList = Lists.newArrayList();
        List<ContainerPathTaskDetail> containerPathTaskDetailList = Lists.newArrayList();

        for (int i = 0; i < 100; i++) {
            if (i%10 == 0 && i >= 10) {
                x = 1;
                y += 1;
            }
            AgvStoragelocation agvStoragelocation = new AgvStoragelocation();
            agvStoragelocation.setAreaNo("AGV000");
            agvStoragelocation.setLocationNo(CoordinateUtils.xyToLocationForRcs(x, y));
            agvStoragelocation.setCeng(1);
            agvStoragelocation.setX(x);
            agvStoragelocation.setY(y);
            agvStoragelocation.setLocationType(LocationConstants.AGV_LOCATION_TYPE_STORAGE);
            agvStoragelocation.setTaskLock(LocationConstants.AGV_TASKLOCK_UNLOCK);
            agvStoragelocation.setStorageLock(LocationConstants.AGV_STORAGELOCK_UNLOCK);
            agvStoragelocationList.add(agvStoragelocation);

            ContainerPathTask containerPathTask = new ContainerPathTask();
            containerPathTask.setPalletNo(String.format("ZJ%s", String.format("%05d", i + 1)));
            containerPathTask.setSourceArea("AGV000");
            containerPathTask.setSourceLocation(agvStoragelocation.getLocationNo());
            containerPathTask.setTargetArea("AGV000");
            containerPathTask.setTargetLocation(agvStoragelocation.getLocationNo());
            containerPathTask.setActualHeight(1);
            containerPathTask.setCallBack(0);
            containerPathTask.setTaskType(LocationConstants.PATH_TASK_TYPE_NONE);
            containerPathTask.setTaskState(LocationConstants.PATH_TASK_STATE_NOTSTARTED);
            containerPathTask.setCreateTime(PrologDateUtils.parseObject(new Date()));
            containerPathTaskList.add(containerPathTask);

            ContainerPathTaskDetail containerPathTaskDetail = new ContainerPathTaskDetail();
            containerPathTaskDetail.setPalletNo(containerPathTask.getPalletNo());
            containerPathTaskDetail.setSourceArea(containerPathTask.getSourceArea());
            containerPathTaskDetail.setSourceLocation(containerPathTask.getSourceLocation());
            containerPathTaskDetail.setNextArea(containerPathTask.getTargetArea());
            containerPathTaskDetail.setNextLocation(containerPathTask.getTargetLocation());
            containerPathTaskDetail.setTaskState(LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE);
            containerPathTaskDetail.setSortIndex(1);
            containerPathTaskDetail.setCreateTime(PrologDateUtils.parseObject(new Date()));
            containerPathTaskDetailList.add(containerPathTaskDetail);

            x ++;
        }
        agvStoragelocationMapper.saveBatch(agvStoragelocationList);
        containerPathTaskMapper.saveBatch(containerPathTaskList);
        containerPathTaskDetailMapper.saveBatch(containerPathTaskDetailList);
    }

    @SneakyThrows
    private void addPickingStation(){
        List<AgvStoragelocation> agvStoragelocationList = Lists.newArrayList();
        List<StoreArea> storeAreaList = Lists.newArrayList();
        int x = 100;
        int y = 100;
        for (int i = 1; i <= 5; i++) {
            StoreArea storeArea = new StoreArea();
            storeArea.setAreaNo(String.format("AGV%s", String.format("%03d", i)));
            storeArea.setAreaType(LocationConstants.AREA_TYPE_AREA);
            storeArea.setDeviceSystem(LocationConstants.DEVICE_SYSTEM_RCS);
            storeArea.setMaxHeight(10);
            storeArea.setTemporaryArea(0);
            storeArea.setMaxCount(2);
            storeArea.setCreateTime(PrologDateUtils.parseObject(new Date()));
            storeAreaList.add(storeArea);

            for (int j = 0; j < 2; j++) {
                AgvStoragelocation agvStoragelocation = new AgvStoragelocation();
                agvStoragelocation.setAreaNo(storeArea.getAreaNo());
                agvStoragelocation.setLocationNo(CoordinateUtils.xyToLocationForRcs(++x, y));
                agvStoragelocation.setCeng(1);
                agvStoragelocation.setX(x);
                agvStoragelocation.setY(y);
                agvStoragelocation.setLocationType(LocationConstants.AGV_LOCATION_TYPE_PALLET);
                agvStoragelocation.setTaskLock(LocationConstants.AGV_TASKLOCK_UNLOCK);
                agvStoragelocation.setStorageLock(LocationConstants.AGV_STORAGELOCK_UNLOCK);
                agvStoragelocationList.add(agvStoragelocation);
            }
        }
        storeAreaMapper.saveBatch(storeAreaList);
        agvStoragelocationMapper.saveBatch(agvStoragelocationList);
    }

    @SneakyThrows
    private void addEmptyContainerStation(){
        StoreArea storeArea = new StoreArea();
        storeArea.setAreaNo("AGV099");
        storeArea.setAreaType(LocationConstants.AREA_TYPE_AREA);
        storeArea.setDeviceSystem(LocationConstants.DEVICE_SYSTEM_RCS);
        storeArea.setMaxHeight(10);
        storeArea.setTemporaryArea(1);
        storeArea.setMaxCount(100);
        storeArea.setCreateTime(PrologDateUtils.parseObject(new Date()));
        storeAreaMapper.save(storeArea);

        AgvStoragelocation agvStoragelocation = new AgvStoragelocation();
        agvStoragelocation.setAreaNo(storeArea.getAreaNo());
        agvStoragelocation.setLocationNo(CoordinateUtils.xyToLocationForRcs(999, 999));
        agvStoragelocation.setCeng(1);
        agvStoragelocation.setX(999);
        agvStoragelocation.setY(999);
        agvStoragelocation.setLocationType(LocationConstants.AGV_LOCATION_TYPE_STORAGE);
        agvStoragelocation.setTaskLock(LocationConstants.AGV_TASKLOCK_UNLOCK);
        agvStoragelocation.setStorageLock(LocationConstants.AGV_STORAGELOCK_UNLOCK);
        agvStoragelocationMapper.save(agvStoragelocation);
    }
}
