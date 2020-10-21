package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: wuxl
 * @create: 2020-09-08 16:26
 * @Version: V1.0
 */
public interface AgvStoragelocationMapper extends EisBaseMapper<AgvStoragelocation> {

    /**
     * 根据站台号查询所有托盘位
     *
     * @param stationId
     * @return
     * @throws Exception
     */
    @Select({"select agvs.area_no areaNo, agvs.location_no locationNo,agvs.x originX,agvs.y originY \r\n" +
            "from agv_storagelocation agvs \r\n" +
            "right join station s  on s.area_no = agvs.area_no \r\n" +
            "where s.id = #{stationId} and agvs.location_type=3"})
    List<AgvStoragelocationDTO> listAgvStoragelocationXyByStationNo(@Param("stationId") String stationId);

    @Select("select count(*) from agv_storagelocation t where t.area_no = #{areaNo}")
    int getAreaLocationCount(@Param("areaNo") String areaNo);


    /**
     * 查询站台集合的可用任务拖区域
     * @param list
     * @return
     */
    @Select("<script> select device_no,COUNT(*) from agv_storagelocation a where a.storage_lock = 0 and a.task_lock = 0 and area_no = 'OT' and  device_no IN " +
            "<foreach  item='item' index='index' collection='list' open='(' separator=',' close=')'> #{item}    " +
            "</foreach> GROUP BY device_no </script>")
    List<StationTrayDTO> findTrayTaskStation(List<Integer> list);


    @Update("update agv_storagelocation a set a.task_lock =1 where a.location_no =#{locationNo}")
    void updateLocationLock(@Param("locationNo") String locationNo);
}
