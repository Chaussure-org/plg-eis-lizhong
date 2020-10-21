package com.prolog.eis.order.service.impl;

import com.prolog.eis.model.OrderBox;
import com.prolog.eis.order.dao.OrderBoxMapper;
import com.prolog.eis.order.service.IOrderBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/21 13:51
 */
@Service
public class OrderBoxServiceImpl implements IOrderBoxService {
    @Autowired
    private OrderBoxMapper orderBoxMapper;
    @Override
    public List<OrderBox> findByMap(Map map) {
        return orderBoxMapper.findByMap(map,OrderBox.class);
    }

    @Override
    public void saveOrderBox(OrderBox orderBox) {
        orderBoxMapper.save(orderBox);
    }

    @Override
    public void updateOrderBox(OrderBox orderBox) {
        orderBoxMapper.update(orderBox);
    }
}
