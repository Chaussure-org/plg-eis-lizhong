package com.prolog.eis.orderpool.service;

import com.prolog.eis.dto.orderpool.OpOrderHz;
import com.prolog.eis.model.order.OrderBill;

import java.util.List;
import java.util.Map;

/**
 * @author panteng
 * @description: 订单池
 * @date 2020/4/17 15:17
 */
public interface OrderPoolService {
    /**
     * 初始化订单池
     * @return
     */
    void initOrderPool();

    /**
     * 拉取新订单
     */
    void pullNewOrders();


    /**
     *将订单标记位已加入订单池
     * @param orderList
     */
    void updateOrder2Pool(List<OpOrderHz> orderList);

    /**
     * 获取订单池
     * @throws Exception
     * @return
     */
    List<OpOrderHz> getOrderPool() throws Exception;



    /**
     * 从订单池删除订单
     * @param ids
     * @throws Exception
     */
    void delOrderList(List<Integer> ids) throws Exception;

    /**
     * 修改订单优先级
     * @param map
     * @throws Exception
     */
    void updatePriority(Map<Integer, Integer> map) throws Exception;
}
