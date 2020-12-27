package com.prolog.eis.location.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.dto.location.PathDTO;
import com.prolog.eis.dto.location.StoreAreaDirectionDTO;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.dao.StoreAreaDirectionMapper;
import com.prolog.eis.location.dao.StoreAreaMapper;
import com.prolog.eis.location.service.LocationService;
import com.prolog.eis.location.service.PathExecutionService;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.location.StoreAreaDirection;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.eis.util.location.LocationUtils;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author: wuxl
 * @create: 2020-08-25 15:52
 * @Version: V1.0
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;
    @Autowired
    private StoreAreaMapper storeAreaMapper;
    @Autowired
    private StoreAreaDirectionMapper storeAreaDirectionMapper;
    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;
    @Autowired
    private PathExecutionService pathExecutionService;

    private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doContainerPathTaskByContainer(String palletNo, String containerNo) throws Exception {
        /**
         * 1.所有区域 2.区域方向 3.任务状态未开始的集合 4.生成路径明细
         */
        //获取区域集合
        List<StoreArea> storeAreaList = storeAreaMapper.findByMap(Maps.newHashMap(), StoreArea.class);
        if (CollectionUtils.isEmpty(storeAreaList)) {
            logger.error("区域表没有初始化");
            throw new Exception("请初始化区域表！");
        }
        //获取区域方向集合
        List<StoreAreaDirection> storeAreaDirectionList = storeAreaDirectionMapper.findByMap(Maps.newHashMap(),
                StoreAreaDirection.class);
        if (CollectionUtils.isEmpty(storeAreaDirectionList)) {
            logger.error("区域方向表没有初始化");
            throw new Exception("请初始化区域方向表！");
        }
        //参数为Null则遍历容器任务，否则查询单个容器任务
        List<ContainerPathTask> containerPathTaskList = containerPathTaskMapper.listContainerPathTasks(palletNo,
                containerNo, LocationConstants.PATH_TASK_STATE_NOTSTARTED);
        if (CollectionUtils.isEmpty(containerPathTaskList)) {
//            throw new Exception("当前没有容器任务！");
            logger.info("当前没有容器任务");
            return;
        }
        for (ContainerPathTask containerPathTask : containerPathTaskList) {
            this.addContainerPathTaskDetail(containerPathTask, storeAreaList, storeAreaDirectionList);
        }
        logger.info("生成容器任务结束");
    }

    @Override
    public synchronized void doPathExecutionByContainer(String palletNo, String containerNo) throws Exception {
        List<ContainerPathTask> containerPathTaskList = containerPathTaskMapper.listContainerPathTasks(palletNo,
                containerNo, LocationConstants.PATH_TASK_STATE_TOBESENT);

        if (CollectionUtils.isEmpty(containerPathTaskList)) {
            return;
        }
        for (ContainerPathTask containerPathTask : containerPathTaskList) {
            List<ContainerPathTaskDetailDTO> containerPathTaskDetailList =
                    containerPathTaskDetailMapper.listContainerPathTaskDetais(containerPathTask.getPalletNo()
                            , containerPathTask.getContainerNo(), LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE);
            if (CollectionUtils.isEmpty(containerPathTaskDetailList)) {
                throw new Exception(containerPathTask.getContainerNo()+"没有任务明细，无法执行");
            }
            String sourceDeviceSystem = containerPathTaskDetailList.get(0).getSourceDeviceSystem();
            String nextDeviceSystem = containerPathTaskDetailList.get(0).getNextDeviceSystem();
            //设备厂商先直接写死
            if (LocationConstants.DEVICE_SYSTEM_MCS.equals(sourceDeviceSystem) && LocationConstants.DEVICE_SYSTEM_WCS.equals(nextDeviceSystem)) {
                //MCS TO WCS
                pathExecutionService.doMcsToWcsTask(containerPathTask, containerPathTaskDetailList.get(0));
            } else if (LocationConstants.DEVICE_SYSTEM_WCS.equals(sourceDeviceSystem) && LocationConstants.DEVICE_SYSTEM_MCS.equals(nextDeviceSystem)) {
                //WCS TO MCS
                pathExecutionService.doWcsToMcsTask(containerPathTask, containerPathTaskDetailList.get(0));
            } else if (LocationConstants.DEVICE_SYSTEM_WCS.equals(sourceDeviceSystem) && LocationConstants.DEVICE_SYSTEM_RCS.equals(nextDeviceSystem)) {
                //WCS TO RCS
                pathExecutionService.doWcsToRcsTask(containerPathTask, containerPathTaskDetailList.get(0));
            } else if (LocationConstants.DEVICE_SYSTEM_RCS.equals(sourceDeviceSystem) && LocationConstants.DEVICE_SYSTEM_WCS.equals(nextDeviceSystem)) {
                //RCS TO WCS
                pathExecutionService.doRcsToWcsTask(containerPathTask, containerPathTaskDetailList.get(0));
            } else if (LocationConstants.DEVICE_SYSTEM_RCS.equals(sourceDeviceSystem) && LocationConstants.DEVICE_SYSTEM_RCS.equals(nextDeviceSystem)) {
                //RCS TO RCS
                pathExecutionService.doRcsToRcsTask(containerPathTask, containerPathTaskDetailList.get(0));
            } else if (LocationConstants.DEVICE_SYSTEM_SAS.equals(sourceDeviceSystem) && LocationConstants.DEVICE_SYSTEM_WCS.equals(nextDeviceSystem)) {
                //SAS TO WCS
                pathExecutionService.doSasToWcsTask(containerPathTask, containerPathTaskDetailList.get(0));
            } else if (LocationConstants.DEVICE_SYSTEM_WCS.equals(sourceDeviceSystem) && LocationConstants.DEVICE_SYSTEM_SAS.equals(nextDeviceSystem)) {
                //WCS TO SAS
                pathExecutionService.doWcsToSasTask(containerPathTask, containerPathTaskDetailList.get(0));
            } else if (LocationConstants.DEVICE_SYSTEM_WCS.equals(sourceDeviceSystem) && LocationConstants.DEVICE_SYSTEM_WCS.equals(nextDeviceSystem)) {
                //WCS TO WCS
                pathExecutionService.doWcsToWcsTask(containerPathTask, containerPathTaskDetailList.get(0));
            } else if (LocationConstants.DEVICE_SYSTEM_MCS.equals(sourceDeviceSystem) && LocationConstants.DEVICE_SYSTEM_MCS.equals(nextDeviceSystem)) {
                pathExecutionService.doMcsToMcsTask(containerPathTask,containerPathTaskDetailList.get(0));
            }
        }
    }

    @Override
    public void doContainerPathTaskAndExecutionByContainer(String palletNo, String containerNo) throws Exception {
        this.doContainerPathTaskByContainer(palletNo, containerNo);
        this.doPathExecutionByContainer(palletNo, containerNo);
    }

    /**
     * 生成容器任务明细
     *
     * @param containerPathTask 路径任务汇总
     * @throws Exception
     */
    private void addContainerPathTaskDetail(ContainerPathTask containerPathTask, List<StoreArea> storeAreaList,
                                            List<StoreAreaDirection> storeAreaDirectionList) throws Exception {
        List<PathDTO> pathDTOList = this.getPathForContainerTask(containerPathTask, storeAreaList,
                storeAreaDirectionList);
        if (CollectionUtils.isEmpty(pathDTOList)) {
            logger.info("容器路径生成失败，没有可执行的路径");
            return;
        }
        //路径点集合
        List<ContainerPathTaskDetail> containerPathTaskDetailList = Lists.newArrayList();
        ContainerPathTaskDetail containerPathTaskDetail = null;

        logger.info("开始生成容器路径明细");
        //第一条路径，如果是滞后任务，则这个任务所有路径均为滞后任务路径
        boolean isMaxCount = pathDTOList.get(0).isMaxCount();
        //全部为滞后任务，进行逻辑判断
        if (isMaxCount) {
            for (PathDTO pathDTO : pathDTOList) {
                int i = 0;
                for (StoreAreaDirectionDTO storeAreaDirectionDTO : pathDTO.getStoreAreaDirectionDTOList()) {
                    //预占容器数
                    int preemptCount = storeAreaDirectionDTO.getPreemptCount();
                    //到位容器数
                    int placeCount = storeAreaDirectionDTO.getPlaceCount();
                    //已到达的最大容器数
                    int realCount = preemptCount + placeCount;
                    //该区域最大容器数
                    int maxCount = storeAreaDirectionDTO.getMaxCount();
                    //起点是否允许暂存
                    int sourceTemporaryArea = storeAreaDirectionDTO.getSourceTemporaryArea();
                    //终点是否允许暂存
                    int targetTemporaryArea = storeAreaDirectionDTO.getTargetTemporaryArea();
                    //现有任务中接驳点容器数量
                    int jointPointCount = storeAreaDirectionDTO.getJointPointCount();
                    if (jointPointCount >= maxCount) {
                        break;
                    }

                    if (realCount < maxCount  //该区域是否已达到最大托盘容量
                            || (realCount >= maxCount && preemptCount < maxCount)  //该区域预占的托盘数量是否达到最大托盘容量
                            || (realCount >= maxCount && preemptCount >= maxCount && sourceTemporaryArea == 0 && targetTemporaryArea == 1)) {   //该区域是否可暂存
                        containerPathTaskDetail = this.setContainerPathTaskDetail(containerPathTask
                                , storeAreaDirectionDTO.getSourceAreaNo(), storeAreaDirectionDTO.getSourceLocationNo()
                                , storeAreaDirectionDTO.getTargetAreaNo(),
                                storeAreaDirectionDTO.getTargetLocationNo(), i++);
                        containerPathTaskDetailList.add(containerPathTaskDetail);
                        if (realCount >= maxCount) {
                            break;
                        }
                    }
                }
            }
        } else {
            //正常可执行路径任务，直接取第一条最优路径存表
            int i = 0;
            for (StoreAreaDirectionDTO storeAreaDirectionDTO : pathDTOList.get(0).getStoreAreaDirectionDTOList()) {
                containerPathTaskDetail = this.setContainerPathTaskDetail(containerPathTask
                        , storeAreaDirectionDTO.getSourceAreaNo(), storeAreaDirectionDTO.getSourceLocationNo()
                        , storeAreaDirectionDTO.getTargetAreaNo(), storeAreaDirectionDTO.getTargetLocationNo(), i++);
                containerPathTaskDetailList.add(containerPathTaskDetail);
            }

        }
        // 该区域所有路径都不满足，直接存一条起点为当前位，终点为空的数据
        if (CollectionUtils.isEmpty(containerPathTaskDetailList) && isMaxCount) {
            containerPathTaskDetail = this.setContainerPathTaskDetail(containerPathTask
                    , containerPathTask.getSourceArea(), null, null
                    , null, 0);
            containerPathTaskDetailList.add(containerPathTaskDetail);
        }
        if (!CollectionUtils.isEmpty(containerPathTaskDetailList)) {
            containerPathTask.setTaskState(LocationConstants.PATH_TASK_STATE_TOBESENT);
            containerPathTask.setUpdateTime(PrologDateUtils.parseObject(new Date()));
            //更新路径汇总表状态
            containerPathTaskMapper.update(containerPathTask);
            //删除这个容器下面的任务明细，重新生成
            containerPathTaskDetailMapper.deleteByMap(MapUtils.put("containerNo", containerPathTask.getContainerNo()).getMap(), ContainerPathTaskDetail.class);
            containerPathTaskDetailMapper.saveBatch(containerPathTaskDetailList);
        }
        logger.info("容器路径明细生成结束");
    }

    /**
     * 生成容器任务路径
     *
     * @param containerPathTask 路径任务汇总
     * @return
     * @throws Exception
     */
    private List<PathDTO> getPathForContainerTask(ContainerPathTask containerPathTask, List<StoreArea> storeAreaList,
                                                  List<StoreAreaDirection> storeAreaDirectionList) throws Exception {
        if (StringUtils.isEmpty(containerPathTask.getPalletNo()) || StringUtils.isEmpty(containerPathTask.getContainerNo())) {
            logger.error("载具编号或容器编号为空");
            throw new Exception("载具编号或容器编号为空！");
        }
        logger.info("开始计算容器路径");
        //起始区域
        String sourceArea = containerPathTask.getSourceArea();
        //终点区域
        String targetArea = containerPathTask.getTargetArea();
        //限高
        int actualHeight = containerPathTask.getActualHeight();
        //传入区域点集合
        storeAreaList.forEach(storeArea -> {
            LocationUtils.vertexList.put(storeArea.getAreaNo(), null);
            LocationUtils.states.put(storeArea.getAreaNo(), false);
        });
        //传入区域方向点集合
        storeAreaDirectionList.forEach(storeAreaDirection ->
                LocationUtils.addPathDTO(storeAreaDirection.getSourceAreaNo(), storeAreaDirection.getTargetAreaNo())
        );
        //查询出所有可能的路径
        List<String> locations = LocationUtils.visit(sourceArea, targetArea);
        //逻辑排除一些路径
        return this.getPathValidate(locations, actualHeight, containerPathTask);
    }

    /**
     * 计算总步长并进行其他相关判断逻辑
     *
     * @return
     */
    private List<PathDTO> getPathValidate(List<String> locations, int actualHeight,
                                          ContainerPathTask containerPathTask) {
        if (CollectionUtils.isEmpty(locations)) {
            return Lists.newArrayList();
        }
        List<PathDTO> pathDTOList = Lists.newArrayList();
        List<PathDTO> pathDTOZhList = Lists.newArrayList();
        locations.forEach(location -> {
            PathDTO pathDTO = new PathDTO();
            List<StoreAreaDirectionDTO> storeAreaDirectionDTOList = Lists.newArrayList();
            int pathStepSum = 0;
            boolean isMax = false;
            boolean isMaxCount = false;
            List<String> paths = Splitter.on("->").trimResults().omitEmptyStrings().splitToList(location);
            int pathsLength = paths.size();
            for (int i = 0; i < pathsLength - 1; i++) {
                String sourceAreaNo = paths.get(i);
                String targetAreaNo = paths.get(i + 1);
                //查询该路径相关信息
                List<StoreAreaDirectionDTO> storeAreaDirectionDTOS =
                        storeAreaDirectionMapper.listStoreAreaDirectionsByParam(sourceAreaNo, targetAreaNo,
                                containerPathTask.getContainerNo());
                //高度限制，容量限制
                if (CollectionUtils.isEmpty(storeAreaDirectionDTOS)
                        || storeAreaDirectionDTOS.get(0).getMaxHeight() < actualHeight
                        || storeAreaDirectionDTOS.get(0).getPlaceCount() >= storeAreaDirectionDTOS.get(0).getMaxCount()) {
                    isMax = true;
                    break;
                }
                //计算步长
                pathStepSum += storeAreaDirectionDTOS.get(0).getPathStep();
                //区域最大容器数逻辑
                int realCount =
                        storeAreaDirectionDTOS.get(0).getPreemptCount() + storeAreaDirectionDTOS.get(0).getPlaceCount();
                if (realCount >= storeAreaDirectionDTOS.get(0).getMaxCount()
                        || storeAreaDirectionDTOS.get(0).getJointPointCount() >= storeAreaDirectionDTOS.get(0).getMaxCount()) {
                    isMaxCount = true;
                }
                StoreAreaDirectionDTO storeAreaDirectionDTO = storeAreaDirectionDTOS.get(0);
                //如果是第一条，则直接从汇总表取起点点位
                if (i == 0) {
                    storeAreaDirectionDTO.setSourceLocationNo(containerPathTask.getSourceLocation());
                }
                storeAreaDirectionDTOList.add(storeAreaDirectionDTO);
            }
            //超过限高,实际占用限制
            if (isMax) {
                return;
            }
            pathDTO.setStoreAreaDirectionDTOList(storeAreaDirectionDTOList);
            pathDTO.setPathStepSum(pathStepSum);
            pathDTO.setPathString(location);
            //是否超过最大容器数
            if (isMaxCount) {
                pathDTO.setMaxCount(true);
                pathDTOZhList.add(pathDTO);
            } else {
                pathDTO.setMaxCount(false);
                pathDTOList.add(pathDTO);
            }
        });
        //根据步长排序
        Collections.sort(pathDTOList, Comparator.comparing(PathDTO::getPathStepSum));
        Collections.sort(pathDTOZhList, Comparator.comparing(PathDTO::getPathStepSum));
        pathDTOList.addAll(pathDTOZhList);
        logger.info("容器路径计算结束");
        return pathDTOList;
    }

    /**
     * 容器路径明细赋值
     *
     * @param containerPathTask 路径任务汇总
     * @param sourceAreaNo      起点区域
     * @param sourceLocationNo  起点点位
     * @param targetAreaNo      终点区域
     * @param targetLocationNo  终点点位
     * @param i
     * @return
     * @throws Exception
     */
    private ContainerPathTaskDetail setContainerPathTaskDetail(ContainerPathTask containerPathTask, String sourceAreaNo
            , String sourceLocationNo, String targetAreaNo, String targetLocationNo, int i) throws Exception {
        ContainerPathTaskDetail containerPathTaskDetail = new ContainerPathTaskDetail();
        containerPathTaskDetail.setPalletNo(containerPathTask.getPalletNo());
        containerPathTaskDetail.setContainerNo(containerPathTask.getContainerNo());
        containerPathTaskDetail.setSourceArea(sourceAreaNo);
        containerPathTaskDetail.setSourceLocation(sourceLocationNo);
        containerPathTaskDetail.setNextArea(targetAreaNo);
        containerPathTaskDetail.setNextLocation(targetLocationNo);
        containerPathTaskDetail.setTaskState(i == 0 ? LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE :
                LocationConstants.PATH_TASK_DETAIL_STATE_NOTSTARTED);
        containerPathTaskDetail.setSortIndex(i + 1);
        containerPathTaskDetail.setArriveTime(i == 0 ? PrologDateUtils.parseObject(new Date()) : null);
        containerPathTaskDetail.setCreateTime(PrologDateUtils.parseObject(new Date()));
        return containerPathTaskDetail;
    }
}
