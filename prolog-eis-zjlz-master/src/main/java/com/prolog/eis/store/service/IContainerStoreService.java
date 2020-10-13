package com.prolog.eis.store.service;

import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.GoodsInfo;

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

    /**
     * 修改库存
     * @param num
     * @param containerNo
     * @throws Exception
     */
    void updateContainerStoreNum(int num,String containerNo) throws Exception;


    /**
     * 根据托盘获取商品库存信息
     * @param containerNo
     * @return
     */
    GoodsInfo findContainerStockInfo(String containerNo);

    /**
     * 获取商品的分配货位属性1
     * @param goodsInfo
     * @return
     */
    String buildTaskProperty1(GoodsInfo goodsInfo);

    /**
     * 获取商品的分配货位属性2
     * @param goodsInfo
     * @return
     */
    String buildTaskProperty2(GoodsInfo goodsInfo);

    /**
     * 将托盘库存设置位空托盘
     * @param containerNo 托盘号
     * @return
     */
    boolean setContainerStoreEmpty(String containerNo);
}
