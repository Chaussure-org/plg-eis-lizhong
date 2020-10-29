package com.prolog.eis.wms.service.impl;

import com.prolog.eis.base.service.IGoodsService;
import com.prolog.eis.dto.inventory.InventoryGoodsDto;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.wms.*;
import com.prolog.eis.enums.BranchTypeEnum;
import com.prolog.eis.inventory.service.IInventoryTaskDetailService;
import com.prolog.eis.inventory.service.IInventoryTaskService;
import com.prolog.eis.model.base.Goods;
import com.prolog.eis.model.inventory.InventoryTask;
import com.prolog.eis.model.inventory.InventoryTaskDetail;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.eis.store.dao.ContainerStoreMapper;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.warehousing.dao.WareHousingMapper;
import com.prolog.eis.wms.service.IWMSCallBackService;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 13:59
 */
@Service
public class WMSCallBackServiceImpl implements IWMSCallBackService {

    @Autowired
    private WareHousingMapper mapper;

    @Autowired
    private IOrderBillService orderBillService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IInventoryTaskService inventoryTaskService;

    @Autowired
    private IInventoryTaskDetailService inventoryTaskDetailService;
    @Autowired
    private ContainerStoreMapper containerStoreMapper;

    /**
     * 处理wms下发的入库任务
     * @param wmsInboundTaskDtos
     */
    @Override
    @LogInfo(desci = "wms入库任务下发",direction = "wms->eis",type = LogDto.WMS_TYPE_SEND_INBOUND_TASK,systemType = LogDto.WMS)
    @Transactional(rollbackFor = Exception.class)
    public void sendInboundTask(List<WmsInboundTaskDto> wmsInboundTaskDtos) {
        for (WmsInboundTaskDto wmsInboundTaskDto : wmsInboundTaskDtos) {
            WmsInboundTask wmsInboundTask = new WmsInboundTask();
            wmsInboundTask.setBillNo(wmsInboundTaskDto.getBILLNO());
            wmsInboundTask.setBillType(wmsInboundTask.getBillType());
            wmsInboundTask.setBoxSpecs(wmsInboundTaskDto.getJZS());
            wmsInboundTask.setBranchType(wmsInboundTaskDto.getBRANCHTYPE());
            wmsInboundTask.setContainerNo(wmsInboundTaskDto.getCONTAINERNO());
            wmsInboundTask.setGoodsId(wmsInboundTaskDto.getITEMID());
            wmsInboundTask.setGoodsName(wmsInboundTaskDto.getITEMNAME());
            wmsInboundTask.setLineId(wmsInboundTaskDto.getLINEID());
            wmsInboundTask.setQty(wmsInboundTaskDto.getQTY().intValue());
            wmsInboundTask.setSeqNo(wmsInboundTaskDto.getSEQNO());
            wmsInboundTask.setTaskState(WmsInboundTask.TYPE_INBOUND);
            wmsInboundTask.setCreateTime(new Date());
            mapper.save(wmsInboundTask);
        }
    }


    /**
     * 处理wms下发的出库任务
     * @param wmsOutboundTaskDtos
     * @throws Exception
     */
    @Override
    @LogInfo(desci = "wms出库任务下发",direction = "wms->eis",type = LogDto.WMS_TYPE_SEND_OUTBOUND_TASK,systemType = LogDto.WMS)
    @Transactional(rollbackFor = Exception.class)
    public void sendOutBoundTask(List<WmsOutboundTaskDto> wmsOutboundTaskDtos) throws Exception {
        //1.如果库内已经存在该箱子，测不允许生成该箱子的任务
        List<String> allStoreContainers = containerStoreMapper.findAllStoreContainers();

        if (wmsOutboundTaskDtos.size()>0) {
            List<String> billNoList =
                    wmsOutboundTaskDtos.stream().map(x -> x.getBILLNO()).distinct().collect(Collectors.toList());
            for (String s : billNoList) {
                List<WmsOutboundTaskDto> order =
                        wmsOutboundTaskDtos.stream().filter(x -> s.equals(x.getBILLNO())).collect(Collectors.toList());
                OrderBill orderBill = new OrderBill();
                orderBill.setOrderNo(s);
                orderBill.setOrderType(order.get(0).getBILLTYPE());
                orderBill.setOrderTaskState(0);
                orderBill.setBillDate(order.get(0).getBILLDATE());
                orderBill.setTaskId(order.get(0).getTASKID());
                orderBillService.saveOrderBill(orderBill);
                List<OrderDetail> orderDetails = new ArrayList<>();
                for (WmsOutboundTaskDto wmsOutboundTaskDto : order) {
                    if (allStoreContainers.contains(wmsOutboundTaskDto.getCONSIGNOR())){
                        throw new Exception("该容器已经被占用"+wmsOutboundTaskDto.getCONSIGNOR()+"此次所有订单任务下发失败！");
                    }
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderBillId(orderBill.getId());
                    orderDetail.setGoodsId(Integer.valueOf(wmsOutboundTaskDto.getITEMID()));
                    orderDetail.setGoodsOrderNo(wmsOutboundTaskDto.getSEQNO());
                    orderDetail.setPlanQty(wmsOutboundTaskDto.getQTY().intValue());
                    orderDetail.setCreateTime(new Date());
                    orderDetails.add(orderDetail);
                }
                orderDetailService.saveOrderDetailList(orderDetails);
            }
        }
    }

