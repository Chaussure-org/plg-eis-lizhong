package com.prolog.eis.station.dao;

import com.prolog.eis.dto.inventory.StationTaskDto;
import com.prolog.eis.dto.station.ContainerTaskDto;
import com.prolog.eis.model.line.LineBindingDetail;
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
public interface StationMapper extends BaseMapper<Station> {


    /**
     * 得到当前站台拣选的订单汇总id
     *
     * @return
     */
    @Select("SELECT\n" +
            "\tob.id \n" +
            "FROM\n" +
            "\torder_bill ob\n" +
            "\tJOIN station s ON s.current_station_pick_id = ob.picking_order_id \n" +
            "WHERE\n" +
            "\ts.id = #{stationId}")
    List<Integer> getStationOrderBillId(@Param("stationId") int stationId);

    /**
     * 清空站台拣选单id
     *
     * @param stationId
     */
    @Update("update station set current_station_pick_id = null where id = #{stationId}")
    void updateStationPickingOrderId(@Param("stationId") int stationId);

    /**
     * 通过容器号查询站台任务
     *
     * @param containerNo
     * @return
     */
    @Select("select lbc.container_no as containerNo,\n" +
            "       lbc.order_bill_id as orderBillId \n" +
            "       from station                                                                " +
            "    left join line_binding_detail lbc  where lbc.container_no = #{containerNo};")
    List<ContainerTaskDto> getTaskByContainerNo(@Param("containerNo") String containerNo);

    /**
     * 切换站台是否索取订单
     */
    @Update("update station set is_lock = #{isLock} WHERE id = #{stationId}")
    void updateStationLock(@Param("isLock") int isLock, @Param("stationId") int stationId);

    @Select("SELECT * FROM agv_storagelocation a left join container_path_task c on a.location_no=c.target_location \n" +
            "where c.task_state=0 AND c.container_no=#{containerNo} AND a.device_no=#{stationId} AND c.target_area='SN01';")
    int cheackContainerNo(@Param("containerNo") String containerNo, @Param("stationId") int stationId);

    /**
     * 获取站台任务数
     * @return
     */
    @Select("SELECT\n" +
            "\ts.id AS stationId,\n" +
            "\tcount( td.id ) AS taskCount \n" +
            "FROM\n" +
            "\tstation s\n" +
            "\tLEFT JOIN inventory_task_detail td ON s.id = td.station_id \n" +
            "WHERE\n" +
            "\ts.is_lock = 0 \n" +
            "\tAND s.station_task_type = 10 \n" +
            "\tAND s.station_type = 1 \n" +
            "GROUP BY\n" +
            "\ts.id ")
    List<StationTaskDto> getStationInfo();
}
