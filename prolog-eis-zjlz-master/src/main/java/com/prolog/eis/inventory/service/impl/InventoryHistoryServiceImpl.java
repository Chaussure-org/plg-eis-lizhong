package com.prolog.eis.inventory.service.impl;

import com.prolog.eis.dto.page.InventoryHistoryDto;
import com.prolog.eis.dto.page.InventoryHistoryQueryDto;
import com.prolog.eis.inventory.dao.InventoryTaskDetailHistoryMapper;
import com.prolog.eis.inventory.service.IInventoryHistoryService;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/16 9:55
 */
@Service
public class InventoryHistoryServiceImpl implements IInventoryHistoryService {
    @Autowired
    private InventoryTaskDetailHistoryMapper mapper;
    @Override
    public Page<InventoryHistoryDto> getInventoryHistoryPage(InventoryHistoryQueryDto inventoryQueryDto) {
        PageUtils.startPage(inventoryQueryDto.getPageNum(),inventoryQueryDto.getPageSize());
        List<InventoryHistoryDto> list =  mapper.getInventoryHistory(inventoryQueryDto);
        Page<InventoryHistoryDto> page = PageUtils.getPage(list);
        return page;
    }
}
