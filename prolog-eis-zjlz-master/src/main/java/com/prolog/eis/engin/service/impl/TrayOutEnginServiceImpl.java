package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.*;
import com.prolog.eis.engin.dao.AgvBindingDetaileMapper;
import com.prolog.eis.engin.dao.TrayOutMapper;
import com.prolog.eis.engin.service.BoxOutEnginService;
import com.prolog.eis.engin.service.TrayOutEnginService;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.order.dao.OrderBillMapper;
import com.prolog.eis.order.dao.OrderDetailMapper;
import com.prolog.eis.store.dao.OContainerStoreMapper;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 托盘库 出库调度
 *
 * @author SunPP
 */
@Service
public class TrayOutEnginServiceImpl implements TrayOutEnginService {

    private final Logger logger = LoggerFactory.getLogger(TrayOutEnginServiceImpl.class);
    @Autowired
    private OContainerStoreMapper containerStoreMapper;
    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private TrayOutMapper trayOutMapper;
    @Autowired
    private AgvBindingDetaileMapper agvBindingDetaileMapper;

    @Autowired
    private BoxOutEnginService boxOutEnginService;

    /**
     * 1.出库时效
     * 2.出库 订单在立库里 库存满足的订单 在订单明细里记状态 出库至 agv 区域
     * 3.不满足时 判断
     * 该订单明细 在立库和箱库 所需要出库的 容器的数量
     * 数量少的 标记 目的的出库位置
     * 只是确定订单明细的要从哪里出库 并且给订单划分 优先级
     *
     * @throws Exception
     */
    @Override
    public void initOrder(List<OrderBill> orderBills) throws Exception {
        //立库 和 agv区没有订单明细 箱库 的库存
        List<StoreGoodsCount> storeGoodsCount = containerStoreMapper.findStoreGoodsCount();
        //orderBillId  没有指定目的区域 的 订单明细
        Map<Integer, List<OrderDetail>> orderDetailMap = orderDetailMapper.findByMap(MapUtils.put("areaNo", "").getMap(), OrderDetail.class).stream().collect(
                Collectors.groupingBy(OrderDetail::getOrderBillId));

        //goodsId qty 立库 和 agv区没有订单明细 的库存
        Map<Integer, Integer> containerStoreMap = storeGoodsCount.stream().filter(x -> !x.getSourceArea().equals("C")).
                collect(Collectors.toMap(StoreGoodsCount::getGoodsId, StoreGoodsCount::getQty, (v1, v2) -> {
                    return v1 + v2;
                }));
        //goodsId qty 箱库 的库存
        Map<Integer, Integer> boxStoreMap = storeGoodsCount.stream().filter(x -> x.getSourceArea().equals("C")).
                collect(Collectors.toMap(StoreGoodsCount::getGoodsId, StoreGoodsCount::getQty, (v1, v2) -> {
                    return v1 + v2;
                }));
        //计算 立库和 agv 有订单明细的库存  可以满足的 订单的所需数量 的订单
        Map<Integer, Set<Integer>> map = this.computeAreaByOrder(orderDetailMap, containerStoreMap);
        if (map.size() > 0) {
            updateOrderDetailArea(map, "A", OrderBill.FIRST_PRIORITY);
        } else {
            //计算 箱库 可以满足的 订单的所需数量 的订单
            Map<Integer, Set<Integer>> boxMap = this.computeAreaByOrder(orderDetailMap, boxStoreMap);
            if (boxMap.size() > 0) {
                updateOrderDetailArea(boxMap, "d", OrderBill.SECOND_PRIORITY);
            } else {
                //这一部分订单是由两个库区的库存组成的
                this.computeAllStoreByOrder(orderDetailMap, containerStoreMap, boxStoreMap);
            }
        }
    }

