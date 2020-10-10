package com.prolog.eis.bz.service.impl;

import com.prolog.eis.bz.service.IStationBZService;
import com.prolog.framework.utils.StringUtils;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/9/28 12:17
 */
public class StationBZServiceImpl implements IStationBZService {
    /**1、判断该站台是否有拣选站，如无则写入拣选站
     * 2、校验托盘或料箱是否在拣选站
     * 3、校验托盘或料箱是否是该站台的拣选任务(否剔除)
     * 4、开始播种作业
     * 5、订单托满换托盘
     * 6、订单结束站台置空
     * @param stationId 站台id
     * @param containerNo 容器编号
     * @param orderBoxNo 订单框编号
     */
    @Override
    public void startBZPicking(int stationId, String containerNo, String orderBoxNo) {
       if (StringUtils.isBlank(containerNo)){
           throw new RuntimeException("容器编号不能为空");
       }
       if (StringUtils.isBlank(orderBoxNo)){
           throw new RuntimeException("容器编号不能为空");
       }

    }

    @Override
    public void pickingConfirm(int stationId, String containerNo, String orderBoxNo) {

    }

    @Override
    public boolean checkContainerToStation(String containerNo, int stationId) {
        return false;
    }

    @Override
    public boolean checkContainerExist(String containerNo, int stationId) {
        return false;
    }
}
