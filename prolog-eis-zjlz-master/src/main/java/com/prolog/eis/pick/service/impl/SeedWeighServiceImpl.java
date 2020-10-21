package com.prolog.eis.pick.service.impl;

import com.prolog.eis.model.order.SeedWeigh;
import com.prolog.eis.pick.dao.SeedWeighMapper;
import com.prolog.eis.pick.service.ISeedWeighService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/21 9:21
 */
@Service
public class SeedWeighServiceImpl implements ISeedWeighService {

    @Autowired
    private SeedWeighMapper seedWeighMapper;
    @Override
    public BigDecimal getBeforeOrderTrayWeight(int orderBillId, String orderTrayNo, int seedId) {
        return seedWeighMapper.getBeforeOrderTrayWeight(orderBillId,orderTrayNo,seedId);
    }

    @Override
    public List<SeedWeigh> findSeedWeighByMap(Map map) {
        return seedWeighMapper.findByMap(map,SeedWeigh.class);
    }

    @Override
    public void saveSeedWeigh(SeedWeigh seedWeigh) {
        seedWeighMapper.save(seedWeigh);
    }

    @Override
    public void updateSeedWeigh(SeedWeigh seedWeigh) {
        seedWeighMapper.update(seedWeigh);
    }
}
