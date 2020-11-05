package com.prolog.eis.store.service.impl;

import com.prolog.eis.model.PickingOrder;
import com.prolog.eis.store.dao.PickingOrderMapper;
import com.prolog.eis.store.service.IPickingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/15 12:01
 */
@Service
public class PickingOrderServiceImpl implements IPickingOrderService {
     @Autowired
     private PickingOrderMapper pickingOrderMapper;

    @Override
    public List<PickingOrder> findByMap(Map map) {
        return pickingOrderMapper.findByMap(map,PickingOrder.class);
    }

    @Override
    public void deleteById(int id) {
        pickingOrderMapper.deleteById(id,PickingOrder.class);
    }
}
