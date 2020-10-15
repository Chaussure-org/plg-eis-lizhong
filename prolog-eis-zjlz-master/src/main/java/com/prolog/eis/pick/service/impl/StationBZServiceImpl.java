package com.prolog.eis.pick.service.impl;

import com.prolog.eis.base.service.IGoodsService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.PickingOrder;
import com.prolog.eis.model.base.Goods;
import com.prolog.eis.model.order.PickingOrderHistory;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.pick.service.IStationBZService;
import com.prolog.eis.dto.bz.BCPGoodsInfoDTO;
import com.prolog.eis.dto.bz.BCPPcikingDTO;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.order.service.IContainerBindingDetailService;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.eis.order.service.ISeedInfoService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.store.service.IPickingOrderHistoryService;
import com.prolog.eis.store.service.IPickingOrderService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/9/28 12:17
 */

@Service
public class StationBZServiceImpl implements IStationBZService {
    @Autowired
    private IContainerBindingDetailService containerBindingDetailService;
    @Autowired
    private IStationService stationService;
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private IOrderBillService orderBillService;
    @Autowired
    private IContainerStoreService containerStoreService;
    @Autowired
    private ISeedInfoService seedInfoService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IPickingOrderService pickingOrderService;
    @Autowired
    private IPickingOrderHistoryService pickingOrderHistoryService;

    /**
     * 2、校验托盘或料箱是否在拣选站
     * 3、校验托盘或料箱是否是该站台的拣选任务(否剔除)
     * 4、开始播种作业
     * 5、订单托满换托盘
     * 6、订单结束站台置空
     *
     * @param stationId   站台id
     * @param containerNo 容器编号
     * @param orderBoxNo  订单框编号
     */
    @Override
    public BCPPcikingDTO startBZPicking(int stationId, String containerNo, String orderBoxNo) throws Exception {
        if (StringUtils.isBlank(containerNo)) {
            throw new RuntimeException("容器编号不能为空");
        }
        if (StringUtils.isBlank(orderBoxNo)) {
            throw new RuntimeException("订单拖编号不能为空");
        }

        Station station = stationService.findById(stationId);
        if (station == null) {
            throw new RuntimeException(stationId + "站台不存在");
        }
        //todo:校验托盘或料箱是否在拣选站
        List<ContainerBindingDetail> containerBinDings = containerBindingDetailService.findMap(MapUtils.put("containerNo", containerNo).getMap());
        if (containerBinDings.size() == 0) {
            throw new Exception("容器【" + containerNo + "】无在播明细");
        }
        ContainerBindingDetail bindingDetail = containerBinDings.get(0);
        BCPPcikingDTO picking = new BCPPcikingDTO();
        picking.setOrderId(bindingDetail.getOrderBillId());
        picking.setPickNum(bindingDetail.getSeedNum());
        BCPGoodsInfoDTO bcpGoodsDTO = orderDetailService.findPickingGoods(bindingDetail.getOrderDetailId()).get(0);
        picking.setGoodsname(bcpGoodsDTO.getGoodsname());
        picking.setGraphNo(bcpGoodsDTO.getGraphNo());
        picking.setGoodsNo(bcpGoodsDTO.getGoodsNo());
        picking.setOrderNo(bcpGoodsDTO.getOrderNo());
        //未拣选订单明细条目
        int count = orderDetailService.notPickingCount(bindingDetail.getOrderBillId());
        picking.setSurplusOrderDetailCount(count);
        return picking;

    }

