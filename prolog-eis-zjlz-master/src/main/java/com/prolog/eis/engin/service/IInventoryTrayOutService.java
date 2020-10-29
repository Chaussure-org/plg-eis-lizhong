package com.prolog.eis.engin.service;

import com.prolog.eis.dto.inventory.InventoryOutDto;
import org.apache.ibatis.annotations.Param;

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
    void inventoryTrayOut();

}