    @Override
    @LogInfo(desci = "wms修改订单优先级",direction = "wms->eis",type = LogDto.WMS_TYPE_UPDATE_PRIORITY,systemType = LogDto.WMS)
    public void upOrderProiority(WmsUpProiorityDto wmsUpProiorityDto) throws Exception {
        orderBillService.upOrderProiorityByBillNo(wmsUpProiorityDto.getBILLNO());
    }

    @Override
    @LogInfo(desci = "wms同步商品资料",direction = "wms->eis",type = LogDto.WMS_TYPE_GOODS_SYNC,systemType = LogDto.WMS)
    public void goodsSync(List<WmsGoodsDto> goodsDtos) {
        List<Goods> newGoods = new ArrayList<>();
        List<Goods> updateGoods = new ArrayList<>();
        for (WmsGoodsDto goodsDto : goodsDtos) {
            Goods goodsByGoodId = goodsService.getGoodsByGoodId(Integer.parseInt(goodsDto.getITEMID()));
            Goods goods = new Goods();
            if (goodsByGoodId == null) {
                goods.setId(Integer.parseInt(goodsDto.getITEMID()));
                goods.setGoodsName(goodsDto.getITEMNAME());
                goods.setGoodsNo(goodsDto.getITEMBARCODE());
                goods.setOwnerDrawnNo(goodsDto.getITEMBARCODE());
                goods.setGoodsOneType(goodsDto.getITEMTYPE());
                goods.setGoodsType(goodsDto.getGATEGORYID());
                goods.setCreateTime(new Date());
                newGoods.add(goods);
            }else {
                goods.setGoodsName(goodsDto.getITEMNAME());
                goods.setGoodsNo(goodsDto.getITEMBARCODE());
                goods.setOwnerDrawnNo(goodsDto.getITEMBARCODE());
                goods.setGoodsOneType(goodsDto.getITEMTYPE());
                goods.setGoodsType(goodsDto.getGATEGORYID());
                goods.setUpdateTime(new Date());
                updateGoods.add(goods);
            }
        }
        goodsService.saveAndUpdateGoods(newGoods,updateGoods);
    }

    @Override
    @LogInfo(desci = "wms盘点任务下发",direction = "wms->eis",type = LogDto.WMS_TYPE_SEND_INVENTORY_TAKS,systemType =
            LogDto.WMS)
    @Transactional(rollbackFor = Exception.class)
    public void sendInventoryTask(List<WmsInventoryTaskDto> wmsInventoryTasks) throws Exception {
        for (WmsInventoryTaskDto wmsInventoryTask : wmsInventoryTasks) {
            Map<String, Object> param = MapUtils.put("branchType", BranchTypeEnum.getEisBranchType(wmsInventoryTask.getBRANCHTYPE())).put(
                    "containerNo",
                    wmsInventoryTask.getCONTAINERNO()).put("goodsType", wmsInventoryTask.getITEMTYPE()).put("goodsId"
                    , wmsInventoryTask.getITEMID()).getMap();
            // 根据wms下发的相关信息找到需盘点的料箱
            List<InventoryGoodsDto> detailsByMap = inventoryTaskDetailService.getDetailsByMap(param);

            if (detailsByMap.size()==0 || detailsByMap == null) {
                throw new Exception("【"+wmsInventoryTask.getBILLNO()+"】未找到满足盘点规则的容器");
            }
            InventoryTask inventoryTask = new InventoryTask();
            inventoryTask.setBillNo(wmsInventoryTask.getBILLNO());
            inventoryTask.setBillDate(wmsInventoryTask.getBILLDATE());
            inventoryTask.setSeqNo(wmsInventoryTask.getSEQNO());
            inventoryTask.setGoodsId(Integer.parseInt(wmsInventoryTask.getITEMID()));
            inventoryTask.setGoodsType(wmsInventoryTask.getITEMTYPE());
            inventoryTask.setInventoryState(InventoryTask.STATE_ISSUED);
            inventoryTask.setInventoryType(InventoryTask.WMS_INVENTORY);
            inventoryTask.setCreateTime(new Date());
            inventoryTask.setIssueTime(inventoryTask.getCreateTime());
            inventoryTask.setTaskId(wmsInventoryTask.getTASKID());
            inventoryTaskService.saveInventoryTask(inventoryTask);
            List<InventoryTaskDetail> inventoryDeatils = new ArrayList<>();
            for (InventoryGoodsDto inventoryGoodsDto : detailsByMap) {
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
    }
}
