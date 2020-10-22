package com.prolog.eis.engin.dao;

import com.prolog.eis.dto.lzenginee.OutDetailDto;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * ClassName:AgvBindingDetaileMapper
 * Package:com.prolog.eis.engin.dao
 * Description:
 *
 * @date:2020/10/9 13:46
 * @author:SunPP
 */
public interface AgvBindingDetaileMapper extends BaseMapper<AgvBindingDetail> {

    @Select("SELECT \n" +
            "abd.container_no AS containerNo,\n" +
            "abd.goods_id AS goodsId,\n" +
            "abd.order_bill_id AS orderBillId,\n" +
            "abd.order_mx_id AS orderMxId,\n" +
            "abd.binding_num AS bindingNum,\n" +
            "abd.order_priority AS orderPriority,\n" +
            "abd.wms_order_priority AS wmsOrderPriority,\n" +
            "abd.update_time AS updateTime\n" +
            "FROM agv_binding_detail abd LEFT JOIN container_path_task cpt ON abd.container_no=cpt.container_no WHERE\n" +
            "cpt.target_area='RCS01' AND cpt.task_state=0")
    List<AgvBindingDetail> findAgvBindingDetails();

    @Select("\tSELECT abd.goodsId AS goodsId,cs.qty AS qty FROM agv_binding_detail abd LEFT JOIN container_store cs ON abd.container_no=cs.container_no\n")
    List<OutDetailDto> findAgvBindingsStore();

    @Update("UPDATE agv_binding_detail abd set abd.detail_status=20 WHERE abd.container_no=#{containerNo}")
    void updateAgvStatus(@Param("containerNo")String containerNo);

    @Delete("DELETE FROM agv_binding_detail a WHERE a.order_bill_id NOT IN (SELECT DISTINCT IFNULL(t.id,0) FROM (SELECT ob.id FROM station s LEFT JOIN order_bill ob ON s.current_station_pick_id =ob.picking_order_id UNION ALL\n" +
            "            SELECT a.order_bill_id FROM agv_binding_detail a WHERE a.wms_order_priority = 10 ) t)")
    void deleteWmsAgvBindingDetail();

}
