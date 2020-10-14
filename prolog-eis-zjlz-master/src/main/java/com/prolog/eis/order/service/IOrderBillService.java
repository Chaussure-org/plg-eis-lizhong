package com.prolog.eis.order.service;

import com.prolog.eis.model.order.OrderBill;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 10:52
 */
public interface IOrderBillService {

    /**
     * 保存订单
     * @param orderBill 订单实体
     */
    void saveOrderBill(OrderBill orderBill);

    /**
     * 通过billNo查询订单
     * @param billNo
     */
    void upOrderProiorityByBillNo(String billNo) throws Exception;
}
