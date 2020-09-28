package com.prolog.eis.engin.service;

import com.prolog.eis.dto.lzenginee.PickingAreaDto;
import com.prolog.eis.model.order.OrderBill;

import java.util.List;

/**
 * agv区域和箱库的 出库
 */
public interface AgvBoxOutEnginService {

    /**
     * init 方法 拣选区域的 信息集合
     * 站台的拣选单
     */
    PickingAreaDto init() throws Exception;

    /**
     * 优先出库的订单
     * 1.优先出 agv 区域 全部满足的 订单 && 再筛选一遍效期
     * 2.出 输送线区域 和 agv区域 共同的订单
     * 3.出输送线上面
     * @throws Exception
     */
    List<OrderBill> computerPickOrder(List<OrderBill> orderBills) throws Exception;

    /**
     * 站台分配拣选单
     * 1.当前站台的 拣选单 全部到达
     * 2.根据路径任务 判断站台的当前  站台的料箱缓存位的空闲位置 最多的
     * 3.分配拣选单
     */
    void tackPickOrder() throws Exception;


}
