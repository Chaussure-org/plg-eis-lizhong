package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.OrderBillDto;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.engin.dao.FinishedProdOutEnginMapper;
import com.prolog.eis.engin.service.FinishedProdOutEnginService;
import com.prolog.eis.engin.service.TrayOutEnginService;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-14 16:27
 */
@Service
public class FinishedProdOutEnginServiceImpl implements FinishedProdOutEnginService {

    private static final Logger logger = LoggerFactory.getLogger(FinishedProdOutEnginServiceImpl.class);

    @Autowired
    private FinishedProdOutEnginMapper mapper;

    @Autowired
    private IOrderBillService orderBillService;

    @Autowired
    private TrayOutEnginService trayOutEnginService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private IStationService stationService;

    /**
     * 1.优先考虑借道成品
     * 2.初始化成品立库信息
     * 3.初始话订单相关
     * 4.根据成品立库信息以及销售出库订单找取合适的箱子出库（单独方法）
     */

    @Override
    public synchronized void finishProdOutByOrder() throws Exception {
        boolean flag = this.checkStation();
        if (flag) {
            this.initFinishedTrayLib();
            List<OrderBillDto> orderBillDtos =  orderBillService.initFinishProdOrder();
            if (orderBillDtos != null && orderBillDtos.size()>0) {
                this.trayOut(orderBillDtos.get(0));
            }
        }else {
            logger.warn("当前站台锁定状态");
        }
    }

    /**
     * 根据最优订单出库
     * @param orderBillDto 订单实体
     * @throws Exception
     */
    private void trayOut(OrderBillDto orderBillDto) throws Exception {
        Map<String, Object> param = MapUtils.put("orderBillId",orderBillDto.getOrderBillId()).getMap();
        List<OrderDetail> orderDetailByMap = orderDetailService.findOrderDetailByMap(param);
        if (orderDetailByMap != null && orderDetailByMap.size() >0) {
            //所有需要出库的托盘
            List<OutContainerDto> outContainerDtos =
                    trayOutEnginService.outByGoodsId(orderDetailByMap.get(0).getGoodsId(),
                    orderDetailByMap.get(0).getPlanQty(), orderBillDto.getWmsOrderPriority());
            //找一个能出的出
        }
    }

    /**
     * 检查站台当前状态是否完成当前订单
     * @return
     */
    private boolean checkStation() throws Exception {
        return stationService.checkStationStatus();
    }

    /**
     * 初始化成品立库信息
     */
    private void initFinishedTrayLib() {
        this.getCanBeUsedStore();
    }

    /**
     * 获取可用库存
     */
    private void getCanBeUsedStore() {

    }
}
