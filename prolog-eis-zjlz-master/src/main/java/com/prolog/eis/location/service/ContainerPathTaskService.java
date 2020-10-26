package com.prolog.eis.location.service;

import com.prolog.eis.dto.location.ContainerPathTaskDetailDTO;
import com.prolog.eis.dto.location.StoreAreaPriorityDTO;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.location.StoreArea;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author: wuxl
 * @create: 2020-09-23 12:26
 * @Version: V1.0
 */
public interface ContainerPathTaskService {

    /**
     * 更新下一条任务明细状态和点位
     * @param containerPathTaskDetail
     * @param containerPathTask
     * @param nowTime
     * @throws Exception
     */
    void updateNextContainerPathTaskDetail(ContainerPathTaskDetail containerPathTaskDetail, ContainerPathTask containerPathTask, Timestamp nowTime) throws Exception;

    /**
     * 根据条件更新路径任务
     * @param containerPathTask
     * @param containerPathTaskDetailDTO
     * @param palletContainerPathTaskDetailDTO
     * @param hzTaskState
     * @param mxTaskState
     * @throws Exception
     */
    void updateContainerPathTask(ContainerPathTask containerPathTask, ContainerPathTaskDetailDTO containerPathTaskDetailDTO
            , ContainerPathTaskDetailDTO palletContainerPathTaskDetailDTO, Integer hzTaskState, Integer mxTaskState) throws Exception;

    /**
     * 获取路径任务汇总
     * @param containerPathTaskDetail
     * @return
     * @throws Exception
     */
    ContainerPathTask getContainerPathTask(ContainerPathTaskDetail containerPathTaskDetail) throws Exception;

    /**
     * 记录日志
     * @param containerPathTaskDetail
     * @param nowTime
     * @throws Exception
     */
    void saveContainerPathTaskHistory(ContainerPathTaskDetail containerPathTaskDetail, Timestamp nowTime) throws Exception;

    /**
     * 区域排序
     * @param spList
     * @return
     */
    List<StoreArea> findBestStoreAreaList(List<StoreAreaPriorityDTO> spList);

    /**
     * 根据map查询
     * @param map
     * @return
     * @throws Exception
     */
    List<ContainerPathTask> findByMap(Map map) throws Exception;

    /**
     * 更新路径任务
     * @param containerPathTask 路径任务
     */
    void updateTask(ContainerPathTask containerPathTask);
}
