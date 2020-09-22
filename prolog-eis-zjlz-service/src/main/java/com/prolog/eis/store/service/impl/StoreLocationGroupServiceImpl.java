package com.prolog.eis.store.service.impl;

import com.prolog.eis.model.store.SxStoreLocationGroup;
import com.prolog.eis.store.dao.StoreLocationGroupMapper;
import com.prolog.eis.store.service.IStoreLocationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:02
 */
@Service
public class StoreLocationGroupServiceImpl implements IStoreLocationGroupService {

    @Autowired
    private StoreLocationGroupMapper storeLocationGroupMapper;

    @Override
    public void saveStoreLocationGroup(SxStoreLocationGroup storeLocationGroup) {
        storeLocationGroupMapper.save(storeLocationGroup);
    }
}
