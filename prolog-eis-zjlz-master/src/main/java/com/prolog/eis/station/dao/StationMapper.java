package com.prolog.eis.station.dao;

import com.prolog.eis.model.Station;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 15:27
 */
public interface StationMapper extends BaseMapper<Station> {


    /**
     *   得到当前站台拣选的订单汇总id
     * @return
     */
    @Select("SELECT\n" +
            "\tob.id \n" +
            "FROM\n" +
            "\torder_bill ob\n" +
            "\tJOIN station s ON s.current_station_pick_id = ob.picking_order_id \n" +
            "WHERE\n" +
            "\tpo.station_id = #{}")
    List<Integer> getStationOrderBillId();
}
