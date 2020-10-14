package com.prolog.eis.order.service.impl;

import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.order.dao.OrderBillMapper;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 10:53
 */
@Service
public class OrderBillServiceImpl implements IOrderBillService {

    @Autowired
    private OrderBillMapper orderBillMapper;

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


}