    @Override
    public void trayOutByOrder() throws Exception {
        //判断agv_binding_detail 里有没有状态为10 的，判断agv空闲位置，生成路径
        //wms订单优先级的订单找库存
        List<AgvBindingDetail> detailStatus = agvBindingDetaileMapper.findByMap(MapUtils.put("detailStatus", OrderBill.ORDER_STATUS_START_OUT).getMap(), AgvBindingDetail.class);
        if (!detailStatus.isEmpty()) {
            // TODO: 2020/10/13 生成路径 更新状态为 20
            return;
        }
        //1.要去往agv区域的订单明细 并且优先级是 第1 优先级 然后按时间排序
        List<OutDetailDto> agvDetailList = orderDetailMapper.findAgvDetail("A");
        List<OutDetailDto> wmsOutdetails = agvDetailList.stream().filter(x -> x.getWmsOrderPriority() == OrderBill.WMS_PRIORITY).collect(Collectors.toList());
        if (!wmsOutdetails.isEmpty()) {
            //有wms订单优先级,整体算出库的绑定明细
            List<OutContainerDto> outContainerDtoList = this.outByDetails(wmsOutdetails);
            //生成agv绑定明细
        } else {
            List<OutDetailDto> agvDetails = agvDetailList.stream().sorted(Comparator.comparing(OutDetailDto::getOrderPriority)).collect(Collectors.toList());
            List<Integer> orderIds = boxOutEnginService.computeRepeat(agvDetails);
            List<OutDetailDto> details = agvDetails.stream().filter(x -> orderIds.contains(x.getOrderBillId())).collect(Collectors.toList());
            if (!details.isEmpty()) {
                List<OutContainerDto> outContainerDtoList = this.outByDetails(details);
            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public List<OutContainerDto> outByDetails(List<OutDetailDto> detailDtos) throws Exception {
        List<OutContainerDto> outContainerList = new ArrayList<>();
        int wmsPriority = detailDtos.get(0).getWmsOrderPriority();
        //按 商品分组
        Map<Integer, List<OutDetailDto>> goodsIdMap = detailDtos.stream().collect(Collectors.groupingBy(x -> x.getGoodsId()));
        for (Map.Entry<Integer, List<OutDetailDto>> map : goodsIdMap.entrySet()) {
            for (OutDetailDto outDetailDto : map.getValue()) {
                int sum = map.getValue().stream().mapToInt(x -> x.getPlanQty()).sum();
                List<OutContainerDto> outContainerDtoList = this.outByGoodsId(map.getKey(), sum, wmsPriority);
                for (OutContainerDto outContainerDto : outContainerDtoList) {
                    if (outContainerDto.getQty() == 0) {
                        break;
                    }

                    OutDetailDto orderDetail = new OutDetailDto();
                    if (outContainerDto.getQty() >= outDetailDto.getPlanQty()) {
                        outContainerDto.setQty(outContainerDto.getQty() - outDetailDto.getPlanQty());
                        outDetailDto.setPlanQty(0);
                        orderDetail.setPlanQty(outDetailDto.getPlanQty());
                    } else {
                        outContainerDto.setQty(0);
                        outDetailDto.setPlanQty(outDetailDto.getPlanQty() - outContainerDto.getQty());
                        orderDetail.setPlanQty(outContainerDto.getQty());
                    }
                    //给此容器添加绑定的明细
                    orderDetail.setDetailId(outDetailDto.getDetailId());
                    outContainerDto.getDetailList().add(orderDetail);
                }
                outContainerList.addAll(outContainerDtoList);
            }
        }
        return outContainerList;
    }

    /**
     * 1.根据goodsId count
     * 返回：料箱 货位 goodsId
     *
     * @throws Exception
     */
    @Override
    public List<OutContainerDto> outByGoodsId(int goodsId, int count, int wmsPriority) throws Exception {
        /**1.移位数最少 2.巷道任务数最少
         2.找到本层 该商品的货位和数量 以及移位数 最少的
         满足明细数量的，注：尾托的 概念 以及比例的选择
         */
        //巷道的出库任务数 和入库任务数
        List<RoadWayContainerTaskDto> RoadWayContainerTasks = trayOutMapper.findRoadWayContainerTask();
        //巷道库存的 goodsId 和 goodsCount
        List<RoadWayGoodsCountDto> roadWayGoodsCounts = trayOutMapper.findRoadWayGoodsCount(goodsId);
        //agv区域的库存 未绑定明细的
        List<RoadWayGoodsCountDto> agvGoodsCounts = trayOutMapper.findAgvGoodsCount(goodsId);
        List<OutContainerDto> outContainerDtoList = new ArrayList<>();
        //给任务数赋值
        for (RoadWayContainerTaskDto taskDto : RoadWayContainerTasks) {
            for (RoadWayGoodsCountDto GoodsCountDto : roadWayGoodsCounts) {
                if (taskDto.getRoadWay() == GoodsCountDto.getRoadWay()) {
                    GoodsCountDto.setTaskCount(taskDto.getInCount() + taskDto.getOutCount());
                }
            }
        }
        boolean isContinue = true;
        int sumCount = 0;
        //优先从agv库存找
        if (wmsPriority == 10) {
            //wms优先级高的订单从所有的 agv库存，包括已经binding的 不是wms优先级的托盘,
            //agv区域的库存 未绑定明细的
            List<RoadWayGoodsCountDto> wmsAgvGoods = trayOutMapper.findAgvGoodsCount(goodsId);
            for (RoadWayGoodsCountDto roadWayGoodsCountDto : wmsAgvGoods) {
                if (sumCount < count) {
                    OutContainerDto outContainer = getOutContainer(roadWayGoodsCountDto, goodsId);
                    outContainerDtoList.add(outContainer);
                    //删除原来agv区域绑定的订单
                    this.deleteAgvBindingDetail(outContainer);
                }
            }
        } else {
            for (RoadWayGoodsCountDto roadWayGoodsCountDto : agvGoodsCounts) {
                if (roadWayGoodsCountDto.getQty() >= count) {
                    OutContainerDto outContainer = getOutContainer(roadWayGoodsCountDto, goodsId);
                    outContainerDtoList.add(outContainer);
                    isContinue = false;
                    break;
                }
            }
        }

        //agv库存没有 从箱库里面找

        if (isContinue) {
            //先找移位数最少 再找巷道任务数最少
            roadWayGoodsCounts.stream().sorted(Comparator.comparing(RoadWayGoodsCountDto::getDeptNum).thenComparing(RoadWayGoodsCountDto::getTaskCount));
            if (sumCount < count) {
                for (RoadWayGoodsCountDto goodsCountDto : roadWayGoodsCounts) {
                    OutContainerDto outContainer = getOutContainer(goodsCountDto, goodsId);
                    outContainerDtoList.add(outContainer);
                    sumCount += goodsCountDto.getQty();
                }
            }
        }
        return outContainerDtoList;
    }

    private OutContainerDto getOutContainer(RoadWayGoodsCountDto goodsCountDto, int goodsId) {
        OutContainerDto outContainerDto = new OutContainerDto();
        outContainerDto.setContainerNo(goodsCountDto.getContainerNo());
        outContainerDto.setGoodsId(goodsId);
        outContainerDto.setStoreLocation(goodsCountDto.getStoreLocation());
        outContainerDto.setQty(goodsCountDto.getQty());
        return outContainerDto;
    }

    /**
     * 删除原来agv绑定的订单明细
     */
    private void deleteAgvBindingDetail(OutContainerDto outContainerDto) {
        agvBindingDetaileMapper.deleteByMap(MapUtils.put("containerNo", outContainerDto.getContainerNo()).getMap(), AgvBindingDetail.class);
    }

    /**
     * 计算订单所满足的 区域
     *
     * @param orderDetailMap
     * @param containerStoreMap
     * @return 订单明细集合
     */
    private Map<Integer, Set<Integer>> computeAreaByOrder(Map<Integer, List<OrderDetail>> orderDetailMap, Map<Integer, Integer> containerStoreMap) {
        //出库 订单在立库里 库存满足的订单 在订单明细里记状态
        Map<Integer, Set<Integer>> map = new HashMap<>();

        boolean isAdd = true;
        for (Integer key : orderDetailMap.keySet()) {
            for (OrderDetail orderDetail : orderDetailMap.get(key)) {
                //一个订单下 如果有任何一个订单明细不满足 则不加入
                if (containerStoreMap.containsKey(orderDetail.getGoodsId()) == false || containerStoreMap.get(orderDetail.getGoodsId()) < orderDetail.getPlanQty()) {
                    isAdd = false;
                    continue;
                }
            }
            if (isAdd) {
                Set<Integer> orderDetailIdList = new HashSet<>();
                orderDetailIdList.addAll(orderDetailMap.get(key).stream().map(OrderDetail::getId).collect(Collectors.toList()));
                map.put(key, orderDetailIdList);
            }
        }
        return map;
    }

    /**
     * 订单所需库存 分布在两个库区里
     *
     * @param orderDetailMap
     * @param containerTrayMap
     * @param containerBoxMap
     * @return 订单明细集合
     */
    private void computeAllStoreByOrder(Map<Integer, List<OrderDetail>> orderDetailMap, Map<Integer, Integer> containerTrayMap, Map<Integer, Integer> containerBoxMap) {
        Map<Integer, Set<Integer>> trayMap = new HashMap<Integer, Set<Integer>>();
        Map<Integer, Set<Integer>> boxMap = new HashMap<Integer, Set<Integer>>();
        for (Integer key : orderDetailMap.keySet()) {
            Set<Integer> trayList = new HashSet<>();
            Set<Integer> boxList = new HashSet<>();
            for (OrderDetail orderDetail : orderDetailMap.get(key)) {
                //首先出库托盘库的托盘，然后出库立库的托盘
                //当两边的库区都有此商品时，计算从那个库存的出的容器数最少
                if (containerTrayMap.containsKey(orderDetail.getGoodsId()) && containerTrayMap.get(orderDetail.getGoodsId()) >= orderDetail.getPlanQty()) {
                    trayList.add(orderDetail.getGoodsId());
                    continue;
                }
                if (containerBoxMap.containsKey(orderDetail.getGoodsId()) && containerBoxMap.get(orderDetail.getGoodsId()) >= orderDetail.getPlanQty()) {
                    boxList.add(orderDetail.getGoodsId());
                } else {
                    logger.info("======================库存不足================");
                }
            }
            trayMap.put(key, trayList);
            boxMap.put(key, trayList);
        }
        if (trayMap.size() > 0) {
            updateOrderDetailArea(trayMap, "a", OrderBill.THIRD_PRIORITY);
        }
        if (boxMap.size() > 0) {
            updateOrderDetailArea(boxMap, "c", OrderBill.THIRD_PRIORITY);
        }
    }

    private void updateOrderDetailArea(Map<Integer, Set<Integer>> map, String area, int priority) {
        for (Integer orderId : map.keySet()) {
            Criteria ctr = Criteria.forClass(OrderDetail.class);
            ctr.setRestriction(Restrictions.in("id", map.get(orderId).toArray()));
            orderDetailMapper.updateMapByCriteria(MapUtils.put("areaNo", area).getMap(), ctr);
            orderBillMapper.updateMapById(orderId, MapUtils.put("orderPriority", priority).getMap(), OrderBill.class);
        }
    }

    private void saveAgvBindingDetail(List<OutContainerDto> outList) {
        for (OutContainerDto containerDto : outList) {
            for (OutDetailDto detailDto : containerDto.getDetailList()) {
                AgvBindingDetail agvBindingDetail = new AgvBindingDetail();
                agvBindingDetail.setOrderMxId(detailDto.getDetailId());
                agvBindingDetail.setGoodsId(containerDto.getGoodsId());
                agvBindingDetail.setBindingNum(detailDto.getPlanQty());
                agvBindingDetail.setContainerNo(containerDto.getContainerNo());
                agvBindingDetail.setOrderPriority(detailDto.getOrderPriority());
                agvBindingDetail.setWmsOrderPriority(detailDto.getWmsOrderPriority());
                agvBindingDetail.setUpdateTime(new Date());
            }
        }
    }
}
