package com.prolog.eis.order.service.impl;

import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.order.dao.OrderDetailMapper;
import com.prolog.eis.order.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 11:22
 */
@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public void saveOrderDetailList(List<OrderDetail> orderDetails) {
        if (orderDetails.size()>0){
            orderDetailMapper.saveBatch(orderDetails);
        }
    }
}
