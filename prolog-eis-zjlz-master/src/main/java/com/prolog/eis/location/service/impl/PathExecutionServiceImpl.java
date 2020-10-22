package com.prolog.eis.location.service.impl;

import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.dto.rcs.RcsRequestResultDto;
import com.prolog.eis.dto.rcs.RcsTaskDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.enums.PointChangeEnum;
import com.prolog.eis.location.dao.ContainerPathTaskDetailMapper;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.PathExecutionService;
import com.prolog.eis.location.service.SxMoveStoreService;
import com.prolog.eis.location.service.SxkLocationService;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.rcs.service.IRCSService;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.eis.wcs.service.IWCSService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 13622
 */
@Service
@Slf4j
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
    @Autowired
    private ContainerPathTaskDetailMapper containerPathTaskDetailMapper;

    @Override
    public void doRcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        //找货位
        String location = containerPathTask.getTargetLocation();
        if (StringUtils.isBlank(location)) {
            AgvStoragelocationDTO agvStoragelocationDTO = agvLocationService.findLoacationByArea(containerPathTaskDetailDTO.getNextArea()
                    , containerPathTaskDetailDTO.getSourceLocation(), 0);

            if(null == agvStoragelocationDTO){
                log.error(String.format("载具{%s}/容器{%s}申请货位失败！", containerPathTask.getPalletNo(), containerPathTask.getContainerNo()));
                return;
            }
            location = agvStoragelocationDTO.getLocationNo();
        }
        containerPathTaskDetailDTO.setNextLocation(location);
        String taskId = this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);
        //给rcs发送移动指令
        try {
            RcsTaskDto rcsTaskDto=new RcsTaskDto(taskId, containerPathTaskDetailDTO.getPalletNo()
                    , containerPathTaskDetailDTO.getSourceLocation(), location, "1", "1");
            RcsRequestResultDto rcsRequestResultDto = rcsRequestService.sendTask(rcsTaskDto);

            //rcs回传成功后，汇总表状态为20已发送指令,改明细表状态50给设备发送移动指令
            if ("0".equals(rcsRequestResultDto.getCode())) {
                containerPathTaskService.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                        , LocationConstants.PATH_TASK_STATE_SEND, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);
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
        //此处只用发到出库接驳口，然后更改此条的状态
        containerPathTaskDetailDTO.setNextDeviceSystem(LocationConstants.DEVICE_SYSTEM_MCS);
        containerPathTaskDetailDTO.setNextLocation(PointChangeEnum.getTarget(containerPathTaskDetailDTO.getNextLocation()));
        sxMoveStoreService.mcsContainerMove(containerPathTask,containerPathTaskDetailDTO);
    }

    @Override
    public void doWcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        System.out.println("wcs to mcs");
        containerPathTaskDetailDTO.setSourceDeviceSystem(LocationConstants.DEVICE_SYSTEM_MCS);
        containerPathTaskDetailDTO.setSourceLocation(PointChangeEnum.getPoint(containerPathTaskDetailDTO.getSourceLocation()));
        sxMoveStoreService.mcsContainerMove(containerPathTask,containerPathTaskDetailDTO);
        String taskId = PrologStringUtils.newGUID();
        WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId,containerPathTaskDetailDTO.getSourceLocation(),
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
        containerPathTaskDetailDTO.setSourceDeviceSystem(LocationConstants.DEVICE_SYSTEM_SAS);
        containerPathTaskDetailDTO.setSourceLocation(PointChangeEnum.getPoint(containerPathTaskDetailDTO.getSourceLocation()));
        sxMoveStoreService.mcsContainerMove(containerPathTask,containerPathTaskDetailDTO);
        String taskId = PrologStringUtils.newGUID();
        WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId,containerPathTaskDetailDTO.getSourceLocation(),
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
