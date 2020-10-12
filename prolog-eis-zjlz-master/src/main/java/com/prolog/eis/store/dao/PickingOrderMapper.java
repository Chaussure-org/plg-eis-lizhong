package com.prolog.eis.store.dao;

import com.prolog.eis.dto.lzenginee.boxoutdto.StationPickingOrderDto;
import com.prolog.eis.model.PickingOrder;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName:PickingOrderMapper
 * Package:com.prolog.eis.store.dao
 * Description:
 *
 * @date:2020/10/12 10:07
 * @author:SunPP
 */
public interface PickingOrderMapper extends BaseMapper<PickingOrder> {
    @Select("SELECT \n" +
            "po.id AS pickingOrderId,\n" +
            "po.station_id AS stationId,\n" +
            "ob.id AS orderBillId\n" +
            "FROM picking_order po LEFT JOIN order_bill ob on po.id=ob.picking_order_id")
    List<StationPickingOrderDto> findPickOrder();
}
