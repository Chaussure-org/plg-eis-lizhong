package com.prolog.eis.store.service.impl;

import com.prolog.eis.model.location.sxk.SxStoreLocation;
import com.prolog.eis.store.dao.StoreLocationMapper;
import com.prolog.eis.store.service.IStoreLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:00
 */
@Service
public class StoreLocationServiceImpl implements IStoreLocationService {

    @Autowired
    private StoreLocationMapper storeLocationMapper;

    @Override
    public void saveBatchStoreLocation(List<SxStoreLocation> sxStoreLocations) {
        storeLocationMapper.saveBatch(sxStoreLocations);
    }

    @Override
    public int countTotal() {
        return (int) storeLocationMapper.findCountByMap(null, SxStoreLocation.class);
    }
}
