package com.prolog.eis.location.service;

import com.prolog.eis.dto.location.ContainerPathTaskDTO;

/**
 * @author: wuxl
 * @create: 2020-09-25 17:11
 * @Version: V1.0
 */
public interface PathSchedulingService {
    /**
     * 入库任务
     * @param palletNo  载具号
     * @param containerNo   容器号
     * @param sourceArea    起始区域
     * @param sourceLocation    起始点位
     * @param targetArea    目标区域
     * @throws Exception
     */
    void inboundTask(String palletNo, String containerNo, String sourceArea, String sourceLocation, String targetArea) throws Exception;

    /**
     * 出库任务-修改任务类型 根据商品id 和站台出库 任务表 更新 更新 task_type
     * @param goodsId
     * @param stationId
     * @return
     * @throws Exception
     */
    ContainerPathTaskDTO outboundTaskForUpdate(int goodsId, String stationId) throws Exception;

    /**
     * 出库任务-成功后生成路径任务并执行
     * @param containerPathTaskDTO
     * @return
     * @throws Exception
     */
    String outboundTaskForSuccess(ContainerPathTaskDTO containerPathTaskDTO) throws Exception;

    /**
     * 出库任务-失败后回滚数据
     * @param containerPathTaskDTO
     * @return
     * @throws Exception
     */
    String outboundTaskForFail(ContainerPathTaskDTO containerPathTaskDTO) throws Exception;

    /**
     * 托盘 移动
     * @param palletNo  载具号
     * @param targetArea    目标区域 更新 task_status
     * @throws Exception
     */
    void containerMoveTask(String palletNo, String targetArea,String targetLocation) throws Exception;

    /**
     * 复位载具位置
     * @param palletNo 载具号
     * @param sourceArea    起点区域
     * @param sourceLocation    起点点位
     */
    void containerResetLocation(String palletNo, String sourceArea,String sourceLocation);


    /**
     * 容器下架，删除路径
     * @param containerNo 容器号
     * @param sourceLocation 起点货位
     */
    void containerPathDelete(String containerNo,String sourceLocation) throws Exception;


}
