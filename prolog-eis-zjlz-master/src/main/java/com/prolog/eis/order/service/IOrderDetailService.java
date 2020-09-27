package com.prolog.eis.order.service;

import com.prolog.eis.model.order.OrderDetail;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 11:21
 */
public interface IOrderDetailService {

    void saveOrderDetailList(List<OrderDetail> orderDetails);
}
