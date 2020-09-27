package com.prolog.eis.wms.service.impl;

import com.prolog.eis.base.service.IGoodsService;
import com.prolog.eis.dto.wms.UpProiorityDto;
import com.prolog.eis.dto.wms.WMSInboundTaskDto;
import com.prolog.eis.dto.wms.WMSOutboundTaskDto;
import com.prolog.eis.model.base.Goods;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.eis.wms.dao.WMSMapper;
import com.prolog.eis.wms.service.IWMSCallBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 13:59
 */
@Service
public class WMSCallBackServiceImpl implements IWMSCallBackService {

    @Autowired
    private WMSMapper mapper;

    @Autowired
    private IOrderBillService orderBillService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IOrderDetailService orderDetailService;

    /**
     * 处理wms下发的入库任务
     * @param wmsInboundTaskDtos
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendInboundTask(List<WMSInboundTaskDto> wmsInboundTaskDtos) {
        for (WMSInboundTaskDto wmsInboundTaskDto : wmsInboundTaskDtos) {
            WmsInboundTask wmsInboundTask = new WmsInboundTask();
            wmsInboundTask.setBillNo(wmsInboundTaskDto.getBillNo());
            wmsInboundTask.setBillType(wmsInboundTask.getBillType());
            wmsInboundTask.setBoxSpecs(wmsInboundTaskDto.getJzs());
            wmsInboundTask.setBranchType(wmsInboundTaskDto.getBranchType());
            wmsInboundTask.setContainerNo(wmsInboundTaskDto.getContainerNo());
            wmsInboundTask.setGoodsCode(wmsInboundTaskDto.getItemCode());
            wmsInboundTask.setGoodsName(wmsInboundTaskDto.getItemName());
            wmsInboundTask.setLineId(wmsInboundTaskDto.getLineId());
            wmsInboundTask.setQty(wmsInboundTaskDto.getQty());
            wmsInboundTask.setSeqNo(wmsInboundTaskDto.getSeqNo());
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
    @Transactional(rollbackFor = Exception.class)
    public void sendOutBoundTask(List<WMSOutboundTaskDto> wmsOutboundTaskDtos) throws Exception {
        if (wmsOutboundTaskDtos.size()>0) {
            List<String> billNoList =
                    wmsOutboundTaskDtos.stream().map(x -> x.getBillNo()).distinct().collect(Collectors.toList());
            for (String s : billNoList) {
                List<WMSOutboundTaskDto> order =
                        wmsOutboundTaskDtos.stream().filter(x -> s.equals(x.getBillNo())).collect(Collectors.toList());
                OrderBill orderBill = new OrderBill();
                orderBill.setOrderNo(s);
                orderBill.setOrderType(order.get(0).getBillType());
                orderBill.setOrderTaskState(0);
                orderBillService.saveOrderBill(orderBill);
                List<OrderDetail> orderDetails = null;
                for (WMSOutboundTaskDto wmsOutboundTaskDto : order) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderBillId(orderBill.getId());
                    Goods goods = goodsService.getGoodsByCode(wmsOutboundTaskDto.getItemCode());
                    orderDetail.setGoodsId(goods.getId().toString());
                    orderDetail.setGoodsOrderNo(wmsOutboundTaskDto.getSeqNo());
                    orderDetail.setPlanQty(wmsOutboundTaskDto.getQty());
                    orderDetail.setCreateTime(new Date());
                    orderDetails.add(orderDetail);
                }
                orderDetailService.saveOrderDetailList(orderDetails);
            }
        }
    }

    @Override
    public void upOrderProiority(UpProiorityDto upProiorityDto) {

    }
}
