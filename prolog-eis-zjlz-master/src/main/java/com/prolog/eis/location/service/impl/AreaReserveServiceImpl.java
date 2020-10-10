package com.prolog.eis.location.service.impl;

import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.location.dao.AreaReserveMapper;
import com.prolog.eis.location.dao.ContainerPathTaskMapper;
import com.prolog.eis.location.dao.SxStoreLocationMapper;
import com.prolog.eis.location.service.AreaReserveService;
import com.prolog.eis.model.location.AreaReserve;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.util.location.LocationConstants;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenbo
 * @date 2020/9/28 16:59
 */
@Service
public class AreaReserveServiceImpl implements AreaReserveService {

    @Autowired
    private AreaReserveMapper areaReserveMapper;
    @Autowired
    private AgvStoragelocationMapper agvStoragelocationMapper;
    @Autowired
    private SxStoreLocationMapper sxStoreLocationMapper;
    @Autowired
    private ContainerPathTaskMapper containerPathTaskMapper;

    /**
     * 获取区域剩余可用货位数
     * @param storeArea
     * @param taskType
     * @return
     * @throws Exception
     */
    @Override
    public int getAreaCanUserLocationCount(StoreArea storeArea, int taskType) throws Exception {

        //获取区域预留数
        int reserveCount = this.getAreaReserveCount(storeArea,taskType);
        //获取总货位数
        int locationCount = this.getAreaLocationCount(storeArea);
        //获取区域容器数
        int containerCount = containerPathTaskMapper.getStoreAreaContainerCountByArea(storeArea.getAreaNo());

        return locationCount - containerCount - reserveCount;
    }

    /**
     * 获取区域预留货位数
     * @param storeArea
     * @param taskType
     * @return
     * @throws Exception
     */
    @Override
    public int getAreaReserveCount(StoreArea storeArea, int taskType) throws Exception {

        AreaReserve areaReserve = areaReserveMapper.findFirstByMap(MapUtils.put("areaNo",storeArea.getAreaNo()).put("taskType",taskType).getMap(), AreaReserve.class);
        if(null == areaReserve){
            return 0;
        }

        switch (areaReserve.getReserveType()){
            case LocationConstants.AREA_RESERVE_TYPE_NUMBER:
                return areaReserve.getReserveCount();
            case LocationConstants.AREA_RESERVE_TYPE_PERCENTAGE:
                return this.getAreaPercentageCount(storeArea,areaReserve.getReserveCount());
            default:
                throw new Exception(String.format("ReserveType异常：%s",String.valueOf(areaReserve.getReserveType())));
        }
    }

    @Override
    public int getAreaLocationCount(StoreArea storeArea) throws Exception {

        switch (storeArea.getDeviceSystem()){
            case LocationConstants.DEVICE_SYSTEM_MCS:
                return sxStoreLocationMapper.getAreaLocationCount(storeArea.getAreaNo());
            case LocationConstants.DEVICE_SYSTEM_RCS:
                return agvStoragelocationMapper.getAreaLocationCount(storeArea.getAreaNo());
            default:
                throw new Exception(String.format("区域厂商未配置：%s",storeArea.getAreaNo()));
        }
    }

    private int getAreaPercentageCount(StoreArea storeArea, int percentage) throws Exception {

        int totalCount = 0;
        switch (storeArea.getDeviceSystem()){
            case LocationConstants.DEVICE_SYSTEM_MCS:
                totalCount = sxStoreLocationMapper.getAreaLocationCount(storeArea.getAreaNo());
                break;
            case LocationConstants.DEVICE_SYSTEM_RCS:
                totalCount = agvStoragelocationMapper.getAreaLocationCount(storeArea.getAreaNo());
                break;
            default:
                throw new Exception(String.format("区域厂商未配置：%s",storeArea.getAreaNo()));
        }

        int result = totalCount * percentage / 100;
        return  result;
    }
}
