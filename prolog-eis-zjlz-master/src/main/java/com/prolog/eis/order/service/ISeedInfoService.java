package com.prolog.eis.order.service;

import com.prolog.eis.model.order.SeedInfo;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/13 17:23
 */
public interface ISeedInfoService {
    /**
     * 播种记录保存
     * @param containerNo
     * @param orderTrayNo
     * @param orderBillId
     * @param orderDetailId
     * @param stationId
     * @param num
     */
    void saveSeedInfo(String containerNo,String orderTrayNo,int orderBillId,int orderDetailId,int stationId,int num,int goodsId);


    /**
     * 查最近一条播种记录
     * @param orderDetailId
     * @return
     */
    SeedInfo findSeedInfoByOrderDetail(int orderDetailId);


    /**
     * 根据map查询seedInfo
     * @param map
     * @return
     */
    List<SeedInfo> findSeedInfoByMap(Map map);


    


}
