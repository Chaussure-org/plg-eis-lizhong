package com.prolog.eis.store.dao;

import com.prolog.eis.dto.lzenginee.StoreGoodsCount;
import com.prolog.eis.model.ContainerStore;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author SunPP
 */
public interface OContainerStoreMapper extends BaseMapper<ContainerStore> {
    /**
     * @return 所有的库存减去占用的库存
     */
    @Select("SELECT\n" +
            "cs.goods_id AS goodsId,\n" +
            "SUM(cs.qty)-(SELECT ifnull(SUM(o.plan_qty-o.has_pick_qty),0) FROM order_detail o WHERE o.goods_id=cs.goods_id AND o.area_no != '')  AS qty\n" +
            "FROM\n" +
            "container_store cs\n" +
            "LEFT JOIN container_path_task cpt ON cs.container_no = cpt.container_no\n" +
            "LEFT JOIN agv_binding_detail abd ON cs.container_no=abd.container_no\n" +
            "WHERE\n" +
            "FIND_IN_SET( cpt.target_area, #{areaNos} ) \n" +
            "AND cpt.task_state=0 GROUP BY cs.goods_id")
    List<StoreGoodsCount> findStoreGoodsCount(@Param("areaNos")String areaNos);
}
