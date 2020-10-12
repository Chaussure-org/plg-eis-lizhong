package com.prolog.eis.location.service;

import com.prolog.eis.model.location.StoreArea;

/**
 * @author chenbo
 * @date 2020/9/28 16:59
 */
public interface AreaReserveService {

    /**
     * 获取区域可用货位数
     * @param storeArea
     * @param taskType
     * @return
     * @throws Exception
     */
    int getAreaCanUserLocationCount(StoreArea storeArea, int taskType) throws Exception;
    /**
     * 获取区域预留货位数
     * @param storeArea
     * @param taskType
     * @return
     * @throws Exception
     */
    int getAreaReserveCount(StoreArea storeArea, int taskType) throws Exception;

    /**
     * 获取区域货位数
     * @param storeArea
     * @return
     * @throws Exception
     */
    int getAreaLocationCount(StoreArea storeArea) throws Exception;
}
