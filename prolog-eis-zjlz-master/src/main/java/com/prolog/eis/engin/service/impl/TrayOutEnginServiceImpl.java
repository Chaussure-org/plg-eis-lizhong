package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.*;
import com.prolog.eis.engin.dao.TrayOutMapper;
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

    /**
     * 1.出库时效
     * 2.出库 订单在立库里 库存满足的订单 在订单明细里记状态 出库至 agv 区域
     * 3.不满足时 判断
     * 该订单明细 在立库和箱库 所需要出库的 容器的数量
     * 数量少的 标记 目的的出库位置
     *只是确定订单明细的要从哪里出库 并且给订单划分 优先级
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
                updateOrderDetailArea(boxMap, "d",OrderBill.SECOND_PRIORITY);
            } else {
                //这一部分订单是由两个库区的库存组成的
                this.computeAllStoreByOrder(orderDetailMap, containerStoreMap, boxStoreMap);
            }
        }
    }

    @Override
    public void trayOutByOrder(List<OrderBill> orderBills) throws Exception {
        //1.要去往agv区域的订单明细 然后订单明细的 订单优先级是高的
        List<GoAgvDetailDto> agvDetailList = orderDetailMapper.findAgvDetail();
        agvDetailList.stream().sorted(Comparator.comparing(GoAgvDetailDto::getPriority));
        for (GoAgvDetailDto agvDetailDto : agvDetailList) {
            boolean b = this.outByGoodsId(agvDetailDto.getGoodsId(), agvDetailDto.getQty());
            if (b) {
                //每次生成几个 去往agv区域的 路径
                break;
            }
        }
    }

    /**
     * 1.根据goodsId count 找到具体的托盘 货位进行出库
     * 规则：
     * 其实 也就是 生成 料箱出库明细  料箱出库汇总
     * 生成路径 path 给点位
     *
     * @throws Exception
     */
    @Override
    public synchronized boolean outByGoodsId(int goodsId, int count) throws Exception {
        /** 1.首先应该找巷道的均衡 计算每层的出库任务数 和 入库任务数
         4.计算商品在每层非锁定的料箱数量
         5.得到 每一层总的任务数  出库任务数 入库 任务数 非锁定的料箱数量
         6.排序 保证出库任务的均衡
         // 如果巷道作业数排序相同，再按层出库任务数排序（升序）
         // 如果层出库任务数相同，再按层入库任务数排序（升序）
         // 如果层任务数相同，再按料箱数排序（降序）
         7.找到是哪一个层
         8.找到本层 该商品的货位和数量 以及移位数 最少的，离提升机 距离最近
         满足明细数量的，注：尾托的 概念 以及比例的选择
         */
        //巷道的出库任务数 和入库任务数
        List<RoadWayContainerTaskDto> layerContainerTasks = trayOutMapper.findRoadWayContainerTask();
        //巷道库存的 goodsId 和 goodsCount
        List<RoadWayGoodsCountDto> roadWayGoodsCounts = trayOutMapper.findRoadWayGoodsCount(goodsId);
        //agv区域的库存
        List<RoadWayGoodsCountDto> agvGoodsCounts = trayOutMapper.findAgvGoodsCount(goodsId);
        List<AgvBindingDetail> agvBindingDetails = new ArrayList<AgvBindingDetail>();
        boolean isContinue=true;
        //优先从agv库存找
        for (RoadWayGoodsCountDto roadWayGoodsCountDto:agvGoodsCounts){
            if (roadWayGoodsCountDto.getQty()>count){
                //重新生成明细
                isContinue=false;
                break;
            }
        }
        if (!isContinue){
            return true;
        }
        //箱库库存找
        int sumCount=0;
        for (RoadWayGoodsCountDto roadWayGoodsCountDto : roadWayGoodsCounts) {
            if (sumCount>=count){
                return true;
            }
            //生成 agv_binding_detail
            sumCount+=roadWayGoodsCountDto.getQty();
        }
        return true;
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
            Set<Integer>trayList=new HashSet<>();
            Set<Integer>boxList=new HashSet<>();
            for (OrderDetail orderDetail : orderDetailMap.get(key)) {
                //首先出库托盘库的托盘，然后出库立库的托盘
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
            trayMap.put(key,trayList);
            boxMap.put(key,trayList);
        }
        if (trayMap.size() > 0) {
            updateOrderDetailArea(trayMap, "a",OrderBill.THIRD_PRIORITY);
        }
        if (boxMap.size() > 0) {
            updateOrderDetailArea(boxMap, "c",OrderBill.THIRD_PRIORITY);
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
}
