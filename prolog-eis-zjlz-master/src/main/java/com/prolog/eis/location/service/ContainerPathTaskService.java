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

    /**
     * 获取接驳点位是否有容器
     * @param storeArea
     * @return
     * @throws Exception
     */
    List<ContainerPathTask> getContainerByPath(String storeArea) throws Exception;

    /**
     * 半成品立库分配堆垛机逻辑
     * @return
     */
    String computeAreaIn();

    /**
     * 更新任务状态
     * @param containerNo
     * @param type
     */
    void updatePathTaskTypeByContainer(String containerNo,int type);


    /**
     * 根据容器删除路径
     * @param containerNo
     */
    void deletePathByContainer(String containerNo);

    /**
     * 保存对象
     * @param containerPathTask
     */
    void saveContainerPath(ContainerPathTask containerPathTask);
}
