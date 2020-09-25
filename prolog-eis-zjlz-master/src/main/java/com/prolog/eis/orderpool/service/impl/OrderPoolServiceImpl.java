//package com.prolog.eis.orderpool.service.impl;
//
//import com.google.common.collect.Lists;
//import com.prolog.eis.dao.order.OrderHzMapper;
//import com.prolog.eis.dao.order.OrderMxMapper;
//import com.prolog.eis.dto.orderpool.OpOrderHz;
//import com.prolog.eis.dto.orderpool.OpOrderMx;
//import com.prolog.eis.model.order.OrderHz;
//import com.prolog.eis.model.order.OrderMx;
//import com.prolog.eis.orderpool.service.OrderPoolService;
//import com.prolog.eis.util.FileLogHelper;
//import com.prolog.eis.util.PrologApiJsonHelper;
//import com.prolog.eis.util.mapper.Query;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author panteng
// * @description:订单池服务
// * @date 2020/4/17 15:18
// */
//
//@Service
//public class OrderPoolServiceImpl implements OrderPoolService {
//
//    private final Logger logger = LoggerFactory.getLogger(OrderPoolServiceImpl.class);
//
//    @Autowired
//    private OrderHzMapper outboundTaskHzMapper;
//    @Autowired
//    private OrderMxMapper outboundTaskMxMapper;
//
//    private static Object lockObj = new Object();
//    private static final List<OpOrderHz> orderBillList = new ArrayList<>();
//
//
//    @Override
//    public void initOrderPool() {
//        List<OpOrderHz> orders = outboundTaskHzMapper.getOrderToOrderPool(1);
//        if (!CollectionUtils.isEmpty(orders)) {
//            findOrderMx(orders);
//        }
//        orderBillList.addAll(orders);
//    }
//
//    /**
//     * 拉取新订单
//     */
//    @Override
//
//    public void pullNewOrders() {
//        List<OpOrderHz> orders = outboundTaskHzMapper.getOrderToOrderPool(0);
//        if (!CollectionUtils.isEmpty(orders)) {
//            findOrderMx(orders);
//        }
//        synchronized (lockObj) {
//            //将订单标记位已加入
//            this.updateOrder2Pool(orders);
//            orderBillList.addAll(orders);
//        }
//    }
//
//
//    /**
//     * 获取订单明细
//     *
//     * @param order
//     */
//    private void findOrderMx(List<OpOrderHz> order) {
//        //500个 获取明细
//        List<List<OpOrderHz>> partition = Lists.partition(order, 500);
//        Query query = new Query(OpOrderMx.class);
//        List<OpOrderMx> mxs = new ArrayList<>();
//        for (List<OpOrderHz> opOrderHzs : partition) {
//            List<Integer> list = opOrderHzs.stream().map(OpOrderHz::getId).collect(Collectors.toList());
//            query.addIn("orderHzId", list);
//            List<OrderMx> outboundTaskMxes = outboundTaskMxMapper.findByEisQuery(query);
//            List<OpOrderMx> opOrderMxes = PrologApiJsonHelper.copyList(outboundTaskMxes, OpOrderMx.class);
//            mxs.addAll(opOrderMxes);
//        }
//        //组装明细数据
//        mxs2Hzs(order, mxs);
//    }
//
//
//    private void mxs2Hzs(List<OpOrderHz> order, List<OpOrderMx> mxs) {
//        Map<Integer, List<OpOrderMx>> map = mxs.stream().collect(Collectors.groupingBy(OpOrderMx::getOrderHzId));
//        order.stream().forEach(t -> {
//            if (map.containsKey(t.getId())) {
//                List<OpOrderMx> opOrderMxes = map.get(t.getId());
//                t.setMxList(opOrderMxes);
//            }
//        });
//    }
//
//    @Override
//    public void updateOrder2Pool(List<OpOrderHz> orderList) {
//        List<Integer> ids = orderList.stream().map(OpOrderHz::getId).collect(Collectors.toList());
//        List<List<Integer>> partition = Lists.partition(ids, 500);
//        for (List<Integer> integers : partition) {
//            String idsStr = StringUtils.join(integers, ',');
//            outboundTaskHzMapper.updateOrder2Pool(idsStr);
//        }
//    }
//    /**
//     * 获取订单池里的订单
//     * @return
//     */
//    @Override
//    public List<OpOrderHz> getOrderPool() throws Exception {
//        synchronized (lockObj) {
//            if (CollectionUtils.isEmpty(orderBillList)) {
//                return null;
//            }
//            List<OpOrderHz> orders = new ArrayList<>();
//            orders.addAll(orderBillList);
//            return orders;
//        }
//    }
//
//    @Override
//    public void updatePriority(List<OrderHz> hzs) throws Exception {
//        if (CollectionUtils.isNotEmpty(hzs)) {
//            Map<Integer, Integer> map = hzs.stream().collect(Collectors.toMap(OrderHz::getId, OrderHz::getPriority));
//            this.updatePriority(map);
//        }
//    }
//
//    /**
//     * 删除订单池的订单集合
//     *
//     * @param ids
//     */
//
//    @Override
//    public void delOrderList(List<Integer> ids) throws Exception {
//        if (ids == null || ids.size() == 0) {
//            return;
//        }
//        synchronized (lockObj) {
//            Iterator<OpOrderHz> iterator = orderBillList.iterator();
//            while (iterator.hasNext()) {
//                OpOrderHz next = iterator.next();
//                if (ids.contains(next.getId())) {
//                    iterator.remove();
//                }
//            }
//        }
//        FileLogHelper.WriteLog("updateOrderPool", MessageFormat.format("删除后订单池数量:{0},删除订单id:{1}",
//                orderBillList.size()), PrologApiJsonHelper.toJson(ids));
//    }
//
//
//    /**
//     * 修改订单优先级
//     *
//     * @param map
//     */
//
//    @Override
//
//    public void updatePriority(Map<Integer, Integer> map) throws Exception {
//        List<OpOrderHz> objects = new ArrayList<>();
//        synchronized (lockObj) {
//            orderBillList.stream().forEach(t -> {
//                if (map.containsKey(t.getId())) {
//                    Integer integer = map.get(t.getId());
//                    t.setPriority(integer);
//                    objects.add(t);
//                }
//            });
//        }
//
//    }
//
//
//}
