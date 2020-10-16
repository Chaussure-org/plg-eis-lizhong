package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.*;
import com.prolog.eis.engin.dao.AgvBindingDetaileMapper;
import com.prolog.eis.engin.dao.LineBindingDetailMapper;
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
import org.apache.commons.lang.StringUtils;
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
    @Autowired
    private LineBindingDetailMapper lineBindingDetailMapper;

    /**
     * 1.出库时效
     * 2.出库 订单在立库里 库存满足的订单 在订单明细里记状态 出库至 agv 区域
     * 3.不满足时 判断
     * 该订单明细 在立库和箱库 所需要出库的 容器的数量
     * 数量少的 标记 目的的出库位置
     * <p>
     * 只是确定订单明细的要从哪里出库 并且给订单划分 优先级
     *
     * @throws Exception
     */
    @Override
    public void initOrder() throws Exception {

        //新增的 wms 订单优先级的订单
        List<OrderBill> wmsAddOrder = orderBillMapper.findByMap(MapUtils.put("wmsOrderPriority", OrderBill.WMS_ADD_PRIORITY).getMap(), OrderBill.class);
        //如果订单数据中 有wms的订单 则进行订单的更新 和agv
        if (wmsAddOrder.size() > 0) {
            orderBillMapper.updateDetailsArea();
            List<Integer> ids = wmsAddOrder.stream().map(OrderBill::getId).collect(Collectors.toList());
            orderBillMapper.updateWmsPriority(StringUtils.join(ids, ","));
            //删除agv 和 输送线的绑定明细
            deleteAgvAndLineBinding(ids);
        }

        //orderBillId  没有指定目的区域 的 订单明细
        List<OutDetailDto> outDetails= orderDetailMapper.findOutDetails();

        if (outDetails.isEmpty()) {
            return;
        }
        Map<Integer, List<OutDetailDto>> orderDetailMap = outDetails.stream().sorted(Comparator.comparing(OutDetailDto::getWmsOrderPriority).reversed()).collect(
                Collectors.groupingBy(OutDetailDto::getOrderBillId));

        //堆垛机 排除 已经指定了目的 区域的明细的数量 和agv区域没有绑定明细的
        List<StoreGoodsCount> storeGoodsCount = containerStoreMapper.findStoreGoodsCount();
        Map<Integer, Integer> containerStoreMap = storeGoodsCount.stream().filter(x -> !x.getSourceArea().equals("B100")).
                collect(Collectors.toMap(StoreGoodsCount::getGoodsId, StoreGoodsCount::getQty, (v1, v2) -> {
                    return v1 + v2;
                }));

        // 箱库的库存 再加上循环线的库存（待加）
        Map<Integer, Integer> boxStoreMap = storeGoodsCount.stream().filter(x -> x.getSourceArea().equals("B100")).
                collect(Collectors.toMap(StoreGoodsCount::getGoodsId, StoreGoodsCount::getQty, (v1, v2) -> {
                    return v1 + v2;
                }));


        //计算 立库和 agv 的库存  可以满足的 订单的所需数量 的订单
        boolean b = this.computeAreaByOrder(orderDetailMap, containerStoreMap, OrderBill.FIRST_PRIORITY, "A100");
        if (b) {
            //直到把 1类 的算完
            return;
        } else {
            //计算 箱库 可以满足的 订单的所需数量 的订单
            boolean bool = this.computeAreaByOrder(orderDetailMap, boxStoreMap, OrderBill.SECOND_PRIORITY, "L100");
            if (bool == false) {
                //这一部分订单是由两个库区的库存组成的
                this.computeAllStoreByOrder(orderDetailMap, containerStoreMap, boxStoreMap);
            }
        }
    }

    @Override
    public void trayOutByOrder() throws Exception {
        //判断agv_binding_detail 里有没有状态为10 的，判断agv空闲位置，生成路径
        List<AgvBindingDetail> detailStatus = agvBindingDetaileMapper.findByMap(MapUtils.put("detailStatus", OrderBill.ORDER_STATUS_START_OUT).getMap(), AgvBindingDetail.class);
        if (!detailStatus.isEmpty()) {
            // TODO: 2020/10/13 生成路径 更新状态为 20
            return;
        }
        //1.要去往agv区域的订单明细,排除已经生成agv任务计划的， 然后按时间排序
        List<OutDetailDto> agvDetailList = orderDetailMapper.findAgvDetail("A100");
        if (agvDetailList.isEmpty()) {
            return;
        }

        List<OutDetailDto> wmsOutdetails = agvDetailList.stream().filter(x -> x.getWmsOrderPriority() == OrderBill.WMS_PRIORITY).collect(Collectors.toList());
        if (!wmsOutdetails.isEmpty()) {
            //有wms订单优先级,整体算出库的绑定明细
            List<OutContainerDto> outContainerDtoList = this.outByDetails(wmsOutdetails);
            this.saveAgvBindingDetail(outContainerDtoList);
        } else {
            List<OutDetailDto> agvDetails = agvDetailList.stream().sorted(Comparator.comparing(OutDetailDto::getOrderPriority)).collect(Collectors.toList());
            List<Integer> orderIds = boxOutEnginService.computeRepeat(agvDetails);
            List<OutDetailDto> details = agvDetails.stream().filter(x -> orderIds.contains(x.getOrderBillId())).collect(Collectors.toList());
            if (!details.isEmpty()) {
                List<OutContainerDto> outContainerDtoList = this.outByDetails(details);
                this.saveAgvBindingDetail(outContainerDtoList);
            }
        }
    }


    /**
     * wms 的订单出库
     *
     * @param detailDtos
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<OutContainerDto> outByDetails(List<OutDetailDto> detailDtos) throws Exception {
        List<OutContainerDto> outContainerList = new ArrayList<OutContainerDto>();
        int wmsPriority = detailDtos.get(0).getWmsOrderPriority();
        //把wms优先级 按 商品分组
        Map<Integer, List<OutDetailDto>> goodsIdMap = detailDtos.stream().collect(Collectors.groupingBy(x -> x.getGoodsId()));

        for (Map.Entry<Integer, List<OutDetailDto>> map : goodsIdMap.entrySet()) {

            int sum = map.getValue().stream().mapToInt(x -> x.getPlanQty()).sum();

            //商品id，总数，算出所需要出的总箱子
            List<OutContainerDto> outContainersByGoods = this.outByGoodsId(map.getKey(), sum, wmsPriority);
            for (OutDetailDto outDetailDto : map.getValue()) {
                //循环每个商品下的明细集合 进行箱子分配 订单明细
                for (OutContainerDto outContainerDto : outContainersByGoods) {
                    //明细已经全部分配完成，则分配下个明细
                    if (outDetailDto.getPlanQty() == 0) {
                        break;
                    }
                    //如果箱子数量为0 则找下个箱子
                    if (outContainerDto.getQty() == 0) {
                        continue;
                    }

                    OutDetailDto temp = new OutDetailDto();
                    if (outContainerDto.getQty() >= outDetailDto.getPlanQty()) {
                        temp.setPlanQty(outDetailDto.getPlanQty());
                        outContainerDto.setQty(outContainerDto.getQty() - outDetailDto.getPlanQty());
                        outDetailDto.setPlanQty(0);
                    } else {
                        temp.setPlanQty(outContainerDto.getQty());
                        outDetailDto.setPlanQty(outDetailDto.getPlanQty() - outContainerDto.getQty());
                        outContainerDto.setQty(0);
                    }
                    //给此容器添加绑定的明细
                    temp.setDetailId(outDetailDto.getDetailId());
                    temp.setOrderBillId(outDetailDto.getOrderBillId());
                    temp.setOrderPriority(outDetailDto.getOrderPriority());
                    temp.setWmsOrderPriority(outDetailDto.getWmsOrderPriority());
                    outContainerDto.getDetailList().add(temp);
                }
            }

            outContainerList.addAll(outContainersByGoods);
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
    public synchronized List<OutContainerDto> outByGoodsId(int goodsId, int count, int wmsPriority) throws Exception {
        /**1.移位数最少 2.巷道任务数最少
         2.找到本层 该商品的货位和数量 以及移位数 最少的
         满足明细数量的，注：尾托的 概念 以及比例的选择
         */
        //巷道的出库任务数 和入库任务数
        List<RoadWayContainerTaskDto> RoadWayContainerTasks = trayOutMapper.findRoadWayContainerTask();
        //巷道库存的 goodsId 和 goodsCount
        List<RoadWayGoodsCountDto> roadWayGoodsCounts = trayOutMapper.findRoadWayGoodsCount(goodsId);
        //agv区域的库存 绑定明细的剩余数量
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
//        //优先从agv库存找
//        if (wmsPriority == 10) {
//            //wms优先级高的订单从所有的 agv库存，包括已经binding的 不是wms优先级的托盘,
//            //agv区域的库存 未绑定明细的
//            List<RoadWayGoodsCountDto> wmsAgvGoods = trayOutMapper.findAgvGoodsCount(goodsId);
//            for (RoadWayGoodsCountDto roadWayGoodsCountDto : wmsAgvGoods) {
//                if (sumCount < count) {
//                    OutContainerDto outContainer = getOutContainer(roadWayGoodsCountDto, goodsId);
//                    outContainerDtoList.add(outContainer);
//                    //删除原来agv区域绑定的订单
//                    this.deleteAgvBindingDetail(outContainer);
//                    //更新已经计算 的订单明细的状态，订单时分开的，订单明细表新加字段
//
//                }
//            }
//        } else {
        for (RoadWayGoodsCountDto goodsCountDto : agvGoodsCounts) {
            if (sumCount >= count) {
                isContinue = false;
                break;
            }
            OutContainerDto outContainer = this.getOutContainer(goodsCountDto, goodsId);
            outContainerDtoList.add(outContainer);
            sumCount += goodsCountDto.getQty();
        }
        // }

        //agv库存没有 从箱库里面找

        if (isContinue) {
            //先找移位数最少 再找巷道任务数最少

            //当需要用到stram多条件排序的时候，需要最后排序的字段需要放在前面排
            List<RoadWayGoodsCountDto> sortList = roadWayGoodsCounts.stream().sorted(Comparator.comparing(RoadWayGoodsCountDto::getTaskCount).
                    thenComparing(RoadWayGoodsCountDto::getQty).reversed().
                    thenComparing(RoadWayGoodsCountDto::getDeptNum)).collect(Collectors.toList());

            for (RoadWayGoodsCountDto goodsCountDto : sortList) {
                if (sumCount >= count) {
                    break;
                }
                OutContainerDto outContainer = this.getOutContainer(goodsCountDto, goodsId);
                outContainerDtoList.add(outContainer);
                sumCount += goodsCountDto.getQty();
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
        if (!outContainerDto.getDetailList().isEmpty()) {
            agvBindingDetaileMapper.deleteByMap(MapUtils.put("containerNo", outContainerDto.getContainerNo()).getMap(), AgvBindingDetail.class);

        }
    }

    /**
     * 计算订单所满足的 区域
     *
     * @param orderDetailMap
     * @param containerStoreMap
     * @return 订单明细集合
     */
    private boolean computeAreaByOrder(Map<Integer, List<OutDetailDto>> orderDetailMap, Map<Integer, Integer> containerStoreMap, int priority, String area) {
        //出库 订单在立库里 库存满足的订单 在订单明细里记状态
        List<Integer> ids = new ArrayList<>();
        List<Integer> details = new ArrayList<>();
//            storeMap.forEach((key, value) -> agvStoreMap.merge(key, value, Integer::sum));
        boolean isAdd = true;
        for (Integer key : orderDetailMap.keySet()) {
            for (OutDetailDto orderDetail : orderDetailMap.get(key)) {
                //一个订单下 如果有任何一个订单明细不满足 则不加入,找下个订单
                if (containerStoreMap.containsKey(orderDetail.getGoodsId()) == false || containerStoreMap.get(orderDetail.getGoodsId()) < orderDetail.getPlanQty()) {
                    isAdd = false;
                    break;
                }
            }
            if (isAdd) {
                //总库存剪掉这个订单的库存
                for (Integer k : containerStoreMap.keySet()) {
                    for (OutDetailDto orderDetail : orderDetailMap.get(key)) {
                        if (orderDetail.getGoodsId() == k) {
                            containerStoreMap.put(k, containerStoreMap.get(k) - orderDetail.getPlanQty());
                        }
                    }
                }
                details.addAll(orderDetailMap.get(key).stream().map(OutDetailDto::getDetailId).collect(Collectors.toList()));
                ids.add(key);
            }
        }
        if (details.size() > 0) {
            this.updateBillPriority(ids, priority);
            this.updateOrderDetailArea(details, area);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 订单所需库存 分布在两个库区里
     *
     * @param orderDetailMap
     * @param containerTrayMap
     * @param containerBoxMap
     * @return 订单明细集合
     */
    private void computeAllStoreByOrder(Map<Integer, List<OutDetailDto>> orderDetailMap, Map<Integer, Integer> containerTrayMap, Map<Integer, Integer> containerBoxMap) {
        List<Integer> ids = new ArrayList<>();
        List<Integer> trayDetailIds = new ArrayList<>();
        List<Integer> boxDetailIds = new ArrayList<>();
//            storeMap.forEach((k, value) -> agvStoreMap.merge(k, value, Integer::sum));
        for (Integer key : orderDetailMap.keySet()) {
            for (OutDetailDto orderDetail : orderDetailMap.get(key)) {
                if (containerTrayMap.containsKey(orderDetail.getGoodsId()) && containerTrayMap.get(orderDetail.getGoodsId()) >= orderDetail.getPlanQty()) {
                    trayDetailIds.add(orderDetail.getDetailId());
                    containerTrayMap.put(key, containerTrayMap.get(key) - orderDetail.getPlanQty());
                    continue;
                }
                if (containerBoxMap.containsKey(orderDetail.getGoodsId()) && containerBoxMap.get(orderDetail.getGoodsId()) >= orderDetail.getPlanQty()) {
                    boxDetailIds.add(orderDetail.getDetailId());
                    containerBoxMap.put(key, containerBoxMap.get(key) - orderDetail.getPlanQty());
                } else {
                    //当一个明细， 则需要从两边的库存一起出库，其中一个库区是一定会出完的
                    if (containerTrayMap.containsKey(orderDetail.getGoodsId()) && containerBoxMap.containsKey(orderDetail.getGoodsId()) &&
                            (containerTrayMap.get(orderDetail.getGoodsId()) + containerBoxMap.get(orderDetail.getGoodsId()) - orderDetail.getPlanQty()) >= 0) {
                        orderDetailMapper.updateMapById(orderDetail.getDetailId(), MapUtils.put("trayPlanQty", containerTrayMap.get(orderDetail.getGoodsId())).getMap(), OrderDetail.class);
                    } else {
                        logger.info("明细" + orderDetail.getDetailId() + "商品" + orderDetail.getGoodsId() + "==============库存不足========");
                    }
                }
            }
            ids.add(key);
        }
        if (trayDetailIds.size() > 0) {
            updateOrderDetailArea(trayDetailIds, "A100");
        }
        if (boxDetailIds.size() > 0) {
            updateOrderDetailArea(boxDetailIds, "L100");
        }
        if (!ids.isEmpty()) {
            updateBillPriority(ids, OrderBill.THIRD_PRIORITY);
        }

    }


    public void updateOrderDetailArea(List<Integer> detailIds, String area) {
        Criteria ctr = Criteria.forClass(OrderDetail.class);
        ctr.setRestriction(Restrictions.in("id", detailIds.toArray()));
        orderDetailMapper.updateMapByCriteria(MapUtils.put("areaNo", area).getMap(), ctr);
    }

    public void updateBillPriority(List<Integer> ids, int priority) {
        Criteria ctr1 = Criteria.forClass(OrderBill.class);
        ctr1.setRestriction(Restrictions.in("id", ids.toArray()));
        orderBillMapper.updateMapByCriteria(MapUtils.put("orderPriority", priority).getMap(), ctr1);
    }

    private void saveAgvBindingDetail(List<OutContainerDto> outList) {
        List<AgvBindingDetail> list = new ArrayList<>();

        for (OutContainerDto containerDto : outList) {
            for (OutDetailDto detailDto : containerDto.getDetailList()) {
                AgvBindingDetail agvBindingDetail = new AgvBindingDetail();

                agvBindingDetail.setContainerNo(containerDto.getContainerNo());
                agvBindingDetail.setOrderBillId(detailDto.getOrderBillId());
                agvBindingDetail.setOrderMxId(detailDto.getDetailId());
                agvBindingDetail.setGoodsId(containerDto.getGoodsId());
                agvBindingDetail.setBindingNum(detailDto.getPlanQty());

                agvBindingDetail.setOrderPriority(detailDto.getOrderPriority());
                agvBindingDetail.setWmsOrderPriority(detailDto.getWmsOrderPriority());
                agvBindingDetail.setDetailStatus(OrderBill.ORDER_STATUS_START_OUT);
                agvBindingDetail.setUpdateTime(new Date());
                list.add(agvBindingDetail);
            }
        }
        agvBindingDetaileMapper.saveBatch(list);
    }

    /**
     * 更新订单的目的区域
     *
     * @throws Exception
     */
    private void deleteAgvAndLineBinding(List<Integer> ids) throws Exception {
        Criteria ctr = new Criteria(AgvBindingDetail.class);
        ctr.setRestriction(Restrictions.in("orderBillId", ids.toArray()));
        agvBindingDetaileMapper.deleteByCriteria(ctr);
        lineBindingDetailMapper.deleteByCriteria(ctr);
    }
}
