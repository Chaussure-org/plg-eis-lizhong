package com.prolog.eis.order.dao;

import com.prolog.eis.dto.lzenginee.GoAgvDetailDto;
import com.prolog.eis.dto.lzenginee.StoreGoodsCount;
import com.prolog.eis.model.order.OrderDetail;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

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
            "\tAND od.area_no='a'\n" +
            "ORDER BY\n" +
            "\tod.create_time DESC")
    List<GoAgvDetailDto> findAgvDetail();
}
