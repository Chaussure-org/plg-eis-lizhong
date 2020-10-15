package com.prolog.eis.order.service;

import com.prolog.eis.dto.OrderBillDto;
import com.prolog.eis.model.order.OrderBill;

import java.util.List;
import java.util.Map;

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
     * 通过billNo更新订单优先级
     * @param billNo 订单号
     */
    void upOrderProiorityByBillNo(String billNo) throws Exception;

    /**
     * 根据id查询查询订单
     * @param orderBillId 订单号
     * @return
     */
     OrderBill findBillById(int orderBillId);

    /**
     * 汇总转历史
     * @param orderBillId 订单号
     */
     void orderBillToHistory(int orderBillId);

    /**
     * 根据map删除
     * @param map map
     */
    void deleteOrderBillByMap(Map map);

    /**
     * 初始化成品库订单
     * @return
     */
    List<OrderBillDto> initFinishProdOrder(Map<Integer,Integer> map);
}
