package com.prolog.eis.store.service;

import com.prolog.eis.model.order.PickingOrderHistory;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/15 12:13
 */
public interface IPickingOrderHistoryService {
    /**
     * 保存历史
     * @param pickingOrderHistory
     */
    void savePickingOrder(PickingOrderHistory pickingOrderHistory);
}
