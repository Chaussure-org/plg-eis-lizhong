package com.prolog.eis.pick.service.impl;

import com.prolog.eis.base.service.IGoodsService;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.bz.OrderTrayWeighDTO;
import com.prolog.eis.dto.bz.PickWmsDto;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.dto.wms.WmsOutboundCallBackDto;
import com.prolog.eis.dto.wms.WmsStartOrderCallBackDto;
import com.prolog.eis.engin.dao.LineBindingDetailMapper;
import com.prolog.eis.location.service.*;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.OrderBox;
import com.prolog.eis.model.PickingOrder;
import com.prolog.eis.model.PointLocation;
import com.prolog.eis.model.base.Goods;
import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.model.location.ContainerPathTaskDetail;
import com.prolog.eis.model.order.*;
import com.prolog.eis.model.station.Station;
import com.prolog.eis.order.service.*;
import com.prolog.eis.pick.service.ISeedWeighService;
import com.prolog.eis.pick.service.IStationBZService;
import com.prolog.eis.dto.bz.BCPGoodsInfoDTO;
import com.prolog.eis.dto.bz.BCPPcikingDTO;
import com.prolog.eis.station.service.IStationService;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.store.service.IPickingOrderHistoryService;
import com.prolog.eis.store.service.IPickingOrderService;
import com.prolog.eis.util.EisStringUtils;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.wcs.service.IWcsService;
import com.prolog.eis.wms.service.IWmsService;
import com.prolog.framework.utils.MapUtils;
import com.prolog.framework.utils.StringUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
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
    private IWmsService wmsService;
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
    @Autowired
    private ISeedWeighService seedWeighService;
    @Autowired
    private IOrderBoxService orderBoxService;
    @Autowired
    private EisProperties eisProperties;
    @Autowired
    private IWcsService wcsService;
    @Autowired
    private IContainerPathTaskDetailService containerPathTaskDetailService;
    @Autowired
    private LineBindingDetailMapper lineBindingDetailMapper;

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
    public BCPPcikingDTO startBZPicking(int stationId, String containerNo, String orderBoxNo,String locationNo) throws Exception {
        if (StringUtils.isBlank(containerNo)) {
            throw new RuntimeException("容器编号不能为空");
        }

        if (StringUtils.isBlank(orderBoxNo)) {
            throw new RuntimeException("订单拖编号不能为空");
        }
        if (StringUtils.isBlank(locationNo)){
            throw new Exception("接驳点位不能为空");
        }

        Station station = stationService.findById(stationId);
        if (station == null) {
            throw new RuntimeException(stationId + "  站台不存在");
        }
        //铁拖站台 校验订单托是否为铁托
        boolean f1 = false;
        if (station.getStationType() == Station.STATION_TYPE_IRON){
             if (!orderBoxNo.startsWith(eisProperties.getIronTrayPrefix())){
                 throw new Exception("站台【"+station.getId()+"】需要铁笼，托盘【"+orderBoxNo+"】不是铁笼");
             }
            f1 = true;
        }
        //校验托盘或料箱是否在拣选站
        boolean b1 = checkContainerExist(containerNo, stationId);
        if (b1) {
            throw new Exception("容器【" + containerNo + "】不在站台");
        }
        //校验订单拖是否在拣选站
        String areaNo = "OD01";
        boolean b2 = checkOrderTrayNo(locationNo, stationId, areaNo);
        if (b2) {
            throw new Exception("站台点位"+locationNo+"无点订单拖");
        }
        //校验容器是否是当前站台播种
        boolean b = checkContainerToStation(containerNo, stationId);
        if (b) {
            throw new Exception("容器【" + containerNo + "】没有当前站台的绑定明细");
        }
        //查询当前容器的播种明细
        List<ContainerBindingDetail> containerBinDings = containerBindingDetailService.getBindingDetail(station.getCurrentStationPickId(), containerNo);
        if (containerBinDings.size() == 0) {
            throw new Exception("容器【" + containerNo + "】无在播明细");
        }

        ContainerBindingDetail bindingDetail = containerBinDings.get(0);
        //判断当前订单托是否是第一次播种，是则请求wms获取订单拖重量保存并将托盘编号写入点位
        saveTrayWeigh(bindingDetail.getOrderBillId(), orderBoxNo,locationNo,f1);

        BCPPcikingDTO picking = new BCPPcikingDTO();
        picking.setOrderBillId(bindingDetail.getOrderBillId());
        picking.setPickNum(bindingDetail.getSeedNum());
        picking.setOrderDetailId(bindingDetail.getOrderDetailId());
        BCPGoodsInfoDTO bcpGoodsDTO = orderDetailService.findPickingGoods(bindingDetail.getOrderDetailId()).get(0);
        List<ContainerStore> containerStores = containerStoreService.findByMap(MapUtils.put("containerNo", containerNo).getMap());
        picking.setQty(containerStores.get(0).getQty());
        picking.setGoodsname(bcpGoodsDTO.getGoodsname());
        picking.setGraphNo(bcpGoodsDTO.getGraphNo());
        picking.setGoodsNo(bcpGoodsDTO.getGoodsNo());
        picking.setOrderNo(bcpGoodsDTO.getOrderNo());
        //未拣选订单明细条目
        int count = orderDetailService.notPickingCount(bindingDetail.getOrderBillId());
        picking.setSurplusOrderDetailCount(count);
        //订单第一次拣选则回告wms拣选开始
        startSeedToWms(bindingDetail.getOrderBillId(),bcpGoodsDTO.getOrderNo());
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
    public void pickingConfirm(int stationId, String containerNo, String orderBoxNo, int completeNum) throws Exception {
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
        if (orderBillIds.size() == 0) {
            throw new Exception("站台【" + stationId + "】无播种订单");
        }
        int orderBillId = orderBillIds.get(0);
        //执行播种
        ContainerBindingDetail containerBindingDetail = this.doPicking(stationId, containerNo, completeNum, orderBillIds.get(0), orderBoxNo);
        //删除agv绑定明细  通过order_mx_id回传
        agvBindingDetailService.deleteBindingDetailByMap(MapUtils.put("orderMxId", containerBindingDetail.getOrderDetailId()).put("containerNo", containerNo).getMap());
        //删除输送线绑定明细
        lineBindingDetailMapper.deleteByMap(MapUtils.put("orderMxId", containerBindingDetail.getOrderDetailId()).put("containerNo", containerNo).getMap(), LineBindingDetail.class);

    }

    @Override
    public boolean checkContainerToStation(String containerNo, int stationId) {
        int count = orderBillService.checkContainer(stationId, containerNo);
        if (count == 0) {
            return true;
        }
        return false;
    }

    /**
     * @param containerNo
     * @param stationId
     * @return true:没找到
     * @throws Exception
     */
    @Override
    public boolean checkContainerExist(String containerNo, int stationId) throws Exception {
        //上层输送线
        //判断站台表 里的 当前料箱字段是否 有值
        List<Station> stations = stationService.findStationByMap(MapUtils.put("containerNo", containerNo).put("id", stationId).getMap());
        int containerArrive = agvLocationService.findContainerArrive(containerNo, stationId);
        if (stations.size() == 0 && containerArrive == 0) {
            return true;
        }

        // 此处代码 有bug add sunpp
        /*String areaNo = "OD01";
        boolean b = checkOrderTrayNo(containerNo, stationId, areaNo);
        if (b){
            return true;
        }*/
        return false;
    }

    /**
     *
     * @param locationNo  点位编号
     * @param stationId
     * @param areaNo
     * @return
     * @throws Exception
     */
    @Override
    public boolean checkOrderTrayNo(String locationNo, int stationId, String areaNo) throws Exception {
        List<AgvStoragelocation> agvStoragelocations = agvLocationService.findByMap(MapUtils.put("deviceNo", stationId)
                .put("areaNo", areaNo).getMap());
        if (agvStoragelocations.size() == 0) {
            throw new Exception("站台【" + stationId + "】点位配置错误");
        }
        List<ContainerPathTask> containerPathTasks = containerPathTaskService.findByMap(MapUtils.put("sourceLocation",locationNo ).getMap());
        if (containerPathTasks.size() == 0){
            throw new Exception("点位【"+locationNo+"】路径配置有误");
        }
        if (containerPathTasks.get(0).getTaskState() != ContainerPathTask.TASK_STATE_NOT){
            throw new Exception("订单拖没有到位,路径状态为【"+containerPathTasks.get(0).getTaskState()+"】");
        }

        for (AgvStoragelocation agvStoragelocation : agvStoragelocations) {
            if (agvStoragelocation.getLocationNo().equals(containerPathTasks.get(0).getSourceLocation())){
                return false;
            }
        }
  

        return true;
    }

    @Override
    public void orderToHistory(int orderBillId) throws Exception {

    }

    @Override
    public void containerNoLeave(String containerNo, int stationId) throws Exception {
        /**
         * 拣选站容器放行， 上层输送线放行，下层任务拖放行 容器无绑定明细则放行，有则找寻下一个站台完成播种
         * 容器库存为0，料箱修改空箱，托盘去空托下架区
         */
        List<Station> stations = stationService.findStationByMap(MapUtils.put("containerNo", containerNo).put("id", stationId).getMap());
        //判断当容器是否还有绑定明细
        List<Integer> stationIds = containerBindingDetailService.getContainerBindingToStation(containerNo);
        if (stationIds.size() == 0) {
            //直接放行
            if (stations.size() > 0) {
                //上层输送线  循环线点位 通过拣选站按钮放行
                  //todo：注释输送线放行
//                String taskId = PrologStringUtils.newGUID();
//                PointLocation point = pointLocationService.getPointByStationId(stationId);
//                WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId,point.getPointId(),PointLocation.POINT_ID_LXHK,containerNo,5);
//                wcsService.lineMove(wcsLineMoveDto,0);

                ContainerStore containerStore = containerStoreService.findByMap(MapUtils.put("containerNo", containerNo).getMap()).get(0);
                if (containerStore.getQty() == 0){
                    //空箱回库改容器默认商品id -1，库存为1
                    containerStoreService.updateEmptyContainer(containerNo);
                }
            } else {
                //下层agv 回暂存区 空托盘去agv区域RCS02 加判断
                ContainerStore containerStore = containerStoreService.findByMap(MapUtils.put("containerNo", containerNo).getMap()).get(0);
                if (containerStore.getQty() == 0){
                    //空托去空拖下架区
                    pathSchedulingService.containerMoveTask(containerNo, "RCS02", null);
                }
                pathSchedulingService.containerMoveTask(containerNo, "RCS01", null);
            }
            containerStoreService.updateTaskTypeByContainer(containerNo,0);
        } else {
            if (stations.size() > 0) {
                //todo：注释输送线放行 通过拣选站按钮放行
//                //计算合适站台
//                int targetStationId = this.computeContainerTargetStation(stationIds, stationId);
//                //上层输送线 发送点位
//                String taskId = PrologStringUtils.newGUID();
//                PointLocation point = pointLocationService.getPointByStationId(stationId);
//                PointLocation targetPoint = pointLocationService.getPointByStationId(targetStationId);
//                WcsLineMoveDto wcsLineMoveDto = new WcsLineMoveDto(taskId,point.getPointId(),targetPoint.getPointId(),containerNo,5);
//                wcsService.lineMove(wcsLineMoveDto,0);

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
                computeTrayStation(stationIds, containerNo,stationId);
            }
        }
    }


    @Override
    public void orderTrayLeave(String orderTrayNo, int orderBillId) throws Exception {
        //订单拖是否有贴标商品，有贴标区
        List<SeedInfo> seedInfos = seedInfoService.findSeedInfoByMap(MapUtils.put("orderBillId", orderBillId).put("orderTrayNo", orderTrayNo).getMap());
        if (seedInfos.size() == 0) {
            throw new Exception("【" + orderTrayNo + "】托盘离开失败，无播种作业");
        }
        boolean flag = orderDetailService.findOrderTrayGoodsLabel(orderBillId, orderTrayNo);
        if (flag) {
            //Agv贴标区
            pathSchedulingService.containerMoveTask(orderTrayNo, "LB01", null);
        } else {
            //agv非贴标
            pathSchedulingService.containerMoveTask(orderTrayNo, "CH01", null);
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
                //Agv贴标区
                pathSchedulingService.containerMoveTask(containerNo, "LB01", null);
            } else {
                //Agv非贴标区
                pathSchedulingService.containerMoveTask(containerNo, "CH01", null);
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
                //先找到一个比当前站台id大且最近的站台(离bcr最近的站台id最小)
                if (sourceStation < stationId) {
                    targetStationId = stationId;
                    return targetStationId;
                }
            }
            //找寻最小id的一个拣选站台
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
        pickingOrderService.deleteById(pickingOrder.getId());

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
    public void seedToWms(ContainerBindingDetail containerBindingDetail) throws Exception {

        if (containerBindingDetail == null) {
            return;
        }
        List<PickWmsDto> pickWmsDtos = orderBillService.findWmsOrderBill(containerBindingDetail.getOrderDetailId());
        PickWmsDto pickWmsDto = pickWmsDtos.get(0);
        WmsOutboundCallBackDto wmsOrderBill = new WmsOutboundCallBackDto();
        wmsOrderBill.setSJC(new Date());
        wmsOrderBill.setCONTAINERNO(containerBindingDetail.getContainerNo());
        wmsOrderBill.setITEMID(EisStringUtils.getRemouldId(pickWmsDto.getGoodsId()));
        wmsOrderBill.setBILLNO(pickWmsDto.getOrderNo());
        wmsOrderBill.setBILLDATE(pickWmsDto.getBillDate());
        wmsOrderBill.setLOTID(pickWmsDto.getLotId());
        wmsOrderBill.setBILLTYPE(String.valueOf(pickWmsDto.getOrderType()));
        wmsOrderBill.setTASKID(pickWmsDto.getTaskId());
        wmsOrderBill.setQTY(Double.valueOf(pickWmsDto.getCompleteQty()));

        //回告wms
        wmsService.outboundTaskCallBack(wmsOrderBill);

    }

    @Override
    public void changeOrderTray(String orderTrayNo, int stationId) throws Exception {
        List<Integer> orderBillId = stationService.findPickingOrderBillId(stationId);
        this.orderTrayLeave(orderTrayNo, orderBillId.get(0));

    }


    @Override
    public ContainerBindingDetail doPicking(int stationId, String containerNo, int completeNum, int orderBillId, String orderBoxNo) throws Exception {
        ContainerBindingDetail containerBinDings = containerBindingDetailService.findMap(MapUtils.put("containerNo", containerNo)
                .put("orderBillId", orderBillId).getMap()).get(0);
        if (containerBinDings == null) {
            throw new Exception("容器【" + containerNo + "】无在播明细");
        }
        if (completeNum > containerBinDings.getSeedNum() ){
            throw new Exception("短拣数量不能大于播种数量");
        }
        OrderDetail orderDetail = orderDetailService.findOrderDetailById(containerBinDings.getOrderDetailId());
        if (orderDetail == null) {
            throw new RuntimeException("绑定料箱【" + containerNo + "】无订单明细");
        }
        //更新orderDetail
        orderDetail.setHasPickQty(orderDetail.getHasPickQty() + containerBinDings.getSeedNum());
        if (!containerBinDings.getSeedNum().equals(completeNum) && completeNum > 0) {
            logger.info("站台【{}】订单明细【{}】短拣完成", stationId, orderDetail.getId());
            orderDetail.setCompleteQty(orderDetail.getCompleteQty() + completeNum);
        } else {
            orderDetail.setCompleteQty(orderDetail.getCompleteQty() + containerBinDings.getSeedNum());
        }

        orderDetail.setUpdateTime(new Date());
        orderDetailService.updateOrderDetail(orderDetail);
        //扣减库存
        containerStoreService.updateContainerStoreNum(containerBinDings.getSeedNum(), containerNo);
        // 删除绑定明细
        containerBindingDetailService.deleteContainerDetail(MapUtils.put("containerNo", containerNo).put("orderDetailId", containerBinDings.getOrderDetailId()).getMap());
        //播种记录保存
        seedInfoService.saveSeedInfo(containerNo, orderBoxNo, orderBillId, containerBinDings.getOrderDetailId(), stationId, containerBinDings.getSeedNum(), orderDetail.getGoodsId());
        boolean b = orderDetailService.checkOrderDetailFinish(containerBinDings.getOrderDetailId());
        if (b) {
            //当前订单明细完成，回告wms
            seedToWms(containerBinDings);
        }
        return containerBinDings;
    }

    @Override
    public void computeTrayStation(List<Integer> stationIds, String containerNo,Integer stationId) throws Exception {
        String storeArea = "SN01";
        List<StationTrayDTO> trayTaskStation = agvLocationService.findTrayTaskStation(storeArea, stationIds);
        //大到小排序
        List<StationTrayDTO> collect = trayTaskStation.stream().sorted(Comparator.comparing(StationTrayDTO::getEmptyCount).reversed()).collect(Collectors.toList());
        int targetId = collect.get(0).getStationId();
        if (targetId == stationId){
            //绑定明细为当前站台回agv暂存区
            pathSchedulingService.containerMoveTask(containerNo, "RCS01", null);
        }
        if (collect.get(0).getEmptyCount() == 0) {
            //站台已满无可用任务拖区域 回暂存区
            pathSchedulingService.containerMoveTask(containerNo, "RCS01", null);
        } else {
            //去往对应站台
            int targetStationId = collect.get(0).getStationId();
            List<String> usableStore = agvLocationService.getUsableStore(storeArea, targetStationId);
            if (usableStore.size() == 0) {
                throw new Exception("站台【" + targetStationId + "】没找到可用区域");
            }
            pathSchedulingService.containerMoveTask(containerNo, storeArea, usableStore.get(0));
        }


    }

    /**
     * 重量计算：1、wms获取周转箱重量 2、wms获取托盘总重量
     * 3、计算托盘内之前商品重量 4、获取订单拖重量 5、计算此次播种商品重量
     * 6  （托盘重量 + 之前商品重量 +周转箱重量 + 计算播种商品重量 ）- 称重托盘重量 小于误差率
     * 7、小于 回传前端true，保存称重重量  大于回传false：进行第二次称重判断
     * 8、第二次回传前端true 保存称重重量
     *
     * @param stationId
     * @param orderDetailId
     * @param passBoxNo
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderTrayWeighDTO weighCheck(int stationId, int orderDetailId, String passBoxNo) throws Exception {
        if (StringUtils.isBlank(passBoxNo)) {
            throw new Exception("周转箱号不能为空");
        }
        //周转箱重量
        BigDecimal containerWeigh = new BigDecimal(0);
        if (!"0".equals(passBoxNo)) {
            //todo:请求wms接口获取重量
        }

        SeedInfo seedInfo = seedInfoService.findSeedInfoByOrderDetail(orderDetailId);

        //获取托盘重量
        List<OrderBox> orderBoxs = orderBoxService.findByMap(MapUtils.put("orderBoxNo", seedInfo.getOrderTrayNo()).getMap());
        if (orderBoxs.size() == 0 || orderBoxs.get(0).getTrayWeigh() == null) {
            throw new Exception("订单拖数据异常");
        }
        BigDecimal trayWeigh = orderBoxs.get(0).getTrayWeigh();

        //获取托盘之前商品重量
        BigDecimal beforeGoodsWeigh = seedWeighService.getBeforeOrderTrayWeight(seedInfo.getOrderBillId(), seedInfo.getOrderTrayNo(), seedInfo.getId());

        //计算当前商品重量
        Goods goods = goodsService.findGoodsById(seedInfo.getGoodsId());
        BigDecimal computeGoodsWeigh = goods.getWeight().multiply(new BigDecimal(seedInfo.getNum()));


        //todo:wms获取称重重量
        BigDecimal sumWeigh = new BigDecimal(0);
        //称重播种商品的实际重量(称重重量 - 托盘重量 - 之前商品重量)
        BigDecimal realityWeigh = sumWeigh.subtract(trayWeigh).subtract(beforeGoodsWeigh);

        //计算误差率 （托盘重量 + 之前商品重量 +周转箱重量 + 计算播种商品重量 ）- 称重托盘重量 小于误差率(称重)
        BigDecimal compute1 = trayWeigh.add(beforeGoodsWeigh).add(containerWeigh).add(computeGoodsWeigh).subtract(sumWeigh).abs();
        BigDecimal errorRate1 = compute1.divide(computeGoodsWeigh,2,BigDecimal.ROUND_HALF_UP);

        OrderTrayWeighDTO orderTrayWeighDTO = new OrderTrayWeighDTO();
        orderTrayWeighDTO.setPassBoxWeigh(containerWeigh);
        orderTrayWeighDTO.setWeigh(sumWeigh);
        //误差率
        BigDecimal errorRate = eisProperties.getErrorRate();
        /**
         * 计算是否符合误差，是则回告前端true，否则判断第二次称重是否有值
         * 每次计算的称重重量保存记录;
         */

        if (errorRate1.compareTo(errorRate) == -1) {
            //小于误差率
            orderTrayWeighDTO.setFlag(true);
            List<SeedWeigh> seedWeighs = seedWeighService.findSeedWeighByMap(MapUtils.put("seedInfoId", seedInfo.getId()).getMap());

            if (seedWeighs.size() == 0) {
                SeedWeigh seedWeigh = new SeedWeigh();
                seedWeigh.setFirstWeigh(realityWeigh);
                seedWeigh.setSeedInfoId(seedInfo.getId());
                seedWeigh.setCreateTime(new Date());
                seedWeigh.setFirstWeighCheck(true);
                seedWeigh.setAuthorityLeave(false);
                seedWeighService.saveSeedWeigh(seedWeigh);
            } else {
                List<SeedWeigh> collect = seedWeighs.stream().filter(x -> x.getFirstWeigh() != null).collect(Collectors.toList());
                if (collect.size() == 0) {
                    throw new Exception("第一次称重数据为空");
                }
                SeedWeigh seedWeigh = seedWeighs.get(0);
                seedWeigh.setSecondWeigh(realityWeigh);
                seedWeigh.setUpdateTime(new Date());
                seedWeigh.setSecondWeighCheck(true);
                seedWeigh.setAuthorityLeave(false);
                seedWeighService.updateSeedWeigh(seedWeigh);
            }

        } else {
            //大于误差率
            List<SeedWeigh> seedWeighs = seedWeighService.findSeedWeighByMap(MapUtils.put("seedInfoId", seedInfo.getId()).getMap());
            if (seedWeighs.size() == 0) {
                orderTrayWeighDTO.setFlag(false);
                SeedWeigh seedWeigh = new SeedWeigh();
                seedWeigh.setFirstWeigh(realityWeigh);
                seedWeigh.setSeedInfoId(seedInfo.getId());
                seedWeigh.setCreateTime(new Date());
                seedWeigh.setFirstWeighCheck(false);
                seedWeighService.saveSeedWeigh(seedWeigh);
            } else {
                List<SeedWeigh> collect = seedWeighs.stream().filter(x -> x.getFirstWeigh() != null).collect(Collectors.toList());
                if (collect.size() == 0) {
                    throw new Exception("第一次称重数据为空");
                }
                orderTrayWeighDTO.setFlag(true);

                SeedWeigh seedWeigh = seedWeighs.get(0);
                seedWeigh.setSecondWeigh(realityWeigh);
                seedWeigh.setUpdateTime(new Date());
                seedWeigh.setSecondWeighCheck(false);
                seedWeigh.setAuthorityLeave(true);
                seedWeighService.updateSeedWeigh(seedWeigh);
            }
        }

        return orderTrayWeighDTO;
    }

    @Override
    public void saveTrayWeigh(int orderBillId, String orderTrayNo,String locationNo,boolean bool) throws Exception {
        List<SeedInfo> seedInfos = seedInfoService.findSeedInfoByMap(MapUtils.put("orderBillId", orderBillId).put("orderTrayNo", orderTrayNo).getMap());
        if (seedInfos.size() > 0) {
            return;
        } else {
            //todo:请求wms获取重量
            BigDecimal trayWeigh = new BigDecimal(0);
            //修改订单框接驳点位订单拖编号 是铁笼则不用写入编号
            if (!bool){
                this.changeOrderTrayNo(orderTrayNo,locationNo);
            }
            //保存订单框重量
            List<OrderBox> orderBoxNos = orderBoxService.findByMap(MapUtils.put("orderBoxNo", orderTrayNo).getMap());
            if (orderBoxNos.size() == 1) {
                OrderBox orderBox = orderBoxNos.get(0);
                orderBox.setTrayWeigh(trayWeigh);
                orderBox.setCreateTime(new Date());
                orderBoxService.updateOrderBox(orderBox);
            } else if (orderBoxNos.size() == 0) {
                OrderBox orderBox = new OrderBox();
                orderBox.setOrderBoxNo(orderTrayNo);
                orderBox.setTrayWeigh(trayWeigh);
                orderBox.setCreateTime(new Date());
                orderBoxService.saveOrderBox(orderBox);
            } else {
                throw new Exception("订单拖【" + orderTrayNo + "】查询到【" + orderBoxNos.size() + "】条数据");
            }
            //
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pickingComplete(int stationId, String containerNo, String orderTrayNo, int orderBillId) throws Exception {
        if (StringUtils.isBlank(containerNo)) {
            throw new Exception("容器号不能为空");
        }
        if (StringUtils.isBlank(orderTrayNo)) {
            throw new Exception("订单拖不能为空");
        }
        Station station = stationService.findById(stationId);
        if (station == null) {
            throw new RuntimeException(stationId + "站台不存在");
        }
        boolean b = checkContainerExist(containerNo, stationId);
        if (b) {
            throw new Exception("容器【" + containerNo + "】已离开站台请勿重复操作");
        }
        //todo:注释物料容器放行

//        校验订单是否完成
        boolean flag = orderDetailService.orderPickingFinish(orderBillId);
        this.containerNoLeave(containerNo, stationId);
        if (flag) {
            //切换拣选单
            this.changePickingOrder(station);
            //明细转历史
            orderBillService.orderBillToHistory(orderBillId);
            //订单拖放行 贴标区或非贴标区
            //todo:2020:1:26注释订单拖放行
            logger.info(orderTrayNo+"订单拖放行");
//           this.orderTrayLeave(orderTrayNo, orderBillId);
        }


    }

    @Override
    public void startSeedToWms(int orderBillId,String orderNo) throws Exception {
        List<SeedInfo> seedInfos = seedInfoService.findSeedInfoByMap(MapUtils.put("orderBillId", orderBillId).getMap());
        if (seedInfos.size() > 0){
            return;
        }else {
            //回告wms

            WmsStartOrderCallBackDto wmsStartOrderCallBackDto = new WmsStartOrderCallBackDto();
            wmsStartOrderCallBackDto.setBILLNO(orderNo);
            wmsStartOrderCallBackDto.setSTATUS("1");
            wmsService.startOrderCallBack(wmsStartOrderCallBackDto);
        }
    }

    /**
     * 修改接驳点订单拖号
     * @param orderTrayNo
     * @param locationNo
     * @throws Exception
     */
    private void changeOrderTrayNo(String orderTrayNo,String locationNo) throws Exception {
        List<ContainerPathTask> containerPathTaskList = containerPathTaskService.findByMap(MapUtils.put("targetLocation", locationNo).
                put("taskState",ContainerPathTask.TASK_STATE_NOT).getMap());
        if (containerPathTaskList.size() == 0){
            throw new Exception("接驳点【"+locationNo+"】容器未到位");

        }
        List<ContainerPathTaskDetail> containerPathTaskDetails = containerPathTaskDetailService.findPathTaskDetailByMap(MapUtils.put("sourceLocation", locationNo).getMap());
        if (containerPathTaskDetails.size() == 0){
            throw new Exception("接驳点"+locationNo+"路径异常");
        }
        containerPathTaskList.get(0).setContainerNo(orderTrayNo);
        containerPathTaskList.get(0).setPalletNo(orderTrayNo);
        containerPathTaskService.updateTask(containerPathTaskList.get(0));
        containerPathTaskDetails.get(0).setContainerNo(orderTrayNo);
        containerPathTaskDetails.get(0).setPalletNo(orderTrayNo);
        containerPathTaskDetailService.updateTaskDetail(containerPathTaskDetails.get(0));

    }
}
