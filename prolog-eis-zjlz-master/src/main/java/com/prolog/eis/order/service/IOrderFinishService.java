package com.prolog.eis.order.service;

import com.prolog.eis.model.order.OrderFinish;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/17 9:46
 * 成品
 */
public interface IOrderFinishService {

    /**
     * 批量保存
     * @param orderFinishes
     */
    void saveFinishList(List<OrderFinish> orderFinishes);
}
