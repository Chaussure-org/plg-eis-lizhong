package com.prolog.eis.location.service.impl;

import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.dto.rcs.RcsRequestResultDto;
import com.prolog.eis.dto.rcs.RcsTaskDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.enums.BcrPointEnum;
import com.prolog.eis.enums.ConstantEnum;
import com.prolog.eis.enums.PointChangeEnum;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.PathExecutionService;
import com.prolog.eis.location.service.SxMoveStoreService;
import com.prolog.eis.location.service.SxkLocationService;
import com.prolog.eis.log.dao.SasLogMapper;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.log.SasLog;
import com.prolog.eis.rcs.service.IRcsService;
import com.prolog.eis.util.PrologDateUtils;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.eis.wcs.service.IWcsService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.reflections.Store;
import org.springframework.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 13622
 */
@Service
@Slf4j
public class PathExecutionServiceImpl implements PathExecutionService {
    @Autowired
    private AgvLocationService agvLocationService;
    @Autowired
    private IRcsService rcsRequestService;
    @Autowired
    private SxMoveStoreService sxMoveStoreService;
    @Autowired
    private ContainerPathTaskService containerPathTaskService;
    @Autowired
    private SxkLocationService sxkLocationService;

    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;
    @Autowired
    private IWcsService wcsService;
    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;

    @Override
    public void doRcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        //找货位
        String location = containerPathTask.getTargetLocation();
        if (StringUtils.isBlank(location)) {
            AgvStoragelocationDTO agvStoragelocationDTO = agvLocationService.findLoacationByArea(containerPathTaskDetailDTO.getNextArea()
                    , containerPathTaskDetailDTO.getSourceLocation(), 0);

            if (null == agvStoragelocationDTO) {
                log.error(String.format("载具{%s}/容器{%s}申请货位失败！", containerPathTask.getPalletNo(), containerPathTask.getContainerNo()));
                return;
            }
            location = agvStoragelocationDTO.getLocationNo();
        }
        containerPathTaskDetailDTO.setNextLocation(location);
        String taskId = this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);
        //给rcs发送移动指令
        try {
            RcsTaskDto rcsTaskDto = new RcsTaskDto(taskId, containerPathTaskDetailDTO.getPalletNo()
                    , containerPathTaskDetailDTO.getSourceLocation(), location, LocationConstants.RCS_TASK_TYPE_TRANSPORT, "1");
            RcsRequestResultDto rcsRequestResultDto = rcsRequestService.sendTask(rcsTaskDto);

            //rcs回传成功后，汇总表状态为20已发送指令,改明细表状态50给设备发送移动指令
            if ("0".equals(rcsRequestResultDto.getCode())) {
                containerPathTaskService.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                        , LocationConstants.PATH_TASK_STATE_SEND, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);
                if (StoreArea.RCS01.equals(containerPathTaskDetailDTO.getNextArea())){
                    //上锁
                    agvLocationService.updateTaskLockByLocationNo(location, AgvStoragelocation.TASK_LOCK);
                }
            } else {
                //...重发等相关
            }
        } catch (Exception e) {
            containerPathTaskDetailMapper.updateMapById(
                    containerPathTaskDetailDTO.getId()
                    , MapUtils.put("taskId", null)
                            .put("nextLocation", null).getMap()
                    , ContainerPathTaskDetail.class);
            log.error(String.format("载具{%s}/容器{%s}rcs-rcs发送rcs指令失败[%s]！", containerPathTask.getPalletNo(), containerPathTask.getContainerNo()), e.getMessage());
        }
    }


    @Override
    public void doMcsToWcsTask(ContainerPathTask containerPathTask,
                               ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("mcs to wcs");
        /**
         //此处只用发到出库接驳口，然后更改此条的状态
        containerPathTaskDetailDTO.setNextDeviceSystem(LocationConstants.DEVICE_SYSTEM_MCS);
        //通过 出库wcs 点位 获取 mcs 点位
        containerPathTaskDetailDTO.setNextLocation(BcrPointEnum.findMcsLocation(containerPathTaskDetailDTO.getNextLocation()));
        sxMoveStoreService.mcsContainerMove(containerPathTask, containerPathTaskDetailDTO);
         */

    }

    @Override
    public void doWcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {

        //此处仅执行一个点位转换
        System.out.println("wcs to mcs");
        ContainerPathTaskDetail containerPathTaskDetail =
                containerPathTaskDetailMapper.findById(containerPathTaskDetailDTO.getId(), ContainerPathTaskDetail.class);
        containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetail, containerPathTask,
                PrologDateUtils.parseObject(new Date()));
        this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);

    }

    @Override
    public void doWcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        //点位转换
        System.out.println("wcs to rcs");
        ContainerPathTaskDetail containerPathTaskDetail = containerPathTaskDetailMapper.findById(containerPathTaskDetailDTO.getId(),
                ContainerPathTaskDetail.class);
        //此 任务直接删除，更新下一个 点位左边为  Rcs-->Rcs
        containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetail, containerPathTask,
                PrologDateUtils.parseObject(new Date()));
    }

    @Override
    public void doRcsToWcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("rcs to wcs");
        //在agv h回告那块处理这个逻辑
        ContainerPathTaskDetail containerPathTaskDetail = containerPathTaskDetailMapper.findById(containerPathTaskDetailDTO.getId(),
                ContainerPathTaskDetail.class);
        containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetail, containerPathTask,
                PrologDateUtils.parseObject(new Date()));
    }

    @Override
    public void doSasToWcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("=====================sas to wcs=========================");
        //点位转换
        ContainerPathTaskDetail containerPathTaskDetail =
                containerPathTaskDetailMapper.findById(containerPathTaskDetailDTO.getId(), ContainerPathTaskDetail.class);
        containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetail, containerPathTask,
                PrologDateUtils.parseObject(new Date()));
