package com.prolog.eis.inventory.service.impl;

import com.prolog.eis.dto.inventory.InventoryShowDto;
import com.prolog.eis.dto.wms.WmsInventoryCallBackDto;
import com.prolog.eis.inventory.dao.InventoryTaskDetailMapper;
import com.prolog.eis.inventory.service.IInventoryTaskDetailService;
import com.prolog.eis.model.inventory.InventoryTaskDetail;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restriction;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

        return inventoryTaskDetailMapper.getInventoryGoods(param);
    }

    @Override
    public void saveInventoryDetailBatch(List<InventoryTaskDetail> inventoryTaskDetails) {
        if (inventoryTaskDetails.size() == 0){
            return;
        }
        inventoryTaskDetailMapper.saveBatch(inventoryTaskDetails);
    }

    @Override
    public void updateContainerTaskState(String containerNo, int taskState) {
        List<InventoryTaskDetail> taskDetails = inventoryTaskDetailMapper.findByMap(MapUtils.put("containerNo", containerNo).getMap(), InventoryTaskDetail.class);
        if (taskDetails.size() != 0){
            InventoryTaskDetail inventoryTaskDetail = taskDetails.get(0);
            inventoryTaskDetail.setTaskState(taskState);
            inventoryTaskDetail.setOutboundTime(new Date());
            inventoryTaskDetailMapper.update(inventoryTaskDetail);
        }
    }

    @Override
    public List<InventoryTaskDetail> findByMap(Map map) {
        return inventoryTaskDetailMapper.findByMap(map,InventoryTaskDetail.class);
    }

    @Override
    public void updateInventoryDetail(InventoryTaskDetail inventoryTaskDetail) {
        inventoryTaskDetailMapper.update(inventoryTaskDetail);
    }

    @Override
    public void deleteById(int id) {
        inventoryTaskDetailMapper.deleteById(id,InventoryTaskDetail.class);
    }

    @Override
    public List<WmsInventoryCallBackDto> findInventoryToWms(int id) {
        return inventoryTaskDetailMapper.findWmsInventory(id);
    }

    @Override
    public List<InventoryShowDto> findInventoryInfo(String containerNo) {
        return inventoryTaskDetailMapper.findInventoryInfo(containerNo);
    }


}
