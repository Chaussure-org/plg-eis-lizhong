package com.prolog.eis.engin.dao;

import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description 成品库出库数据服务
 * @CreateTime 2020-10-14 16:34
 */
@Repository
public interface FinishedProdOutEnginMapper extends BaseMapper {

    /**
     * 查询所有成品库库存
     * @return
     */
    @Select("select kc.goods_id as goodsId,sum(kc.qty) as num from (select cs.goods_id,cs.qty from container_store " +
            "cs\n" +
            "left join container_path_task cptd on cs.container_no = cptd.container_no\n" +
            "join sx_store_location sl on cptd.source_area = sl.area_no and cptd.source_location = sl.id\n" +
            "where sl.area_no = \"D050\") kc group by kc.goods_id")
    List<Map<String, Integer>> findAllGoodsCount();


    /**
     * 商品使用库存
     * @return
     */
    @Select("select us.goods_id as goodsId, sum(us.qty) as num from (select od.goods_id, od.plan_qty - od" +
            ".complete_qty as qty from \n" +
            "container_binding_detail cbd join order_detail od on cbd.order_detail_id = od.id) us\n" +
            "group by us.goods_id")
    List<Map<String, Integer>> findUsedGoodsCount();
}
