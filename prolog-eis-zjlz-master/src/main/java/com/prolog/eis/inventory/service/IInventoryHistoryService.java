package com.prolog.eis.inventory.service;

import com.prolog.eis.dto.page.InventoryHistoryDto;
import com.prolog.eis.dto.page.InventoryHistoryQueryDto;
import com.prolog.framework.core.pojo.Page;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/16 9:54
 * 盘点历史服务层
 */
public interface IInventoryHistoryService {


    /**
     * 分页查盘点历史
     * @param inventoryQueryDto
     * @return
     */
    Page<InventoryHistoryDto> getInventoryHistoryPage(InventoryHistoryQueryDto inventoryQueryDto);


    /**
     * 盘点转历史
     * @param containerNo
     */
    void inventoryToHistory(String containerNo) throws Exception;

}
