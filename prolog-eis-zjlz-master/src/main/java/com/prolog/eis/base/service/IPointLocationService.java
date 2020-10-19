package com.prolog.eis.base.service;

import com.prolog.eis.model.PointLocation;

import java.util.List;
import java.util.Map;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/19 15:22
 */
public interface IPointLocationService {

    /**
     * 根据map查询
     * @param map
     * @return
     */
    List<PointLocation> findByMap(Map map);
}
