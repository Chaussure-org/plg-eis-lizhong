package com.prolog.eis.order.service.impl;

import com.prolog.eis.dto.bz.BCPGoodsInfoDTO;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.eis.model.order.OrderDetailHistory;
import com.prolog.eis.order.dao.OrderDetailMapper;
import com.prolog.eis.order.service.IOrderDetailHistoryService;
import com.prolog.eis.order.service.IOrderDetailService;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 11:22
 */
@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private IOrderDetailHistoryService orderDetailHistoryService;

    @Override
    public void saveOrderDetailList(List<OrderDetail> orderDetails) {
        if (orderDetails.size() > 0) {
            orderDetailMapper.saveBatch(orderDetails);
        }
    }

    @Override
    public List<BCPGoodsInfoDTO> findPickingGoods(int orderDetailId) throws Exception {
        return orderDetailMapper.findPickingGoods(orderDetailId);
    }

    @Override
    public int notPickingCount(int orderBillId) throws Exception {
        return orderDetailMapper.notPickingCount(orderBillId);
    }

    @Override
    public void updateOrderDetail(OrderDetail orderDetail) throws Exception {
        if (orderDetail != null) {
            orderDetailMapper.update(orderDetail);
        }
    }

    @Override
    public OrderDetail findOrderDetailById(int id) throws Exception {
        return orderDetailMapper.findById(id, OrderDetail.class);
    }

    @Override
    public boolean orderPickingFinish(int orderBillId) throws Exception {
        int count = orderDetailMapper.checkOrderFinish(orderBillId);
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void orderDetailToHistory(int orderBillId) {
        List<OrderDetail> orderDetails = orderDetailMapper.findByMap(MapUtils.put("orderBillId", orderBillId).getMap(), OrderDetail.class);
        if (orderDetails.size() > 0) {
            List<OrderDetailHistory> orderDetailHistories = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetails) {
                OrderDetailHistory orderDetailHistory = new OrderDetailHistory();
                BeanUtils.copyProperties(orderDetail, orderDetailHistory);
                orderDetailHistory.setUpdateTime(new Date());
                orderDetailHistories.add(orderDetailHistory);
            }
            orderDetailHistoryService.saveBatch(orderDetailHistories);
            //删除明细
            orderDetailMapper.deleteByMap(MapUtils.put("orderBillId", orderBillId).getMap(), OrderDetail.class);
        }

    }

    @Override
    public void deleteOrderDetailByMap(Map map) {
        if (map != null) {
            orderDetailMapper.deleteByMap(map, OrderDetail.class);
        }
    }

    @Override
    public List<OrderDetail> findOrderDetailByMap(Map map) {
        return orderDetailMapper.findByMap(map,OrderDetail.class);
    }

    @Override
    public boolean findOrderTrayGoodsLabel(int orderBillId, String orderTrayNo) {
        int size = orderDetailMapper.goodsLabelInfo(orderBillId, orderTrayNo).stream().filter(x -> x.getPastLabelFlg() == 1)
                .collect(Collectors.toList()).size();
        if (size > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean checkOrderDetailFinish(int orderDetailId) {
        OrderDetail orderDetail = orderDetailMapper.findById(orderDetailId, OrderDetail.class);
        if (orderDetail == null){
            throw new RuntimeException("没有找到对应订单明细");
        }
        if (orderDetail.getPlanQty().equals(orderDetail.getHasPickQty())){
            return true;
        }
        return false;
    }
}
