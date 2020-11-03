package com.prolog.eis.engin.service;

import com.prolog.eis.dto.inventory.RickerInfoDto;
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

    /**
     * 容器出库修改状态
     * @param containerNo
     * @throws Exception
     */
    void outUpdateStore(String containerNo) throws Exception;


    /**
     * 巷道任务容器查询
     * @return
     */
    List<RickerInfoDto> getRickerInfos();






}
