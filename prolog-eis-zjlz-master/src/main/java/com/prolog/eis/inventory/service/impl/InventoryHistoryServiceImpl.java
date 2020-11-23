package com.prolog.eis.inventory.service.impl;

import com.prolog.eis.dto.inventory.InventoryWmsDto;
import com.prolog.eis.dto.page.InventoryHistoryDto;
import com.prolog.eis.dto.page.InventoryHistoryQueryDto;
import com.prolog.eis.dto.wms.WmsInboundCallBackDto;
import com.prolog.eis.dto.wms.WmsInventoryCallBackDto;
import com.prolog.eis.inventory.dao.InventoryTaskDetailHistoryMapper;
import com.prolog.eis.inventory.dao.InventoryTaskDetailMapper;
import com.prolog.eis.inventory.dao.InventoryTaskHistoryMapper;
import com.prolog.eis.inventory.dao.InventoryTaskMapper;
import com.prolog.eis.inventory.service.IInventoryHistoryService;
import com.prolog.eis.model.inventory.InventoryTask;
import com.prolog.eis.model.inventory.InventoryTaskDetail;
import com.prolog.eis.model.inventory.InventoryTaskDetailHistory;
import com.prolog.eis.model.inventory.InventoryTaskHistory;
import com.prolog.eis.util.EisStringUtils;
import com.prolog.eis.wms.service.IWmsService;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private IWmsService wmsService;
    @Override
    public Page<InventoryHistoryDto> getInventoryHistoryPage(InventoryHistoryQueryDto inventoryQueryDto) {
        PageUtils.startPage(inventoryQueryDto.getPageNum(),inventoryQueryDto.getPageSize());
        List<InventoryHistoryDto> list =  taskDetailHistoryMapper.getInventoryHistory(inventoryQueryDto);
        Page<InventoryHistoryDto> page = PageUtils.getPage(list);
        return page;
    }

    @Override
    public void inventoryToHistory(String containerNo) throws Exception {
        List<InventoryTaskDetail> taskDetails = taskDetailMapper.findByMap(MapUtils.put("containerNo", containerNo).getMap(), InventoryTaskDetail.class);
        if (taskDetails.size() > 0){
            List<InventoryTaskDetail> inventoryTaskId = taskDetailMapper.findByMap(MapUtils.put("inventoryTaskId", taskDetails.get(0).getInventoryTaskId()).getMap(), InventoryTaskDetail.class);
            int size = inventoryTaskId.stream().filter(x -> x.getTaskState() != InventoryTask.STATE_SEND_TO_WMS).collect(Collectors.toList()).size();
            if (size == 0){
                //盘点计划所对应明细全部完成转历史
                List<InventoryTaskDetailHistory> detailHistories = new ArrayList<>();
                for (InventoryTaskDetail inventoryTaskDetail : inventoryTaskId) {
                    InventoryTaskDetailHistory taskDetailHistory = new InventoryTaskDetailHistory();
                    BeanUtils.copyProperties(inventoryTaskDetail,taskDetailHistory);
                    detailHistories.add(taskDetailHistory);
                }
                taskDetailHistoryMapper.saveBatch(detailHistories);
                taskDetailMapper.deleteByMap(MapUtils.put("inventoryTaskId",taskDetails.get(0).getInventoryTaskId()).getMap(),InventoryTaskDetail.class);

                //汇总转历史
                InventoryTask inventoryTask = inventoryTaskMapper.findById(taskDetails.get(0).getInventoryTaskId(), InventoryTask.class);
                if (inventoryTask != null){
                    InventoryTaskHistory inventoryTaskHistory = new InventoryTaskHistory();
                    BeanUtils.copyProperties(inventoryTask,inventoryTaskHistory);
                    inventoryTaskHistory.setEndTime(new Date());
                    inventoryTaskHistory.setInventoryState(InventoryTask.STATE_FINISHED);
                    taskHistoryMapper.save(inventoryTaskHistory);
                    inventoryTaskMapper.deleteById(inventoryTask.getId(),InventoryTask.class);
                }

                // TODO: 2020/11/18  盘点回告wms
                InventoryWmsDto inventoryWmsDto = taskDetailMapper.findWmsInventory(inventoryTask.getId()).get(0);
                WmsInventoryCallBackDto wmsInventoryCallBackDto = new WmsInventoryCallBackDto();
                wmsInventoryCallBackDto.setAFFQTY(inventoryWmsDto.getAffQty());
                wmsInventoryCallBackDto.setBILLDATE(inventoryWmsDto.getBillDate());
                wmsInventoryCallBackDto.setBILLNO(inventoryWmsDto.getBillNo());
                wmsInventoryCallBackDto.setITEMID(EisStringUtils.getRemouldId(inventoryWmsDto.getGoodsId()));
                wmsInventoryCallBackDto.setITEMTYPE(inventoryWmsDto.getGoodsType());
                wmsInventoryCallBackDto.setSEQNO(inventoryWmsDto.getSeqNo());
                wmsInventoryCallBackDto.setSJZ(new Date());
                wmsInventoryCallBackDto.setBRANCHCODE("C001");
                wmsInventoryCallBackDto.setBRANCHAREA(inventoryWmsDto.getBranchType());
                wmsService.inventoryTaskCallBack(wmsInventoryCallBackDto);

            }
        }
    }


}
