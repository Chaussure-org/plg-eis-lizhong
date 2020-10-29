package com.prolog.eis.engin.dao;

import com.prolog.eis.dto.lzenginee.LayerGoodsCountDto;
import com.prolog.eis.dto.lzenginee.boxoutdto.LayerTaskDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName:BoxOutMapper
 * Package:com.prolog.eis.engin.dao
 * Description:
 *
 * @date:2020/9/30 14:05
 * @author:SunPP
 */
public interface BoxOutMapper {
    @Select("SELECT\n" +
            "\tsl.layer AS layer,\n" +
            "\tcpt.container_no AS containerNo,\n" +
            "\tcs.goods_id AS goodsId,\n" +
            "\tg.last_container_rate AS rate,\n" +
            "\tsl.dept_num AS deptNum,\n" +
            "\tsl.store_no AS storeLocation,\n" +
            "\tcs.qty \n" +
            "FROM\n" +
            "\tcontainer_store cs\n" +
            "\tLEFT JOIN container_path_task cpt ON cs.container_no = cpt.container_no\n" +
            "\tLEFT JOIN goods g ON cs.goods_id = g.id\n" +
            "\tLEFT JOIN sx_store_location sl ON sl.id = cpt.source_location \n" +
            "WHERE\n" +
            "\tcpt.task_state=0 and cpt.target_area='SAS01' \n" +
            "\tAND cs.goods_id = #{goodsId}\n" +
            "ORDER BY\n" +
            "\tcs.qty DESC")
    List<LayerGoodsCountDto> findLayerGoodsCount(@Param("goodsId")int goodsId);

    @Select("SELECT\n" +
            "\tsl.x AS roadWay,\n" +
            "\tSUM( CASE c.task_status WHEN 20 THEN 1 ELSE 0 END ) AS outCount,\n" +
            "\tSUM( CASE c.task_status WHEN 10 THEN 1 ELSE 0 END ) AS inCount \n" +
            "FROM\n" +
            "\tcontainer_path_task cpt\n" +
            "\tLEFT JOIN container_store c ON c.container_no = cpt.container_no\n" +
            "\tLEFT JOIN sx_store_location sl ON cpt.source_location = sl.store_no \n" +
            "\tOR cpt.target_location = sl.store_no WHERE cpt.target_area ='SAS01' or cpt.source_area ='SAS01'\n" +
            "GROUP BY\n" +
            "\tsl.x")
        List<LayerTaskDto> findLayerTaskCount();


    @Select("SELECT DISTINCT\n" +
            "\tabd.goods_id AS goodsId,\n" +
            "\tabd.container_no AS containerNo,\n" +
            "\tcs.qty - ( SELECT SUM( a.binding_num ) FROM line_binding_detail a WHERE a.container_no = abd.container_no ) AS qty,\n" +
            "\tg.last_container_rate AS rate \n" +
            "FROM\n" +
            "\tline_binding_detail abd\n" +
            "\tLEFT JOIN container_store cs ON abd.container_no = cs.container_no\n" +
            "\tLEFT JOIN goods g ON cs.goods_id = g.id \n" +
            "WHERE\n" +
            "\tabd.goods_id = #{goodsId} \n" +
            "ORDER BY\n" +
            "\tqty")
    List<LayerGoodsCountDto> findLineGoodsCount(@Param("goodsId")int goodsId);

}
