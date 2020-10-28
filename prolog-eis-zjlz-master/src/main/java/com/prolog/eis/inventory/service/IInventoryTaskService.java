package com.prolog.eis.inventory.service;

import com.prolog.eis.model.inventory.InventoryTask;

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
}
