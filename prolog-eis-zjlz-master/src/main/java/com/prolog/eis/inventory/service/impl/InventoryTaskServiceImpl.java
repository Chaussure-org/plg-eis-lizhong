package com.prolog.eis.inventory.service.impl;

import com.prolog.eis.inventory.dao.InventoryTaskMapper;
import com.prolog.eis.inventory.service.IInventoryTaskService;
import com.prolog.eis.model.inventory.InventoryTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wangkang
 * @Description 盘点服务实现类
 * @CreateTime 2020-10-14 14:47
 */
@Service
public class InventoryTaskServiceImpl implements IInventoryTaskService {
    @Autowired
    private InventoryTaskMapper inventoryTaskMapper;
    @Override
    public void saveInventoryTask(InventoryTask inventoryTask) {
        if (inventoryTask != null){
            inventoryTaskMapper.save(inventoryTask);
        }

    }
}
