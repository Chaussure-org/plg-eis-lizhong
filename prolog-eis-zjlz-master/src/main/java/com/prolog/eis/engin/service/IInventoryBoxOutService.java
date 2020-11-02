package com.prolog.eis.engin.service;

import java.io.IOException;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/29 18:00
 * 箱库盘点
 */
public interface IInventoryBoxOutService {

    /**
     * 箱库出库调度
     */
    void inventoryBoxOut() throws Exception;


    /**
     * 盘点料箱分配拣选站
     * @param containerNo 容器号
     * @param address 起点位置
     */
    void inventoryAllotStation(String containerNo,String address) throws Exception;
}
