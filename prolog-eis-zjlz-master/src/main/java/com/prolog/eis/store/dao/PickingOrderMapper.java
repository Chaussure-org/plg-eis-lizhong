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
    @Select("SELECT\n" +
            "\tpo.id AS pickingOrderId,\n" +
            "\tpo.station_id AS stationId,\n" +
            "\tob.id AS orderBillId,\n" +
            "\ta.location_no AS locationNo \n" +
            "FROM\n" +
            "\tpicking_order po\n" +
            "\tLEFT JOIN order_bill ob ON po.id = ob.picking_order_id\n" +
            "\tLEFT JOIN agv_storagelocation a ON po.station_id = a.device_no \n" +
            "\tAND a.area_no = 'SA'")
    List<StationPickingOrderDto> findPickOrder();
}
