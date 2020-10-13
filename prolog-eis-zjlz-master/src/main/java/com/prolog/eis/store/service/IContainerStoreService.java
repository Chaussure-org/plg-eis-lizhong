package com.prolog.eis.store.service;

import com.prolog.eis.model.ContainerStore;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/12 18:28
 */
public interface IContainerStoreService {
    /**
     * 更新
     * @param containerStore
     */
    void updateContainerStore(ContainerStore containerStore);

    void updateContainerStoreNum(int num,String containerNo) throws Exception;
}
