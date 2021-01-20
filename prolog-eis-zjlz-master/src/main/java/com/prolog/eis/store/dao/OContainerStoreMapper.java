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

    @Select("SELECT \n" +
            "c.qty AS qty,\n" +
            "c.goods_id as goodsId\n" +
            "FROM\n" +
            "container_store c LEFT JOIN container_path_task cpt\n" +
            "ON c.container_no = cpt.container_no WHERE FIND_IN_SET( cpt.target_area, #{areaNos})  and task_state=0")
    List<StoreGoodsCount> findStore(@Param("areaNos") String areaNos);

    @Select("SELECT DISTINCT\n" +
            "\tabd.goods_id AS goodsId,\n" +
            "\tcs.qty - ( SELECT SUM( a.binding_num ) FROM line_binding_detail a WHERE a.container_no = abd.container_no ) AS qty\n" +
            "FROM\n" +
            "\tline_binding_detail abd\n" +
            "\tLEFT JOIN container_store cs ON abd.container_no = cs.container_no\n" +
            "\tLEFT JOIN goods g ON cs.goods_id = g.id")
    List<StoreGoodsCount> findLineStore();

    @Select("SELECT DISTINCT\n" +
            "\tabd.goods_id AS goodsId,\n" +
            "\tcs.qty - ( SELECT SUM( a.binding_num ) FROM line_binding_detail a WHERE a.container_no = abd.container_no ) AS qty\n" +
            "FROM\n" +
            "\tagv_binding_detail abd\n" +
            "\tLEFT JOIN container_store cs ON abd.container_no = cs.container_no\n" +
            "\tLEFT JOIN goods g ON cs.goods_id = g.id")
    List<StoreGoodsCount> findAgvBindStore();

    @Select("SELECT\n" +
            "\tcs.goods_id AS goodsId,\n" +
            "\tcs.qty AS qty \n" +
            "FROM\n" +
            "\tcontainer_path_task c\n" +
            "\t JOIN container_store cs ON c.container_no = cs.container_no \n" +
            "WHERE\n" +
            "\tc.target_area = 'RCS01' \n" +
            "\tAND c.task_state = 0 \n" +
            "\tAND c.container_no NOT IN (\n" +
            "\tSELECT\n" +
            "\t\ta.container_no \n" +
            "\tFROM\n" +
            "\tagv_binding_detail a \n" +
            "\t)")
    List<StoreGoodsCount> findAgvStore();
}
