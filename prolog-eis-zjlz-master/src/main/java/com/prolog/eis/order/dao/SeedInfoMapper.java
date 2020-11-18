package com.prolog.eis.order.dao;

import com.prolog.eis.dto.page.PickingPrintDto;
import com.prolog.eis.dto.page.PickingPrintQueryDto;
import com.prolog.eis.model.order.SeedInfo;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/13 17:22
 */
public interface SeedInfoMapper extends BaseMapper<SeedInfo> {

    /**
     * 查询最近一条播种记录
     * @param orderDetailId
     * @return
     */
    @Select("SELECT\n" +
            "\tid AS id,\n" +
            "\tcontainer_no AS containerNo,\n" +
            "\torder_bill_id AS orderBillId,\n" +
            "\torder_detail_id AS orderDetailId,\n" +
            "\torder_tray_no AS orderTrayNo,\n" +
            "\tstation_id AS stationId,\n" +
            "\tnum AS num,\n" +
            "\tcreate_time AS createTime,\n" +
            "\tgoods_id AS goodsId \n" +
            "FROM\n" +
            "\tseed_info s \n" +
            "WHERE\n" +
            "\ts.order_detail_id = #{orderDetailId} order by create_time desc limit 1")
    SeedInfo findSeedInfoByOrderDetail(@Param("orderDetailId") int orderDetailId);

    /**
     * 派工单查询
     * @param printQueryDto
     * @return
     */
    @Select("<script>" +
            "SELECT DISTINCT\n" +
            "\ts.order_no AS orderNo,\n" +
            "\ts.order_tray_no AS orderTrayNo,\n" +
            "\tstation_id AS stationId,\n" +
            "\ts.operator AS operator \n" +
            "FROM\n" +
            "\tseed_info s \n" +
            "WHERE 1 =1 \n" +
            "<if test = 'printQueryDto.orderTrayNo != null and printQueryDto.orderTrayNo != \"\" '>\n" +
            "\tand s.order_tray_no like concat('%',#{printQueryDto.orderTrayNo},'%')\n" +
            "</if>\n" +
            "<if test = 'printQueryDto.orderNo != null and printQueryDto.orderNo != \"\" '>\n" +
            "\tand s.order_no like concat('%',#{printQueryDto.orderNo},'%')\n" +
            "</if>" +
            "ORDER BY " +
            "s.order_no DESC" +
            "</script>")
    List<PickingPrintDto> getSeedInfo(@Param("printQueryDto")PickingPrintQueryDto printQueryDto);
}
