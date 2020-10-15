package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.boxoutdto.StationPickingOrderDto;
import com.prolog.eis.engin.dao.AgvBindingDetaileMapper;
import com.prolog.eis.engin.dao.LineBindingDetailMapper;
import com.prolog.eis.engin.service.AgvLineOutEnginService;
import com.prolog.eis.model.PickingOrder;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.eis.model.line.LineBindingDetail;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * sunpp
 * agv和输送线的调度
 */
@Service
public class AgvLineOutEnginServiceImpl implements AgvLineOutEnginService {

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



    @Override
    public List<OrderBill> computerPickOrder() throws Exception {
        // 站台分配拣选单
        //  一.生成拣选单
        //  1.优先找agv_binding_detail
        //  2.根据时间找，优先级为 1 的订单
        //  3.如果没有了找 line_binding_detail 里的订单
        //  二 生成拣选单
        //  1.找到合适的站台分配拣选单

        //1.优先找agv_binding_detail 已经到达到达agv区域的订单明细
        List<AgvBindingDetail> agvDetailsTemp = agvBindingDetaileMapper.findAgvBindingDetails();
        if (!agvDetailsTemp.isEmpty()) {
            List<AgvBindingDetail> agvDetails = agvDetailsTemp.stream().filter(x -> x.getOrderPriority().equals(OrderBill.FIRST_PRIORITY)).collect(Collectors.toList());
            if (agvDetails.isEmpty()) {
                //找已经到达循环线的箱子
                List<LineBindingDetail> lineDetails = lineBindingDetailMapper.findLineDetails();
                this.takePickOrderByLineAndAgv(lineDetails);
            } else {
                this.takePickOrderByAgv(agvDetails);
            }
        }
        return null;
    }


    /**
     * 站台索取订单 1.全部生成订单binding_detail 2.删除agv_binding_detail 3.全部生成路径
     * 这样每次站台 只需要判断站台 有没有订单
     */
    @Transactional(rollbackFor = Exception.class)
    public void takePickOrderByAgv(List<AgvBindingDetail> detailList) throws Exception {

        //所有的站台集合
        List<Station> stationsTemp = stationMapper.findByMap(null, Station.class);
        List<Station> stations = stationsTemp.stream().filter(x -> x.getIsLock().equals(Station.UN_LOCK)).collect(Collectors.toList());
        List<StationPickingOrderDto> pickOrders = pickingOrderMapper.findPickOrder();
        //如果没用开启的站台
        if (stations.isEmpty()) {
            return;
        }
        String ids = detailList.stream().map(AgvBindingDetail::getOrderBillId).collect(Collectors.toSet()).toString();
        //判断agv区域订单全部到齐的订单优先索取
        List<OrderDetailCountsDto> orderDetailCounts = orderBillMapper.findOrderDetailCount(ids);
        Map<Integer, List<AgvBindingDetail>> orderMapTemp = detailList.stream().sorted(Comparator.comparing(AgvBindingDetail::getUpdateTime)).collect(Collectors.groupingBy(AgvBindingDetail::getOrderBillId));
        //先按照wms优先级排序  到达明细最多的 排在前面
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
        Map<Integer, List<AgvBindingDetail>> map = detailList.stream().collect(Collectors.groupingBy(AgvBindingDetail::getOrderBillId));
        for (Station station : stations) {
            //如果站台没有拣选单
            if (station.getCurrentStationPickId() == null) {
                //生成拣选单

                for (Map.Entry<Integer, List<AgvBindingDetail>> orderMap : map.entrySet()) {
                    this.savePickOrder(station, orderMap.getKey());
                    //1.生成订单绑定明细 2.生成路径 3.删除agv_binding_detail
                    this.saveAgvContainerBindingDetail(orderMap.getValue());
                    // TODO: 2020/10/12  生成路径
                    //删除agv_binding_detail
                    agvBindingDetaileMapper.deleteByMap(MapUtils.put("orderBillId", orderMap.getKey()).getMap(), AgvBindingDetail.class);
                }
            } else {
                //如果站台有拣选单
                for (StationPickingOrderDto pickingOrder : pickOrders) {
                    if (station.getCurrentStationPickId().equals(pickingOrder.getPickingOrderId())) {
                        //1.生成订单绑定明细 2.生成路径 3.删除agv_binding_detail
                        this.saveAgvContainerBindingDetail(map.get(pickingOrder.getOrderBillId()));
                        // TODO: 2020/10/12  生成路径
                        //删除agv_binding_detail
                        agvBindingDetaileMapper.deleteByMap(MapUtils.put("orderBillId", pickingOrder.getOrderBillId()).getMap(), AgvBindingDetail.class);
                    }
                }
            }

        }
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
        Map<Integer, List<LineBindingDetail>> map = detailList.stream().collect(Collectors.groupingBy(LineBindingDetail::getOrderBillId));
        for (Station station : stations) {
            //如果站台没有拣选单
            if (station.getCurrentStationPickId() == null) {
                for (Map.Entry<Integer, List<LineBindingDetail>> orderMap : map.entrySet()) {
                    //生成拣选单
                    this.savePickOrder(station, orderMap.getKey());
                    //1.生成订单绑定明细
                    // TODO: 2020/10/12  生成路径
                    //删除agv_binding_detail
                    agvBindingDetaileMapper.deleteByMap(MapUtils.put("orderBillId", orderMap.getKey()).getMap(), AgvBindingDetail.class);
                    return;
                }
            } else {
                //如果站台有拣选单
                for (StationPickingOrderDto pickingOrder : pickOrders) {
                    if (station.getCurrentStationPickId().equals(pickingOrder.getPickingOrderId())) {
                        //1.生成订单绑定明细 2.生成路径 3.删除agv_binding_detail
                        this.saveLineContainerDetail(map.get(pickingOrder.getOrderBillId()));
                        // TODO: 2020/10/12  生成路径
                        //删除agv_binding_detail
                        agvBindingDetaileMapper.deleteByMap(MapUtils.put("orderBillId", pickingOrder.getOrderBillId()).getMap(), AgvBindingDetail.class);
                    }
                }
            }

        }
    }


