package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.inventory.InventoryOutDto;
import com.prolog.eis.dto.lzenginee.RoadWayContainerTaskDto;
import com.prolog.eis.engin.dao.TrayOutMapper;
import com.prolog.eis.engin.service.IInventoryTrayOutService;
import com.prolog.eis.inventory.dao.InventoryTaskDetailMapper;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/28 12:12
 */
public class InventoryTrayOutServiceImpl implements IInventoryTrayOutService {
    @Autowired
    private InventoryTaskDetailMapper inventoryTaskDetailMapper;
    @Autowired
    private TrayOutMapper trayOutMapper;

    /**
     * 1、初始化盘点容器
     * 2、任务数最少堆垛机找容器
     * 3、移位数最少容器
     * 4、创建时间最早的容器
     */
    @Override
    public void inventoryTrayOut() {
        //初始化数据
        List<InventoryOutDto> inventoryOutDtos = this.initInventoryStore();
        if (inventoryOutDtos.size() == 0){
            return;
        }
        //巷道任务数
        List<RoadWayContainerTaskDto> roadWayContainerTask = trayOutMapper.findRoadWayContainerTask();
        
        
    }


    private List<InventoryOutDto> initInventoryStore() {
        String area = "MCS01,MCS02,MCS03,MCS04";
        List<InventoryOutDto> inventoryStore = inventoryTaskDetailMapper.getInventoryStore(area);
        return inventoryStore;
    }
}
