package com.prolog.eis.order.service;

import com.prolog.eis.model.OrderBox;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/21 13:47
 */
public interface IOrderBoxService {

    /**
     * 根据map查询
     * @param map
     * @return
     */
    List<OrderBox>  findByMap(Map map);

    /**
     * 保存对象
     * @param orderBox
     */
    void saveOrderBox(OrderBox orderBox);

    /**
     * 根据对象修改
     * @param orderBox
     */
    void updateOrderBox(OrderBox orderBox);
}
