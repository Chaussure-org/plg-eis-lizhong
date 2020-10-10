package com.prolog.eis.store.service;

import com.prolog.eis.model.location.sxk.SxStoreLocationGroup;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:01
 */
public interface IStoreLocationGroupService {

    /**
     * 保存货位组
     * @param storeLocationGroup 货位组实体类
     */
    void saveStoreLocationGroup(SxStoreLocationGroup storeLocationGroup);
}
