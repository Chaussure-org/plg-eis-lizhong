package com.prolog.eis.inventory.service;

import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description 盘点详情服务类
 * @CreateTime 2020-10-14 14:49
 */
public interface IInventoryTaskDetailService {
    /**
     * 通过map中得值去找盘点明细
     * @param param map
     */
    List getDetailsByMap(Map<String, Object> param);
}
