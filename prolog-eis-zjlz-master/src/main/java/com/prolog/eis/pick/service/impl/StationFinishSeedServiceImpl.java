package com.prolog.eis.pick.service.impl;

import com.prolog.eis.dto.bz.FinishNotSeedDTO;
import com.prolog.eis.dto.bz.FinishTrayDTO;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.eis.pick.service.IStationBZService;
import com.prolog.eis.pick.service.IStationFinishSeedService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/18 20:33
 */
@Service
public class StationFinishSeedServiceImpl implements IStationFinishSeedService {

    @Autowired
    private IOrderBillService orderBillService;
    @Autowired
    private IStationService stationService;
    @Autowired
    private IStationBZService stationBZService;
    @Autowired
    private IOrderDetailService orderDetailService;

    @Override
    public FinishNotSeedDTO getNotSeedCount() throws Exception {
        FinishNotSeedDTO seedCount = orderBillService.getNoSeedCount();
        List<Station> stations = stationService.findStationByMap(MapUtils.put("stationType", Station.STATION_TYPE_FINISHEDPROD).getMap());
        if (stations.size() != 1){
            throw new Exception("成品库站台配置异常");
        }
        Station station = stations.get(0);
        seedCount.setIsLock(station.getIsLock());
        return seedCount;

    }

    @Override
    public void changeStationIsLock(int isLock) throws Exception {
        stationService.changeFinishStationStatus(isLock);
    }

    @Override
    public void confirmSeed(String containerNo, int num) throws Exception {
        if (StringUtils.isBlank(containerNo)) {
            throw new RuntimeException("容器编号不能为空");
        }
        List<Station> stations = stationService.findStationByMap(MapUtils.put("stationType", Station.STATION_TYPE_FINISHEDPROD).getMap());
        Station station = stations.get(0);
        //获取当前播种拣选单
        List<Integer> orderBillIds = stationService.findPickingOrderBillId(station.getId());
        if (orderBillIds.size() == 0) {
            throw new Exception("站台【" + station.getId() + "】无拣选订单");
        }
        //执行拣选
        ContainerBindingDetail containerBinDings = stationBZService.doPicking(station.getId(), containerNo, num, orderBillIds.get(0), null);
        //
        boolean flag = orderDetailService.orderPickingFinish(orderBillIds.get(0));
        boolean b = orderDetailService.checkOrderDetailFinish(containerBinDings.getOrderDetailId());

        //todo:注释放行
//        this.finishTrayLeave(containerNo);


        if (b) {
            // TODO: 2020/11/6  当前订单明细完成，回告wms
            stationBZService.seedToWms(containerBinDings);
        }
        try {
            if (flag) {
                //切换拣选单
                stationBZService.changePickingOrder(station);
                //明细转历史
                orderBillService.orderBillToHistory(orderBillIds.get(0));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finishTrayLeave(String containerNo) throws Exception {
        //todo：回库
    }

    @Override
    public FinishTrayDTO getFinishSeed(String containerNo) throws Exception {
        if (StringUtils.isBlank(containerNo)) {
            throw new RuntimeException("容器编号不能为空");
        }
        List<Station> stations = stationService.findStationByMap(MapUtils.put("stationType", Station.STATION_TYPE_FINISHEDPROD).getMap());
        if (stations.size() != 1) {
            throw new Exception("成品库站台配置有问题");
        }
        //todo：注释校验
//        if (!containerNo.equals(stations.get(0).getContainerNo())){
//            throw new Exception("容器【"+containerNo+"】不在播种站台");
//        }
        //校验托盘是否有播种任务
        boolean flag = stationBZService.checkContainerToStation(containerNo, stations.get(0).getId());
        if (flag){
            throw new Exception("容器【"+containerNo+"】没有站台的绑定明细");
        }
        List<FinishTrayDTO> finishSeedInfo = orderBillService.getFinishSeedInfo(containerNo, stations.get(0).getCurrentStationPickId());
        if (finishSeedInfo.size() == 0){
            throw new Exception("容器【"+containerNo+"】没有拣选明细");
        }
        //订单第一次拣选回告wms
        stationBZService.startSeedToWms(finishSeedInfo.get(0).getOrderBillId(),finishSeedInfo.get(0).getOrderNo());
        //播种页面展示
        return finishSeedInfo.get(0);

    }


}