    /**
     * 保存容器绑定明细
     */
    private void saveAgvContainerBindingDetail(List<AgvBindingDetail> detailList) {
        List<ContainerBindingDetail> containerBindingDetails = new ArrayList<>();
        for (AgvBindingDetail agvBindingDetail : detailList) {
            ContainerBindingDetail containerBindingDetail = new ContainerBindingDetail();
            containerBindingDetail.setContainerNo(agvBindingDetail.getContainerNo());
            containerBindingDetail.setBindingNum(agvBindingDetail.getBindingNum());
            containerBindingDetail.setOrderBillId(agvBindingDetail.getOrderBillId());
            containerBindingDetail.setOrderDetailId(agvBindingDetail.getOrderMxId());
        }
        containerBindingDetailMapper.saveBatch(containerBindingDetails);
    }

    private void saveLineContainerDetail(List<LineBindingDetail> detailList) {
        List<ContainerBindingDetail> containerBindingDetails = new ArrayList<>();
        for (LineBindingDetail lineBindingDetail : detailList) {
            ContainerBindingDetail containerBindingDetail = new ContainerBindingDetail();
            containerBindingDetail.setContainerNo(lineBindingDetail.getContainerNo());
            containerBindingDetail.setBindingNum(lineBindingDetail.getBindingNum());
            containerBindingDetail.setOrderBillId(lineBindingDetail.getOrderBillId());
            containerBindingDetail.setOrderDetailId(lineBindingDetail.getOrderMxId());
        }
        containerBindingDetailMapper.saveBatch(containerBindingDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    public void savePickOrder(Station station, Integer orderBillId) {
        //保存拣选单
        PickingOrder pickingOrder = new PickingOrder();
        String pickOrderNo = PrologStringUtils.newGUID();
        pickingOrder.setStationId(station.getId());
        pickingOrder.setCurrentSeedBoxNo(pickOrderNo);
        pickingOrder.setOrderState(0);
        pickingOrder.setOrderStartTime(new Date());
        pickingOrder.setUpdateTime(new Date());
        pickingOrderMapper.save(pickingOrder);
        //更新站台的拣选单
        stationMapper.updateMapById(station.getId(), MapUtils.put("currentStationPickId", pickingOrder.getId()).getMap(), Station.class);
        //更新订单的 拣选单id
        orderBillMapper.updateMapById(orderBillId, MapUtils.put("pickOrderId", pickingOrder.getId()).getMap(), OrderBill.class);
    }
}
