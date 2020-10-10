package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.PickingAreaDto;
import com.prolog.eis.engin.service.AgvLineOutEnginService;
import com.prolog.eis.model.order.OrderBill;

import java.util.List;

public class AgvBoxOutEnginServiceImpl implements AgvLineOutEnginService {
    @Override
    public PickingAreaDto init() throws Exception {
        return null;
    }

    @Override
    public List<OrderBill> computerPickOrder(List<OrderBill> orderBills) throws Exception {
        return null;
    }

    @Override
    public void tackPickOrder() throws Exception {

    }
}
