package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.boxoutdto.StationPickingOrderDto;
import com.prolog.eis.location.dao.AgvBindingDetaileMapper;
import com.prolog.eis.engin.dao.LineBindingDetailMapper;
import com.prolog.eis.engin.service.AgvLineOutEnginService;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.PickingOrder;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.StoreArea;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderDetailCountsDto;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.order.dao.ContainerBindingDetailMapper;
import com.prolog.eis.order.dao.OrderBillMapper;
import com.prolog.eis.station.dao.StationMapper;
import com.prolog.eis.store.dao.PickingOrderMapper;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * sunpp
 * agv和输送线的调度
 */
@Service
public class AgvLineOutEnginServiceImpl implements AgvLineOutEnginService {

    private final Logger logger = LoggerFactory.getLogger(AgvLineOutEnginServiceImpl.class);

    @Autowired
    private AgvBindingDetaileMapper agvBindingDetaileMapper;
    @Autowired
    private LineBindingDetailMapper lineBindingDetailMapper;
    @Autowired
    private StationMapper stationMapper;
    @Autowired
    private PickingOrderMapper pickingOrderMapper;
    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private ContainerBindingDetailMapper containerBindingDetailMapper;
    @Autowired
    private AgvStoragelocationMapper agvStoragelocationMapper;

    @Autowired
    private PathSchedulingService pathSchedulingService;

