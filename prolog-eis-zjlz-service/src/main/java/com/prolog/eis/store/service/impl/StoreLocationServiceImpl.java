package com.prolog.eis.store.service.impl;

import com.prolog.eis.model.store.SxStoreLocation;
import com.prolog.eis.store.dao.StoreLocationMapper;
import com.prolog.eis.store.service.IStoreLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void saveStoreLocation(SxStoreLocation sxStoreLocation) {
        storeLocationMapper.save(sxStoreLocation);
    }
}
