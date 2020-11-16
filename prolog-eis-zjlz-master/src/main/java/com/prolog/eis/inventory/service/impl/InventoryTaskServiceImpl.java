package com.prolog.eis.inventory.service.impl;

import com.prolog.eis.dto.page.InventoryInfoDto;
import com.prolog.eis.dto.page.InventoryQueryDto;
import com.prolog.eis.inventory.dao.InventoryTaskMapper;
import com.prolog.eis.inventory.service.IInventoryTaskService;
import com.prolog.eis.model.inventory.InventoryTask;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Page<InventoryInfoDto> getInventoryPage(InventoryQueryDto inboundQueryDto) {
        PageUtils.startPage(inboundQueryDto.getPageNum(),inboundQueryDto.getPageSize());
        List<InventoryInfoDto> inventoryInfoDtoList =  inventoryTaskMapper.getInventoryInfo(inboundQueryDto);
        Page<InventoryInfoDto> page = PageUtils.getPage(inventoryInfoDtoList);
        return page;
    }
}
