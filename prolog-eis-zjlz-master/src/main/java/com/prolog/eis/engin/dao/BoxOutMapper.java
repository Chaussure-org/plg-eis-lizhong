package com.prolog.eis.engin.dao;

import com.prolog.eis.dto.lzenginee.RoadWayGoodsCountDto;
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
            "\tcpt.task_type = null\n" +
            "\tAND cs.goods_id = #{goodsId}\n" +
            "\tAND source_area='b'\n" +
            "ORDER BY\n" +
            "\tcs.qty DESC")
    List<RoadWayGoodsCountDto> findLayerGoodsCount(@Param("goodsId")int goodsId);

    @Select("SELECT\n" +
            "\tsl.layer AS layer,\n" +
            "\tSUM( CASE cpt.task_type WHEN 20 OR 30 THEN 1 ELSE 0 END ) AS outCount,\n" +
            "\tSUM( CASE cpt.task_type WHEN 0 OR 10 THEN 1 ELSE 0 END ) AS inCount \n" +
            "FROM\n" +
            "\tcontainer_path_task cpt\n" +
            "\tLEFT JOIN sx_store_location sl ON cpt.source_location = sl.id \n" +
            "GROUP BY\n" +
            "\tsl.layer")
        List<LayerTaskDto> findLayerTaskCount();

}
