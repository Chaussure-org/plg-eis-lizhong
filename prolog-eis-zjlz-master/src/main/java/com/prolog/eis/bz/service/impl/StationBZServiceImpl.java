package com.prolog.eis.bz.service.impl;

import com.prolog.eis.bz.service.IStationBZService;
import com.prolog.eis.model.Station;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.order.service.IContainerBindingDetailService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/9/28 12:17
 */
public class StationBZServiceImpl implements IStationBZService {
    @Autowired
    private IContainerBindingDetailService containerBindingDetailService;
    @Autowired
    private IStationService stationService;
    /**1、判断该站台是否有拣选单，如无则写入到拣选站
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
    public void startBZPicking(int stationId, String containerNo, String orderBoxNo) throws Exception {
       if (StringUtils.isBlank(containerNo)){
           throw new RuntimeException("容器编号不能为空");
       }
       if (StringUtils.isBlank(orderBoxNo)){
           throw new RuntimeException("容器编号不能为空");
       }

//        校验是否当前第一个在播明细是则写入拣选单号
        Station station = stationService.findById(stationId);
        if (station == null){
            throw new RuntimeException(stationId+"站台不存在");
        }
        List<ContainerBindingDetail> containerBinDings = containerBindingDetailService.findMap(MapUtils.put("containerNo", containerNo).getMap());
        if (containerBinDings.size() == 0 ){
            throw new Exception("容器【"+containerNo+"】无在播明细");
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
