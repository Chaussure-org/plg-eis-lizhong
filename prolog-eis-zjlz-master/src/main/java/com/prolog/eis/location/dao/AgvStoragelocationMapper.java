package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: wuxl
 * @create: 2020-09-08 16:26
 * @Version: V1.0
 */
@Repository
public interface AgvStoragelocationMapper extends EisBaseMapper<AgvStoragelocation> {

    /**
     * 根据站台号查询所有托盘位
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
    int getAreaLocationCount(@Param("areaNo")String areaNo);
}
