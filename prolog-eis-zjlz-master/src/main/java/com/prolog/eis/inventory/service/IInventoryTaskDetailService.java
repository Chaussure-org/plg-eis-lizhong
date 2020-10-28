package com.prolog.eis.inventory.service;

import com.prolog.eis.dto.inventory.InventoryGoodsDto;
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
}
