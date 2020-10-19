package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.OrderBillDto;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.engin.dao.FinishedProdOutEnginMapper;
import com.prolog.eis.engin.service.FinishedProdOutEnginService;
import com.prolog.eis.engin.service.TrayOutEnginService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.order.service.IContainerBindingDetailService;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.framework.utils.MapUtils;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
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
    private IOrderDetailService orderDetailService;

    @Autowired
    private IStationService stationService;

    @Autowired
    private IContainerStoreService containerStoreService;

    @Autowired
    private IContainerBindingDetailService containerBindingDetailService;

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
                int bindingNum = 0;
                //找一个能出的出
                List<ContainerStore> containerListByGoodsId =
                        containerStoreService.findContainerListByGoodsId(orderDetail.getGoodsId());
                List<ContainerStore> collect =
                        containerListByGoodsId.stream().filter(x -> x.getQty() >= orderDetail.getPlanQty()-orderDetail.getOutQty()).collect(Collectors.toList());
                ContainerStore containerStore = null;
                if (collect.size()>0){
                     containerStore = collect.get(0);
                     bindingNum = orderDetail.getPlanQty()-orderDetail.getOutQty();
                }else {
                    List<ContainerStore> collect1 =
                            containerListByGoodsId.stream().sorted(Comparator.comparing(ContainerStore::getQty).reversed()).collect(Collectors.toList());
                    containerStore = collect1.get(0);
                    bindingNum = containerStore.getQty();
                }
                try{
                    this.getOutTray(containerStore,orderDetail,bindingNum);
                    break;
                }catch (Exception e){
                    logger.warn(containerStore.getContainerNo()+"出库失败："+e.getMessage());
                    continue;
                }

            }
        }else{
            logger.warn("未找到可出库订单");
        }
    }

    /**
     * 出库托盘
     * @param containerStore
     */
    @Transactional(rollbackFor = Exception.class)
    public void getOutTray(ContainerStore containerStore, OrderDetail orderDetail, int bindingNum) throws Exception {
        /**
         * 修改订单状态
         * 生产绑定明细
         * 修改托盘任务类型
         * 出库
         */
        orderDetail = checkOrderDetailStatus(containerStore,orderDetail);
        orderBillService.updateOrderBillStatus(orderDetail);
        createContainerBindindDetail(containerStore,orderDetail,bindingNum);
        containerStoreService.updateContainerTaskType(containerStore);
        //todo 修改路径相关进行出库

    }

    private void createContainerBindindDetail(ContainerStore containerStore, OrderDetail orderDetail,
                                              Integer bindingNum) {
        ContainerBindingDetail containerBindingDetail = new ContainerBindingDetail();
        containerBindingDetail.setContainerStoreId(containerStore.getId());
        containerBindingDetail.setContainerNo(containerStore.getContainerNo());
        containerBindingDetail.setOrderBillId(orderDetail.getOrderBillId());
        containerBindingDetail.setOrderDetailId(orderDetail.getId());
        containerBindingDetail.setBindingNum(bindingNum);
        containerBindingDetail.setSeedNum(bindingNum);
        containerBindingDetailService.saveInfo(containerBindingDetail);
    }

    /**
     * 改变订单明细的数量
     * @param orderDetail
     * @return
     */
    private OrderDetail checkOrderDetailStatus(ContainerStore containerStore,OrderDetail orderDetail) {
        Integer planQty = orderDetail.getPlanQty();
        if (planQty>orderDetail.getOutQty()+containerStore.getQty()){
            orderDetail.setOutQty(orderDetail.getOutQty()+containerStore.getQty());
        }else {
            orderDetail.setOutQty(planQty);
        }
        return orderDetail;
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
        if (usedGoodsCount == null || usedGoodsCount.size() == 0) {
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
