package com.prolog.eis.location.dao;

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

    @Select("SELECT\n" +
            "\tabd.container_no AS containerNo,\n" +
            "\tabd.goods_id AS goodsId,\n" +
            "\tabd.order_bill_id AS orderBillId,\n" +
            "\tabd.order_mx_id AS orderMxId,\n" +
            "\tabd.binding_num AS bindingNum,\n" +
            "\tabd.order_priority AS orderPriority,\n" +
            "\tabd.wms_order_priority AS wmsOrderPriority,\n" +
            "\tabd.update_time AS updateTime,\n" +
            "\to.iron_tray AS ironTray \n" +
            "FROM\n" +
            "\tagv_binding_detail abd\n" +
            "\tLEFT JOIN container_path_task cpt ON abd.container_no = cpt.container_no\n" +
            "\tLEFT JOIN order_bill o ON abd.order_bill_id = o.id \n" +
            "WHERE\n" +
            "\tcpt.target_area = 'RCS01' \n" +
            "\tAND cpt.task_state =0")
    List<AgvBindingDetail> findAgvBindingDetails();

    @Select("\tSELECT abd.goodsId AS goodsId,cs.qty AS qty FROM agv_binding_detail abd LEFT JOIN container_store cs ON abd.container_no=cs.container_no\n")
    List<OutDetailDto> findAgvBindingsStore();

    @Update("UPDATE agv_binding_detail abd set abd.detail_status=#{status} WHERE abd.container_no=#{containerNo}")
    void updateAgvStatus(@Param("containerNo")String containerNo,@Param("status")int status);

    @Delete("DELETE FROM agv_binding_detail a WHERE a.order_bill_id NOT IN (SELECT DISTINCT IFNULL(t.id,0) FROM (SELECT ob.id FROM station s LEFT JOIN order_bill ob ON s.current_station_pick_id =ob.picking_order_id UNION ALL\n" +
            "            SELECT a.order_bill_id FROM agv_binding_detail a WHERE a.wms_order_priority = 10 ) t)")
    void deleteWmsAgvBindingDetail();


    @Select("SELECT\n" +
            "\ta.container_no AS containerNo \n" +
            "FROM\n" +
            "\tagv_binding_detail a\n" +
            "\tLEFT JOIN container_path_task c ON a.container_no = c.container_no \n" +
            "WHERE\n" +
            "\ta.detail_status = 10 \n" +
            "\tAND c.target_area != 'RCS01' \n" +
            "\tAND c.task_state =0\n" +
            "\torder by a.dept_num asc")
    List<AgvBindingDetail> findAgvContainerTopath();

}
