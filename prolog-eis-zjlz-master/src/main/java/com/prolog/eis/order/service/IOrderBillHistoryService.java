package com.prolog.eis.order.service;

import com.prolog.eis.model.order.OrderBillHistory;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/12 15:22
 */
public interface IOrderBillHistoryService {
    /**
     * 保存数据
     * @param orderBillHistory
     */
    void saveOrderBill(OrderBillHistory orderBillHistory);
}
