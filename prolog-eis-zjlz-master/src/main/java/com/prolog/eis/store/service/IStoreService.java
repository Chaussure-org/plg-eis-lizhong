package com.prolog.eis.store.service;

import com.prolog.eis.dto.store.InitStoreDto;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:52
 */
public interface IStoreService {

    /**
     * 生产货位
     * @param initStoreDto 初始化货位参数实体
     * @throws Exception
     */
    void initStore(InitStoreDto initStoreDto) throws Exception;

}
