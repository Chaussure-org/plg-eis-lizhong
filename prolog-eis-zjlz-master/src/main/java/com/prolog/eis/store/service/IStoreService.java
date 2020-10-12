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
     * 初始化货位及货位组
     * @param layerCount 层+1
     * @param xStart 开始巷道
     * @param xCount 巷道数+1
     * @param yCount 行数+1
     * @param ascent 深位数
     * @param exList 排除列(子道,母道)
     * @throws Exception
     */
    void initStore(int layerCount,int xStart, int xCount, int yCount, int ascent,
                   List<Integer> exList,
                   String areaNo) throws Exception;

    void initStore(InitStoreDto initStoreDto) throws Exception;

}
