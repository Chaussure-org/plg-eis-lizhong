package com.prolog.eis.location.service;

import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.model.location.ContainerPathTask;

public interface PathExecutionService {
    //SAS TO WCS
    //WCS TO SAS

    /**
     * 执行rcs-rcs路径任务(agv区)
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     * @throws Exception
     */
    void doRcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;


    /**
     * 执行mcs-wcs路径任务(堆垛机到输送线)
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     * @throws Exception
     */
    void doMcsToWcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行wcs-mcs路径任务(输送线到堆垛机)
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     * @throws Exception
     */
    void doWcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行wcs-rcs路径任务(输送线到agv)
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     * @throws Exception
     */
    void doWcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行rcs-wcs路径任务(agv到输送线)
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     * @throws Exception
     */
    void doRcsToWcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行sas-wcs路径任务(提升机到输送线)
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     * @throws Exception
     */
    void doSasToWcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行wcs-sas路径任务(输送线到提升机)
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     * @throws Exception
     */
    void doWcsToSasTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;


    /**
     * 执行wcs-wcs路径任务(借道)
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     */
    void doWcsToWcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行mcs-mcs路径任务
     *
     * @param containerPathTask          路径任务
     * @param containerPathTaskDetailDTO 详情实体
     * @throws Exception
     */
    void doMcsToMcsTask(ContainerPathTask containerPathTask,
                        ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * SAS 到 SAS 路径
     *
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     * @throws Exception
     */
    void doSasToSasTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;
}
