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
     * @return 查找 立库 和 agv区 没有订单明细 的库存
     */
    @Select("SELECT\n" +
            "\tcs.goods_id AS goodsId,\n" +
            "\tcs.qty,\n" +
            "\tcpt.source_area AS sourceArea,\n" +
            "\tcs.task_type AS taskType \n" +
            "FROM\n" +
            "\tcontainer_store cs\n" +
            "\tLEFT JOIN container_path_task cpt ON cs.container_no = cpt.container_no\n" +
            "\tLEFT JOIN agv_binding_detail abd ON cs.container_no = abd.container_no \n" +
            "WHERE\n" +
            "\tFIND_IN_SET( cpt.source_area, 'a,b,c' ) \n" +
            "\tAND cpt.target_area = NULL \n" +
            "\tAND abd.order_mx_id = NULL")
    List<StoreGoodsCount> findStoreGoodsCount();
}
