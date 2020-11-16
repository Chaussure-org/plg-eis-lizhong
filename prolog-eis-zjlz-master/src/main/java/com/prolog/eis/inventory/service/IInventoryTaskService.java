package com.prolog.eis.inventory.service;

import com.prolog.eis.dto.page.InventoryInfoDto;
import com.prolog.eis.dto.page.InventoryQueryDto;
import com.prolog.eis.model.inventory.InventoryTask;
import com.prolog.framework.core.pojo.Page;

/**
 * @Author wangkang
 * @Description 盘点服务类
 * @CreateTime 2020-10-14 14:46
 */
public interface IInventoryTaskService {

    /**
     * 保存盘点计划
     * @param inventoryTask
     */
    void saveInventoryTask(InventoryTask inventoryTask);

    /**
     * 分页查盘点计划
     * @param inboundQueryDto
     * @return
     */
    Page<InventoryInfoDto> getInventoryPage(InventoryQueryDto inboundQueryDto);

}
