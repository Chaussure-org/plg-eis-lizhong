package com.prolog.eis.warehousing.dao;

import com.prolog.eis.dto.page.InboundQueryDto;
import com.prolog.eis.dto.page.WmsInboundInfoDto;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wangkang
 * @Description 入库任务数据处理层
 * @CreateTime 2020-10-19 10:10
 */
public interface WareHousingMapper extends BaseMapper<WmsInboundTask> {

    /**
     * 多条件查入库任务
     * @param inboundQueryDto
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "\tw.branch_type AS branchType,\n" +
            "\tw.line_id AS lineId,\n" +
            "\tw.bill_no AS billNo,\n" +
            "\tw.container_no AS containerNo,\n" +
            "\tw.seq_no AS seqNo,\n" +
            "\tw.goods_id AS goodsId,\n" +
            "\tw.goods_name AS goodsName,\n" +
            "\tw.qty AS qty,\n" +
            "\tw.create_time AS createTime,\n" +
            "CASE\n" +
            "\t\tw.bill_type \n" +
            "\t\tWHEN 1 THEN\n" +
            "\t\t'入库上架' \n" +
            "\t\tWHEN 2 THEN\n" +
            "\t\t'移库上架' ELSE '空拖盘上架' \n" +
            "\tEND AS billType,\n" +
            "CASE\n" +
            "\t\tw.task_state \n" +
            "\t\tWHEN 0 THEN\n" +
            "\t\t'创建' ELSE '进入库内' \n" +
            "\tEND \n" +
            "FROM\n" +
            "\twms_inbound_task w\n" +
            "\tWHERE 1= 1\n" +
            "\t<if test = 'inboundQueryDto.branchType != null and inboundQueryDto.branchType != \"\"'>\n" +
            "\tand w.branch_type like concat('%',#{inboundQueryDto.branchType},'%')\n" +
            "</if>\n" +
            "\t<if test = 'inboundQueryDto.billNo != null and inboundQueryDto.billNo != \"\"'>\n" +
            "\tand w.bill_no like concat('%',#{inboundQueryDto.billNo},'%')\n" +
            "</if>\n" +
            "\t<if test = 'inboundQueryDto.containerNo != null and inboundQueryDto.containerNo != \"\"'>\n" +
            "\tand w.container_no like concat('%',#{inboundQueryDto.containerNo},'%')\n" +
            "</if>\n" +
            "\t<if test = 'inboundQueryDto.goodsName != null and inboundQueryDto.goodsName != \"\"'>\n" +
            "\tand w.goods_name like concat('%',#{inboundQueryDto.goodsName},'%')\n" +
            "</if>\n" +
            "order by w.create_time asc" +
            "</script>")
    List<WmsInboundInfoDto> getInboundInfo(@Param("inboundQueryDto") InboundQueryDto inboundQueryDto);
}
