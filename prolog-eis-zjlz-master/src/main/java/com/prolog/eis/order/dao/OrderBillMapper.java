package com.prolog.eis.order.dao;

import com.prolog.eis.model.order.OrderBill;
import com.prolog.eis.model.order.OrderDetailCountsDto;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 10:53
 */
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
}
