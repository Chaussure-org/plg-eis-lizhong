package com.prolog.eis.order.service.impl;

import com.prolog.eis.dto.OrderBillDto;
import com.prolog.eis.dto.wms.WmsOutboundCallBackDto;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderBillHistory;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.order.dao.OrderBillMapper;
import com.prolog.eis.order.service.IOrderBillHistoryService;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.framework.utils.MapUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prolog.eis.order.service.IOrderDetailService;
import org.springframework.beans.BeanUtils;

import java.util.List;

import java.util.Date;
import java.util.Map;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 10:53
 */
@Service
public class OrderBillServiceImpl implements IOrderBillService {

    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private IOrderBillHistoryService orderBillHistoryService;
    @Autowired
    private IOrderDetailService orderDetailService;

    @Override
    public void saveOrderBill(OrderBill orderBill) {
        orderBillMapper.save(orderBill);
    }

    @Override
    public void upOrderProiorityByBillNo(String billNo) throws Exception {
        List<OrderBill> orderBills = orderBillMapper.findByMap(MapUtils.put("orderNo", billNo).getMap(), OrderBill.class);
        if (orderBills != null && orderBills.size()>0) {
            OrderBill orderBill = orderBills.get(0);
            orderBill.setWmsOrderPriority(10);
            orderBillMapper.update(orderBill);
        }else{
            throw new Exception("未找到订单号对应的订单");
        }
    }


    @Override
    public OrderBill findBillById(int orderBillId) {
        return orderBillMapper.findById(orderBillId,OrderBill.class);
    }

    @Override
    public void orderBillToHistory(int orderBillId) {
        OrderBill orderBill = orderBillMapper.findById(orderBillId, OrderBill.class);
        if (orderBill != null){
            OrderBillHistory orderBillHistory = new OrderBillHistory();
            BeanUtils.copyProperties(orderBill,orderBillHistory);
            orderBillHistory.setCompleteTime(new Date());
            orderBillHistoryService.saveOrderBill(orderBillHistory);
            //明细转历史
            orderDetailService.orderDetailToHistory(orderBillId);
            //删除汇总
            orderBillMapper.deleteById(orderBillId,OrderBill.class);
        }
    }

    @Override
    public void deleteOrderBillByMap(Map map) {
        if (map != null){
            orderBillMapper.deleteByMap(map,OrderBill.class);
        }
    }

    /**
     * 改方法需要按照订单优先级以及时间来将成品库出库排序
     * 判断库存是否满足
     * @return
     */
    @Override
    public List<OrderBillDto> initFinishProdOrder(Map<Integer, Integer> map) {
        List<OrderBillDto> orderBillList = orderBillMapper.initFinishProdOrder();
        orderBillList.stream().filter(x->{
            Integer orderBillId = x.getOrderBillId();
            List<OrderDetail> orderDetailByMap = orderDetailService.findOrderDetailByMap(MapUtils.put("orderBillId",
                    orderBillId).getMap());
            for (OrderDetail orderDetail : orderDetailByMap) {
                if(orderDetail.getPlanQty() < (map.get(orderDetail.getGoodsId()))){
                    map.put(orderDetail.getGoodsId(),map.get(orderDetail.getGoodsId())-orderDetail.getPlanQty());
                }else{
                    return false;
                }
            }
            return true;
        });
        return orderBillList;
    }

    @Override
    public void updateOrderBillStatus(OrderDetail orderDetail) throws Exception {
        OrderBill orderBill = orderBillMapper.findById(orderDetail.getOrderBillId(), OrderBill.class);
        orderBill.setOrderTaskState(OrderBill.ORDER_STATUS_OUTING);
        orderBill.setStartTime(new Date());
        orderBillMapper.update(orderBill);
        orderDetail.setUpdateTime(new Date());
        orderDetailService.updateOrderDetail(orderDetail);

    }

    @Override
    public List<WmsOutboundCallBackDto> findWmsOrderBill(int orderBillId) {
        return orderBillMapper.findWmsOrderBill(orderBillId);
    }

}
