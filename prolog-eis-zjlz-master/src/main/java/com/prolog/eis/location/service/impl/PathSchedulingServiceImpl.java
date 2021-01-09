package com.prolog.eis.location.service.impl;

import com.prolog.eis.dto.location.ContainerPathTaskDTO;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.LocationService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.location.CommonConstants;
import com.prolog.eis.util.location.ContainerStoreConstants;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author: wuxl
 * @create: 2020-09-25 17:23
 * @Version: V1.0
 */
@Service
public class PathSchedulingServiceImpl implements PathSchedulingService {

    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;
    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;
    @Autowired
    private LocationService locationService;
    @Autowired
    private AgvLocationService agvLocationService;
    @Autowired
    private ContainerStoreMapper containerStoreMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void inboundTask(String palletNo, String containerNo, String sourceArea, String sourceLocation, String targetArea) throws Exception {
        //TODO 参数校验先放着
        List<ContainerPathTask> containerPathTaskList = containerPathTaskMapper.findByMap(
                MapUtils.put("containerNo", containerNo).getMap()
                , ContainerPathTask.class);
        if (containerPathTaskList.size() > 0) {
            throw new Exception("此容器路径任务已经存在");
        }

        //创建容器任务
        Timestamp nowTime = PrologDateUtils.parseObject(new Date());
        ContainerPathTask containerPathTask = new ContainerPathTask();
        containerPathTask.setPalletNo(palletNo);
        containerPathTask.setContainerNo(containerNo);
        containerPathTask.setSourceArea(sourceArea);
        containerPathTask.setSourceLocation(sourceLocation);
        containerPathTask.setTargetArea(targetArea);
        containerPathTask.setActualHeight(0);
        containerPathTask.setCallBack(0);
        containerPathTask.setTaskType(0);
        containerPathTask.setTaskState(0);
        containerPathTask.setCreateTime(nowTime);
        containerPathTaskMapper.save(containerPathTask);

        ContainerPathTaskDetail containerPathTaskDetail = new ContainerPathTaskDetail();
        containerPathTaskDetail.setPalletNo(palletNo);
        containerPathTaskDetail.setContainerNo(containerNo);
        containerPathTaskDetail.setSourceArea(sourceArea);
        containerPathTaskDetail.setSourceLocation(sourceLocation);
        containerPathTaskDetail.setNextArea(targetArea);
        containerPathTaskDetail.setTaskState(0);
        containerPathTaskDetail.setSortIndex(1);
        containerPathTaskDetail.setCreateTime(nowTime);
        containerPathTaskDetailMapper.save(containerPathTaskDetail);

        // locationService.doContainerPathTaskAndExecutionByContainer(palletNo, containerNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContainerPathTaskDTO outboundTaskForUpdate(int goodsId, String stationId) throws Exception {
        ContainerPathTaskDTO containerPathTaskDTO = agvLocationService.agvOutboundTask(goodsId, stationId);
        if (null == containerPathTaskDTO) {
            //TODO agv无库存，去四向库找，先返回null
            return null;
        }
        Criteria criteria = Criteria.forClass(ContainerStore.class);
        criteria.setRestriction(Restrictions.and(
                Restrictions.eq("containerNo", containerPathTaskDTO.getContainerNo())
                , Restrictions.eq("taskType", ContainerStoreConstants.TASK_TYPE_NOTHING)));

        long l = 0L;
        synchronized (ContainerPathTaskDTO.class) {
            l = containerStoreMapper.updateMapByCriteria(
                    MapUtils.put("taskType", ContainerStoreConstants.TASK_TYPE_OUTBOUND)
                            .put("updateTime", PrologDateUtils.parseObject(new Date())).getMap()
                    , criteria);
        }
        if (l == 0L) {
            return null;
        }
        return containerPathTaskDTO;
    }

    @Override
    public String outboundTaskForSuccess(ContainerPathTaskDTO containerPathTaskDTO) throws Exception {
        //生成托盘路径任务
        Timestamp nowTime = PrologDateUtils.parseObject(new Date());
        containerPathTaskMapper.updateMapById(containerPathTaskDTO.getId()
                , MapUtils.put("targetArea", containerPathTaskDTO.getTargetArea())
                        .put("targetLocation", null)
                        .put("taskType", LocationConstants.PATH_TASK_TYPE_OUTBOUND)
                        .put("taskState", LocationConstants.PATH_TASK_STATE_NOTSTARTED)
                        .put("updateTime", nowTime).getMap()
                , ContainerPathTask.class);

        locationService.doContainerPathTaskByContainer(containerPathTaskDTO.getPalletNo(), containerPathTaskDTO.getContainerNo());
        return CommonConstants.SUCCESS;
    }

    @Override
    public String outboundTaskForFail(ContainerPathTaskDTO containerPathTaskDTO) throws Exception {
        Criteria criteria = Criteria.forClass(ContainerStore.class);
        criteria.setRestriction(Restrictions.and(
                Restrictions.eq("containerNo", containerPathTaskDTO.getContainerNo())
                , Restrictions.eq("taskType", ContainerStoreConstants.TASK_TYPE_OUTBOUND)));

        long l = containerStoreMapper.updateMapByCriteria(
                MapUtils.put("taskType", ContainerStoreConstants.TASK_TYPE_NOTHING)
                        .put("updateTime", PrologDateUtils.parseObject(new Date())).getMap()
                , criteria);
        if (l == 0L) {
            return "托盘库存有误";
        }
        return CommonConstants.SUCCESS;
    }

    /**
     * @param palletNo   容器号
     * @param targetArea 目标区域 更新 task_status
     * @throws Exception
     */
    @Override
    public void containerMoveTask(String palletNo, String targetArea, String targetLocation) throws Exception {
        //TODO 参数校验先放着

        List<ContainerPathTask> containerPathTaskList = containerPathTaskMapper.findByMap(
                MapUtils.put("palletNo", palletNo)
                        .put("taskState", LocationConstants.PATH_TASK_STATE_NOTSTARTED).getMap()
                , ContainerPathTask.class);
        if (CollectionUtils.isEmpty(containerPathTaskList)) {
            //throw new Exception("");
            return;
        }
        ContainerPathTask containerPathTask = containerPathTaskList.get(0);
        containerPathTaskMapper.updateMapById(containerPathTask.getId()
                , MapUtils.put("targetArea", targetArea)
                        .put("taskType", LocationConstants.PATH_TASK_TYPE_OUTBOUND)
                        .put("targetLocation", targetLocation).getMap()
                , ContainerPathTask.class);

        locationService.doContainerPathTaskAndExecutionByContainer(containerPathTask.getPalletNo(), containerPathTask.getContainerNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void containerResetLocation(String palletNo, String sourceArea, String sourceLocation) {

        Criteria criteria = Criteria.forClass(ContainerPathTask.class);
        criteria.setRestriction(Restrictions.and(Restrictions.eq("palletNo", palletNo)));

        containerPathTaskMapper.updateMapByCriteria(MapUtils.put("sourceArea", sourceArea).put("sourceLocation", sourceLocation).getMap(), criteria);
    }

    @Override
    public void containerPathDelete(String containerNo, String sourceLocation) throws Exception {
        List<ContainerPathTask> containerPathTasks = containerPathTaskMapper.findByMap(MapUtils.put("containerNo", containerNo).put("sourceLocation", sourceLocation).getMap(), ContainerPathTask.class);
        if (containerPathTasks.size() > 0) {
            if (containerPathTasks.get(0).getTaskState() != 0) {
                throw new Exception("容器未到位删除失败");

            }
        }
//        containerPathTaskMapper.deleteByCriteria()
    }
}
