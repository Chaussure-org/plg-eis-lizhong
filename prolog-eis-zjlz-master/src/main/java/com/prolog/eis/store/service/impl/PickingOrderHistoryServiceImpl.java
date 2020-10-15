package com.prolog.eis.store.service.impl;

import com.prolog.eis.model.order.PickingOrderHistory;
import com.prolog.eis.store.dao.PickingOrderHistoryMapper;
import com.prolog.eis.store.service.IPickingOrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/15 12:13
 */
public class PickingOrderHistoryServiceImpl implements IPickingOrderHistoryService {
    @Autowired
    private PickingOrderHistoryMapper pickingOrderHistoryMapper;

    @Override
    public void savePickingOrder(PickingOrderHistory pickingOrderHistory) {
        if (pickingOrderHistory != null) {
            pickingOrderHistoryMapper.save(pickingOrderHistory);
        }

    }
}
