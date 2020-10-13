package com.prolog.eis.order.service;

import com.prolog.eis.model.order.SeedInfo;

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
    void saveSeedInfo(String containerNo,String orderTrayNo,int orderBillId,int orderDetailId,int stationId,int num);

    
}
