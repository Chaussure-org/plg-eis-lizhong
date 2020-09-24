package com.prolog.eis.init.service.impl;

import com.prolog.eis.init.service.DataInitService;
import com.prolog.eis.store.service.IStoreLocationService;
import com.prolog.eis.store.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 16:03
 */
@Service
public class DataInitServiceImpl implements DataInitService {

    @Autowired
    private IStoreLocationService locationService;

    @Autowired
    private IStoreService storeService;

    @Override
    public void initStoreDate() {
        int count = locationService.countTotal();
        if ("0".equals(count)){
            //storeService.initStore();
        }
    }
}
