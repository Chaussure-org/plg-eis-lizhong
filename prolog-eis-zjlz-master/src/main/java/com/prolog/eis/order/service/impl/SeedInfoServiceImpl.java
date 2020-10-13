package com.prolog.eis.order.service.impl;

import com.prolog.eis.model.order.SeedInfo;
import com.prolog.eis.order.dao.SeedInfoMapper;
import com.prolog.eis.order.service.ISeedInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/13 17:24
 */
@Service
public class SeedInfoServiceImpl implements ISeedInfoService {
    @Autowired
    private SeedInfoMapper seedInfoMapper;
    @Override
    public void saveSeedInfo(String containerNo,String orderTrayNo,int orderBillId,int orderDetailId,int stationId,int num) {
        SeedInfo seedInfo = new SeedInfo();
        seedInfo.setContainerNo(containerNo);
        seedInfo.setNum(num);
        seedInfo.setOrderBillId(orderBillId);
        seedInfo.setOrderDetailId(orderDetailId);
        seedInfo.setOrderTrayNo(orderTrayNo);
        seedInfo.setStationId(stationId);
        seedInfo.setCreateTime(new Date());
        seedInfoMapper.save(seedInfo);
    }
}
