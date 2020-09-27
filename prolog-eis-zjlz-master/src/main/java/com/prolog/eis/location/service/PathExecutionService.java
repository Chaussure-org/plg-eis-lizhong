package com.prolog.eis.location.service;

import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;

import java.sql.Timestamp;

public interface PathExecutionService {

    /**
     * 执行rcs-rcs路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     */
    void doRcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行mcs-rcs路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     */
    void doMcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行rcs-mcs路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     */
    void doRcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;
    
    /**
     * 执行rcs-mcs路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     */
    void doMcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 修改下一条任务明细状态和点位
     * @param containerPathTaskDetail
     * @param containerPathTask
     * @param nowTime
     * @throws Exception
     */
    void updateNextContainerPathTaskDetail(ContainerPathTaskDetail containerPathTaskDetail,
                                           ContainerPathTask containerPathTask, Timestamp nowTime) throws Exception;
}
