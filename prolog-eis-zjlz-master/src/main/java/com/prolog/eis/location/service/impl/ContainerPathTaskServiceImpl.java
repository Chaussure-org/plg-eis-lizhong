package com.prolog.eis.location.service.impl;

import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.dto.location.StoreAreaContainerCountDTO;
import com.prolog.eis.dto.location.StoreAreaPriorityDTO;
import com.prolog.eis.dto.location.TaskCountDto;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskHistoryMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.base.Goods;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.ContainerPathTaskHistory;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.util.ListHelper;
import com.prolog.eis.util.PowerCalculation;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: wuxl
 * @create: 2020-09-23 12:27
 * @Version: V1.0
 */
@Service
public class ContainerPathTaskServiceImpl implements ContainerPathTaskService {

    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;
    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;
    @Autowired
    private ContainerPathTaskHistoryMapper containerPathTaskHistoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNextContainerPathTaskDetail(ContainerPathTaskDetail containerPathTaskDetail, ContainerPathTask containerPathTask, Timestamp nowTime) throws Exception {
        //查询 下一条任务是否存在  add sunpp
        List<ContainerPathTaskDetail> containerPathTaskDetailList = containerPathTaskDetailMapper.findByMap(
                MapUtils.put("palletNo", containerPathTaskDetail.getPalletNo())
                        .put("sortIndex", containerPathTaskDetail.getSortIndex() + 1).getMap()
                , ContainerPathTaskDetail.class);

        // 查询有没有下一条任务
        // 没有则修改汇总状态为0未开始,当前任务起点和终点改为一致
        if (CollectionUtils.isEmpty(containerPathTaskDetailList)) {
            containerPathTaskMapper.updateMapById(containerPathTask.getId()
                    , MapUtils.put("sourceArea", containerPathTaskDetail.getNextArea())
                            .put("sourceLocation", containerPathTaskDetail.getNextLocation())
                            .put("taskState", LocationConstants.PATH_TASK_STATE_NOTSTARTED)
                            .put("updateTime", nowTime).getMap()
                    , ContainerPathTask.class);

            containerPathTaskDetailMapper.updateMapById(containerPathTaskDetail.getId()
                    , MapUtils.put("sourceArea", containerPathTaskDetail.getNextArea())
                            .put("sourceLocation", containerPathTaskDetail.getNextLocation())
                            .put("taskState", LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE)
                            .put("taskId", null)
                            .put("updateTime", nowTime).getMap()
                    , ContainerPathTaskDetail.class);
        } else {
            // 有则改下一条明细状态和点位,清除当前任务
            //更新hz 表状态 add sun
            containerPathTaskMapper.updateMapById(containerPathTask.getId()
                    , MapUtils.put("taskState", LocationConstants.PATH_TASK_STATE_TOBESENT)
                            .put("updateTime", nowTime).getMap()
                    , ContainerPathTask.class);

            //更新 明细表状态  add sun
            ContainerPathTaskDetail nextContainerPathTaskDetail = containerPathTaskDetailList.get(0);
            containerPathTaskDetailMapper.updateMapById(nextContainerPathTaskDetail.getId()
                    , MapUtils.put("sourceLocation", containerPathTaskDetail.getNextLocation())
                            .put("taskState", LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE)
                            .put("arriveTime", nowTime)
                            .put("updateTime", nowTime).getMap()
                    , ContainerPathTaskDetail.class);

            containerPathTaskDetailMapper.deleteById(containerPathTaskDetail.getId(), ContainerPathTaskDetail.class);
        }
    }

