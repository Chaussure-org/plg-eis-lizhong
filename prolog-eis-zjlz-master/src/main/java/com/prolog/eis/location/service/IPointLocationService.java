package com.prolog.eis.location.service;

import com.prolog.eis.model.PointLocation;

import java.util.*;

/**
 * @Author wangkang
 * @Description wcs点位服务
 * @CreateTime 2020-10-19 9:56
 */
public interface IPointLocationService {

    /**
     * 通过类型找点位
     * @param type 类型
     * @return
     */
    List<PointLocation> getPointByType(int type);

    /**
     * 通过id找点位
     * @param address 原位子
     * @return
     */
    PointLocation getPointByPointId(String address);

    /**
     * 通过站台号找站台点位
     * @param stationId 站台号
     * @return
     */
    PointLocation getPointByStationId(Integer stationId);
}
