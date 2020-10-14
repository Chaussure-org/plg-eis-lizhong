package com.prolog.eis.store.service;

import com.prolog.eis.model.store.SxStoreLocation;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:59
 */
public interface IStoreLocationService {

    void saveBatchStoreLocation(List<SxStoreLocation> sxStoreLocations);

    int countTotal();
}