    /**
     * 播种确认
     * 1、找到当前拣选站所需绑定明细
     * 2、库存扣减，订单明细更新
     * 3、明细完成回告wms
     * 4、播种记录保存
     * 5、任务拖放行（容器还有绑定明细则找到最近一个绑定明细发往拣选站台，托盘最后一个绑定明细为尾单则直接放行贴标区）
     * 6、订单完成更新站台当前作业picking订单拖放行、明细转历史（是否贴标）
     * 7、结束上一个拣选单
     *
     * @param stationId
     * @param containerNo
     * @param orderBoxNo
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pickingConfirm(int stationId, String containerNo, String orderBoxNo) throws Exception {
        if (StringUtils.isBlank(orderBoxNo)) {
            throw new RuntimeException("订单拖编号不能为空");
        }
        if (StringUtils.isBlank(containerNo)) {
            throw new RuntimeException("容器编号不能为空");
        }
        Station station = stationService.findById(stationId);
        if (station == null) {
            throw new RuntimeException(stationId + "站台不存在");
        }
        //找到当前播种的绑定明细
        List<Integer> orderBillIds = stationService.findPickingOrderBillId(stationId);
        ContainerBindingDetail containerBinDings = containerBindingDetailService.findMap(MapUtils.put("containerNo", containerNo)
                .put("orderBillId", orderBillIds.get(0)).getMap()).get(0);
        if (containerBinDings == null) {
            throw new Exception("容器【" + containerNo + "】无在播明细");
        }
        OrderDetail orderDetail = orderDetailService.findOrderDetailById(containerBinDings.getOrderDetailId());
        if (orderDetail == null) {
            throw new RuntimeException("绑定料箱【" + containerNo + "】无订单明细");
        }
        //更新orderDetail
        Integer orderBillId = containerBinDings.getOrderBillId();
        orderDetail.setHasPickQty(orderDetail.getHasPickQty() + containerBinDings.getSeedNum());
        orderDetail.setCompleteQty(orderDetail.getCompleteQty() + containerBinDings.getSeedNum());
        orderDetail.setUpdateTime(new Date());
        orderDetailService.updateOrderDetail(orderDetail);
        //扣减库存
        containerStoreService.updateContainerStoreNum(containerBinDings.getSeedNum(), containerNo);
        // 删除绑定明细
        containerBindingDetailService.deleteContainerDetail(MapUtils.put("containerNo", containerNo).put("orderDetailId", containerBinDings.getOrderBillId()).getMap());
        //todo:   回告wms
        boolean b = orderDetailService.checkOrderDetailFinish(containerBinDings.getOrderDetailId());
        if (b){
           //当前订单明细完成，回告wms
        }
        //订单播种完成后续操作  明细转历史、订单拖放行、回告wms
        //播种记录保存
        seedInfoService.saveSeedInfo(containerNo, orderBoxNo, orderBillId, containerBinDings.getOrderDetailId(), stationId, containerBinDings.getSeedNum());
        boolean flag = orderDetailService.orderPickingFinish(orderBillId);
        if (flag) {
            //切换拣选单
            this.changePickingOrder(station);
            //明细转历史
            orderBillService.orderBillToHistory(orderBillId);
            //todo：订单拖放行 贴标区或非贴标区
            this.orderTrayLeave(containerNo, stationId, orderBillId);


        }
        //todo：任务拖放行

    }

    @Override
    public boolean checkContainerToStation(String containerNo, int stationId) {
        return false;
    }

    @Override
    public boolean checkContainerExist(String containerNo, int stationId) {
        return false;
    }

    @Override
    public void orderToHistory(int orderBillId) throws Exception {

    }

    @Override
    public void containerNoLeave(String containerNo, int stationId) throws Exception {
        /**
         * 拣选站容器放行， 上层输送线放行，下层任务拖放行 容器无绑定明细则放行，有则找寻下一个站台完成播种
         */
        List<Station> stations = stationService.findStationByMap(MapUtils.put("containerNo", containerNo).put("stationId", stationId).getMap());
        //判断当容器是否还有绑定明细
        List<Integer> stationIds = containerBindingDetailService.getContainerBindingToStation(containerNo);
        if (stationIds.size() == 0) {
            //直接放行
            if (stations.size() > 0) {
                //上层输送线 回暂存区
            } else {
                //下层agv  循环线点位
            }
        } else {
            //计算合适站台
            int targetStationId = this.computeTargetStation(stationIds, stationId);
            if (stations.size() > 0) {
                //上层输送线
            } else {
                //下层agv 只有一条绑定明细尾拖则直接去贴标区或非贴标区
                if (stationIds.size() == 1) {
                    //计算是否为尾拖，是则直接直接发往贴标区或非贴标区
                    boolean flag = this.computeLastTray(containerNo);
                    if (!flag) {
                        //不是尾拖，发往拣选站
                    }
                } else {
                    // 计算合适站台
                }
            }
        }
    }


    @Override
    public void orderTrayLeave(String orderTrayNo, int stationId, int orderBillId) throws Exception {
        //订单拖是否有贴标商品，有贴标区
        boolean flag = orderDetailService.findOrderTrayGoodsLabel(orderBillId, orderTrayNo);
        if (flag) {
            //todo:Agv贴标区
        } else {
            //todo：agv非贴标
        }
    }

    @Override
    public boolean computeLastTray(String containerNo) throws Exception {
        ContainerStore containerStore = containerStoreService.findByMap(MapUtils.put("containerNo", containerNo).getMap()).get(0);
        List<ContainerBindingDetail> containerBindingDetails = containerBindingDetailService.findMap(MapUtils.put("containerNo", containerNo).getMap());
        if (containerBindingDetails.size() != 1) {
            throw new RuntimeException("当前托盘有多个订单任务不是尾单");
        }
        Goods goods = goodsService.findGoodsById(containerStore.getGoodsId());
        //尾拖计算 播种数量乘以尾拖占比 减 库存数 大于等于0则为尾拖
        boolean flag = containerBindingDetails.get(0).getSeedNum() * (1 + goods.getPastLabelFlg()) - containerStore.getQty() >= 0;
        if (flag) {
            if (goods.getPastLabelFlg() == 1) {
                //:todo:Agv贴标区
            } else {
                //：todo：Agv非贴标区
            }
            return true;
        }

        return false;
    }

    @Override
    public int computeTargetStation(List<Integer> stationIds, int sourceStation) {
        int targetStationId = 0;
        if (stationIds.size() == 1) {
            targetStationId = stationIds.get(0);
        } else {
            for (Integer stationId : stationIds) {
                //先找到一个比当前站台id且最近的站台
                if (sourceStation > stationId) {
                    targetStationId = stationId;
                    break;
                }
            }
            //找寻最大的一个拣选站台
            if (targetStationId == 0) {
                targetStationId = stationIds.get(0);
            }

        }
        return targetStationId;
    }

    @Override
    public void changePickingOrder(Station station) throws Exception {
        //当前拣选单转历史
        PickingOrder pickingOrder = pickingOrderService.findByMap(MapUtils.put("id", station.getCurrentStationPickId()).getMap()).get(0);
        pickingOrder.setPickCompleteTime(new Date());
        pickingOrder.setSeedCompleteTime(new Date());
        pickingOrder.setUpdateTime(new Date());
        PickingOrderHistory pickingOrderHistory = new PickingOrderHistory();
        BeanUtils.copyProperties(pickingOrder, pickingOrderHistory);
        pickingOrderHistoryService.savePickingOrder(pickingOrderHistory);

        //清除站台信息
        stationService.clearStationPickingOrder(station.getId());
        //获取下一个拣选单
        List<PickingOrder> pickingOrders = pickingOrderService.findByMap(MapUtils.put("stationId", station.getId()).getMap());
        if (pickingOrders.size() != 0) {
            station.setCurrentStationPickId(pickingOrders.get(0).getId());
            stationService.updateStation(station);
        }
    }


}
