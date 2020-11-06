package com.prolog.eis.engin.dao;

import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * ClassName:LineBindingDetailMapper
 * Package:com.prolog.eis.engin.dao
 * Description:
 *
 * @date:2020/10/9 13:45
 * @author:SunPP
 */
public interface LineBindingDetailMapper extends BaseMapper<LineBindingDetail> {
    @Select("SELECT\n" +
            "\tlbd.container_no AS containerNo,\n" +
            "\tlbd.goods_id AS goodsId,\n" +
            "\tlbd.order_bill_id AS orderBillId,\n" +
            "\tlbd.binding_num AS bindingNum,\n" +
            "\tlbd.wms_order_priority AS wmsOrderPriority,\n" +
            "\tlbd.order_mx_id AS orderMxId,\n" +
            "\tlbd.update_time AS updateTime \n" +
            "FROM\n" +
            "\tline_binding_detail lbd\n" +
            "\tLEFT JOIN container_path_task cpt ON lbd.container_no = cpt.container_no \n" +
            "WHERE\n" +
            "\tcpt.target_area = 'WCS081' ")
    List<AgvBindingDetail> findLineDetails();
    @Delete("DELETE FROM agv_binding_detail a WHERE a.order_bill_id NOT IN (SELECT DISTINCT IFNULL(t.id,0) FROM (SELECT ob.id FROM station s LEFT JOIN order_bill ob ON s.current_station_pick_id =ob.picking_order_id UNION ALL\n" +
            "            SELECT a.order_bill_id FROM line_binding_detail a WHERE a.wms_order_priority = 10 ) t)")
    void deleteWmsAgvBindingDetails();

    @Select("SELECT\n" +
            "\ta.container_no as containerNo\n" +
            "FROM\n" +
            "\tline_binding_detail a\n" +
            "\tLEFT JOIN container_path_task c ON a.container_no = c.container_no \n" +
            "WHERE\n" +
            "\ta.detail_status = 10 and c.target_area ='SAS01' AND c.task_state=0")
    List<LineBindingDetail> findLineContainerTopath();

    @Update("UPDATE line_binding_detail abd set abd.detail_status=#{status} WHERE abd.container_no=#{containerNo}")
    void updateLineStatus(@Param("containerNo")String containerNo, @Param("status")int status);

    @Select("SELECT COUNT(*) from container_path_task c WHERE c.target_area='WCS081' and c.task_state=0")
    int findLineBoxCount();
}
