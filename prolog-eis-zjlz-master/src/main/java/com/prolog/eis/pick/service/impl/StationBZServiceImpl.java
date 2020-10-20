package com.prolog.eis.pick.service.impl;

import com.prolog.eis.base.service.IGoodsService;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.dto.wms.WmsOutboundCallBackDto;
import com.prolog.eis.engin.service.IAgvBindingDetailService;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.ContainerPathTaskService;
import com.prolog.eis.location.service.IPointLocationService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.PickingOrder;
import com.prolog.eis.model.base.Goods;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.order.PickingOrderHistory;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.pick.service.IStationBZService;
import com.prolog.eis.dto.bz.BCPGoodsInfoDTO;
import com.prolog.eis.dto.bz.BCPPcikingDTO;
import com.prolog.eis.model.order.ContainerBindingDetail;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.order.service.IContainerBindingDetailService;
import com.prolog.eis.order.service.IOrderBillService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.eis.order.service.ISeedInfoService;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.store.service.IPickingOrderHistoryService;
import com.prolog.eis.store.service.IPickingOrderService;
import com.prolog.eis.wms.service.IWMSService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(StationBZServiceImpl.class);
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
    @Autowired
    private IWMSService wmsService;
    @Autowired
    private ContainerPathTaskService containerPathTaskService;
    @Autowired
    private IPointLocationService pointLocationService;
    @Autowired
    private AgvLocationService agvLocationService;

    @Autowired
    private IAgvBindingDetailService agvBindingDetailService;
    @Autowired
    private PathSchedulingService pathSchedulingService;





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
        //校验托盘或料箱是否在拣选站
        boolean b1 = checkContainerExist(containerNo, stationId);
        if (b1){
             throw new Exception("容器【"+containerNo+"】不在站台");
        }
        //校验订单拖是否在拣选站
        String areaNo = "OT";
        boolean b2 = checkOrderTrayNo(orderBoxNo, stationId,areaNo);
        if (b2){
            throw new Exception("订单拖【"+orderBoxNo+"】不在站台");
        }
        //校验容器是否是当前站台播种
        boolean b = checkContainerToStation(containerNo, stationId);
        if (b){
            throw new Exception("容器【"+containerNo+"】没有当前站台的绑定明细");
        }
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
     * 6、订单完成更新站台当前作业picking订、明细转历史、单拖放行（是否贴标）
     * 7、结束上一个拣选单
     *
     * @param stationId
     * @param containerNo
     * @param orderBoxNo
     * @param completeNum 短拣完成数量
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pickingConfirm(int stationId, String containerNo, String orderBoxNo,int completeNum) throws Exception {
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
        if (orderBillIds.size() == 0){
            throw new Exception("站台【"+stationId+"】无播种订单");
        }
        int orderBillId = orderBillIds.get(0);
        //执行播种
        this.doPicking(stationId,containerNo,completeNum,orderBillIds.get(0),orderBoxNo);
        //删除agv绑定明细
        agvBindingDetailService.deleteBindingDetailByMap(MapUtils.put("orderBillId",orderBillId).put("containerNo",containerNo).getMap());
        //校验订单是否完成
        boolean flag = orderDetailService.orderPickingFinish(orderBillId);
        if (flag) {
            //切换拣选单
            this.changePickingOrder(station);
            //明细转历史
            orderBillService.orderBillToHistory(orderBillId);
            //todo：订单拖放行 贴标区或非贴标区
            this.orderTrayLeave(containerNo,orderBillId);


        }
        //物料容器放行
        this.containerNoLeave(containerNo,stationId);
    }

    @Override
    public boolean checkContainerToStation(String containerNo, int stationId) {
        int count = orderBillService.checkContainer(stationId, containerNo);
        if (count == 0){
            return true;
        }
        return false;
    }

    /**
     *
     * @param containerNo
     * @param stationId
     * @return     true:没找到
     * @throws Exception
     */
    @Override
    public boolean checkContainerExist(String containerNo, int stationId) throws Exception {

        //上层输送线
        List<Station> stations = stationService.findStationByMap(MapUtils.put("containerNo", containerNo).put("stationId", stationId).getMap());
        if (stations.size() == 0) {
            return true;
        }
        String areaNo = "OD01";
        boolean b = checkOrderTrayNo(containerNo, stationId, areaNo);
        if (b){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkOrderTrayNo(String orderTrayNo, int stationId,String areaNo) throws Exception {
        List<AgvStoragelocation> agvStoragelocations = agvLocationService.findByMap(MapUtils.put("deviceNo", stationId)
                .put("areaNo",areaNo).getMap());
        if (agvStoragelocations.size() == 0){
            throw new Exception("站台【"+stationId+"】点位配置错误");
        }
        for (AgvStoragelocation agvStoragelocation : agvStoragelocations) {
            List<ContainerPathTask> containerPathTasks = containerPathTaskService.findByMap(MapUtils.put("containerNo", orderTrayNo).
                    put("sourceLocation",agvStoragelocation.getLocationNo())
                    .put("taskState",ContainerPathTask.TASK_STATE_NOT).getMap());
            if (containerPathTasks.size() == 0){
                return true;
            }
        }

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
                //上层输送线  循环线点位
            } else {
                //下层agv 回暂存区
            }
        } else {
            if (stations.size() > 0) {
                //计算合适站台
                int targetStationId = this.computeContainerTargetStation(stationIds, stationId);
                //上层输送线 发送点位
            } else {
                //下层agv 只有一条绑定明细尾拖则直接去贴标区或非贴标区
//                if (stationIds.size() == 1) {
//                    //计算是否为尾拖，是则直接直接发往贴标区或非贴标区
//                    boolean flag = this.computeLastTray(containerNo);
//                    if (!flag) {
//                        //不是尾拖，发往拣选站
//                    }
//                } else {

//                }

                // 计算合适站台
                computeTrayStation(stationId,stationIds,containerNo);
            }
        }
    }


    @Override
    public void orderTrayLeave(String orderTrayNo, int orderBillId) throws Exception {
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
    public int computeContainerTargetStation(List<Integer> stationIds, int sourceStation) {
        int targetStationId = 0;
        if (stationIds.size() == 1) {
            targetStationId = stationIds.get(0);
        } else {
            for (Integer stationId : stationIds) {
                //先找到一个比当前站台id小且最近的站台
                if (sourceStation > stationId) {
                    targetStationId = stationId;
                    return targetStationId;
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

    @Override
    public void seedToWms(ContainerBindingDetail containerBindingDetail) {

        if (containerBindingDetail == null){
            return;
        }
        WmsOutboundCallBackDto wmsOrderBill = orderBillService.findWmsOrderBill(containerBindingDetail.getOrderBillId()).get(0);
        wmsOrderBill.setSJC(new Date());
        wmsOrderBill.setCONTAINERNO(containerBindingDetail.getContainerNo());
        //回告wms
        wmsService.outboundTaskCallBack(wmsOrderBill);

    }

    @Override
    public void changeOrderTray(String orderTrayNo,int stationId) throws Exception {
        List<Integer> orderBillId = stationService.findPickingOrderBillId(stationId);
        this.orderTrayLeave(orderTrayNo,orderBillId.get(0));

    }

    @Override
    public void doPicking(int stationId, String containerNo, int completeNum,int orderBillId,String orderBoxNo) throws Exception {
        ContainerBindingDetail containerBinDings = containerBindingDetailService.findMap(MapUtils.put("containerNo", containerNo)
                .put("orderBillId", orderBillId).getMap()).get(0);
        if (containerBinDings == null) {
            throw new Exception("容器【" + containerNo + "】无在播明细");
        }
        OrderDetail orderDetail = orderDetailService.findOrderDetailById(containerBinDings.getOrderDetailId());
        if (orderDetail == null) {
            throw new RuntimeException("绑定料箱【" + containerNo + "】无订单明细");
        }
        //更新orderDetail
        orderDetail.setHasPickQty(orderDetail.getHasPickQty() + containerBinDings.getSeedNum());
        if (completeNum >= 0 ){
            logger.info("站台{}订单明细【{}】短拣完成",stationId,orderDetail.getId());
            orderDetail.setCompleteQty(orderDetail.getCompleteQty() + completeNum);
        } else {
            orderDetail.setCompleteQty(orderDetail.getCompleteQty() + containerBinDings.getSeedNum());
        }

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
            this.seedToWms(containerBinDings);
        }
        //订单播种完成后续操作  明细转历史、订单拖放行、回告wms
        //播种记录保存
        seedInfoService.saveSeedInfo(containerNo, orderBoxNo, orderBillId, containerBinDings.getOrderDetailId(), stationId, containerBinDings.getSeedNum());


    }

    @Override
    public void computeTrayStation(int stationId, List<Integer> stationIds, String containerNo) throws Exception {
        List<StationTrayDTO> trayTaskStation = agvLocationService.findTrayTaskStation(stationIds);
        trayTaskStation.stream().sorted(Comparator.comparing(StationTrayDTO::getCount).reversed()).collect(Collectors.toList());
        if (trayTaskStation.get(0).getCount() == 0){
            //站台已满无可用任务拖区域 回暂存区
            pathSchedulingService.containerMoveTask(containerNo,"RCS01",null);
        }else {
            //去往对应站台
            int targetStationId = trayTaskStation.get(0).getStationId();
            List<AgvStoragelocation> agvStoragelocations = agvLocationService.findByMap(MapUtils.put("deviceNo", targetStationId).put("taskLock",0)
                    .put("storageLock",0).put("areaNo","OT").getMap());
            if (agvStoragelocations.size() == 0){
                throw new Exception("【"+targetStationId+"】未找到可用库区");
            }
            pathSchedulingService.containerMoveTask(containerNo,agvStoragelocations.get(0).getAreaNo(),agvStoragelocations.get(0).getLocationNo());
        }
        
        
    }


}
