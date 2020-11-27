package com.prolog.eis.store.service.impl;

import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.lzenginee.RoadWayContainerTaskDto;
import com.prolog.eis.engin.dao.TrayOutMapper;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.IContainerPathTaskDetailService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.store.service.ITrayStoreReleaseService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/3 11:00
 */
@Service
public class TrayStoreReleaseServiceImpl implements ITrayStoreReleaseService {
    @Autowired
    private ContainerPathTaskService pathTaskService;
    @Autowired
    private PathSchedulingService pathSchedulingService;
    @Autowired
    private IContainerStoreService containerStoreService;
    @Autowired
    private TrayOutMapper trayOutMapper;
    @Autowired
    private AgvLocationService agvLocationService;
    @Autowired
    private IContainerPathTaskDetailService containerPathTaskDetailService;
    @Autowired
    private EisProperties eisProperties;
    /**
     * 接驳口下架
     * 1、agv区域空托盘下架
     * 2、贴标区下架去暂存区
     * 3、暂存区下架
     *
     * @param containerNo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void storeRelease(String containerNo, String transhipNo) throws Exception {
        if (StringUtils.isBlank(containerNo) || StringUtils.isBlank(transhipNo)) {
            throw new Exception("参数不能为空");

        }
        List<ContainerPathTask> containerPathTasks = pathTaskService.findByMap(MapUtils.put("containerNo", containerNo)
                .put("targetLocation", transhipNo).getMap());

        if (containerPathTasks.size() == 0) {
            throw new Exception("容器【" + containerNo + "】不在接驳口【" + transhipNo + "】或已被释放");
        }
        ContainerPathTask containerPathTask = containerPathTasks.get(0);
        if (containerPathTask.getTaskState() != 0) {
            throw new Exception("容器【" + containerNo + "】未到位");
        }
        switch (containerPathTask.getSourceArea()) {
            //暂存区
            case StoreArea.CH01:
                //空托区
            case StoreArea.RCS02:
                //释放货位
                pathTaskService.deletePathByContainer(containerNo);
                //删除容器
                containerStoreService.deleteContainerByMap(containerNo);
                break;
            //贴标区
            case StoreArea.LB01:
                //发往暂存区
                pathSchedulingService.containerMoveTask(containerNo, StoreArea.CH01, null);
                break;
            default:
                throw new Exception("区域类型有误");
        }


    }

    /**
     * 空托上架 1、铁拖上架铁笼区，普通托盘上架入库
     *
     * @param trayNo
     * @param transhipNo
     * @throws Exception
     */
    @Override
    public void emptyTrayPull(String trayNo, String transhipNo) throws Exception {
        if (StringUtils.isBlank(trayNo) || StringUtils.isBlank(transhipNo)) {
            throw new Exception("参数不能为空");
        }
        List<ContainerPathTask> containerNo = pathTaskService.findByMap(MapUtils.put("containerNo", trayNo).getMap());
        if (containerNo.size() > 0){
            throw new Exception("容器【"+trayNo+"】已存在路径");
        }
        //接驳点点位校验
        List<AgvStoragelocation> locationNo = agvLocationService.findByMap(MapUtils.put("locationNo", transhipNo).getMap());
        if (locationNo.size() == 0) {
            throw new Exception("接驳点【" + transhipNo + "】没有被管理");
        }
        AgvStoragelocation agvStoragelocation = locationNo.get(0);
        if (agvStoragelocation.getStorageLock() == 1){
            throw new Exception("接驳点【"+transhipNo+"】为锁定状态，无法上架");
        }
        switch (agvStoragelocation.getAreaNo()) {
            //普通托盘上架
            case StoreArea.RCS03:
                if (trayNo.startsWith(eisProperties.getIronTrayPrefix())){
                    throw new Exception("上架托盘编号有误,普通托盘上架区托盘编号前缀不能为【"+eisProperties.getIronTrayPrefix()+"】");
                }
                saveContainerStore(trayNo);
                List<RoadWayContainerTaskDto> roadWayContainerTasks = trayOutMapper.findRoadWayContainerTask();
                List<RoadWayContainerTaskDto> collect = roadWayContainerTasks.stream().sorted(Comparator.comparing(RoadWayContainerTaskDto::getInCount).thenComparing(RoadWayContainerTaskDto::getOutCount)).collect(Collectors.toList());
                //入库发路径
                pathSchedulingService.inboundTask(trayNo, trayNo, StoreArea.RCS03, transhipNo, "MCS0" + collect.get(0).getRoadWay());
                break;
            //铁拖上架
            case StoreArea.IT01:
                //保存路径
                if (!trayNo.startsWith(eisProperties.getIronTrayPrefix())){
                     throw new Exception("上架托盘编号有误,铁拖上架区托盘编号前缀为【"+eisProperties.getIronTrayPrefix()+"】");
                }
                this.saveContainerPath(trayNo,transhipNo,StoreArea.IT01);
                break;
            default:
                throw new Exception("接驳点【"+transhipNo+"】不属于空托上架区");

        }


    }

    /**
     * 生成空托库存 空拖默认商品id  -2 库存为1
     *
     * @param trayNo
     */
    private void saveContainerStore(String trayNo) throws Exception {
        List<ContainerStore> containerStores = containerStoreService.findByMap(MapUtils.put("containerNo", trayNo).getMap());
        if (containerStores.size() > 0){
            throw new Exception("容器编号【"+trayNo+"】已被使用");
        }
        ContainerStore containerStore = new ContainerStore();
        containerStore.setContainerNo(trayNo);
        containerStore.setQty(1);
        containerStore.setTaskStatus(10);
        containerStore.setContainerNo(trayNo);
        containerStore.setTaskType(10);
        containerStore.setWorkCount(1);
        containerStore.setGoodsId(-2);
        containerStore.setCreateTime(new Date());
        containerStoreService.saveContainerStore(containerStore);

    }

    /**
     * 保存路径
     */
    private void saveContainerPath(String containerNo,String sourceLocation,String areaNo){
        ContainerPathTask containerPathTask = new ContainerPathTask();
        containerPathTask.setPalletNo(containerNo);
        containerPathTask.setContainerNo(containerNo);
        containerPathTask.setSourceArea(areaNo);
        containerPathTask.setSourceLocation(sourceLocation);
        containerPathTask.setCreateTime(new Date());
        containerPathTask.setTargetArea(areaNo);
        containerPathTask.setTargetLocation(sourceLocation);
        containerPathTask.setActualHeight(0);
        containerPathTask.setCallBack(0);
        containerPathTask.setTaskType(0);
        containerPathTask.setTaskState(0);

        pathTaskService.saveContainerPath(containerPathTask);

        ContainerPathTaskDetail containerPathTaskDetail = new ContainerPathTaskDetail();
        containerPathTaskDetail.setPalletNo(containerNo);
        containerPathTaskDetail.setContainerNo(containerNo);
        containerPathTaskDetail.setSourceArea(areaNo);
        containerPathTaskDetail.setSourceLocation(sourceLocation);
        containerPathTaskDetail.setNextArea(areaNo);
        containerPathTaskDetail.setCreateTime(new Date());
        containerPathTaskDetail.setNextLocation(sourceLocation);
        containerPathTaskDetail.setTaskState(0);
        containerPathTaskDetail.setSortIndex(1);
        containerPathTaskDetailService.savePathDetail(containerPathTaskDetail);
    }
}
