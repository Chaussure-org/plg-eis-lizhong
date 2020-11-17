package com.prolog.eis.inventory.service.impl;

import com.prolog.eis.dto.page.InventoryHistoryDto;
import com.prolog.eis.dto.page.InventoryHistoryQueryDto;
import com.prolog.eis.inventory.dao.InventoryTaskDetailHistoryMapper;
import com.prolog.eis.inventory.dao.InventoryTaskDetailMapper;
import com.prolog.eis.inventory.dao.InventoryTaskHistoryMapper;
import com.prolog.eis.inventory.dao.InventoryTaskMapper;
import com.prolog.eis.inventory.service.IInventoryHistoryService;
import com.prolog.eis.model.inventory.InventoryTask;
import com.prolog.eis.model.inventory.InventoryTaskDetail;
import com.prolog.eis.model.inventory.InventoryTaskDetailHistory;
import com.prolog.eis.model.inventory.InventoryTaskHistory;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/16 9:55
 */
@Service
public class InventoryHistoryServiceImpl implements IInventoryHistoryService {
    @Autowired
    private InventoryTaskDetailHistoryMapper taskDetailHistoryMapper;
    @Autowired
    private InventoryTaskMapper inventoryTaskMapper;
    @Autowired
    private InventoryTaskDetailMapper taskDetailMapper;
    @Autowired
    private InventoryTaskHistoryMapper taskHistoryMapper;
    @Override
    public Page<InventoryHistoryDto> getInventoryHistoryPage(InventoryHistoryQueryDto inventoryQueryDto) {
        PageUtils.startPage(inventoryQueryDto.getPageNum(),inventoryQueryDto.getPageSize());
        List<InventoryHistoryDto> list =  taskDetailHistoryMapper.getInventoryHistory(inventoryQueryDto);
        Page<InventoryHistoryDto> page = PageUtils.getPage(list);
        return page;
    }

    @Override
    public void inventoryToHistory(String containerNo) {
        List<InventoryTaskDetail> taskDetails = taskDetailMapper.findByMap(MapUtils.put("containerNo", containerNo).getMap(), InventoryTaskDetail.class);
        if (taskDetails.size() > 0){
            InventoryTaskDetailHistory taskDetailHistory = new InventoryTaskDetailHistory();
            BeanUtils.copyProperties(taskDetails.get(0),taskDetailHistory);
            taskDetailHistoryMapper.save(taskDetailHistory);
            taskDetailMapper.deleteById(taskDetails.get(0).getId(),InventoryTaskDetail.class);
            List<InventoryTaskDetail> inventoryTasks = taskDetailMapper.findByMap(MapUtils.put("inventoryTaskId", taskDetails.get(0).getInventoryTaskId()).getMap(), InventoryTaskDetail.class);
            //计划转历史
            if (inventoryTasks.size() == 0){
                InventoryTask inventoryTask = inventoryTaskMapper.findById(taskDetails.get(0).getInventoryTaskId(), InventoryTask.class);
                if (inventoryTask != null){
                    InventoryTaskHistory inventoryTaskHistory = new InventoryTaskHistory();
                    BeanUtils.copyProperties(inventoryTask,inventoryTaskHistory);
                    inventoryTaskHistory.setEndTime(new Date());
                    inventoryTaskHistory.setInventoryState(InventoryTask.STATE_FINISHED);
                    taskHistoryMapper.save(inventoryTaskHistory);
                    inventoryTaskMapper.deleteById(inventoryTask.getId(),InventoryTask.class);
                }
            }
        }
    }


}
