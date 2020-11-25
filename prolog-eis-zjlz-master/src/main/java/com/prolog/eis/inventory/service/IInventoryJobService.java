package com.prolog.eis.inventory.service;

import com.prolog.eis.dto.inventory.InventoryShowDto;
import com.prolog.eis.dto.inventory.RickerInfoDto;
import com.prolog.eis.model.inventory.InventoryTaskDetail;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/2 11:07
 * 盘点作业service
 */
public interface IInventoryJobService {


    /**
     * 查看盘点详情 发送前端
     * @param containerNo
     * @return
     */
    InventoryShowDto findInventoryDetail(String containerNo) throws Exception;

    /**
     * 盘点执行
     * @param containerNo 容器号
     * @param qty  数量
     */
    void doInventoryTask(String containerNo,int qty) throws Exception;

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


    /**
     * agv回库计算合适巷道
     * @return
     */
    RickerInfoDto computeAreaNo() throws Exception;
    
}
