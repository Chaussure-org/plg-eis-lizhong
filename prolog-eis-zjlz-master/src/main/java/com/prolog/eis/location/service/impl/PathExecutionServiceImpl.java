package com.prolog.eis.location.service.impl;

import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.dto.rcs.RcsRequestResultDto;
import com.prolog.eis.dto.rcs.RcsTaskDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.enums.PointChangeEnum;
import com.prolog.eis.location.service.*;
import com.prolog.eis.model.PointLocation;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.rcs.service.IRCSService;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.eis.wcs.service.IWCSService;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 13622
 */
@Service
public class PathExecutionServiceImpl implements PathExecutionService {
    @Autowired
    private AgvLocationService agvLocationService;
    @Autowired
    private IRCSService rcsRequestService;
    @Autowired
    private SxMoveStoreService sxMoveStoreService;
    @Autowired
    private ContainerPathTaskService containerPathTaskService;
    @Autowired
    private SxkLocationService sxkLocationService;
    @Autowired
    private IWCSService wcsService;

    @Override
    public void doRcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        //找货位
        String location = containerPathTask.getTargetLocation();
        if (StringUtils.isBlank(location)) {
            AgvStoragelocationDTO agvStoragelocationDTO = agvLocationService.findLoacationByArea(containerPathTaskDetailDTO.getNextArea()
                    , containerPathTaskDetailDTO.getSourceLocation(), 0);

            if (null == agvStoragelocationDTO) {
                throw new Exception("货位已满");
            }
            location = agvStoragelocationDTO.getLocationNo();
        }
        containerPathTaskDetailDTO.setNextLocation(location);
        String taskId = this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);
        //给rcs发送移动指令
        RcsTaskDto rcsTaskDto=new RcsTaskDto(taskId, containerPathTaskDetailDTO.getPalletNo()
                , containerPathTaskDetailDTO.getSourceLocation(), location, "1", "1");
        RcsRequestResultDto rcsRequestResultDto = rcsRequestService.sendTask(rcsTaskDto);

        //rcs回传成功后，汇总表状态为20已发送指令,改明细表状态50给设备发送移动指令
        if ("200".equals(rcsRequestResultDto.getCode())) {
            containerPathTaskService.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                    , LocationConstants.PATH_TASK_STATE_SEND, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);
        } else {
            //...重发等相关
        }
        //rcs回告到位后改汇总和明细，并判断是否最终任务


    }


    @Override
    public void doMcsToWcsTask(ContainerPathTask containerPathTask,
                               ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("mcs to wcs");
        //此处只用发到出库接驳口，然后更改此条的状态
        containerPathTaskDetailDTO.setNextDeviceSystem(LocationConstants.DEVICE_SYSTEM_MCS);
        containerPathTaskDetailDTO.setNextLocation(PointChangeEnum.getTarget(containerPathTaskDetailDTO.getNextLocation()));
        sxMoveStoreService.mcsContainerMove(containerPathTask,containerPathTaskDetailDTO);
    }

    @Override
    public void doWcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("wcs to mcs");
        this.updateTaskId(containerPathTask,containerPathTaskDetailDTO);
        ContainerPathTaskDetailDTO containerPathTaskDetailDTO1 = new ContainerPathTaskDetailDTO();
        BeanUtils.copyProperties(containerPathTaskDetailDTO,containerPathTaskDetailDTO1);
        containerPathTaskDetailDTO1.setSourceDeviceSystem(LocationConstants.DEVICE_SYSTEM_MCS);
        containerPathTaskDetailDTO1.setSourceLocation(PointChangeEnum.getPoint(containerPathTaskDetailDTO.getSourceLocation()));
        sxMoveStoreService.mcsContainerMove(containerPathTask,containerPathTaskDetailDTO1);
        containerPathTaskDetailDTO.setNextLocation(PointChangeEnum.getCorr(containerPathTaskDetailDTO.getSourceLocation()));
        WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(containerPathTaskDetailDTO.getTaskId(),
                containerPathTaskDetailDTO.getSourceLocation(),
                containerPathTaskDetailDTO.getNextLocation(),containerPathTaskDetailDTO.getContainerNo(),5);
        wcsService.lineMove(wcsLineMoveDto);
    }

    @Override
    public void doWcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("wcs to rcs");
        containerPathTaskService.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                , LocationConstants.PATH_TASK_STATE_SEND, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);
    }

    @Override
    public void doRcsToWcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("rcs to wcs");
        containerPathTaskService.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                , LocationConstants.PATH_TASK_STATE_SEND, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);
    }

    @Override
    public void doSasToWcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("sas to wcs");
        containerPathTaskDetailDTO.setNextDeviceSystem(LocationConstants.DEVICE_SYSTEM_SAS);
        containerPathTaskDetailDTO.setNextLocation(PointChangeEnum.getTarget(containerPathTaskDetailDTO.getNextLocation()));
        sxMoveStoreService.mcsContainerMove(containerPathTask,containerPathTaskDetailDTO);
    }

    @Override
    public void doWcsToSasTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("wcs to sas");
        this.updateTaskId(containerPathTask,containerPathTaskDetailDTO);
        containerPathTaskDetailDTO.setSourceDeviceSystem(LocationConstants.DEVICE_SYSTEM_SAS);
        containerPathTaskDetailDTO.setSourceLocation(PointChangeEnum.getPoint(containerPathTaskDetailDTO.getSourceLocation()));
        sxMoveStoreService.mcsContainerMove(containerPathTask,containerPathTaskDetailDTO);
        WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(containerPathTaskDetailDTO.getTaskId(),
                containerPathTaskDetailDTO.getSourceLocation(),
                containerPathTaskDetailDTO.getNextLocation(),containerPathTaskDetailDTO.getContainerNo(),5);
        wcsService.lineMove(wcsLineMoveDto);
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
        //确定路径任务号，汇总表状态为10待发送任务，改明细表状态0到位
        containerPathTaskService.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                , LocationConstants.PATH_TASK_STATE_TOBESENT, LocationConstants.PATH_TASK_DETAIL_STATE_INPLACE);
        return taskId;
    }
}