    /**
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     * @param palletContainerPathTaskDetailDTO
     * @param hzTaskState                      10 容器任务状态待发送
     * @param mxTaskState
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContainerPathTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO
            , ContainerPathTaskDetailDTO palletContainerPathTaskDetailDTO, Integer hzTaskState, Integer mxTaskState) throws Exception {
        Timestamp nowTime = PrologDateUtils.parseObject(new Date());

        containerPathTaskMapper.updateMapById(containerPathTask.getId()
                , MapUtils.put("taskState", hzTaskState)
                        .put("updateTime", nowTime).getMap()
                , ContainerPathTask.class);

        ContainerPathTaskDetail containerPathTaskDetail = new ContainerPathTaskDetail();
        BeanUtils.copyProperties(containerPathTaskDetailDTO, containerPathTaskDetail);
        containerPathTaskDetail.setTaskState(mxTaskState);
        containerPathTaskDetail.setUpdateTime(nowTime);
        //根据状态判断插入哪个时间字段
        switch (mxTaskState) {
            case LocationConstants.PATH_TASK_DETAIL_STATE_SEND:
                containerPathTaskDetail.setSendTime(nowTime);
                break;
            case LocationConstants.PATH_TASK_DETAIL_STATE_APPLYPALLET:
                containerPathTaskDetail.setApplyTime(nowTime);
                break;
            case LocationConstants.PATH_TASK_DETAIL_STATE_PALLETINPLACE:
                containerPathTaskDetail.setPalletArriveTime(nowTime);
                break;
            case LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE:
                containerPathTaskDetail.setArriveTime(nowTime);
                break;
            default:
        }
        containerPathTaskDetailMapper.update(containerPathTaskDetail);

        //如传入载具明细，则是申请载具流程，需要生成一条rcs移载任务
        if (null != palletContainerPathTaskDetailDTO) {
            ContainerPathTaskDetail palletContainerPathTaskDetail = new ContainerPathTaskDetail();
            BeanUtils.copyProperties(palletContainerPathTaskDetailDTO, palletContainerPathTaskDetail);
            ContainerPathTask palletContainerPathTask = containerPathTaskMapper.getRequestPallet(palletContainerPathTaskDetail.getPalletNo());
            containerPathTaskMapper.updateMapById(palletContainerPathTask.getId(),
                    MapUtils.put("targetArea", containerPathTaskDetailDTO.getNextArea())
                            .put("targetLocation", containerPathTaskDetailDTO.getNextLocation())
                            .put("taskState", hzTaskState)
                            .put("updateTime", nowTime).getMap()
                    , ContainerPathTask.class);

            containerPathTaskDetailMapper.updateMapById(palletContainerPathTaskDetail.getId(),
                    MapUtils.put("taskId", containerPathTaskDetailDTO.getTaskId())
                            .put("taskState", mxTaskState)
                            .put("nextArea", containerPathTaskDetailDTO.getNextArea())
                            .put("nextLocation", containerPathTaskDetailDTO.getNextLocation())
                            .put("updateTime", nowTime).getMap()
                    , ContainerPathTaskDetail.class);
        }
    }

    @Override
    public ContainerPathTask getContainerPathTask(ContainerPathTaskDetail containerPathTaskDetail) throws Exception {
        List<ContainerPathTask> containerPathTaskList = containerPathTaskMapper.findByMap(
                MapUtils.put("palletNo", containerPathTaskDetail.getPalletNo()).getMap(), ContainerPathTask.class);
        if (CollectionUtils.isEmpty(containerPathTaskList)) {
            throw new Exception("任务不存在");
        }
        return containerPathTaskList.get(0);
    }

    @Override
    public void saveContainerPathTaskHistory(ContainerPathTaskDetail containerPathTaskDetail, Timestamp nowTime) throws Exception {
        ContainerPathTaskHistory containerPathTaskHistory = new ContainerPathTaskHistory();
        BeanUtils.copyProperties(containerPathTaskDetail, containerPathTaskHistory);
        containerPathTaskHistory.setEndTime(nowTime);
        containerPathTaskHistoryMapper.save(containerPathTaskHistory);
    }

    /**
     * 区域排序
     *
     * @param spList
     * @return
     */
    @Override
    public List<StoreArea> findBestStoreAreaList(List<StoreAreaPriorityDTO> spList) {

        List<StoreArea> result = new ArrayList<>();

        //查询所有的容器任务
        List<ContainerPathTaskDetail> taskDetails = containerPathTaskDetailMapper.listRequestContainer();
        //获取区域任务数
        List<StoreAreaContainerCountDTO> areaCount = containerPathTaskMapper.getStoreAreaContainerCount();

        //LocationConstants.DEVICE_SYSTEM_MCS.equals(sourceDeviceSystem) && LocationConstants.DEVICE_SYSTEM_RCS.equals(nextDeviceSystem
        //按优先级排序
        spList.sort((p1, p2) -> {
            return p2.getPriority() - p1.getPriority();
        });
        //按优先级分组
        Map<Integer, List<StoreAreaPriorityDTO>> mapGroup = ListHelper.buildGroupDictionary(spList, p -> p.getPriority());
        for (List<StoreAreaPriorityDTO> group : mapGroup.values()) {
            //拿到需要按任务数分组的集合
            List<StoreAreaPriorityDTO> tasks = ListHelper.where(group, p -> p.getTaskPower() > 0);
            if (!tasks.isEmpty()) {
                //计算区域任务数
                List<StoreAreaPriorityDTO> taskPriorityList = this.getContainerTaskPriority(tasks, taskDetails);
                result.addAll(ListHelper.select(taskPriorityList, p -> p.getStoreArea()));
            }

            //计算剩下的区域排序
            List<StoreAreaPriorityDTO> noTasks = ListHelper.where(group, p -> p.getTaskPower() == 0);
            List<StoreAreaPriorityDTO> containerCountPriorityList = this.getContainerCountPriority(noTasks, areaCount);
            result.addAll(ListHelper.select(containerCountPriorityList, p -> p.getStoreArea()));
        }

        return result;
    }

