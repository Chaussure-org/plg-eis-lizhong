package com.prolog.eis.inventory.service;

import com.prolog.eis.model.inventory.InventoryTaskDetail;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/2 11:07
 * 盘点作业service
 */
public interface IInventoryJobService {

    /**
     * 盘点执行
     * @param containerNo 容器号
     * @param qty  数量
     * @param lotId   批号
     */
    void doInventoryTask(String containerNo,int qty,String lotId) throws Exception;

    /**
     * 盘点明细转历史
     */
    void inventoryDetailToHistory(InventoryTaskDetail inventoryTaskDetail);

    /**
     * 站台容器放行
     * @param stationId
     * @param containerNo
     * @param target
     */
    void containerLeaveByStation(int stationId,String target,String containerNo) throws Exception;
    
}
