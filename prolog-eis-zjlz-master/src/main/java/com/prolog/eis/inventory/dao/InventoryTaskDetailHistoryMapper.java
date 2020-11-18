package com.prolog.eis.inventory.dao;

import com.prolog.eis.dto.page.InventoryHistoryDto;
import com.prolog.eis.dto.page.InventoryHistoryQueryDto;
import com.prolog.eis.model.inventory.InventoryTaskDetailHistory;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/16 9:53
 * 盘点历史mapper
 */
public interface InventoryTaskDetailHistoryMapper extends BaseMapper<InventoryTaskDetailHistory> {

    /**
     * 盘点历史
     * @param inventoryQueryDto
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "\tdh.inventory_task_id AS inventoryId,\n" +
            "\tdh.container_no AS containerNo,\n" +
            "\tdh.goods_name AS goodsName,\n" +
            "\tdh.goods_no AS goodsNo,\n" +
            "\tdh.original_count AS originalCount,\n" +
            "\tdh.modify_count AS modifyCount,\n" +
            "\tdh.station_id AS stationId,\n" +
            "\tdh.end_time AS endTime \n" +
            "FROM\n" +
            "\tinventory_task_detail_history dh\n" +
            "\t<where>\n" +
            "\t<if test = 'inventoryQueryDto.containerNo != null and inventoryQueryDto.containerNo != \"\"' >\n" +
            "\t\t\tdh.container_no like concat('%',#{inventoryQueryDto.containerNo},'%')\n" +
            "\t</if>\n" +
            "\t\t<if test = 'inventoryQueryDto.goodsNo != null and inventoryQueryDto.goodsNo != \"\"' >\n" +
            "\t\t\tdh.goods_no like concat('%',#{inventoryQueryDto.goodsNo},'%')\n" +
            "\t</if>\n" +
            "\t\t<if test = 'inventoryQueryDto.goodsName != null and inventoryQueryDto.goodsName != \"\"' >\n" +
            "\t\t\tdh.goods_name like concat('%',#{inventoryQueryDto.goodsName},'%')\n" +
            "\t</if>\n" +
            "\t<if test = 'inventoryQueryDto.stationId != null and inventoryQueryDto.stationId != \"\"' >\n" +
            "\t\t\tdh.station_id = #{inventoryQueryDto.stationId}\n" +
            "\t</if>\n" +
            "\t\t<if test = 'inventoryQueryDto.startTime != null' >\n" +
            "\t\t\tdh.end_time >= #{inventoryQueryDto.startTime}\n" +
            "\t</if>\n" +
            "\t\t<if test = 'inventoryQueryDto.endTime != null ' >\n" +
            "\t\t\tdh.end_time &lt;= #{inventoryQueryDto.endTime}\n" +
            "\t</if>\n" +
            "\t\t<if test = 'inventoryQueryDto.different == \"1\"' >\n" +
            "\t\t\tand dh.original_count = dh.modify_count\n" +
            "\t</if>\n" +
            "\t\t\t<if test = 'inventoryQueryDto.different == \"2\"' >\n" +
            "\t\t\tand dh.original_count != dh.modify_count\n" +
            "\t</if>\n" +
            "\t</where>" +
            "</script>")
    List<InventoryHistoryDto> getInventoryHistory(@Param("inventoryQueryDto") InventoryHistoryQueryDto inventoryQueryDto);

}
