package com.prolog.eis.store.service;

import com.prolog.eis.model.location.sxk.SxStoreLocation;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:59
 */
public interface IStoreLocationService {

    void saveStoreLocation(SxStoreLocation sxStoreLocation);

    int countTotal();
}
