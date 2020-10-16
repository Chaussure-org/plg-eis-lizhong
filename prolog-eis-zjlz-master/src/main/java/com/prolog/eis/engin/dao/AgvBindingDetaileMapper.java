package com.prolog.eis.engin.dao;

import com.prolog.eis.dto.lzenginee.OutDetailDto;
import com.prolog.eis.model.agv.AgvBindingDetail;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

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
            "abd.goodsId AS goodsId,\n" +
            "abd.order_bill_id AS orderBilld,\n" +
            "abd.order_mx_id AS orderMxId,\n" +
            "abd.order_priority AS orderPriority,\n" +
            "abd.update_time AS updateTime\n" +
            "FROM agv_binding_detail abd LEFT JOIN container_path_task cpt ON abd.container_no=cpt.container_no WHERE\n" +
            "cpt.source_area='A100' AND cpt.task_state=0 and abd.wms_order_priority !=10")
    List<AgvBindingDetail> findAgvBindingDetails();

    @Select("\tSELECT abd.goodsId AS goodsId,cs.qty AS qty FROM agv_binding_detail abd LEFT JOIN container_store cs ON abd.container_no=cs.container_no\n")
    List<OutDetailDto> findAgvBindingsStore();

}
