package com.prolog.eis.order.service.impl;

import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderBillHistory;
import com.prolog.eis.order.dao.OrderBillMapper;
import com.prolog.eis.order.service.IOrderBillHistoryService;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.framework.utils.MapUtils;
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

}
