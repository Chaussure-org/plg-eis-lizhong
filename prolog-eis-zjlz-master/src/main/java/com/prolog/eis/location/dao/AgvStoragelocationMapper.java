package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.AgvStoragelocationDTO;
import com.prolog.eis.dto.page.AgvStoreInfoDto;
import com.prolog.eis.dto.page.AgvStoreQueryDto;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.model.location.ContainerPathTask;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Insert;
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
    @Select("<script> SELECT\n" +
            "\ta.device_no AS stationId,\n" +
            "\tCOUNT( a.id ) - count( c.target_location ) AS emptyCount," +
            "count(c.target_location) as useCount \n" +
            "FROM\n" +
            "\tagv_storagelocation a\n" +
            "\tLEFT JOIN container_path_task c ON a.location_no = c.target_location \n" +
            "WHERE\n" +
            "\ta.storage_lock = 0 " +
            "and a.task_lock = 0 \n" +
            "\tAND a.area_no = #{storeArea} \n" +
            "\tAND a.device_no in" +
            "<foreach  item='item' index='index' collection='list' open='(' separator=',' close=')'> #{item}    " +
            "</foreach> GROUP BY a.device_no </script>")
    List<StationTrayDTO> findTrayTaskStation(@Param("storeArea") String storeArea,@Param("list") List<Integer> list);


    @Update("update agv_storagelocation a set a.task_lock =#{type} where a.location_no =#{locationNo}")
    void updateLocationLock(@Param("locationNo") String locationNo,@Param("type")int type);

    /**
     * 根据站台区域找寻一个可用位置
     * @param storeArea
     * @param stationId
     * @return
     */
    @Select("SELECT\n" +
            " a.location_no\n" +
            "FROM\n" +
            "\tagv_storagelocation a\n" +
            "\tLEFT JOIN container_path_task c ON a.location_no = c.target_location \n" +
            "WHERE\n" +
            "\ta.storage_lock = 0 " +
            "and a.task_lock = 0 \n" +
            "\tAND a.area_no = #{storeArea} \n" +
            "\tAND a.device_no = #{stationId}\n" +
            "\tand c.container_no is null")
    List<String> getUsableStore(@Param("storeArea") String storeArea,@Param("stationId") int stationId);

    /**
     *查托盘到位
     * @param containerNo
     * @param stationId
     * @return
     */
    @Select("SELECT count(*) FROM agv_storagelocation a left join container_path_task c on a.location_no=c.target_location \n" +
            "where c.task_state=0 AND c.container_no=#{containerNo} AND a.device_no=#{stationId} AND c.target_area='SN01';")
    int findContainerArrive(@Param("containerNo") String containerNo, @Param("stationId") int stationId);


    @Select("SELECT\n" +
            "\tc.container_no AS containerNo,\n" +
            "\tc.update_time as updateTime,\n" +
            "\tc.source_location as sourceLocation\n" +
            "FROM\n" +
            "\tcontainer_path_task c \n" +
            "WHERE\n" +
            "\tc.target_area = 'RCS01' \n" +
            "\tAND c.task_state = 0 \n" +
            "\tAND c.container_no NOT IN ( SELECT a.container_no FROM agv_binding_detail a )")
    List<ContainerPathTask>findEmptyAgvContainer();


    /**
     * 多条件查agv货位信息
     * @param agvQueryDto
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "a.id as agvStoreId," +
            "\ta.area_no AS areaNo,\n" +
            "\ta.location_no AS locationNo,\n" +
            "\ta.ceng AS layer,\n" +
            "\ta.x AS x,\n" +
            "\ta.y AS y,\n" +
            "\tcpt.container_no AS containerNo,\n" +
            "\ta.task_lock AS taskLock,\n" +
            "\ta.storage_lock AS storagelock,\n" +
            "\ta.device_no AS deviceNo,\n" +
            "\ta.location_type as locationType\n" +
            "\tFROM\n" +
            "\t\tagv_storagelocation a\n" +
            "\t\tLEFT JOIN container_path_task cpt ON cpt.source_location = a.location_no\n" +
            "\t<where>\n" +
            "\t<if test = 'agvQueryDto.areaNo != null and agvQueryDto.areaNo != \"\"'>\n" +
            "\t and a.area_no like CONCAT('%',#{agvQueryDto.areaNo},'%')\n" +
            "\t</if>\n" +
            "\t\t<if test = 'agvQueryDto.locationNo != null and agvQueryDto.locationNo != \"\"'>\n" +
            "\t and a.location_no like CONCAT('%',#{agvQueryDto.locationNo},'%')\n" +
            "\t</if>\n" +
            "\t\t\t<if test = 'agvQueryDto.containerNo != null and agvQueryDto.containerNo != \"\"'>\n" +
            "\t and cpt.container_no like CONCAT('%',#{agvQueryDto.containerNo},'%')\n" +
            "\t</if>\n" +
            "\t\t\t\t<if test = 'agvQueryDto.x != null and agvQueryDto.x != \"\"'>\n" +
            "\t and a.x = #{agvQueryDto.x}\n" +
            "\t</if>\n" +
            "\t\t\t\t\t<if test = 'agvQueryDto.y != null and agvQueryDto.y != \"\"'>\n" +
            "\t and a.x = #{agvQueryDto.y}\n" +
            "\t</if>\n" +
            "\t\t\t\t\t\t<if test = 'agvQueryDto.locationType != null and agvQueryDto.locationType != \"\"'>\n" +
            "\t and a.location_type = #{agvQueryDto.locationType}\n" +
            "\t</if>\n" +
            "\t\t\t\t\t\t\t<if test = 'agvQueryDto.taskLock != null and agvQueryDto.taskLock != \"\"'>\n" +
            "\t and a.task_lock = #{agvQueryDto.taskLock}\n" +
            "\t</if>\n" +
            "\t\t\t\t\t\t\t\t<if test = 'agvQueryDto.storagelock != null and agvQueryDto.storagelock != \"\"'>\n" +
            "\t and a.storage_lock = #{agvQueryDto.storagelock}\n" +
            "\t</if>\n" +
            "\t\n" +
            "</where>\t\n" +
            "\tORDER BY\n" +
            "\t\tcpt.container_no DESC" +
            "</script>")
    List<AgvStoreInfoDto> getAgvStoreInfo(@Param("agvQueryDto") AgvStoreQueryDto agvQueryDto);

    /**
     * 查铁笼区铁笼集合
     * @param areaNo
     * @return
     */
    @Select("SELECT\n" +
            "\tcpt.container_no \n" +
            "FROM\n" +
            "\tagv_storagelocation a\n" +
            "\tJOIN container_path_task cpt ON cpt.source_location = a.location_no \n" +
            "WHERE\n" +
            "\tcpt.task_state = 0\n" +
            "\tand a.storage_lock = 0\n" +
            "\tand a.area_no = #{areaNo}")
    List<String> getIronTrays(@Param("areaNo") String areaNo);
}
