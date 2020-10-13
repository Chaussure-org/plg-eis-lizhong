package com.prolog.eis.order.service;

import com.prolog.eis.dto.bz.BCPGoodsInfoDTO;
import com.prolog.eis.model.order.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 11:21
 */
public interface IOrderDetailService {

    void saveOrderDetailList(List<OrderDetail> orderDetails);


    List<BCPGoodsInfoDTO> findPickingGoods(int orderDetailId) throws Exception;

    /**
     * 没有拣选的商品条目
     * @param orderBillId   订单id
     * @return
     * @throws Exception
     */
    int notPickingCount(int orderBillId) throws Exception;

    /**
     * 根据id修改orderDetail
     */
    void updateOrderDetail(OrderDetail orderDetail) throws Exception;
    /**
     * 根据id查询
     */
    OrderDetail findOrderDetailById(int id) throws Exception;
    /**
     * 检查订单是否播种完成
     */
    boolean orderPickingFinish(int orderBillId) throws Exception;

    /**
     * 明细转历史
     * @param orderBillTd
     */
    void orderDetailToHistory(int orderBillTd);

    /**
     * 根据map删除
     * @param map
     */
    void deleteOrderDetailByMap(Map map);

    /**
     * 根据map查询
     * @param map
     * @return
     */
    List<OrderDetail> findOrderDetailByMap(Map map);

    /**
     * 
     */
}
