package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.PickingAreaDto;
import com.prolog.eis.engin.dao.AgvBindingDetaileMapper;
import com.prolog.eis.engin.dao.LineBindingDetailMapper;
import com.prolog.eis.engin.service.AgvLineOutEnginService;
import com.prolog.eis.model.PickingOrder;
import com.prolog.eis.model.Station;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.order.dao.ContainerBindingDetailMapper;
import com.prolog.eis.order.dao.OrderBillMapper;
import com.prolog.eis.station.dao.StationMapper;
import com.prolog.eis.store.dao.PickingOrderMapper;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * sunpp
 * agv和输送线的调度
 */
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
    public PickingAreaDto init() throws Exception {
        return null;
    }

    @Override
    public List<OrderBill> computerPickOrder(List<OrderBill> orderBills) throws Exception {
        // 站台分配拣选单
        //  一.生成拣选单
        //  1.优先找agv_binding_detail
        //  2.根据时间找，优先级为 1 的订单
        //  3.如果没有了找 line_binding_detail 里的订单
        //  二 生成拣选单
        //  1.找到合适的站台分配拣选单

        //1.优先找agv_binding_detail 到达agv区域的订单明细 排除站台已经获取的订单
        List<AgvBindingDetail> agvDetailsTemp = agvBindingDetaileMapper.findAgvBindingDetails();
        List<AgvBindingDetail> agvDetails = agvDetailsTemp.stream().filter(x -> x.getOrderPriority().equals(OrderBill.FIRST_PRIORITY)).collect(Collectors.toList());
        if (agvDetails.isEmpty()) {

        } else {
            //越早的越在前面
            Map<Integer, List<AgvBindingDetail>> orderMap = agvDetails.stream().sorted(Comparator.comparing(AgvBindingDetail::getUpdateTime)).collect(Collectors.groupingBy(AgvBindingDetail::getOrderBillId));

        }

        return null;
    }

    /**
     * 为站台索取一个拣选单 并选择最合适的站台出一个托盘
     *
     * @param map
     */
    private void stationTakePickOrder(Map<Integer, List<AgvBindingDetail>> map) {
        //所有的站台集合
        List<Station> stationsTemp = stationMapper.findByMap(null, Station.class);
        List<Station> stations = stationsTemp.stream().filter(x -> x.getIsLock().equals(Station.UN_LOCK)).collect(Collectors.toList());
        List<PickingOrder> pickOrders = pickingOrderMapper.findByMap(null, PickingOrder.class);
        //如果没用开启的站台
        if (stations.isEmpty()) {
            return;
        }
        for (Map.Entry<Integer, List<AgvBindingDetail>> orderMap : map.entrySet()) {
            for (Station station : stations) {
                if (station.getCurrentStationPickId() == null) {
                    //生成拣选单
                    this.savePickOrder(station, orderMap.getKey());
                    //1.生成订单绑定明细 2.生成路径 3.删除agv_binding_detail
                    this.saveContainerBindingDetail(orderMap);
                    // TODO: 2020/10/12  生成路径
                    //删除agv_binding_detail
                    agvBindingDetaileMapper.deleteByMap(MapUtils.put("orderBillId",orderMap.getKey()).getMap(),AgvBindingDetail.class);
                    return;
                }
            }
        }
        //找到合适的站台出托盘 1.站台的agv位置是为空的
        Station station = new Station();
        Optional<PickingOrder> pickingOrder = pickOrders.stream().filter(x -> x.getId().equals(station.getCurrentStationPickId())).findFirst();
        if (pickingOrder.isPresent()) {
            //1.生成订单绑定明细 2.生成路径 3.删除agv_binding_detail

        }
        /**
         * 站台索取订单 1.全部生成订单binding_detail 2.删除agv_binding_detail 3.全部生成路径
         * 这样每次站台 只需要判断站台 有没有订单
         */

    }

    @Override
    public void tackPickOrder() throws Exception {

    }

    /**
     * 保存容器绑定明细
     */
    private void saveContainerBindingDetail(Map.Entry<Integer, List<AgvBindingDetail>> orderMap) {
        List<ContainerBindingDetail> containerBindingDetails = new ArrayList<>();
        for (AgvBindingDetail agvBindingDetail : orderMap.getValue()) {
            ContainerBindingDetail containerBindingDetail = new ContainerBindingDetail();
            containerBindingDetail.setContainerNo(agvBindingDetail.getContainerNo());
            containerBindingDetail.setBindingNum(agvBindingDetail.getBindingNum());
            containerBindingDetail.setOrderBillId(agvBindingDetail.getOrderBillId());
            containerBindingDetail.setOrderDetailId(agvBindingDetail.getOrderMxId());
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
