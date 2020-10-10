package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.PickingAreaDto;
import com.prolog.eis.engin.dao.AgvBindingDetaileMapper;
import com.prolog.eis.engin.dao.LineBindingDetailMapper;
import com.prolog.eis.engin.service.AgvLineOutEnginService;
import com.prolog.eis.model.Station;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.station.dao.StationMapper;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        List<AgvBindingDetail> agvDetails = agvBindingDetaileMapper.findByMap(MapUtils.put("orderPriority", OrderBill.FIRST_PRIORITY).getMap(), AgvBindingDetail.class);
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
        List<Station> stations = stationMapper.findByMap(null, Station.class);
        for (Station station : stations) {
            if (station.getCurrentStationPickId() == null) {
            //生成拣选单

            }
        }
    }

    @Override
    public void tackPickOrder() throws Exception {

    }
}
