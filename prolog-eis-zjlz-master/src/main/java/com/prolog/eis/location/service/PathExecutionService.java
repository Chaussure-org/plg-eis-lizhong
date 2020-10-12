package com.prolog.eis.location.service;

import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.model.location.ContainerPathTask;

public interface PathExecutionService {

    /**
     * 执行rcs-rcs路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     * @throws Exception
     */
    void doRcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行mcs-rcs路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     * @throws Exception
     */
    void doMcsToRcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行rcs-mcs路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     * @throws Exception
     */
    void doRcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;
    
    /**
     * 执行mcs-mcs路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     * @throws Exception
     */
    void doMcsToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    ///**
    // * 执行mcs-wcs路径任务
    // * @param containerPathTask
    // * @param containerPathTaskDetailDTO
    // * @throws Exception
    // */
    //void doMcsToWcsTask(ContainerPathTask containerPathTask,ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;
    //
    ///**
    // * 执行wcs-mcs路径任务
    // * @param containerPathTask
    // * @param containerPathTaskDetailDTO
    // * @throws Exception
    // */
    //void doWcsToMcsTask(ContainerPathTask containerPathTask,ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;
    //
    ///**
    // * 执行wcs-mcs路径任务
    // * @param containerPathTask
    // * @param containerPathTaskDetailDTO
    // * @throws Exception
    // */
    //void doWcsToRcsTask(ContainerPathTask containerPathTask,ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;
    //
    ///**
    // * 执行rcs-wcs路径任务
    // * @param containerPathTask
    // * @param containerPathTaskDetailDTO
    // * @throws Exception
    // */
    //void doRcsToWcsTask(ContainerPathTask containerPathTask,ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;
    //
    ///**
    // * 执行sas-wcs路径任务
    // * @param containerPathTask
    // * @param containerPathTaskDetailDTO
    // * @throws Exception
    // */
    //void doSasToWcsTask(ContainerPathTask containerPathTask,ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;
    //
    ///**
    // * 执行wcs-sas路径任务
    // * @param containerPathTask
    // * @param containerPathTaskDetailDTO
    // * @throws Exception
    // */
    //void doWcsToSasTask(ContainerPathTask containerPathTask,ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;




    /**
     * 执行sas-sas路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     */
    void doSasToSasTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * sas-mcs路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     */
    void doSasToMcsTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

    /**
     * 执行mcs-sas路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     */
    void doMcsToSasTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO) throws Exception;

}
