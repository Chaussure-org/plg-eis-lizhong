package com.prolog.eis.store.service;

import com.prolog.eis.model.PickingOrder;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/15 11:54
 */
public interface IPickingOrderService {

    /**
     * 根据map查询
     * @param map
     * @return
     */
    List<PickingOrder> findByMap(Map map);
}
