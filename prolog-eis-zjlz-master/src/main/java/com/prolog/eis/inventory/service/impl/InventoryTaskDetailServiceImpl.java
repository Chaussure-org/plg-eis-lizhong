package com.prolog.eis.inventory.service.impl;

import com.prolog.eis.inventory.dao.InventoryTaskDetailMapper;
import com.prolog.eis.inventory.service.IInventoryTaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description 盘点详情服务实现类
 * @CreateTime 2020-10-14 14:48
 */
@Service
public class InventoryTaskDetailServiceImpl implements IInventoryTaskDetailService {

    @Autowired
    private InventoryTaskDetailMapper inventoryTaskDetailMapper;

    @Override
    public List getDetailsByMap(Map<String, Object> param) {

        return null;
    }
}
