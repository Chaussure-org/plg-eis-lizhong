package com.prolog.eis.order.service;

import com.prolog.eis.model.order.OrderDetailHistory;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/12 15:49
 */
public interface IOrderDetailHistoryService {
    /**
     * 批量保存
     * @param orderDetails
     */
    void saveBatch(List<OrderDetailHistory> orderDetails);

    /**
     * 根据对象保存
     * @param orderDetailHistory
     */
    void saveOrderHistory(OrderDetailHistory orderDetailHistory);
}
