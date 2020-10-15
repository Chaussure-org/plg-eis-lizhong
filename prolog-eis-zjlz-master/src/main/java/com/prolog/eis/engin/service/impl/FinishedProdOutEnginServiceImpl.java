package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.OrderBillDto;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.engin.dao.FinishedProdOutEnginMapper;
import com.prolog.eis.engin.service.FinishedProdOutEnginService;
import com.prolog.eis.engin.service.TrayOutEnginService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private IContainerStoreService containerStoreService;

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
            Map<Integer, Integer> map = this.initFinishedTrayLib();
            List<OrderBillDto> orderBillDtos =  orderBillService.initFinishProdOrder(map);
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
            for (OrderDetail orderDetail : orderDetailByMap) {
                //找一个能出的出
                List<ContainerStore> containerListByGoodsId =
                        containerStoreService.findContainerListByGoodsId(orderDetail.getGoodsId());
                List<ContainerStore> collect =
                        containerListByGoodsId.stream().filter(x -> x.getQty() >= orderDetail.getPlanQty()).collect(Collectors.toList());
                if (collect.size()>0){
                    ContainerStore containerStore = collect.get(0);
                }else {
                    containerListByGoodsId.stream().sorted();
                }

            }
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
    private Map<Integer,Integer> initFinishedTrayLib() {
        return this.getCanBeUsedStore();
    }

    /**
     * 获取可用库存
     */
    @Override
    public Map<Integer,Integer> getCanBeUsedStore() {
        //所有成品拖
        Map<Integer,Integer> allGoodsCount = changeList(mapper.findAllGoodsCount());

        //绑定任务成品拖
        Map<Integer,Integer> usedGoodsCount = changeList(mapper.findUsedGoodsCount());
        //可使用成品拖
        Map<Integer,Integer> canBeUsedStore = new HashMap<>();
        if (usedGoodsCount != null || usedGoodsCount.size() != 0) {
            return allGoodsCount;
        }
        usedGoodsCount.forEach((k, v) -> {
                if (allGoodsCount.get(k)!=null){
                    allGoodsCount.put(k,allGoodsCount.get(k)-v);
                }
            });
        canBeUsedStore.putAll(allGoodsCount);
        return canBeUsedStore;
    }

    private Map<Integer, Integer> changeList(List<Map<String, Integer>> list) {
        Map<Integer,Integer> useMap = new HashMap();
        if (list ==null || list.size()==0){
            return useMap;
        }
        for (Map<String, Integer> map : list) {
            useMap.put(map.get("goodsId"),map.get("num"));
        }
        return useMap;
    }

}
