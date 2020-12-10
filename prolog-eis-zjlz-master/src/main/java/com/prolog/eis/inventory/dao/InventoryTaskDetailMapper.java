package com.prolog.eis.inventory.dao;

import com.prolog.eis.dto.inventory.InventoryGoodsDto;
import com.prolog.eis.dto.inventory.InventoryOutDto;
import com.prolog.eis.dto.inventory.InventoryShowDto;
import com.prolog.eis.dto.inventory.InventoryWmsDto;
import com.prolog.eis.dto.page.InventoryDetailInfoDto;
import com.prolog.eis.model.inventory.InventoryTaskDetail;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description 盘点详情数据层
 * @CreateTime 2020-10-14 14:51
 */
public interface InventoryTaskDetailMapper extends BaseMapper<InventoryTaskDetail> {

    /**
     * 查询可盘点容器
     *
     * @param map
     * @return
     */
    @Select("<script> SELECT\n" +
            "\tcs.container_no AS containerNo,\n" +
            "\tg.goods_no AS goodsNo,\n" +
            "\tg.goods_name AS goodsName,\n" +
            "\tcs.qty AS originalCount,\n" +
            "\tg.id as goodsId," +
            "g.owner_drawn_no as \n" +
            "FROM\n" +
            "\tcontainer_store cs\n" +
            "\tJOIN container_path_task cpt ON cpt.container_no = cs.container_no\n" +
            "\tJOIN sx_store_location s ON s.store_no = cpt.source_location\n" +
            "\tJOIN goods g ON g.id = cs.goods_id \n" +
            "WHERE\n" +
            "\tcpt.task_state = 0 \n" +
            "<if test = 'map.goodsId != null '>" +
            "   and g.id = #{map.goodsId}" +
            "</if>" +
            "<if test = 'map.goodsType != null and map.goodsType != \"\"'>" +
            "   and g.goods_type = #{map.goodsType}" +
            "</if>" +
            "<if test = 'map.containerNo != null and map.containerNo != \"\"'>" +
            "   and s.container_no = #{map.containerNo}" +
            "</if>" +
            "<if test = 'map.branchType == \"A\"'>" +
            " and cpt.target_area = 'SAS01'" +
            "</if>" +
            "<if test = 'map.branchType == \"B\"'>" +
            " and cpt.target_area IN ( 'MCS01','MCS02','MCS03','MCS04','MCS05')" +
            "</if>" +
            "<if test = 'map.lotId != null and map.lotId != \"\"'>" +
            " and cs.lotId = #{map.lotId}" +
            "</if>"+
            "</script>")
    List<InventoryGoodsDto> getInventoryGoods(@Param("map") Map<String, Object> map);


    /**
     * 根据区域获取盘点库存
     *
     * @param area
     * @return
     */
    @Select("SELECT\n" +
            "\ttd.container_no AS containerNo,\n" +
            "\ts.layer AS layer,\n" +
            "\ts.dept_num AS deptNum,\n" +
            "\tcpt.source_area AS areaNo,\n" +
            "\ttd.create_time AS createTime \n" +
            "FROM\n" +
            "\tinventory_task_detail td\n" +
            "\tLEFT JOIN container_path_task cpt ON cpt.container_no = td.container_no\n" +
            "\tLEFT JOIN sx_store_location s ON s.store_no = cpt.source_location \n" +
            "WHERE\n" +
            "\tcpt.task_state = 0 \n" +
            "\tAND td.task_state = 10 \n" +
            "\tAND FIND_IN_SET( cpt.source_area, #{area} ) \n" +
            "ORDER BY\n" +
            "\ttd.create_time DESC")
    List<InventoryOutDto> getInventoryStore(@Param("area") String area);

    /**
     * 盘点回告wms
     *
     * @param id
     * @return
     */
    @Select("SELECT\n" +
            "\th.task_id AS taskId,\n" +
            "\th.bill_no AS billNo,\n" +
            "\th.bill_date AS billDate,\n" +
            "\th.seq_no AS seqNo,\n" +
            "\th.goods_type AS goodsType,\n" +
            "\th.goods_id AS goodsId,\n" +
            "\t( SELECT sum( d.original_count - d.modify_count ) FROM inventory_task_detail d WHERE d.inventory_task_id = h.id ) AS affQty,\n" +
            "\th.branch_type AS branchType,\n" +
            "\tcs.lot_id \n" +
            "FROM\n" +
            "\tinventory_task h\n" +
            "\tLEFT JOIN inventory_task_detail d ON d.inventory_task_id = h.id\n" +
            "\tJOIN container_store cs ON cs.container_no = d.container_no \n" +
            "WHERE\n" +
            "\th.id = #{id}")
    List<InventoryWmsDto> findWmsInventory(@Param("id") int id);

    /**
     * 查看盘点容器信息
     *
     * @param containerNo
     * @return
     */
    @Select("SELECT\n" +
            "\tg.goods_name AS goodsName,\n" +
            "\tg.owner_drawn_no AS ownerDrawnNo,\n" +
            "\tt.bill_no AS billNo,\n" +
            "\tcs.qty AS goodsNum,\n" +
            "\tcs.lot as lot\n" +
            "FROM\n" +
            "\tinventory_task_detail td\n" +
            "\tJOIN container_store cs ON cs.container_no = td.container_no\n" +
            "\tJOIN inventory_task t ON t.id = td.inventory_task_id\n" +
            "\tJOIN goods g ON g.id = cs.goods_id\n" +
            "\twhere cs.container_no = #{containerNo}")
    List<InventoryShowDto> findInventoryInfo(@Param("containerNo") String containerNo);

    /**
     * id查详情
     * @param inventoryId
     * @return
     */
    @Select("SELECT\n" +
            "\td.container_no AS containerNo,\n" +
            "\td.goods_no AS goodsNo,\n" +
            "\td.goods_name AS goodsName,\n" +
            "\td.original_count AS originalCount,\n" +
            "\td.modify_count AS modifyCount,\n" +
            "CASE\n" +
            "\t\td.task_state \n" +
            "\t\tWHEN 10 THEN\n" +
            "\t\t'下发' \n" +
            "\t\tWHEN 20 THEN\n" +
            "\t\t'出库' \n" +
            "\tEND AS taskState,\n" +
            "\td.station_id AS stationId,\n" +
            "\td.create_time AS createTime,\n" +
            "\td.outbound_time AS outboundTime \n" +
            "FROM\n" +
            "\tinventory_task_detail d \n" +
            "WHERE\n" +
            "\td.inventory_task_id = #{inventoryId}")
    List<InventoryDetailInfoDto> getInventoryDetail(@Param("inventoryId") int inventoryId);
}
