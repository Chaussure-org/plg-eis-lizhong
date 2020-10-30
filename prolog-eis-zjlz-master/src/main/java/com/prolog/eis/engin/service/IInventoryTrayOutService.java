package com.prolog.eis.engin.service;

import com.prolog.eis.dto.inventory.RickerTaskDto;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/28 11:13
 * 半成品立库盘调度
 */
public interface IInventoryTrayOutService {

    /**
     * 盘点容器出库
     */
    void inventoryTrayOut() throws Exception;

    /**
     * 计算堆垛机任务数
     * @return
     */
    List<RickerTaskDto> computeRickerTask();


    /**
     *获取空货位数
     */
    int getEmptyStore();

    void outUpdateStore(String containerNo) throws Exception;






}
