package com.prolog.eis.order.service.impl;

import com.prolog.eis.model.order.OrderFinish;
import com.prolog.eis.order.dao.OrderFinishMapper;
import com.prolog.eis.order.service.IOrderFinishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/17 9:49
 */
@Service
public class OrderFinishSeviceImpl implements IOrderFinishService {
    @Autowired
    private OrderFinishMapper orderFinishMapper;
    @Override
    public void saveFinishList(List<OrderFinish> orderFinishes) {
        if (orderFinishes.size() > 0){
            orderFinishMapper.saveBatch(orderFinishes);
        }
    }
}
