package com.prolog.eis.order.dao;

import com.prolog.eis.dto.OrderBillDto;
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
            "            where (od.plan_qty-od.out_qty)>0 and ob.order_type=\"C\"\n" +
            "GROUP BY od.order_bill_id order by\n" +
            "            ob.create_time asc,count desc")
    List<OrderBillDto> initFinishProdOrder();
}
