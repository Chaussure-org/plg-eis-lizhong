package com.prolog.eis.engin.dao;

import com.prolog.eis.dto.inventory.BoxLayerTaskDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/30 11:14
 * 箱库盘点出库mapper
 */
public interface InventoryBoxOutMapper {


    /**
     * 获取层任务数
     * @return
     *
     */
    @Select("SELECT\n" +
            "\tss.layer AS layer,\n" +
            "\tsum( CASE WHEN cpt.task_type > 0 THEN 1 ELSE 0 END ) \n" +
            "FROM\n" +
            "\tsx_store_location ss\n" +
            "\tLEFT JOIN container_path_task cpt ON cpt.source_location = ss.store_no \n" +
            "\tOR cpt.target_location = ss.store_no \n" +
            "WHERE\n" +
            "\tss.area_no = 'SAS01' \n" +
            "GROUP BY\n" +
            "\tss.layer;")
    List<BoxLayerTaskDto> getLayerTaskInfo();
}
