package com.prolog.eis.location.service.impl;

import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.sas.SasMoveTaskDto;
import com.prolog.eis.dto.store.SxStoreGroupDto;
import com.prolog.eis.dto.store.SxStoreLockDto;
import com.prolog.eis.dto.mcs.McsResultDto;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.dao.StoreAreaMapper;
import com.prolog.eis.location.dao.SxStoreLocationGroupMapper;
import com.prolog.eis.location.dao.SxStoreLocationMapper;
import com.prolog.eis.location.dao.SxStoreMapper;
import com.prolog.eis.location.service.SxMoveStoreService;
import com.prolog.eis.location.service.SxkLocationService;
import com.prolog.eis.mcs.service.IMCSService;
import com.prolog.eis.model.GoodsInfo;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.store.SxStoreLocation;
import com.prolog.eis.model.store.SxStoreLocationGroup;
import com.prolog.eis.sas.service.ISASService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.ListHelper;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class SxMoveStoreServiceImpl implements SxMoveStoreService {

    @Autowired
    private SxStoreMapper sxStoreMapper;
    @Autowired
    private SxkLocationService sxkLocationService;
    @Autowired
    private SxStoreLocationMapper sxStoreLocationMapper;
    @Autowired
    private IMCSService mcsRequestService;
    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;
    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;
    @Autowired
    private SxStoreLocationGroupMapper sxStoreLocationGroupMapper;
    @Autowired
    private StoreAreaMapper storeAreaMapper;
    @Autowired
    private IContainerStoreService containerStoreService;
    @Autowired
    private ISASService sasService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mcsContainerMove(ContainerPathTask containerPathTask,
								 ContainerPathTaskDetailDTO containerPathTaskDetailDTO) {
        //boolean bl = false;

        try {
            //检查移动任务类型
            StoreArea sourceStoreArea = storeAreaMapper.findById(containerPathTaskDetailDTO.getSourceArea(),
					StoreArea.class);
            StoreArea targetStoreArea = storeAreaMapper.findById(containerPathTaskDetailDTO.getNextArea(),
					StoreArea.class);

            int mcsTaskType = this.getMcsTaskType(sourceStoreArea, targetStoreArea);
            switch (mcsTaskType) {
                case 0:
                    //借道任务
                    this.sxkThrough(containerPathTask, containerPathTaskDetailDTO);
                    break;
                case 1:
                    //入库
                    this.sxkInStoreDetail(containerPathTask, containerPathTaskDetailDTO);
                    break;
                case 2:
                    //出库
                    this.sxkOutStore(containerPathTask, containerPathTaskDetailDTO);
                    break;
                case 3:
                    //同区移位
                    this.sxkMoveStore(containerPathTask, containerPathTaskDetailDTO);
                    break;
                default:
                    break;
            }
            //return bl;
        } catch (Exception e) {
            // TODO: handle exception
            //return false;
            int a = 0;
        }
    }

    ;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mcsCallBackStart(ContainerPathTaskDetail containerPathTaskDetail, Timestamp time) {

        try {
            ContainerPathTask containerPathTask = containerPathTaskMapper.findFirstByMap(
                    MapUtils.put("palletNo", containerPathTaskDetail.getPalletNo()).getMap(), ContainerPathTask.class);

            //修改路径任务状态
            this.updateContainerPathTaskStart(containerPathTask.getId(), containerPathTaskDetail.getId(), time);
            //解锁货位组
            this.unlockStartkSxStoreLocation(containerPathTaskDetail);
        } catch (Exception e) {
            // TODO: handle exception
            //return false;
            int a = 0;
        }
        //
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mcsCallBackComplete(ContainerPathTaskDetail containerPathTaskDetail, Timestamp time) {

        try {
            ContainerPathTask containerPathTask = containerPathTaskMapper.findFirstByMap(
                    MapUtils.put("palletNo", containerPathTaskDetail.getPalletNo()).getMap(), ContainerPathTask.class);

            //修改路径任务状态
            this.updateContainerPathTaskComplete(containerPathTask, containerPathTaskDetail, time);
            //解锁货位组
            this.unlockCompletekSxStoreLocation(containerPathTaskDetail);
        } catch (Exception e) {
            // TODO: handle exception
            //return false;
            int a = 0;
        }
        //
    }

    /**
     * 四向库入库
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 路径任务明细
     */
    private void sxkThrough(ContainerPathTask containerPathTask,
							ContainerPathTaskDetailDTO containerPathTaskDetailDTO) {

        try {
            String taskId = containerPathTaskDetailDTO.getTaskId();
            if (StringUtils.isBlank(taskId)) {
                taskId = PrologStringUtils.newGUID();
            }

            sendMoveTask(containerPathTaskDetailDTO.getContainerNo(), taskId, 0,
                    containerPathTaskDetailDTO.getSourceLocation(), null,
                    containerPathTaskDetailDTO.getNextLocation(), null, containerPathTask.getId(),
                    containerPathTaskDetailDTO.getId(), containerPathTaskDetailDTO.getSourceDeviceSystem());
        } catch (Exception e) {
            // TODO: handle exception
            int a = 0;
        }
    }

    /**
     * 四向库入库
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 路径任务明细
     */
    private void sxkInStoreDetail(ContainerPathTask containerPathTask,
								  ContainerPathTaskDetailDTO containerPathTaskDetailDTO) {

        try {
            String taskId = containerPathTaskDetailDTO.getTaskId();
            if (StringUtils.isBlank(taskId)) {
                taskId = PrologStringUtils.newGUID();
            }

            if (StringUtils.isBlank(containerPathTaskDetailDTO.getNextLocation())) {
                //需要自己分货位
                GoodsInfo goodsInfo =
						containerStoreService.findContainerStockInfo(containerPathTaskDetailDTO.getContainerNo());
                //获取商品id
                String taskProperty1 = null;
                String taskProperty2 = null;
                if (goodsInfo != null) {
                    taskProperty1 = containerStoreService.buildTaskProperty1(goodsInfo);
                    taskProperty2 = containerStoreService.buildTaskProperty2(goodsInfo);
                }

                SxStoreLocation targetSxStoreLocation =
						sxkLocationService.findLoacationByArea(containerPathTaskDetailDTO.getNextArea(),
                        0, 0, 0, 0, taskProperty1, taskProperty2);
                if (null == targetSxStoreLocation) {
                    //找不到货位
                    return;
                }

                sendMoveTask(containerPathTaskDetailDTO.getContainerNo(), taskId, 1,
                        containerPathTaskDetailDTO.getSourceLocation(), null,
                        targetSxStoreLocation.getStoreNo(), targetSxStoreLocation.getStoreLocationGroupId(),
                        containerPathTask.getId(), containerPathTaskDetailDTO.getId(),
						containerPathTaskDetailDTO.getSourceDeviceSystem());

                SxStoreLocationGroup sxStoreLocationGroup =
						sxStoreLocationGroupMapper.findById(targetSxStoreLocation.getStoreLocationGroupId(),
								SxStoreLocationGroup.class);
                sxkLocationService.computeLocation(sxStoreLocationGroup);
            } else {
                SxStoreLocation targetSxStoreLocation = sxStoreLocationMapper.findFirstByMap(MapUtils.put("storeNo",
						containerPathTaskDetailDTO.getNextLocation()).getMap(), SxStoreLocation.class);
                this.sendMoveTask(containerPathTaskDetailDTO.getContainerNo(), taskId, 1,
                        containerPathTaskDetailDTO.getSourceLocation(), null,
                        targetSxStoreLocation.getStoreNo(), targetSxStoreLocation.getStoreLocationGroupId(),
                        containerPathTaskDetailDTO.getId(), containerPathTaskDetailDTO.getId(),
                        containerPathTaskDetailDTO.getSourceDeviceSystem());
            }
        } catch (Exception e) {
            // TODO: handle exception
            int a = 0;
        }
    }

    /**
     * 四向库出库任务
     *
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     */
    private void sxkOutStore(ContainerPathTask containerPathTask,
							 ContainerPathTaskDetailDTO containerPathTaskDetailDTO) {

        SxStoreLockDto sxStoreLock = sxStoreMapper.findSxStoreLock(containerPathTask.getContainerNo());
        if (sxStoreLock.getAscentGroupLockState() == 1 || sxStoreLock.getAscentLockState() == 1 || sxStoreLock.getIsLock() == 1) {
            return;
        } else {
            if (sxStoreLock.getDeptNum() == 0) {
                this.carOutLogic(containerPathTask, containerPathTaskDetailDTO);
                //FileLogHelper.WriteLog("checkStore", "容器出库成功，容器号：【"+containerCode+"】");
            } else {
                this.carPassiveMoveLogic(containerPathTask, containerPathTaskDetailDTO);
                //FileLogHelper.WriteLog("checkStore", "容器移位出库成功，容器号：【"+containerCode+"】");
            }
        }
    }

    /**
     * 四向库内移库方法
     *
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     */
    private void sxkMoveStore(ContainerPathTask containerPathTask,
							  ContainerPathTaskDetailDTO containerPathTaskDetailDTO) {

        SxStoreLockDto sxStoreLock = sxStoreMapper.findSxStoreLock(containerPathTask.getContainerNo());
        if (sxStoreLock.getAscentGroupLockState() == 1 || sxStoreLock.getAscentLockState() == 1 || sxStoreLock.getIsLock() == 1) {
            return;
        } else {
            if (sxStoreLock.getDeptNum() == 0) {
                this.carMoveLogic(containerPathTask, containerPathTaskDetailDTO);
                //FileLogHelper.WriteLog("checkStore", "容器出库成功，容器号：【"+containerCode+"】");
            } else {
                this.carPassiveMoveLogic(containerPathTask, containerPathTaskDetailDTO);
                //FileLogHelper.WriteLog("checkStore", "容器移位出库成功，容器号：【"+containerCode+"】");
            }
        }
    }

    private void carOutLogic(ContainerPathTask containerPathTask,
							 ContainerPathTaskDetailDTO containerPathTaskDetailDTO) {
        try {
            String taskId = containerPathTaskDetailDTO.getTaskId();
            if (StringUtils.isBlank(taskId)) {
                taskId = PrologStringUtils.newGUID();
            }

            //当前货位
            SxStoreLocation sourceLocation = sxStoreLocationMapper.findFirstByMap(MapUtils.put("storeNo",
					containerPathTaskDetailDTO.getSourceLocation()).getMap(), SxStoreLocation.class);

            this.sendMoveTask(containerPathTaskDetailDTO.getContainerNo(), taskId, 2,
                    sourceLocation.getStoreNo(), sourceLocation.getStoreLocationGroupId(),
                    containerPathTaskDetailDTO.getNextLocation(), null, containerPathTask.getId(),
                    containerPathTaskDetailDTO.getId(), containerPathTaskDetailDTO.getSourceDeviceSystem());
        } catch (Exception e) {
            // TODO: handle exception
            int a = 0;
        }
    }

    /**
     * 主动移位方法
     *
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     */
    private void carMoveLogic(ContainerPathTask containerPathTask,
							  ContainerPathTaskDetailDTO containerPathTaskDetailDTO) {
        try {
            String taskId = containerPathTaskDetailDTO.getTaskId();
            if (StringUtils.isBlank(taskId)) {
                taskId = PrologStringUtils.newGUID();
            }

            //当前货位
            SxStoreLocation sourceLocation = sxStoreLocationMapper.findFirstByMap(MapUtils.put("storeNo",
					containerPathTaskDetailDTO.getSourceLocation()).getMap(), SxStoreLocation.class);
            SxStoreLocation nextLocation = sxStoreLocationMapper.findFirstByMap(MapUtils.put("storeNo",
					containerPathTaskDetailDTO.getNextLocation()).getMap(), SxStoreLocation.class);

            this.sendMoveTask(containerPathTaskDetailDTO.getContainerNo(), taskId, 3,
                    sourceLocation.getStoreNo(), sourceLocation.getStoreLocationGroupId(),
                    nextLocation.getStoreNo(), nextLocation.getStoreLocationGroupId(),
                    containerPathTask.getId(), containerPathTaskDetailDTO.getId(),
					containerPathTaskDetailDTO.getSourceDeviceSystem());

            SxStoreLocationGroup sxStoreLocationGroup =
					sxStoreLocationGroupMapper.findById(nextLocation.getStoreLocationGroupId(),
							SxStoreLocationGroup.class);
            sxkLocationService.computeLocation(sxStoreLocationGroup);
        } catch (Exception e) {
            // TODO: handle exception
            int a = 0;
        }
    }

    /**
     * 被动移位方法
     *
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     * @throws Exception
     */
    private void carPassiveMoveLogic(ContainerPathTask containerPathTask,
									 ContainerPathTaskDetailDTO containerPathTaskDetailDTO) {

        try {
            SxStoreLocation waitLocation = sxStoreLocationMapper.findFirstByMap(MapUtils.put("storeNo",
					containerPathTaskDetailDTO.getSourceLocation()).getMap(), SxStoreLocation.class);
            List<SxStoreGroupDto> groupDtoList = sxStoreMapper.findStoreGroup(waitLocation.getStoreLocationGroupId());
            // 找出移库数为0的货位，并且库存状态是已上架
            List<SxStoreGroupDto> dtos = ListHelper.where(groupDtoList,
					p -> p.getDeptNum() == 0 && p.getStoreState() == LocationConstants.PATH_TASK_STATE_NOTSTARTED);

            //查找需要移动的托盘的起点
            SxStoreGroupDto moveSxStoreGroupDto = null;
            if (dtos.size() == 1) {
                SxStoreGroupDto dto = dtos.get(0);

                moveSxStoreGroupDto = dto;
            } else if (dtos.size() == 2) {
                SxStoreGroupDto dto1 = dtos.get(0);
                SxStoreGroupDto dto2 = dtos.get(1);
                int numOne = Math.abs(waitLocation.getLocationIndex() - dto1.getLocationIndex());
                int numTwo = Math.abs(waitLocation.getLocationIndex() - dto2.getLocationIndex());

                if (numTwo > numOne) {
                    //dto1比较近
                    moveSxStoreGroupDto = dto1;
                } else {
                    //dto2比较近
                    moveSxStoreGroupDto = dto2;
                }
            } else {
                throw new Exception("货位【" + waitLocation.getStoreLocationGroupId() + "】资料有误！同货位组有库存托盘移位数为0的货位大于2个！");
            }

            //移位分货位
            GoodsInfo goodsInfo = containerStoreService.findContainerStockInfo(moveSxStoreGroupDto.getContainerNo());
            //获取商品库存属性
            String taskProperty1 = containerStoreService.buildTaskProperty1(goodsInfo);
            String taskProperty2 = containerStoreService.buildTaskProperty2(goodsInfo);

            SxStoreLocation targetSxStoreLocation =
					sxkLocationService.findLoacationByArea(containerPathTaskDetailDTO.getSourceArea(),
                    moveSxStoreGroupDto.getX(), moveSxStoreGroupDto.getY(), 0, 0, taskProperty1, taskProperty2);
            if (null == targetSxStoreLocation) {
                //找不到货位
                return;
            }

            //修改移动托盘
            ContainerPathTask moveContainerPathTask = containerPathTaskMapper.findFirstByMap(MapUtils.put(
            		"containerNo", moveSxStoreGroupDto.getContainerNo()).put("taskState",
					LocationConstants.PATH_TASK_STATE_NOTSTARTED).getMap(), ContainerPathTask.class);
            if (null == moveContainerPathTask) {
                return;
            }
            ContainerPathTaskDetail moveContainerPathTaskDetail =
					containerPathTaskDetailMapper.findFirstByMap(MapUtils.put("containerNo",
							moveSxStoreGroupDto.getContainerNo()).put("taskState",
							LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE).getMap(), ContainerPathTaskDetail.class);
            if (null == moveContainerPathTaskDetail) {
                return;
            }

            String taskId = containerPathTaskDetailDTO.getTaskId();
            if (StringUtils.isBlank(taskId)) {
                taskId = PrologStringUtils.newGUID();
            }

            //发送托盘移位任务
            this.sendMoveTask(moveSxStoreGroupDto.getContainerNo(), taskId, 3,
                    moveSxStoreGroupDto.getStoreNo(), moveSxStoreGroupDto.getStoreLocationGroupId(),
                    targetSxStoreLocation.getStoreNo(), targetSxStoreLocation.getStoreLocationGroupId(),
                    moveContainerPathTask.getId(), moveContainerPathTaskDetail.getId(),
					containerPathTaskDetailDTO.getSourceDeviceSystem());

            SxStoreLocationGroup sxStoreLocationGroup =
					sxStoreLocationGroupMapper.findById(targetSxStoreLocation.getStoreLocationGroupId(),
							SxStoreLocationGroup.class);
            sxkLocationService.computeLocation(sxStoreLocationGroup);
        } catch (Exception e) {
            // TODO: handle exception
            int a = 0;
        }
    }

    /**
     * mcs移动方法
     *
     * @param containerNo        容器号
     * @param taskId             任务号
     * @param taskType           任务类型
     * @param sourceStoreNo      起点点位
     * @param sourceGroupId      起点货位组
     * @param nextStoreNo        目标点位
     * @param nextGroupId        目的货位组
     * @param pathTaskId         路径任务Id
     * @param pathTaskDetailId   路径明细Id
     * @param sourceDeviceSystem
     */
    private void sendMoveTask(String containerNo, String taskId, int taskType, String sourceStoreNo,
                              Integer sourceGroupId, String nextStoreNo, Integer nextGroupId, int pathTaskId,
							  int pathTaskDetailId, String sourceDeviceSystem) {
        //发送mcs移动指令
        try {
            if (LocationConstants.DEVICE_SYSTEM_MCS.equals(sourceDeviceSystem)) {
                McsMoveTaskDto mcsMoveTaskDto = new McsMoveTaskDto(taskId, taskType, containerNo, sourceStoreNo,
                        nextStoreNo, "", "99", 0);
                McsResultDto mcsResultDto = mcsRequestService.mcsContainerMove(mcsMoveTaskDto);
                if (mcsResultDto.isRet()) {
                    //发送成功
                    //修改路径状态
                    this.updateContainerPathTask(pathTaskId, LocationConstants.PATH_TASK_STATE_SEND, pathTaskDetailId
							, nextStoreNo, taskId, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);

                    this.lockSxStoreLocation(taskType, sourceGroupId, nextGroupId);
                }
            } else if (LocationConstants.DEVICE_SYSTEM_SAS.equals(sourceDeviceSystem)) {
                SasMoveTaskDto sasMoveTaskDto = new SasMoveTaskDto(taskId, taskType, containerNo,
                        sourceStoreNo, nextStoreNo, "", "99", 0);

                RestMessage<String> result = sasService.sendContainerTask(sasMoveTaskDto);
                if (result.isSuccess()) {
                    //发送成功
                    //修改路径状态
                    this.updateContainerPathTask(pathTaskId, LocationConstants.PATH_TASK_STATE_SEND, pathTaskDetailId
							, nextStoreNo, taskId, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);

                    this.lockSxStoreLocation(taskType, sourceGroupId, nextGroupId);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            containerPathTaskDetailMapper.updateMapById(pathTaskId,
                    MapUtils.put("taskId", taskId).getMap(),
                    ContainerPathTaskDetail.class);

            //记录异常
        }
    }

    private int getMcsTaskType(StoreArea sourceStoreArea, StoreArea targetStoreArea) throws Exception {
        if (LocationConstants.DEVICE_SYSTEM_RCS.equals(sourceStoreArea.getDeviceSystem()) || LocationConstants.DEVICE_SYSTEM_RCS.equals(targetStoreArea.getDeviceSystem())) {
            throw new Exception(String.format("起点区域:%s,终点区域:%s不是%s", sourceStoreArea, targetStoreArea,
					LocationConstants.DEVICE_SYSTEM_RCS));
        }

        if (sourceStoreArea.getAreaType() == LocationConstants.AREA_TYPE_AREA && targetStoreArea.getAreaType() == LocationConstants.AREA_TYPE_AREA) {
            //货位到货位对于mcs属于同层移位任务
            return 3;
        } else if (sourceStoreArea.getAreaType() == LocationConstants.AREA_TYPE_AREA && targetStoreArea.getAreaType() == LocationConstants.AREA_TYPE_POINT) {
            //货位到接驳口对于mcs属于出库任务
            return 2;
        } else if (sourceStoreArea.getAreaType() == LocationConstants.AREA_TYPE_POINT && targetStoreArea.getAreaType() == 20) {
            //借道任务
            return 0;
        } else if (sourceStoreArea.getAreaType() == LocationConstants.AREA_TYPE_POINT && targetStoreArea.getAreaType() == LocationConstants.AREA_TYPE_AREA) {
            //接驳口到货位对于mcs属于入库任务
            return 1;
        }

        throw new Exception("areaType异常");
    }

    private void updateContainerPathTask(int taskHzId, int taskHzState, int taskDetailId, String nextStoreNo,
										 String taskId, int taskDetailState) throws Exception {
        containerPathTaskMapper.updateMapById(taskHzId,
                MapUtils.put("updateTime", PrologDateUtils.parseObject(new Date()))
                        .put("taskState", taskHzState).getMap(), ContainerPathTask.class);

        containerPathTaskDetailMapper.updateMapById(taskDetailId,
                MapUtils.put("nextLocation", nextStoreNo)
                        .put("taskId", taskId)
                        .put("taskState", taskDetailState).getMap(),
                ContainerPathTaskDetail.class);
    }

    private void updateContainerPathTaskStart(int taskHzId, int taskDetailId, Timestamp time) throws Exception {

        containerPathTaskMapper.updateMapById(taskHzId,
                MapUtils.put("updateTime", time)
                        .put("taskState", LocationConstants.PATH_TASK_STATE_START).getMap(), ContainerPathTask.class);

        containerPathTaskDetailMapper.updateMapById(taskDetailId,
                MapUtils.put("updateTime", time)
                        .put("taskState", LocationConstants.PATH_TASK_DETAIL_STATE_START).getMap(),
                ContainerPathTaskDetail.class);


    }

    private void updateContainerPathTaskComplete(ContainerPathTask containerPathTask,
												 ContainerPathTaskDetail containerPathTaskDetail, Timestamp time) throws Exception {

        //判断当前的任务节点是否为最后一个节点
        ContainerPathTaskDetail nextContainerPathTaskDetail = containerPathTaskDetailMapper.findFirstByMap(
                MapUtils.put("palletNo", containerPathTaskDetail.getPalletNo())
                        .put("containerNo", containerPathTaskDetail.getContainerNo())
                        .put("taskState", LocationConstants.PATH_TASK_DETAIL_STATE_NOTSTARTED)
                        .put("sortIndex", containerPathTaskDetail.getSortIndex() + 1).getMap(),
				ContainerPathTaskDetail.class);

        if (null == nextContainerPathTaskDetail) {
            //属于最后一个节点
            //后续添加移动日志表

            containerPathTaskDetailMapper.updateMapById(containerPathTaskDetail.getId(),
                    MapUtils.put("sourceArea", containerPathTaskDetail.getNextArea())
                            .put("sourceLocation", containerPathTaskDetail.getNextLocation())
                            .put("taskState", LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE)
                            .put("arriveTime", time)
                            .put("sortIndex", 1).put("taskId",null).getMap(),
                    ContainerPathTaskDetail.class);

            //判断整个任务是否完成
            if (containerPathTaskDetail.getNextArea().equals(containerPathTask.getTargetArea())) {
                //整个任务完成
                containerPathTaskMapper.updateMapById(containerPathTask.getId(),
                        MapUtils.put("sourceArea", containerPathTaskDetail.getNextArea())
                                .put("sourceLocation", containerPathTaskDetail.getNextLocation())
                                .put("targetLocation", containerPathTaskDetail.getNextLocation())
                                .put("taskState", LocationConstants.PATH_TASK_STATE_NOTSTARTED)
                                .put("taskType", LocationConstants.PATH_TASK_TYPE_NONE)
                                .put("updateTime", time).getMap(),
                        ContainerPathTask.class);
            } else {
                //整个任务未完成
                containerPathTaskMapper.updateMapById(containerPathTask.getId(),
                        MapUtils.put("sourceArea", containerPathTaskDetail.getNextArea())
                                .put("sourceLocation", containerPathTaskDetail.getNextLocation())
                                .put("taskState", LocationConstants.PATH_TASK_STATE_TOBESENT)
                                .put("updateTime", time).getMap(),
                        ContainerPathTask.class);
            }
        } else {
            //不属于最后一个节点
            //后续添加移动日志表
            containerPathTaskDetailMapper.deleteById(containerPathTaskDetail.getId(), ContainerPathTaskDetail.class);

            containerPathTaskDetailMapper.updateMapById(nextContainerPathTaskDetail.getId(),
                    MapUtils.put("taskState", LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE)
                            .put("arriveTime", time).getMap(), ContainerPathTaskDetail.class);

            containerPathTaskMapper.updateMapById(containerPathTask.getId(),
                    MapUtils.put("sourceArea", containerPathTaskDetail.getNextArea())
                            .put("sourceLocation", containerPathTaskDetail.getNextLocation())
                            .put("taskState", LocationConstants.PATH_TASK_STATE_TOBESENT)
                            .put("updateTime", time).getMap(),
                    ContainerPathTask.class);
        }
    }

    private void lockSxStoreLocation(int taskType, Integer sourceGroupId, Integer targetGroupId) {

        if (taskType == 1) {
            //入库锁目标货位
            sxStoreLocationGroupMapper.updateMapById(targetGroupId,
                    MapUtils.put("ascentLockState", LocationConstants.GROUP_ASCENTLOCK_LOCK).getMap(),
					SxStoreLocationGroup.class);
        } else if (taskType == 2) {
            //出库锁原货位
            sxStoreLocationGroupMapper.updateMapById(sourceGroupId,
                    MapUtils.put("ascentLockState", LocationConstants.GROUP_ASCENTLOCK_LOCK).getMap(),
					SxStoreLocationGroup.class);
        } else if (taskType == 3) {
            //出库锁原货位
            sxStoreLocationGroupMapper.updateMapById(sourceGroupId,
                    MapUtils.put("ascentLockState", LocationConstants.GROUP_ASCENTLOCK_LOCK).getMap(),
					SxStoreLocationGroup.class);

            sxStoreLocationGroupMapper.updateMapById(targetGroupId,
                    MapUtils.put("ascentLockState", LocationConstants.GROUP_ASCENTLOCK_LOCK).getMap(),
					SxStoreLocationGroup.class);
        }
    }

    private void unlockStartkSxStoreLocation(ContainerPathTaskDetail containerPathTaskDetail) throws Exception {

        //检查移动任务类型
        StoreArea sourceStoreArea = storeAreaMapper.findById(containerPathTaskDetail.getSourceArea(), StoreArea.class);
        StoreArea targetStoreArea = storeAreaMapper.findById(containerPathTaskDetail.getNextArea(), StoreArea.class);

        int mcsTaskType = this.getMcsTaskType(sourceStoreArea, targetStoreArea);

        if (mcsTaskType == 2) {
            //出库解锁原货位
            SxStoreLocation sourceLocation = sxStoreLocationMapper.findFirstByMap(MapUtils.put("storeNo", containerPathTaskDetail.getSourceLocation()).getMap(), SxStoreLocation.class);

            sxStoreLocationGroupMapper.updateMapById(sourceLocation.getStoreLocationGroupId(),
                    MapUtils.put("ascentLockState", LocationConstants.GROUP_ASCENTLOCK_UNLOCK).getMap(), SxStoreLocationGroup.class);

            SxStoreLocationGroup sxStoreLocationGroup = sxStoreLocationGroupMapper.findById(sourceLocation.getStoreLocationGroupId(), SxStoreLocationGroup.class);
            sxkLocationService.computeLocation(sxStoreLocationGroup);
        } else if (mcsTaskType == 3) {
            //移位解锁原货位
            //出库解锁原货位
            SxStoreLocation sourceLocation = sxStoreLocationMapper.findFirstByMap(MapUtils.put("storeNo", containerPathTaskDetail.getSourceLocation()).getMap(), SxStoreLocation.class);

            sxStoreLocationGroupMapper.updateMapById(sourceLocation.getStoreLocationGroupId(),
                    MapUtils.put("ascentLockState", LocationConstants.GROUP_ASCENTLOCK_UNLOCK).getMap(), SxStoreLocationGroup.class);

            SxStoreLocationGroup sxStoreLocationGroup = sxStoreLocationGroupMapper.findById(sourceLocation.getStoreLocationGroupId(), SxStoreLocationGroup.class);
            sxkLocationService.computeLocation(sxStoreLocationGroup);
        }
    }

    private void unlockCompletekSxStoreLocation(ContainerPathTaskDetail containerPathTaskDetail) throws Exception {

        //检查移动任务类型
        StoreArea sourceStoreArea = storeAreaMapper.findById(containerPathTaskDetail.getSourceArea(), StoreArea.class);
        StoreArea targetStoreArea = storeAreaMapper.findById(containerPathTaskDetail.getNextArea(), StoreArea.class);

        int mcsTaskType = this.getMcsTaskType(sourceStoreArea, targetStoreArea);
        if (mcsTaskType == 1) {
            //解锁目标货位组
            SxStoreLocation nextLocation = sxStoreLocationMapper.findFirstByMap(MapUtils.put("storeNo", containerPathTaskDetail.getNextLocation()).getMap(), SxStoreLocation.class);
            sxStoreLocationGroupMapper.updateMapById(nextLocation.getStoreLocationGroupId(),
                    MapUtils.put("ascentLockState", LocationConstants.GROUP_ASCENTLOCK_UNLOCK).getMap(), SxStoreLocationGroup.class);
        } else if (mcsTaskType == 2) {
            //判断起始点是否存在漏解锁情况，兼容开始状态未解锁时，结束任务将原货位组解锁
            this.reUnlockStartkSxStoreLocation(containerPathTaskDetail.getSourceLocation());
        } else if (mcsTaskType == 3) {
            //判断起始点是否存在漏解锁情况，兼容开始状态未解锁时，结束任务将原货位组解锁
            this.reUnlockStartkSxStoreLocation(containerPathTaskDetail.getSourceLocation());

            //解锁目标货位组
            SxStoreLocation nextLocation = sxStoreLocationMapper.findFirstByMap(MapUtils.put("storeNo", containerPathTaskDetail.getNextLocation()).getMap(), SxStoreLocation.class);
            sxStoreLocationGroupMapper.updateMapById(nextLocation.getStoreLocationGroupId(),
                    MapUtils.put("ascentLockState", LocationConstants.GROUP_ASCENTLOCK_UNLOCK).getMap(), SxStoreLocationGroup.class);
        }
    }

    /**
     * 判断起始点是否存在漏解锁情况，兼容开始状态未解锁时，结束任务将原货位组解锁
     *
     * @param storeNo
     * @throws Exception
     */
    private void reUnlockStartkSxStoreLocation(String storeNo) throws Exception {

        SxStoreLocation nextLocation = sxStoreLocationMapper.findFirstByMap(MapUtils.put("storeNo", storeNo).getMap(), SxStoreLocation.class);

        SxStoreLocationGroup sxStoreLocationGroup = sxStoreLocationGroupMapper.findById(nextLocation.getStoreLocationGroupId(), SxStoreLocationGroup.class);
        if (null == sxStoreLocationGroup) {
            throw new Exception("货位组" + nextLocation.getStoreLocationGroupId() + "异常");
        }

        //判断起点是否锁上
        if (sxStoreLocationGroup.getAscentLockState() == LocationConstants.GROUP_ASCENTLOCK_LOCK) {
            //判断是否需要解锁
            Integer groupId = sxStoreLocationGroupMapper.checkGroupLock(nextLocation.getStoreLocationGroupId(), LocationConstants.PATH_TASK_STATE_SEND);
            if (null == groupId) {
                //需要解锁
                sxStoreLocationGroupMapper.updateMapById(nextLocation.getStoreLocationGroupId(),
                        MapUtils.put("ascentLockState", LocationConstants.GROUP_ASCENTLOCK_UNLOCK).getMap(), SxStoreLocationGroup.class);

                sxkLocationService.computeLocation(sxStoreLocationGroup);
            }
        }
    }
}