    @Override
    public List<ContainerPathTask> findByMap(Map map) throws Exception {
        return containerPathTaskMapper.findByMap(map, ContainerPathTask.class);
    }

    /**
     * 更新路径任务
     *
     * @param containerPathTask 路径任务
     */
    @Override
    public void updateTask(ContainerPathTask containerPathTask) {
        containerPathTaskMapper.update(containerPathTask);
    }

    @Override
    public List<ContainerPathTask> getContainerByPath(String storeArea) throws Exception {
        List<ContainerPathTask> containerPathTasks = containerPathTaskMapper.findByMap(MapUtils.put("sourceArea", storeArea).
                put("targetArea", storeArea).getMap(), ContainerPathTask.class);
        return containerPathTasks;
    }

    /**
     * 半成品入库分配堆垛机
     *
     * @return
     */
    @Override
    public String computeAreaIn(Goods goods) {
        List<TaskCountDto> taskCountDtos = containerPathTaskMapper.findInTaskCount();
        //04区3个   03区2个  02 01 区一个
        List<TaskCountDto> mcs04 =
                taskCountDtos.stream().filter(taskCountDto -> "MCS04".equals(taskCountDto.getAreaNo()) && taskCountDto.getTaskCount() > 2).collect(Collectors.toList());
        if (mcs04.size() == 0) {
            return goods.getGoodsOneType().equals("包材")?"":"MCS04";
        }
        List<TaskCountDto> mcs03 =
                taskCountDtos.stream().filter(taskCountDto -> "MCS03".equals(taskCountDto.getAreaNo()) && taskCountDto.getTaskCount() > 1).collect(Collectors.toList());
        if (mcs03.size() == 0) {
            return goods.getGoodsOneType().equals("包材")?"":"MCS03";
        }
        List<TaskCountDto> mcs02 =
                taskCountDtos.stream().filter(taskCountDto -> "MCS02".equals(taskCountDto.getAreaNo()) && taskCountDto.getTaskCount() > 0).collect(Collectors.toList());
        if (mcs02.size() == 0) {
            return goods.getGoodsOneType().equals("包材")?"":"MCS02";
        }
        List<TaskCountDto> mcs01 =
                taskCountDtos.stream().filter(taskCountDto -> "MCS01".equals(taskCountDto.getAreaNo()) && taskCountDto.getTaskCount() > 0).collect(Collectors.toList());
        if (mcs01.size() == 0) {
            return goods.getGoodsOneType().equals("包材")?"":"MCS01";
        }
        return taskCountDtos.get(0).getAreaNo();
    }

