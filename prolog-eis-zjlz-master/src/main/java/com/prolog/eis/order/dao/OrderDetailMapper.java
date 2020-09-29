package com.prolog.eis.order.dao;

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
    @Select("SELECT \n" +
            "od.goods_id AS goodsId,\n" +
            "od.plan_qty AS qty\n" +
            "from order_detail od WHERE od.id NOT IN (SELECT abd.order_mx_id FROM agv_binding_detail abd) ORDER BY od.create_time DESC")
    List<StoreGoodsCount> findAgvDetail();
}
