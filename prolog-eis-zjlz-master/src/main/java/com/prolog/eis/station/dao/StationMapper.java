package com.prolog.eis.station.dao;

import com.prolog.eis.model.station.Station;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/10/10 15:27
 */
@Repository
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
            "\tpo.station_id = #{stationId}")
    List<Integer> getStationOrderBillId(@Param("stationId") int stationId);

    /**
     * 清空站台拣选单id
     * @param stationId
     */
    @Select("update station set current_station_pick_id = null where station_id = #{stationId}")
    void updateStationPickingOrderId(@Param("stationId") int stationId);

    /**
     * 切换站台是否索取订单
     */
    @Update("update station set is_lock = #{isLock} WHERE id = #{stationId}")
    void updateStationLock(@Param("isLock") int isLock,@Param("stationId") int stationId);
}
