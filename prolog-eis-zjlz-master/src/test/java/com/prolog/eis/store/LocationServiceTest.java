package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.inventory.InventoryGoodsDto;
import com.prolog.eis.dto.inventory.InventoryOutDto;
import com.prolog.eis.dto.inventory.RickerTaskDto;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.engin.dao.InventoryTrayOutMapper;
import com.prolog.eis.enums.BranchTypeEnum;
import com.prolog.eis.inventory.dao.InventoryTaskDetailMapper;
import com.prolog.eis.inventory.service.IInventoryTaskDetailService;
import com.prolog.eis.inventory.service.IInventoryTaskService;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.LocationService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.inventory.InventoryTask;
import com.prolog.eis.model.inventory.InventoryTaskDetail;
import com.prolog.framework.utils.MapUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-16 15:40
 */
@SpringBootTest(classes = ZjlzApplication.class)
@RunWith(SpringRunner.class)
public class LocationServiceTest {

    @Autowired
    private PathSchedulingService pathSchedulingService;

    @Autowired
    private AgvLocationService agvLocationService;
    @Autowired
    private InventoryTaskDetailMapper mapper;
    @Autowired
    private IInventoryTaskService inventoryTaskService;

    @Autowired
    private IInventoryTaskDetailService inventoryTaskDetailService;
    @Autowired
    private InventoryTrayOutMapper trayOutMapper;

    @Test
    public void doTask() throws Exception {
        pathSchedulingService.containerMoveTask("1","RCS01",null);
    }

    @Test
    public void testPd(){
        List<InventoryGoodsDto> goodsId = mapper.getInventoryGoods(MapUtils.put("goodsId", null).put("goodsType","")
                .put("branchType","A").getMap());

        InventoryTask inventoryTask = new InventoryTask();
        inventoryTask.setBillNo("1111");
        inventoryTask.setBillDate(new Date());
        inventoryTask.setSeqNo("1111");
        inventoryTask.setGoodsId(1);
        inventoryTask.setGoodsType("1111");
        inventoryTask.setInventoryState(InventoryTask.STATE_ISSUED);
        inventoryTask.setInventoryType(InventoryTask.WMS_INVENTORY);
        inventoryTask.setCreateTime(new Date());
        inventoryTask.setIssueTime(inventoryTask.getCreateTime());
        inventoryTask.setTaskId("111111");
        inventoryTaskService.saveInventoryTask(inventoryTask);
        List<InventoryTaskDetail> inventoryDeatils = new ArrayList<>();
        for (InventoryGoodsDto inventoryGoodsDto : goodsId) {
            InventoryTaskDetail inventoryTaskDetail = new InventoryTaskDetail();
            inventoryTaskDetail.setInventoryTaskId(inventoryTask.getId());
            inventoryTaskDetail.setContainerNo(inventoryGoodsDto.getContainerNo());
            inventoryTaskDetail.setCreateTime(new Date());
            inventoryTaskDetail.setGoodsName(inventoryGoodsDto.getGoodsName());
            inventoryTaskDetail.setGoodsNo(inventoryGoodsDto.getGoodsNo());
            inventoryTaskDetail.setOriginalCount(inventoryGoodsDto.getOriginalCount());
            inventoryTaskDetail.setModifyCount(0);
            inventoryTaskDetail.setTaskState(InventoryTaskDetail.TASK_STATE_ISSUE);
            inventoryTaskDetail.setPdType(0);
            inventoryDeatils.add(inventoryTaskDetail);
        }

        inventoryTaskDetailService.saveInventoryDetailBatch(inventoryDeatils);
    }

    @Test
    public void testInventory(){
        String area = "SAS01,MCS01";
        System.out.println("aaaa");
        List<InventoryOutDto> inventoryStore = mapper.getInventoryStore(area);
        return;
    }

}
