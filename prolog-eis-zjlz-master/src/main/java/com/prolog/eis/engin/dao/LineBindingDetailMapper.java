package com.prolog.eis.engin.dao;

import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

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
    @Select("SELECT \n" +
            "lbd.container_no AS containerNo,\n" +
            "lbd.goodsId AS goodsId,\n" +
            "lbd.order_bill_id AS orderBilld,\n" +
            "lbd.order_mx_id AS orderMxId,\n" +
            "lbd.update_time AS updateTime\n" +
            "FROM line_binding_detail lbd LEFT JOIN container_path_task cpt ON lbd.container_no=cpt.container_no WHERE\n" +
            "cpt.source_area='a' AND cpt.task_state=0")
    List<LineBindingDetail> findLineDetails();
    @Delete("DELETE FROM agv_binding_detail a WHERE a.order_bill_id NOT IN (SELECT DISTINCT IFNULL(t.id,0) FROM (SELECT ob.id FROM station s LEFT JOIN order_bill ob ON s.current_station_pick_id =ob.picking_order_id UNION ALL\n" +
            "            SELECT a.order_bill_id FROM line_binding_detail a WHERE a.wms_order_priority = 10 ) t)")
    void deleteWmsAgvBindingDetails();
}