//        containerPathTaskDetailDTO.setNextDeviceSystem(LocationConstants.DEVICE_SYSTEM_SAS);
//        containerPathTaskDetailDTO.setNextLocation(PointChangeEnum.getTarget(containerPathTaskDetailDTO.getNextLocation()));
//        sxMoveStoreService.mcsContainerMove(containerPathTask, containerPathTaskDetailDTO);
    }

    @Override
    public void doWcsToSasTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("wcs to sas");
        //改为路径的更新和删除
        ContainerPathTaskDetail containerPathTaskDetail =
                containerPathTaskDetailMapper.findById(containerPathTaskDetailDTO.getId(), ContainerPathTaskDetail.class);
        containerPathTaskService.updateNextContainerPathTaskDetail(containerPathTaskDetail, containerPathTask,
                PrologDateUtils.parseObject(new Date()));
        this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);

/*
        //更新 1.hz表任务状态为10  2.更新明细表的时间
        this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);
        ContainerPathTaskDetailDTO containerPathTaskDetailDTO1 = new ContainerPathTaskDetailDTO();
        BeanUtils.copyProperties(containerPathTaskDetailDTO, containerPathTaskDetailDTO1);
        containerPathTaskDetailDTO1.setSourceDeviceSystem(LocationConstants.DEVICE_SYSTEM_SAS);
        //给SAS发任务 入库任务
        sxMoveStoreService.mcsContainerMove(containerPathTask, containerPathTaskDetailDTO1);*/
    }

    @Override
    public void doSasToSasTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("sas to sas");
        //改为路径的更新和删除

        //更新 1.hz表任务状态为10  2.更新明细表的时间
        this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);
        ContainerPathTaskDetailDTO containerPathTaskDetailDTO1 = new ContainerPathTaskDetailDTO();
        BeanUtils.copyProperties(containerPathTaskDetailDTO, containerPathTaskDetailDTO1);
        containerPathTaskDetailDTO1.setSourceDeviceSystem(LocationConstants.DEVICE_SYSTEM_SAS);
        //containerPathTaskDetailDTO1.setSourceLocation(PointChangeEnum.getPoint(containerPathTaskDetailDTO.getSourceLocation()));
        //给SAS发任务 入库任务
        sxMoveStoreService.mcsContainerMove(containerPathTask, containerPathTaskDetailDTO1);
        new Thread();
    }
    /**
     * 执行wcs-wcs路径任务(借道)
     * MODEFIED 现在修改为bcr到bcr
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     */
    @Override
    public void doWcsToWcsTask(ContainerPathTask containerPathTask,
                               ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("wcs to wcs");
        String location = containerPathTaskDetailDTO.getSourceLocation();
        //二楼的  出库 无需发送路径任务，但入库需要发送 行走任务
        if (!ConstantEnum.secondOutPoints.contains(location)) {
            this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);
            WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(containerPathTaskDetailDTO.getTaskId(),
                    containerPathTaskDetailDTO.getSourceLocation(),
                    containerPathTaskDetailDTO.getNextLocation(), containerPathTaskDetailDTO.getContainerNo(), 5);
            RestMessage<String> result = wcsService.lineMove(wcsLineMoveDto, 0);
            if (result.isSuccess()) {
                //输送线接收任务 成功，更改hz 表状态为 20
                containerPathTaskMapper.updateMapById(containerPathTask.getId(),
                        MapUtils.put("updateTime", PrologDateUtils.parseObject(new Date()))
                                .put("taskState", LocationConstants.PATH_TASK_STATE_SEND).getMap(), ContainerPathTask.class);
                //更改明细表状态为 50
                containerPathTaskDetailMapper.updateMapById(containerPathTaskDetailDTO.getId(),
                        MapUtils.put("taskState", LocationConstants.PATH_TASK_DETAIL_STATE_SEND).getMap(),
                        ContainerPathTaskDetail.class);
            }

        }

    }

    /**
     * 执行mcs-mcs路径任务
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     * @throws Exception
     */
    @Override
    public void doMcsToMcsTask(ContainerPathTask containerPathTask,
                               ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        //发送mcs移动任务
        System.out.println("mcs to mcs");
        sxMoveStoreService.mcsContainerMove(containerPathTask, containerPathTaskDetailDTO);
    }


    /**
     * 记录taskId
     *
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     * @return
     * @throws Exception
     */
    private String updateTaskId(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        String taskId = PrologStringUtils.newGUID();
        containerPathTaskDetailDTO.setTaskId(taskId);
        //确定路径任务号，update汇总表状态为10-待发送任务，改明细表状态0-到位
        containerPathTaskService.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                , LocationConstants.PATH_TASK_STATE_TOBESENT, LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE);
        return taskId;
    }


}
