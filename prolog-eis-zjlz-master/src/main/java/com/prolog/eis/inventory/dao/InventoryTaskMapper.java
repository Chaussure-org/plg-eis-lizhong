package com.prolog.eis.inventory.dao;

import com.prolog.eis.dto.page.InventoryInfoDto;
import com.prolog.eis.dto.page.InventoryQueryDto;
import com.prolog.eis.model.inventory.InventoryTask;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wangkang
 * @Description 盘点数据
 * @CreateTime 2020-10-14 14:47
 */
public interface InventoryTaskMapper extends BaseMapper<InventoryTask> {

    /**
     * 多条件查盘点计划
     * @param inboundQueryDto
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "\tt.id AS id,\n" +
            "\tt.bill_no AS billNo,\n" +
            "\tg.goods_name AS goodsName,\n" +
            "\tg.owner_drawn_no AS ownerDrawnNo,\n" +
            "\t( SELECT count( * ) FROM inventory_task_detail d WHERE d.inventory_task_id = t.id ) AS taskCount,\n" +
            "\t( SELECT count( * ) FROM inventory_task_detail d WHERE d.inventory_task_id = t.id AND d.task_state = 20 ) AS outboundCount,\n" +
            "\tt.goods_type AS goodsType,\n" +
            "\tt.seq_no AS seqNo,\n" +
            "\tt.bill_date AS billDate,\n" +
            "\tt.create_time AS createTime \n" +
            "FROM\n" +
            "\tinventory_task t\n" +
            "\tLEFT JOIN goods g ON t.goods_id = g.id\n" +
            "\t<where>\n" +
            "\t<if test = 'inboundQueryDto.billNo != null and inboundQueryDto.billNo != \"\"'>\n" +
            "\tand t.bill_no like concat('%',#{inboundQueryDto.billNo},'%')\n" +
            "\t</if>\n" +
            "\t\t<if test = 'inboundQueryDto.goodsName != null and inboundQueryDto.goodsName != \"\"'>\n" +
            "\tand g.goods_name like concat('%',#{inboundQueryDto.goodsName},'%')\n" +
            "\t</if>\n" +
            "\t\t\t<if test = 'inboundQueryDto.ownerDrawnNo != null and inboundQueryDto.ownerDrawnNo != \"\"'>\n" +
            "\tand g.owner_drawn_no like concat('%',#{inboundQueryDto.ownerDrawnNo},'%')\n" +
            "\t</if>\n" +
            "\t\t\t\t<if test = 'inboundQueryDto.goodsType != null and inboundQueryDto.goodsType != \"\"'>\n" +
            "\tand t.goods_type like concat('%',#{inboundQueryDto.goodsType},'%')\n" +
            "\t</if>\n" +
            "\t</where>" +
            "</script>")
    List<InventoryInfoDto> getInventoryInfo(@Param("inboundQueryDto") InventoryQueryDto inboundQueryDto);

}
