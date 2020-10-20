package com.prolog.eis.station.dao;

import com.prolog.eis.dto.station.ContainerTaskDto;
import com.prolog.eis.model.line.LineBindingDetail;
import com.prolog.eis.model.station.Station;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
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
     * 通过容器号查询站台任务
     * @param containerNo
     * @return
     */
    @Select("select lbc.container_no as containerNo,\n" +
            "       lbc.order_bill_id as orderBillId,\n" +
            "       po.id as pickOrderId,\n" +
            "       s.id as stationId from                                                               " +
            "station s\n" +
            "    left join picking_order po on s.id = po.station_id\n" +
            "    left join order_bill ob on po.id = ob.picking_order_id\n" +
            "    left join line_binding_detail lbc on ob.id = lbc.order_bill_id where s.container_no = #{containerNo};")
    List<ContainerTaskDto> getTaskByContainerNo(@Param("containerNo") String containerNo);
}
