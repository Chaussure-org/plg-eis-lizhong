package com.prolog.eis.location.service.impl;

import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.dto.rcs.RcsRequestResultDto;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.PathExecutionService;
import com.prolog.eis.location.service.SxMoveStoreService;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.rcs.service.IRCSService;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.util.location.LocationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void doRcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        //找货位
        AgvStoragelocationDTO agvStoragelocationDTO = agvLocationService.findLoacationByArea(containerPathTaskDetailDTO.getNextArea()
                , containerPathTaskDetailDTO.getSourceLocation(), 0);
        if (null == agvStoragelocationDTO) {
            throw new Exception("货位已满");
        }
        containerPathTaskDetailDTO.setNextLocation(agvStoragelocationDTO.getLocationNo());
        String taskId = this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);
        //给rcs发送移动指令
        RcsRequestResultDto rcsRequestResultDto = rcsRequestService.sendTask(taskId, containerPathTaskDetailDTO.getPalletNo()
                , containerPathTaskDetailDTO.getSourceLocation(), agvStoragelocationDTO.getLocationNo(), "1", "1");

        //rcs回传成功后，汇总表状态为20已发送指令,改明细表状态50给设备发送移动指令
        if ("0".equals(rcsRequestResultDto.getCode())) {
            containerPathTaskService.updateContainerPathTask(containerPathTask, containerPathTaskDetailDTO, null
                    , LocationConstants.PATH_TASK_STATE_SEND, LocationConstants.PATH_TASK_DETAIL_STATE_SEND);
        } else {
            //...重发等相关
        }
        //rcs回告到位后改汇总和明细，并判断是否最终任务
    }

    @Override
    public void doMcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        //直接发送一个rcs移动任务即可
        this.doRcsToRcsTask(containerPathTask, containerPathTaskDetailDTO);
    }

    @Override
    public void doRcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        String taskId = this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);
        // TODO 发送mcs指令
    }
    
    @Override
    public void doMcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        String taskId = this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);
        // TODO 发送mcs指令
    }

    @Override
    public void doSasToSasTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        sxMoveStoreService.mcsContainerMove(containerPathTask, containerPathTaskDetailDTO);
    }

    @Override
    public void doSasToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        String taskId = this.updateTaskId(containerPathTask, containerPathTaskDetailDTO);
        // TODO 发送mcs指令
    }

    @Override
    public void doMcsToSasTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception {
        this.doSasToSasTask(containerPathTask, containerPathTaskDetailDTO);
    }

    /**
     * 记录taskId
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
