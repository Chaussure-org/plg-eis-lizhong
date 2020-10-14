package com.prolog.eis.order.service.impl;

import com.prolog.eis.model.order.OrderBillHistory;
import com.prolog.eis.order.dao.OrderBillHistoryMapper;
import com.prolog.eis.order.service.IOrderBillHistoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/12 15:25
 */
public class OrderBillHistoryServiceImpl implements IOrderBillHistoryService {
    @Autowired
    private OrderBillHistoryMapper orderBillHistoryMapper;
    @Override
    public void saveOrderBill(OrderBillHistory orderBillHistory) {
        if (orderBillHistory != null){
            orderBillHistoryMapper.save(orderBillHistory);
        }

    }
}
