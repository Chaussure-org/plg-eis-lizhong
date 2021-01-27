package com.prolog.eis.location.service;

import com.prolog.eis.model.location.ContainerPathTaskDetail;

import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-21 18:32
 */
public interface IContainerPathTaskDetailService {

    /**
     * 通过任务id找路径详情
     * @param taskId
     * @return
     */
    List<ContainerPathTaskDetail> getTaskDetailByTaskId(String taskId);


    /**
     * 更新路径任务详情
     * @param containerPathTaskDetail 路径任务详情
     */
    void updateTaskDetail(ContainerPathTaskDetail containerPathTaskDetail);


    /**
     * 保存路径明细
     * @param containerPathTaskDetail
     */
    void savePathDetail(ContainerPathTaskDetail containerPathTaskDetail);


    /**
     *根据map查对象
     */
    List<ContainerPathTaskDetail> findPathTaskDetailByMap(Map map);
}
