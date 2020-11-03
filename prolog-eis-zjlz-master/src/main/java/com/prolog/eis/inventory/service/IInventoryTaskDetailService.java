package com.prolog.eis.inventory.service;

import com.prolog.eis.dto.inventory.InventoryGoodsDto;
import com.prolog.eis.dto.wms.WmsInventoryCallBackDto;
import com.prolog.eis.model.inventory.InventoryTaskDetail;

import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description 盘点详情服务类
 * @CreateTime 2020-10-14 14:49
 */
public interface IInventoryTaskDetailService {
    /**
     * 通过map中得值去找盘点明细
     * @param param
     * @return
     */
    List<InventoryGoodsDto> getDetailsByMap(Map<String, Object> param);


    /**
     * 保存盘点计划明细
     * @param inventoryTaskDetails
     */
    void saveInventoryDetailBatch(List<InventoryTaskDetail> inventoryTaskDetails);


    /**
     * 修改盘点容器状态
     * @param containerNo
     * @param taskState
     */
    void updateContainerTaskState(String containerNo,int taskState);


    /**
     * 根据map查询
     * @param map
     * @return
     */
    List<InventoryTaskDetail> findByMap(Map map);


    /**
     * 修改明细
     * @param inventoryTaskDetail
     */
     void updateInventoryDetail(InventoryTaskDetail inventoryTaskDetail);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(int id);

    /**
     * 盘点回告wms
     * @param id
     */
    List<WmsInventoryCallBackDto> findInventoryToWms(int id);

}
