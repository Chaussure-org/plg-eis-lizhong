package com.prolog.eis.order.service.impl;

import com.prolog.eis.model.order.OrderDetailHistory;
import com.prolog.eis.order.dao.OrderDetailHistoryMapper;
import com.prolog.eis.order.service.IOrderDetailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/12 15:51
 */
@Service
public class OrderDetailHistoryServiceImpl implements IOrderDetailHistoryService {
    @Autowired
    private OrderDetailHistoryMapper orderDetailHistoryMapper;

    @Override
    public void saveBatch(List<OrderDetailHistory> orderDetails) {
        if (orderDetails.size() > 0) {
            orderDetailHistoryMapper.saveBatch(orderDetails);
        }

    }
}
