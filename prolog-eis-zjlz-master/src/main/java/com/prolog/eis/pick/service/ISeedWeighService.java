package com.prolog.eis.pick.service;

import com.prolog.eis.model.order.SeedWeigh;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/21 9:20
 */
public interface ISeedWeighService {

    /**
     * 得到订单拖之前重量
     * @param orderBillId
     * @param orderTrayNo
     * @param seedId
     * @return
     */
    BigDecimal getBeforeOrderTrayWeight(int orderBillId,String orderTrayNo,int seedId);


    /**
     * 根据map查询
     * @param map
     * @return
     */
    List<SeedWeigh> findSeedWeighByMap(Map map);


    /**
     * 保存对象
     * @param seedWeigh
     */
    void saveSeedWeigh(SeedWeigh seedWeigh);


    /**
     * 修改
     * @param seedWeigh
     */
    void updateSeedWeigh(SeedWeigh seedWeigh);
}