    @Override
    public void updatePathTaskTypeByContainer(String containerNo, int type) {
        Criteria ctr = Criteria.forClass(ContainerStore.class);
        ctr.setRestriction(Restrictions.eq("containerNo", containerNo));
        containerPathTaskMapper.updateMapByCriteria(MapUtils.put("taskType", type).getMap(), ctr);
    }

    @Override
    public void deletePathByContainer(String containerNo) {
        containerPathTaskMapper.deleteByMap(MapUtils.put("containerNo", containerNo).getMap(), ContainerPathTask.class);
        containerPathTaskDetailMapper.deleteByMap(MapUtils.put("containerNo", containerNo).getMap(), ContainerPathTaskDetail.class);
    }

    @Override
    public void saveContainerPath(ContainerPathTask containerPathTask) {
        containerPathTaskMapper.save(containerPathTask);
    }

    private List<StoreAreaPriorityDTO> getContainerTaskPriority(List<StoreAreaPriorityDTO> storeAreaPriorityList, List<ContainerPathTaskDetail> taskDetails) {

        List<StoreAreaPriorityDTO> result = new ArrayList<>();

        Map<String, Integer> mapCount = new HashMap<>();
        for (ContainerPathTaskDetail taskDetail : taskDetails) {
            if (mapCount.containsKey(taskDetail.getSourceArea())) {
                mapCount.put(taskDetail.getSourceArea(), mapCount.get(taskDetail.getSourceArea()) + 1);
            } else {
                mapCount.put(taskDetail.getSourceArea(), 1);
            }
            if (mapCount.containsKey(taskDetail.getNextArea())) {
                mapCount.put(taskDetail.getNextArea(), mapCount.get(taskDetail.getNextArea()) + 1);
            } else {
                mapCount.put(taskDetail.getNextArea(), 1);
            }
        }
        Map<String, Integer> mapPower = new HashMap<>();
        for (StoreAreaPriorityDTO storeAreaPriorityDTO : storeAreaPriorityList) {
            mapPower.put(storeAreaPriorityDTO.getStoreArea().getAreaNo(), storeAreaPriorityDTO.getTaskPower());
        }

        List<String> minAreas = PowerCalculation.minPower(mapCount, mapPower);

        for (String minArea : minAreas) {
            StoreAreaPriorityDTO storeAreaPriorityDTO = ListHelper.firstOrDefault(storeAreaPriorityList, p -> minArea.equals(p.getStoreArea().getAreaNo()));
            if (null != storeAreaPriorityDTO) {
                result.add(storeAreaPriorityDTO);
            }
        }

        return result;
    }

    private List<StoreAreaPriorityDTO> getContainerCountPriority(List<StoreAreaPriorityDTO> storeAreaPriorityList, List<StoreAreaContainerCountDTO> areaCount) {

        List<StoreAreaPriorityDTO> result = new ArrayList<>();

        Map<String, Integer> mapCount = new HashMap<>();
        for (StoreAreaContainerCountDTO storeAreaContainerCountDTO : areaCount) {
            mapCount.put(storeAreaContainerCountDTO.getAreaNo(), storeAreaContainerCountDTO.getContainerCount());
        }
        Map<String, Integer> mapPower = new HashMap<>();
        for (StoreAreaPriorityDTO storeAreaPriorityDTO : storeAreaPriorityList) {
            mapPower.put(storeAreaPriorityDTO.getStoreArea().getAreaNo(), storeAreaPriorityDTO.getPriority());
        }
        List<String> minAreas = PowerCalculation.minPower(mapCount, mapPower);

        for (String minArea : minAreas) {
            StoreAreaPriorityDTO storeAreaPriorityDTO = ListHelper.firstOrDefault(storeAreaPriorityList, p -> minArea.equals(p.getStoreArea().getAreaNo()));
            if (null != storeAreaPriorityDTO) {
                result.add(storeAreaPriorityDTO);
            }
        }

        return result;
    }
}
