package com.prolog.eis.order.dao;

import com.prolog.eis.dto.bz.BCPGoodsInfoDTO;
import com.prolog.eis.dto.lzenginee.OutDetailDto;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 11:22
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    @Select("SELECT\n" +
            "ob.id AS orderBillId,\n" +
            "ob.order_priority AS orderPriority,\n" +
            "od.id AS detailId,\n" +
            "\tod.goods_id AS goodsId,\n" +
            "\tod.plan_qty AS qty \n" +
            "FROM\n" +
            "order_bill ob JOIN\n" +
            "\torder_detail od on ob.id=od.order_bill_id\n" +
            "WHERE\n" +
            "\tod.id NOT IN ( SELECT abd.order_mx_id FROM agv_binding_detail abd ) \n" +
            "\tAND od.area_no=#{areaNo}\n" +
            "ORDER BY\n" +
            "\tod.create_time DESC")
    List<OutDetailDto> findAgvDetail(@Param("areaNo")String areaNo);

    @Select("SELECT\n" +
            "ob.id AS orderBillId,\n" +
            "ob.order_priority AS orderPriority,\n" +
            "od.id AS detailId,\n" +
            "\tod.goods_id AS goodsId,\n" +
            "\tod.plan_qty AS qty \n" +
            "FROM\n" +
            "order_bill ob JOIN\n" +
            "\torder_detail od on ob.id=od.order_bill_id\n" +
            "WHERE\n" +
            "\tod.id NOT IN ( SELECT abd.order_mx_id FROM line_binding_detail abd ) \n" +
            "\tAND od.area_no=#{areaNo}\n" +
            "ORDER BY\n" +
            "\tod.create_time DESC")
    List<OutDetailDto> findLineDetail(@Param("areaNo")String areaNo);

    /**
     * 查当前播种商品信息
     * @param orderDetailId
     * @return
     * @throws Exception
     */
    @Select("SELECT\n" +
            "\tb.order_no AS orderNo,\n" +
            "\td.graph_no AS graphNo,\n" +
            "\tg.goods_name AS goodsName,\n" +
            "\tg.goods_no AS goodsNo \n" +
            "FROM\n" +
            "\torder_detail d\n" +
            "\tJOIN order_bill b ON b.id = d.order_bill_id\n" +
            "\tJOIN goods g ON g.id = d.goods_id \n" +
            "WHERE\n" +
            "\td.id = #{orderDetailId}")
    List<BCPGoodsInfoDTO> findPickingGoods(@Param("orderDetailId") int orderDetailId) throws Exception;

    /**
     * 当前订单播种商品条目
     * @param orderBillId
     * @return
     * @throws Exception
     */
    @Select("SELECT\n" +
            "\tcount( * ) \n" +
            "FROM\n" +
            "\torder_detail d \n" +
            "WHERE\n" +
            "\t d.plan_qty > d.complete_qty   \n" +
            "\tAND d.order_bill_id = #{orderBillId}")
    int notPickingCount(@Param("orderBillId") int orderBillId) throws Exception;

    /**
     * 检查订单播种情况
     * @param orderBillId
     * @return
     * @throws Exception
     */
    @Select("select COUNT(*) from order_detail where plan_qty <= complete_qty and order_bill_id = #{orderBillId}")
    int checkOrderFinish(@Param("orderBillId") int orderBillId) throws Exception;
}
