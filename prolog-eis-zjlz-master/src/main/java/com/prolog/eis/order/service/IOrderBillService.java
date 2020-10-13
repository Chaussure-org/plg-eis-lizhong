package com.prolog.eis.order.service;

import com.prolog.eis.model.order.OrderBill;

import java.util.Map;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 10:52
 */
public interface IOrderBillService {

    void saveOrderBill(OrderBill orderBill);

    /**
     * 根据id查询
     * @param orderBillId
     * @return
     */
     OrderBill findBillById(int orderBillId);

    /**
     * 汇总转历史
     * @param orderBillId
     */
     void orderBillToHistory(int orderBillId);

    /**
     * 根据map删除
     * @param map
     */
    void deleteOrderBillByMap(Map map);

}