    @Override
    public void computerPickOrder() throws Exception {
        List<Station> stationsTemp = stationMapper.findByMap(null, Station.class);
        //不锁定的 半成品站台
        List<Station> stations = stationsTemp.stream().filter(x -> x.getIsLock().equals(Station.UN_LOCK) && x.getStationType().equals(Station.STATION_TYPE_UNFINISHEDPROD)).collect(Collectors.toList());

        //如果没用开启的站台
        if (stations.isEmpty()) {
            logger.info("半成品库无没有可用站台=============");
            return;
        }
        for (Station station : stations) {
            List<AgvBindingDetail> details = agvBindingDetaileMapper.findAgvBindingDetails();
            List<StationPickingOrderDto> pickOrders = pickingOrderMapper.findPickOrder();
            if (station.getCurrentStationPickId() == null) {
                //为站台索取一个拣选单
                this.tackPickOrder(station, details, pickOrders);
            } else {
                Optional<StationPickingOrderDto> first = pickOrders.stream().filter(x -> x.getStationId() == station.getId()).findFirst();
                if (first.isPresent()) {
                    //1.站台agv位置为空
                    List<AgvStoragelocation> list = agvStoragelocationMapper.findByMap(
                            MapUtils.put("deviceNo", station.getId()).
                                    put("areaNo", StoreArea.SN01).put("taskLock", 0).put("storageLock", 0).getMap(), AgvStoragelocation.class);
                    if (list.isEmpty()) {
                        //站台无空位
                        return;
                    }
                    //2.该站台 到达的 托盘
                    List<AgvBindingDetail> agvBindList = details.stream().filter(x -> x.getOrderBillId().equals(first.get().getOrderBillId())).collect(Collectors.toList());
                    if (agvBindList.size() > 0) {
                        //生成该站台的料箱绑定明细
                        this.saveContainerBindingDetail(agvBindList);
                        //发送任务 1.此站台没有任务正在执行
                        agvBindList.forEach(x -> {
                            try {
                                pathSchedulingService.containerMoveTask(x.getContainerNo(), StoreArea.SN01, list.get(0).getLocationNo());
                                //锁定此位置的状态
                                agvStoragelocationMapper.updateLocationLock(list.get(0).getLocationNo(), AgvStoragelocation.TASK_LOCK);
                                logger.info("================生成拣选单去往" + station.getId() + "站台的路径=============");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        }
    }


    private void tackPickOrder(Station station, List<AgvBindingDetail> agvDetailsTemp, List<StationPickingOrderDto> pickOrders) throws Exception {
        int stationId = station.getId();
        List<AgvBindingDetail> agvArriveDetails = agvDetailsTemp.stream().filter(x -> x.getOrderPriority().equals(OrderBill.FIRST_PRIORITY)).collect(Collectors.toList());
        List<AgvBindingDetail> irons = agvArriveDetails.stream().filter(x -> x.getIronTray() == 1).collect(Collectors.toList());
        if (agvArriveDetails.size() > 0) {
            //先索取 agv区域到达的 一类 的新订单
            if (station.getStationType().equals(3) && irons.size() > 0) {
                //铁笼托盘
                this.createPickOrder(stationId, pickOrders, irons, false);
            } else {
                this.createPickOrder(stationId, pickOrders, agvArriveDetails, false);
            }
        } else {
            //找循环线体的 新订单,要去往和到达 循环线的箱子
            List<AgvBindingDetail> lineDetails = lineBindingDetailMapper.findLineDetails();
            List<AgvBindingDetail> collect = lineDetails.stream().filter(x -> x.getIronTray() == 1).collect(Collectors.toList());
            if (lineDetails.size() > 0) {
                if (station.getStationType().equals(3) && collect.size() > 0) {
                    this.createPickOrder(stationId, pickOrders, collect, true);
                } else {
                    this.createPickOrder(stationId, pickOrders, lineDetails, true);
                }
            }
        }
    }

    private void createPickOrder(int stationId, List<StationPickingOrderDto> pickOrders, List<AgvBindingDetail> details, boolean b) throws Exception {
        List<AgvBindingDetail> sortDetails = sortDetails(details);
        List<Integer> orderIds = pickOrders.stream().map(StationPickingOrderDto::getOrderBillId).collect(Collectors.toList());
        //排除站台已经索取过了的 订单
        Map<Integer, List<AgvBindingDetail>> map = sortDetails.stream().filter(x -> !orderIds.contains(x.getOrderBillId())).collect(Collectors.groupingBy(AgvBindingDetail::getOrderBillId));
        for (Map.Entry<Integer, List<AgvBindingDetail>> orderMap : map.entrySet()) {
            this.savePickOrder(stationId, orderMap.getKey());
            if (b) {
                this.saveContainerBindingDetail(orderMap.getValue());
            }
            logger.info("==========正在生成拣选单" + orderMap.getValue() + stationId + "站台===========");
            break;
        }
    }

    private List<AgvBindingDetail> sortDetails(List<AgvBindingDetail> detailList) {

        String ids = detailList.stream().map(AgvBindingDetail::getOrderBillId).collect(Collectors.toSet()).toString();
        //判断agv区域订单全部到齐的订单优先索取
        List<OrderDetailCountsDto> orderDetailCounts = orderBillMapper.findOrderDetailCount(ids);
        Map<Integer, List<AgvBindingDetail>> orderMapTemp = detailList.stream().collect(Collectors.groupingBy(AgvBindingDetail::getOrderBillId));
        //先按照wms优先级排序  到达明细最多的 排在前面 再按照时间排序
        Collections.sort(detailList, new Comparator<AgvBindingDetail>() {
            @Override
            public int compare(AgvBindingDetail o1, AgvBindingDetail o2) {
                if (o1.getWmsOrderPriority().equals(OrderBill.WMS_PRIORITY)) {
                    return -1;
                } else if (!o1.getWmsOrderPriority().equals(OrderBill.WMS_PRIORITY)) {
                    return 1;
                } else {
                    int o1Count = orderDetailCounts.stream().filter(x -> x.getOrderBillId() == o1.getOrderBillId()).collect(Collectors.toList()).size() - orderMapTemp.get(o1.getOrderBillId()).size();
                    int o2Count = orderDetailCounts.stream().filter(x -> x.getOrderBillId() == o2.getOrderBillId()).collect(Collectors.toList()).size() - orderMapTemp.get(o2.getOrderBillId()).size();

                    return o1Count - o2Count;
                }
            }
        });
        return detailList;
    }


    /**
     * 保存容器绑定明细
     */
    private void saveContainerBindingDetail(List<AgvBindingDetail> detailList) {
        List<ContainerBindingDetail> containerBindingDetails = new ArrayList<>();
        for (AgvBindingDetail agvBindingDetail : detailList) {
            ContainerBindingDetail containerBindingDetail = new ContainerBindingDetail();
            containerBindingDetail.setContainerNo(agvBindingDetail.getContainerNo());
            containerBindingDetail.setBindingNum(agvBindingDetail.getBindingNum());
            containerBindingDetail.setSeedNum(agvBindingDetail.getBindingNum());
            containerBindingDetail.setOrderBillId(agvBindingDetail.getOrderBillId());
            containerBindingDetail.setOrderDetailId(agvBindingDetail.getOrderMxId());
            containerBindingDetails.add(containerBindingDetail);
        }
        containerBindingDetailMapper.saveBatch(containerBindingDetails);
    }


    @Transactional(rollbackFor = Exception.class)
    public void savePickOrder(int stationId, Integer orderBillId) throws Exception {
        //保存拣选单
        PickingOrder pickingOrder = new PickingOrder();
        String pickOrderNo = PrologStringUtils.newGUID();
        pickingOrder.setStationId(stationId);
        pickingOrder.setCurrentSeedBoxNo(pickOrderNo);
        pickingOrder.setOrderState(0);
        pickingOrder.setOrderStartTime(new Date());
        pickingOrder.setUpdateTime(new Date());
        pickingOrderMapper.save(pickingOrder);
        //更新站台的拣选单
        stationMapper.updateMapById(stationId, MapUtils.put("currentStationPickId", pickingOrder.getId()).getMap(), Station.class);
        //更新订单的 拣选单id
        orderBillMapper.updateMapById(orderBillId, MapUtils.put("pickingOrderId", pickingOrder.getId()).getMap(), OrderBill.class);
    }


/*    private void saveLineContainerDetail(List<LineBindingDetail> detailList) {
        List<ContainerBindingDetail> containerBindingDetails = new ArrayList<>();
        for (LineBindingDetail lineBindingDetail : detailList) {
            ContainerBindingDetail containerBindingDetail = new ContainerBindingDetail();
            containerBindingDetail.setContainerNo(lineBindingDetail.getContainerNo());
            containerBindingDetail.setBindingNum(lineBindingDetail.getBindingNum());
            containerBindingDetail.setOrderBillId(lineBindingDetail.getOrderBillId());
            containerBindingDetail.setOrderDetailId(lineBindingDetail.getOrderMxId());
            containerBindingDetails.add(containerBindingDetail);
        }
        containerBindingDetailMapper.saveBatch(containerBindingDetails);
    }*/

/*    List<LineBindingDetail> sortLineDetails(List<LineBindingDetail> detailList) {

        String ids = detailList.stream().map(LineBindingDetail::getOrderBillId).collect(Collectors.toSet()).toString();
        //判断agv区域订单全部到齐的订单优先索取
        List<OrderDetailCountsDto> orderDetailCounts = orderBillMapper.findOrderDetailCount(ids);
        Map<Integer, List<LineBindingDetail>> orderMapTemp = detailList.stream().sorted(Comparator.comparing(LineBindingDetail::getUpdateTime)).collect(Collectors.groupingBy(LineBindingDetail::getOrderBillId));
        //先按照wms优先级排序  到达明细最多的 排在前面
        Collections.sort(detailList, new Comparator<LineBindingDetail>() {
            @Override
            public int compare(LineBindingDetail o1, LineBindingDetail o2) {
                if (o1.getWmsOrderPriority().equals(OrderBill.WMS_PRIORITY)) {
                    return -1;
                } else if (!o1.getWmsOrderPriority().equals(OrderBill.WMS_PRIORITY)) {
                    return 1;
                } else {
                    int o1Count = orderDetailCounts.stream().filter(x -> x.getOrderBillId() == o1.getOrderBillId()).collect(Collectors.toList()).size() - orderMapTemp.get(o1.getOrderBillId()).size();
                    int o2Count = orderDetailCounts.stream().filter(x -> x.getOrderBillId() == o2.getOrderBillId()).collect(Collectors.toList()).size() - orderMapTemp.get(o2.getOrderBillId()).size();

                    return o1Count - o2Count;
                }
            }
        });
        return detailList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void takePickOrderByLineAndAgv(List<LineBindingDetail> detailList) throws Exception {
        //所有的站台集合
        List<Station> stationsTemp = stationMapper.findByMap(null, Station.class);
        List<Station> stations = stationsTemp.stream().filter(x -> x.getIsLock().equals(Station.UN_LOCK)).collect(Collectors.toList());
        List<StationPickingOrderDto> pickOrders = pickingOrderMapper.findPickOrder();
        //如果没用开启的站台
        if (stations.isEmpty()) {
            return;
        }
        List<LineBindingDetail> sortdetails = this.sortLineDetails(detailList);


        List<Integer> orderIds = pickOrders.stream().map(StationPickingOrderDto::getOrderBillId).collect(Collectors.toList());
        //不包含 站台 的orderId 的details
        Map<Integer, List<LineBindingDetail>> map = sortdetails.stream().filter(x -> !orderIds.contains(x.getOrderBillId())).
                collect(Collectors.groupingBy(LineBindingDetail::getOrderBillId));
        for (Station station : stations) {
            //如果站台没有拣选单
            if (station.getCurrentStationPickId() == null) {
                for (Map.Entry<Integer, List<LineBindingDetail>> orderMap : map.entrySet()) {
                    //生成拣选单
                    this.savePickOrder(station, orderMap.getKey());
                    //1.生成订单绑定明细
                    this.saveLineContainerDetail(orderMap.getValue());
                }
            } else {
                //如果站台有拣选单
                for (StationPickingOrderDto pickingOrder : pickOrders) {
                    if (station.getCurrentStationPickId().equals(pickingOrder.getPickingOrderId())) {
                        //1.站台agv位置为空
                        List<AgvStoragelocation> list = agvStoragelocationMapper.findByMap(
                                MapUtils.put("deviceNo", station.getId()).put("areaNo", "SA").put("taskLock", 1).getMap(), AgvStoragelocation.class);
                        if (list.isEmpty()) {
                            //站台无空位
                            return;
                        }
                        //2.agv区域 无任务的托盘 属于该站台的
                        Optional<LineBindingDetail> first = sortdetails.stream().filter(x -> x.getOrderBillId().equals(pickingOrder.getOrderBillId())).findFirst();
                        if (first.isPresent()) {
                            //尾托的概念不考虑，生成路径
                            List<AgvStoragelocation> cintainerNos = agvStoragelocationMapper.findByMap(MapUtils.put("cintainerNo", first.get().getContainerNo()).getMap(), AgvStoragelocation.class);
                            if (!cintainerNos.isEmpty()) {
                                //发送任务 1.此站台没有任务正在执行
                                pathSchedulingService.containerMoveTask(first.get().getContainerNo(), AgvStoragelocation.AGV_STATION_LOCATION, pickingOrder.getLocationNo());
                                //锁定此位置的状态
                                agvStoragelocationMapper.updateLocationLock(pickingOrder.getLocationNo());
                            }
                        }
                    }
                }
            }

        }
    }*/

}
