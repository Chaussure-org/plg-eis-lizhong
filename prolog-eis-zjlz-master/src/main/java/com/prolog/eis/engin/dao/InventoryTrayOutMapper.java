package com.prolog.eis.engin.dao;

import com.prolog.eis.dto.inventory.RickerTaskDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/29 9:56
 */
public interface InventoryTrayOutMapper {

    /**
     * 查立库巷道入库任务数
     */
    @Select("SELECT\n" +
            "\tcpt.target_area AS alleyWay,\n" +
            "\tcount( * ) AS taskCount \n" +
            "FROM\n" +
            "\tcontainer_store cs\n" +
            "\tJOIN container_path_task cpt ON cpt.container_no = cs.container_no \n" +
            "WHERE\n" +
            "\tcs.task_type <> 0 \n" +
            "\tAND cpt.target_area IN ( 'MCS01', 'MCS02', 'MCS03', 'MCS04' ) \n" +
            "GROUP BY\n" +
            "\tcpt.target_area")
    List<RickerTaskDto> getInTaskCount();

    /**
     * 查立库巷道出库任务数
     */
    @Select("SELECT\n" +
            "\tcpt.source_area AS alleyWay,\n" +
            "\tcount( * ) AS taskCount \n" +
            "FROM\n" +
            "\tcontainer_store cs\n" +
            "\tJOIN container_path_task cpt ON cpt.container_no = cs.container_no \n" +
            "WHERE\n" +
            "\tcs.task_type <> 0 \n" +
            "\tAND cpt.source_area IN ( 'MCS01', 'MCS02', 'MCS03', 'MCS04' ) \n" +
            "GROUP BY\n" +
            "\tcpt.source_area")
    List<RickerTaskDto> getOutTaskCount();


    /**
     * 获取agv暂存区空货位
     */
    @Select("SELECT\n" +
            "\tCOUNT( aa.id ) - count( cpt.id ) \n" +
            "FROM\n" +
            "\tagv_storagelocation aa\n" +
            "\tLEFT JOIN container_path_task cpt ON cpt.target_area = aa.area_no \n" +
            "WHERE\n" +
            "\taa.storage_lock = 0 \n" +
            "\tAND aa.area_no = 'RCS01'")
    int getEmpty();

}
