package com.prolog.eis.engin.dao;

import com.prolog.eis.dto.lzenginee.RoadWayGoodsCountDto;
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
    @Select("SELECT \n" +
            "sl.x as roadWay,\n" +
            "cpt.container_no AS containerNo,\n" +
            "cs.goods_id AS goodsId,g.last_container_rate AS rate,sl.dept_num as deptNum,sl.store_no as storeLocation,\n" +
            "cs.qty \n" +
            "FROM\n" +
            "\tcontainer_store cs\n" +
            "\tLEFT JOIN container_path_task cpt ON cs.container_no = cpt.container_no LEFT JOIN goods g on cs.goods_id=g.id\n" +
            "\tLEFT JOIN sx_store_location sl on sl.id=cpt.source_location\n" +
            "\tWHERE cpt.task_type=0 and cs.goods_id=#{goodsId} order by cs.qty DESC")
    List<RoadWayGoodsCountDto> findLayerGoodsCount(@Param("goodsId")int goodsId);
}
