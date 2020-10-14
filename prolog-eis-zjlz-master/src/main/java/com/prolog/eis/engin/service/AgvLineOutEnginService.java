package com.prolog.eis.engin.service;

import com.prolog.eis.dto.lzenginee.PickingAreaDto;
import com.prolog.eis.model.order.OrderBill;

import java.util.List;

/**
 * agv区域和输送线的 出库
 * @author sunpp
 */
public interface AgvLineOutEnginService {

    /**
     * 生成拣选单
     */
    PickingAreaDto init() throws Exception;



    List<OrderBill> computerPickOrder(List<OrderBill> orderBills) throws Exception;

    /**
     * 站台分配拣选单
     * 一.生成拣选单
     * 1.优先找agv_binding_detail
     * 2.根据时间找，优先级为 1 的订单
     * 3.如果没有了找 line_binding_detail 里的订单
     * 二。生成拣选单
     * 1.找到合适的站台分配拣选单
     */



}
