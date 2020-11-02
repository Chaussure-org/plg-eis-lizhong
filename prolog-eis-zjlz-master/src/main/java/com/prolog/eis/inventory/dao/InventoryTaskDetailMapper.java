package com.prolog.eis.inventory.dao;

import com.prolog.eis.dto.inventory.InventoryGoodsDto;
import com.prolog.eis.dto.inventory.InventoryOutDto;
import com.prolog.eis.dto.wms.WmsInventoryCallBackDto;
import com.prolog.eis.model.inventory.InventoryTaskDetail;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
     * @param map
     * @return
     */
    @Select("<script> SELECT\n" +
            "\tcs.container_no AS containerNo,\n" +
            "\tg.goods_no AS goodsNo,\n" +
            "\tg.goods_name AS goodsName,\n" +
            "\tcs.qty AS originalCount,\n" +
            "\tg.id as goodsId\n" +
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
            "</if>"+
            "</script>")
    List<InventoryGoodsDto> getInventoryGoods(@Param("map") Map<String,Object> map);


    /**
     * 根据区域获取盘点库存
     * @param area
     * @return
     */
    @Select("SELECT\n" +
            "\ttd.container_no AS containerNo,\n" +
            "\ts.layer AS layer,\n" +
            "\ts.dept_num AS deptNum,\n" +
            "\tcpt.source_area as areaNo,\n" +
            "td.create_time as createTime" +
            "FROM\n" +
            "\tinventory_task_detail td\n" +
            "\tLEFT JOIN container_path_task cpt ON cpt.container_no = td.container_no\n" +
            "\tLEFT JOIN sx_store_location s ON s.store_no = cpt.source_location \n" +
            "WHERE\n" +
            "\tcpt.task_state = 0 and td.task_state = 10\n" +
            "\tAND FIND_IN_SET(cpt.source_area,#{area}) order by td.create_time  desc")
    List<InventoryOutDto> getInventoryStore(@Param("area") String area);

    /**
     * 盘点回告wms
     * @param id
     * @return
     */
    @Select("SELECT\n" +
            "\th.task_id AS TASKID,\n" +
            "\th.bill_no AS BILLNO,\n" +
            "\tH.bill_date AS BILLDATE,\n" +
            "\th.seq_no AS SEQNO,\n" +
            "\th.goods_type AS ITEMTYPE,\n" +
            "\th.goods_id AS ITEMID,\n" +
            "\tm.container_no AS CONTAINERNO,\n" +
            "\tg.lot_id as LOTID,\n" +
            "\tg.id as ITEMID\n" +
            "FROM\n" +
            "\tinventory_task h\n" +
            "\tJOIN inventory_task_detail m ON m.inventory_task_id = h.id \n" +
            "\tjoin goods g on g.goods_no = m.goods_no" +
            "WHERE\n" +
            "\tm.id = #{id}")
    List<WmsInventoryCallBackDto> findWmsInventory(@Param("id") int id);
}
