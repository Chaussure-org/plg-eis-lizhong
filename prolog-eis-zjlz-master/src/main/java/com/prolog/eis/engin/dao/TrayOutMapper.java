package com.prolog.eis.engin.dao;

import com.prolog.eis.dto.lzenginee.LayerContainerTaskDto;
import com.prolog.eis.dto.lzenginee.LayerGoodsCountDto;
import com.prolog.eis.dto.lzenginee.RoadWayContainerTaskDto;
import com.prolog.eis.dto.lzenginee.RoadWayGoodsCountDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName:TrayOutMapper
 * Package:com.prolog.eis.engin.dao
 * Description:半成品托盘出库 dao层
 * @date: 2020/9/28 10:25
 * @author: SunPP
 */
public interface TrayOutMapper {
    @Select("SELECT\n" +
            "\tsl.x AS roadWay,\n" +
            "\tSUM( CASE cpt.task_type WHEN 20 or 30 THEN 1 ELSE 0 END ) AS outCount,\n" +
            "\tSUM( CASE cpt.task_type WHEN 0 OR  10 THEN 1 ELSE 0 END ) AS inCount \n" +
            "FROM\n" +
            "\tcontainer_path_task cpt\n" +
            "\tLEFT JOIN sx_store_location sl ON cpt.source_location = sl.id \n" +
            "GROUP BY\n" +
            "\tsl.x")
    List<RoadWayContainerTaskDto> findRoadWayContainerTask();

    @Select("SELECT \n" +
            "sl.x as roadWay,\n" +
            "cpt.container_no AS containerNo,\n" +
            "cs.goods_id AS goodsId,\n" +
            "cs.qty \n" +
            "FROM\n" +
            "\tcontainer_store cs\n" +
            "\tLEFT JOIN container_path_task cpt ON cs.container_no = cpt.container_no\n" +
            "\tLEFT JOIN sx_store_location sl on sl.id=cpt.source_location\n" +
            "\tWHERE cpt.task_type=0 and cs.goods_id=#{goodsId}")
    List<RoadWayGoodsCountDto> findRoadWayGoodsCount(@Param("goodsId")int goodsId);
}
