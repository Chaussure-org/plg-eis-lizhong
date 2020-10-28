package com.prolog.eis.engin.service.impl;

import com.prolog.eis.dto.lzenginee.*;
import com.prolog.eis.engin.dao.AgvBindingDetaileMapper;
import com.prolog.eis.engin.dao.LineBindingDetailMapper;
import com.prolog.eis.engin.dao.TrayOutMapper;
import com.prolog.eis.engin.service.BoxOutEnginService;
import com.prolog.eis.engin.service.TrayOutEnginService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.eis.model.location.StoreArea;
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

    @Autowired
    private PathSchedulingService pathSchedulingService;

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
            this.updateAllStore(wmsAddOrder);
        }

        //orderBillId  没有指定目的区域 的 订单明细
        List<OutDetailDto> outDetails = orderDetailMapper.findOutDetails();

        if (outDetails.isEmpty()) {
            return;
        }
        Map<Integer, List<OutDetailDto>> orderDetailMap = outDetails.stream().sorted(Comparator.comparing(OutDetailDto::getWmsOrderPriority).reversed()).collect(
                Collectors.groupingBy(OutDetailDto::getOrderBillId));

        //堆垛机 agv 区域 所有的库存减掉 已经占用的库存
        List<StoreGoodsCount> storeGoodsCount = containerStoreMapper.findStoreGoodsCount("MCS01,MCS02,MCS03,MCS04,RCS01");
        Map<Integer, Integer> containerStoreMap = storeGoodsCount.stream().collect(Collectors.toMap(StoreGoodsCount::getGoodsId, StoreGoodsCount::getQty, (v1, v2) -> {
            return v1 + v2;
        }));

        // 箱库的库存 再加上循环线的库存（待加）
        List<StoreGoodsCount> boxGoodsCount = containerStoreMapper.findStoreGoodsCount("SAS01,L01");
        Map<Integer, Integer> boxStoreMap = boxGoodsCount.stream().collect(Collectors.toMap(StoreGoodsCount::getGoodsId, StoreGoodsCount::getQty, (v1, v2) -> {
            return v1 + v2;
        }));


        //计算 立库和 agv 的库存  可以满足的 订单的所需数量 的订单
        boolean b = this.computeAreaByOrder(orderDetailMap, containerStoreMap, OrderBill.FIRST_PRIORITY, StoreArea.RCS01);
        if (b) {
            //直到把 1类 的算完
            return;
        } else {
            //计算 箱库 可以满足的 订单的所需数量 的订单
            boolean bool = this.computeAreaByOrder(orderDetailMap, boxStoreMap, OrderBill.SECOND_PRIORITY, StoreArea.L01);
            if (bool == false) {
                //这一部分订单是由两个库区的库存组成的
                this.computeAllStoreByOrder(orderDetailMap, containerStoreMap, boxStoreMap);
            }
        }
    }

    @Override
    public void trayOutByOrder() throws Exception {
        //判断agv_binding_detail 里有状态为10 的，判断agv空闲位置，生成路径
        List<AgvBindingDetail> detailStatus = agvBindingDetaileMapper.findByMap(MapUtils.put("detailStatus", OrderBill.ORDER_STATUS_START_OUT).getMap(), AgvBindingDetail.class);
        if (!detailStatus.isEmpty()) {
            pathSchedulingService.containerMoveTask(detailStatus.get(0).getContainerNo(), StoreArea.RCS01, null);
            agvBindingDetaileMapper.updateAgvStatus(detailStatus.get(0).getContainerNo(),OrderBill.ORDER_STATUS_OUTING);
            logger.info(detailStatus.get(0).getContainerNo()+"生成去往agv区域路径======================");
            return;
        }
        //1.要去往agv区域的订单明细,排除已经生成agv任务计划的， 然后按时间排序
        List<OutDetailDto> agvDetailList = orderDetailMapper.findAgvDetail(StoreArea.RCS01);
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
        // 商品分组
        Map<Integer, List<OutDetailDto>> goodsIdMap = detailDtos.stream().collect(Collectors.groupingBy(x -> x.getGoodsId()));

        for (Map.Entry<Integer, List<OutDetailDto>> map : goodsIdMap.entrySet()) {

            int sum = map.getValue().stream().mapToInt(x -> x.getPlanQty()).sum();

            //商品id，总数，算出所需要出的总箱子
            List<OutContainerDto> outContainersByGoods = this.outByGoodsId(map.getKey(), sum);
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
    public synchronized List<OutContainerDto> outByGoodsId(int goodsId, int count) throws Exception {
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
        if (agvGoodsCounts.size()>0) {
            for (RoadWayGoodsCountDto goodsCountDto : agvGoodsCounts) {
                if (goodsCountDto.getQty() <= 0) {
                    continue;
                }
                if (sumCount >= count) {
                    isContinue = false;
                    break;
                }
                OutContainerDto outContainer = this.getOutContainer(goodsCountDto, goodsId);
                outContainerDtoList.add(outContainer);
                sumCount += goodsCountDto.getQty();
            }
        }

        if (isContinue) {
            //1. Comparator.comparing(类::属性一).reversed();
            //2. Comparator.comparing(类::属性一,Comparator.reverseOrder());
            //两种排序是完全不一样的,一定要区分开来 1 是得到排序结果后再排序,2是直接进行排序,很多人会混淆导致理解出错,2更好理解,建议使用2

           //1移位数最少 2.巷道任务数最少的 3.箱子库存数量最多的
            List<RoadWayGoodsCountDto> sortList = roadWayGoodsCounts.stream().sorted(Comparator.comparing(RoadWayGoodsCountDto::getDeptNum).
                    thenComparing(RoadWayGoodsCountDto::getTaskCount).
                    thenComparing(RoadWayGoodsCountDto::getQty,Comparator.reverseOrder())).
                    collect(Collectors.toList());

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

    private void sendPathTask() {

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
        boolean isAdd;
        for (Integer orderBillId : orderDetailMap.keySet()) {
            isAdd = true;
            for (OutDetailDto orderDetail : orderDetailMap.get(orderBillId)) {
                //一个订单下 如果有任何一个订单明细不满足 则不加入,找下个订单
                if (containerStoreMap.containsKey(orderDetail.getGoodsId()) == false || containerStoreMap.get(orderDetail.getGoodsId()) < orderDetail.getPlanQty()) {
                    isAdd = false;
                    break;
                }
            }
            if (isAdd) {
                //总库存剪掉这个订单的库存
                for (Integer k : containerStoreMap.keySet()) {
                    for (OutDetailDto orderDetail : orderDetailMap.get(orderBillId)) {
                        if (orderDetail.getGoodsId() == k) {
                            containerStoreMap.put(k, containerStoreMap.get(k) - orderDetail.getPlanQty());
                        }
                    }
                }
                details.addAll(orderDetailMap.get(orderBillId).stream().map(OutDetailDto::getDetailId).collect(Collectors.toList()));
                ids.add(orderBillId);
            }
        }
        if (details.size() > 0) {
            logger.info(ids.toString()+"找到"+priority+"类订单");
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
        boolean isAdd = true;
        for (Integer orderBillId : orderDetailMap.keySet()) {
            isAdd = true;
            for (OutDetailDto orderDetail : orderDetailMap.get(orderBillId)) {
                if (containerTrayMap.containsKey(orderDetail.getGoodsId()) && containerTrayMap.get(orderDetail.getGoodsId()) >= orderDetail.getPlanQty()) {
                    trayDetailIds.add(orderDetail.getDetailId());
                    containerTrayMap.put(orderBillId, containerTrayMap.get(orderBillId) - orderDetail.getPlanQty());
                    continue;
                }
                if (containerBoxMap.containsKey(orderDetail.getGoodsId()) && containerBoxMap.get(orderDetail.getGoodsId()) >= orderDetail.getPlanQty()) {
                    boxDetailIds.add(orderDetail.getDetailId());
                    containerBoxMap.put(orderBillId, containerBoxMap.get(orderBillId) - orderDetail.getPlanQty());
                } else {
                    //当一个明细， 则需要从两边的库存一起出库，其中一个库区是一定会出完的
                    if (containerTrayMap.containsKey(orderDetail.getGoodsId()) && containerBoxMap.containsKey(orderDetail.getGoodsId()) &&
                            (containerTrayMap.get(orderDetail.getGoodsId()) + containerBoxMap.get(orderDetail.getGoodsId()) - orderDetail.getPlanQty()) >= 0) {
                        orderDetailMapper.updateMapById(orderDetail.getDetailId(), MapUtils.put("trayPlanQty", containerTrayMap.get(orderDetail.getGoodsId())).getMap(), OrderDetail.class);
                        //更新目的位置
                    } else {
                        isAdd = false;
                        logger.info("明细" + orderDetail.getDetailId() + "商品" + orderDetail.getGoodsId() + "==============库存不足========");
                    }
                }
            }
            if (isAdd) {
                //库存不满足的订单不加入
                ids.add(orderBillId);
            }
        }
        if (trayDetailIds.size() > 0) {
            updateOrderDetailArea(trayDetailIds, StoreArea.RCS01);
        }
        if (boxDetailIds.size() > 0) {
            updateOrderDetailArea(boxDetailIds, StoreArea.L01);
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
        logger.info("生成agv绑定明细"+list.toString());
    }

    /**
     * @throws Exception
     */
    private void updateAllStore(List<OrderBill> wmsAddOrder) throws Exception {
        orderBillMapper.updateDetailsArea();
        List<Integer> ids = wmsAddOrder.stream().map(OrderBill::getId).collect(Collectors.toList());
        orderBillMapper.updateWmsPriority(StringUtils.join(ids, ","));
        //删除agv 和 输送线的绑定明细
        agvBindingDetaileMapper.deleteWmsAgvBindingDetail();
        lineBindingDetailMapper.deleteWmsAgvBindingDetails();
    }
}
