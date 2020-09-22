package com.prolog.eis.wcs.service;

import com.prolog.eis.model.point.PointLocation;
import com.prolog.eis.wcs.model.WCSHistoryTask;
import com.prolog.eis.wcs.model.WCSTask;

import java.util.List;
import java.util.Map;

public interface IWCSTaskService {

    /**
     * 添加任务
     * @param task
     * @throws Exception
     */
    void add(WCSTask task) throws Exception;

    /**
     * 更新任务
     * @param task
     * @throws Exception
     */
    void update(WCSTask task) throws Exception;

    /**
     * 转历史
     * @param task
     * @throws Exception
     */
    void toHistory(WCSTask task) throws Exception;

    /**
     * 删除
     * @param id
     */
    void delete(String... id);

    /**
     * 删除历史
     * @param id
     */
    void deleteHistory(String... id);

    /**
     * 查询任务
     * @param id
     * @return
     */
    WCSTask getTask(String id);

    /**
     * 根据map查询任务
     * @param params
     * @return
     */
    List<WCSTask> getTaskByMap(Map<String, Object> params);

    /**
     * 根据map查询数量
     * @param params
     * @return
     */
    long getTaskCountByMap(Map<String, Object> params);
    /**
     * 根据map查询历史任务
     * @param params
     * @return
     */
    List<WCSHistoryTask> getHistoryTaskByMap(Map<String, Object> params);

    /**
     * 开始任务
     * @param taskId
     * @throws Exception
     */
    void startTask(String taskId) throws Exception;

    /**
     * 结束任务
     * @param taskId
     * @param success
     * @throws Exception
     */
    void finishTask(String taskId, boolean success) throws Exception;

    /**
     * 根据料箱号结束任务
     * @param containerNo
     * @param success
     * @throws Exception
     */
    void finishTaskByContainerNo(String containerNo, boolean success) throws Exception;

    /**
     * 根据料箱号获取任务
     * @param containerNo
     * @return
     */
    WCSTask getByContainerNo(String containerNo);

    /**
     * 根据起始点位获取任务信息
     * @param address
     * @return
     */
    List<WCSTask> getByAdrress(String address);

    /**
     * 根据目标点位获取任务信息
     * @param target
     * @return
     */
    List<WCSTask> getByTarget(String target);
    /**
     * 寻找出库任务最少的出库口
     * @return
     */
    PointLocation getBestOutboundLocation();

    /**
     * 寻找最佳入库点
     * @return
     */
    PointLocation getBestInboundLocation();

    /**
     * 获取出库任务
     * @param stationId
     * @return
     */
    List<WCSTask> getOutboundTask(int stationId);

    /**
     * 获取入库任务
     * @param stationId
     * @return
     */
    List<WCSTask> getInboundTask(int stationId);

    /**
     * 通过料箱查任务
     */
    List<WCSTask> getTaskByContainer(String containerNo);
}
