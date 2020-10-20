package com.prolog.eis.order.dao;

import com.prolog.eis.dto.OrderBillDto;
import com.prolog.eis.dto.bz.FinishNotSeedDTO;
import com.prolog.eis.dto.bz.FinishTrayDTO;
import com.prolog.eis.dto.wms.WmsOutboundCallBackDto;
import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderDetailCountsDto;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 10:53
 */
@Repository
public interface OrderBillMapper extends BaseMapper<OrderBill> {

    @Select("SELECT\n" +
            "\tob.id AS orderBillId,\n" +
            "\tCOUNT(*) AS detailCount \n" +
            "FROM\n" +
            "\torder_bill ob\n" +
            "\tLEFT JOIN order_detail od ON ob.id = od.order_bill_id \n" +
            "WHERE\n" +
            "\tFIND_IN_SET( ob.id, '1,2,3' ) \n" +
            "GROUP BY\n" +
            "\tob.id")
    List<OrderDetailCountsDto> findOrderDetailCount(@Param("ids")String ids);

    @Select("select  \n" +
            "            ob.id as orderBillId, \n" +
            "            ob.wms_order_priority as wmsOrderPriority, \n" +
            "            count(od.order_bill_id) as count \n" +
            "            from order_bill ob join order_detail od on ob.id = od.order_bill_id \n" +
            "            where (od.plan_qty-od.out_qty)>0\n" +
            "GROUP BY od.order_bill_id order by\n" +
            "            ob.create_time asc,count desc")
    List<OrderBillDto> initFinishProdOrder();

    @Update("UPDATE order_detail t \n" +
            "SET t.area_no = NULL \n" +
            "WHERE t.id NOT IN (SELECT DISTINCT t.id FROM (SELECT ob.id FROM station s LEFT JOIN order_bill ob ON s.current_station_pick_id =ob.picking_order_id UNION ALL\n" +
            "SELECT a.order_bill_id FROM agv_binding_detail a WHERE a.wms_order_priority = 10 ) t)")
    void updateDetailsArea();

    @Update("UPDATE order_bill t set t.wms_order_priority = 10 where FIND_IN_SET(t.id,#{ids})")
    void updateWmsPriority(@Param("ids")String ids);

    /**
     * 回告wms实体查询
     * @param orderBillId
     * @return
     */
    @Select("SELECT\n" +
            "\tb.task_id AS TASKID,\n" +
            "\tb.order_no AS BILLNO,\n" +
            "\tb.bill_date AS BILLDATE,\n" +
            "\tb.order_type AS BILLTYPE,\n" +
            "\td.goods_id AS ITEMID,\n" +
            "\td.lot_id AS LOTID,\n" +
            "\td.complete_qty AS QTY,\n" +
            "\tb.order_no as BRANCHAREA\n" +
            "FROM\n" +
            "\torder_bill b\n" +
            "\tJOIN order_detail d ON b.id = d.order_bill_id \n" +
            "WHERE\n" +
            "\td.id = #{orderBillId}")
    List<WmsOutboundCallBackDto> findWmsOrderBill(@Param("orderBillId")int orderBillId);

    /**
     * 获取成品库未完成播种的明细及汇总数量
     * @return
     */
    @Select("SELECT\n" +
            "\tCOUNT( DISTINCT ob.id ) AS notOutOrderCount,\n" +
            "\tCOUNT( od.id ) AS notOutOrderDetailCount \n" +
            "FROM\n" +
            "\torder_bill ob\n" +
            "\tJOIN order_detail od ON od.order_bill_id = ob.id \n" +
            "WHERE\n" +
            "\tod.plan_qty != od.has_pick_qty and ob.order_type = 'C'")
    FinishNotSeedDTO getFinishSeedCount();


    /**
     * 校验容器是否有播种任务
     * @param stationId
     * @return
     */
    @Select("SELECT\n" +
            "\tcount( * ) \n" +
            "FROM\n" +
            "\tcontainer_binding_detail cb\n" +
            "\tJOIN order_bill ob ON ob.id = cb.order_bill_id\n" +
            "\tJOIN picking_order po ON po.id = ob.picking_order_id \n" +
            "WHERE\n" +
            "\tpo.station_id = #{stationId} and cb.container_no =  #{containerNo}")
    int checkContainer(@Param("stationId") int stationId,@Param("containerNo") String containerNo);

    /**
     * 查询成品库播种详情
     * @param containerNo
     * @return
     */
    @Select("SELECT\n" +
            "\tg.goods_no as goodsNo,\n" +
            "\tg.goods_name as goodsName,\n" +
            "\tob.order_no as orderNo,\n" +
            "\tcb.seed_num as seedCount\n" +
            "\t\n" +
            "FROM\n" +
            "\tcontainer_binding_detail cb join  order_bill ob on ob.id = cb.order_bill_id\n" +
            "\tjoin order_detail od on od.id = cb.order_detail_id\n" +
            "\tJOIN goods g ON g.id = od.goods_id\n" +
            "WHERE\n" +
            "\tcs.container_no = #{containerNo}\n" +
            "\tand ob.picking_order_id = #{pickingOrderId}")
    List<FinishTrayDTO> getFinishSeedInfo(@Param("containerNo") String containerNo,@Param("pickingOrderId") int pickingOrderId);
}
