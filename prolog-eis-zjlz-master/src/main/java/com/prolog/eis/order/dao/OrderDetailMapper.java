package com.prolog.eis.order.dao;

import com.prolog.eis.dto.bz.BCPGoodsInfoDTO;
import com.prolog.eis.dto.bz.OrderDetailLabelDTO;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
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
            "ob.wms_order_priority AS wmsOrderPriority,\n" +
            "od.id AS detailId,\n" +
            "\tod.goods_id AS goodsId,\n" +
            "\tIF(od.tray_plan_qty=0,od.plan_qty,od.tray_plan_qty) AS planQty \n" +
            "FROM\n" +
            "order_bill ob JOIN\n" +
            "\torder_detail od on ob.id=od.order_bill_id\n" +
            "WHERE\n" +
            "\t od.plan_qty != od.has_pick_qty and od.id NOT IN ( SELECT abd.order_mx_id FROM agv_binding_detail abd ) \n" +
            "\tAND od.area_no=#{areaNo} and ob.order_priority in (1,3) \n" +
            "ORDER BY\n" +
            "\tod.create_time DESC")
    List<OutDetailDto> findAgvDetail(@Param("areaNo") String areaNo);


    @Select("SELECT\n" +
            "ob.id AS orderBillId,\n" +
            "ob.order_priority AS orderPriority,\n" +
            "ob.wms_order_priority AS wmsOrderPriority,\n" +
            "od.id AS detailId,\n" +
            "\tod.goods_id AS goodsId,\n" +
            "\tIF(od.tray_plan_qty=0,od.plan_qty,od.tray_plan_qty) AS planQty \n" +
            "FROM\n" +
            "order_bill ob JOIN\n" +
            "\torder_detail od on ob.id=od.order_bill_id\n" +
            "WHERE\n" +
            "\tod.id NOT IN ( SELECT abd.order_mx_id FROM line_binding_detail abd ) \n" +
            "\tAND od.area_no=#{areaNo} \n" +
            "ORDER BY\n" +
            "\tod.create_time DESC")
    List<OutDetailDto> findLineDetail(@Param("areaNo") String areaNo);

    @Select("SELECT\n" +
            "\tod.order_bill_id AS orderBillId,\n" +
            "\tod.id AS detailId,\n" +
            "\tod.goods_id AS goodsId,\n" +
            "\tob.wms_order_priority AS wmsOrderPriority,\n" +
            "\tod.plan_qty AS planQty\n" +
            "FROM\n" +
            "\torder_bill ob\n" +
            "\tLEFT JOIN order_detail od ON ob.id = od.order_bill_id " +
            "where od.area_no ='' or od.area_no is null and ob.order_type=1 ")
    List<OutDetailDto> findOutDetails();

    /**
     * 查当前播种商品信息
     *
     * @param orderDetailId
     * @return
     * @throws Exception
     */
    @Select("SELECT\n" +
            "\tb.order_no AS orderNo,\n" +
            "\tg.owner_drawn_no AS graphNo,\n" +
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
     *
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
     *
     * @param orderBillId
     * @return
     * @throws Exception
     */
    @Select("select COUNT(*) from order_detail where plan_qty != has_pick_qty and order_bill_id = #{orderBillId}")
    int checkOrderFinish(@Param("orderBillId") int orderBillId) throws Exception;

    /**
     * 商品是否贴标
     *
     * @param orderBillId
     * @param orderTrayNo
     * @return
     */
    @Select("SELECT\n" +
            "\tod.goods_id AS goodsId,\n" +
            "\tg.past_label_flg AS pastLabelFlg \n" +
            "FROM\n" +
            "\tseed_info s\n" +
            "\tJOIN order_detail od ON od.id = s.order_detail_id\n" +
            "\tJOIN goods g ON g.id = od.goods_id \n" +
            "WHERE\n" +
            "\ts.order_bill_id = #{orderBillId} \n" +
            "\tAND s.order_tray_no = #{orderTrayNo}")
    List<OrderDetailLabelDTO> goodsLabelInfo(@Param("orderBillId") int orderBillId, @Param("orderTrayNo") String orderTrayNo);

    @Select("SELECT \n" +
            "ob.id AS orderBillId,\n" +
            "od.id AS detailId,\n" +
            "od.goods_id AS goodsId,\n" +
            "od.plan_qty AS planQty,ob.branch_type as branchType \n" +
            "FROM order_bill ob LEFT JOIN order_detail od on ob.id=od.order_bill_id where \n")
    List<OutDetailDto> findTransfer();
}
