package com.prolog.eis.store.service;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 14:52
 */
public interface IStoreService {

    /**
     * 初始化货位及货位组
     * @param layerCount
     * @param xCount 巷道数+1
     * @param yCount 行数+1
     * @param ascent 深位数
     * @param factor 组因子
     * @param exList 排除列(子道,母道)
     * @throws Exception
     */
    void initStore(int layerCount, int xCount, int yCount, int ascent, int factor, List<Integer> exList,
                   String areaNo) throws Exception;

}
