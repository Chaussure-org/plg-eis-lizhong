package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.sxk.SxStoreLocationDto;
import com.prolog.eis.model.location.sxk.SxStoreLocation;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SxStoreLocationMapper extends EisBaseMapper<SxStoreLocation> {

	@Select("select * from sx_store_location sl where sl.STORE_LOCATION_GROUP_ID = #{locationGroupId} and sl.limit_weight > #{weight} and sl.is_inBound_location = 1")
	List<SxStoreLocation> checkWeight(@Param("locationGroupId") Integer locationGroupId, @Param("weight") double weight);

	@Select("select sl.id,sl.STORE_LOCATION_GROUP_ID as storeLocationGroupId,sl.LAYER as layer ,sl.x,sl.y,sl.STORE_LOCATION_ID1 as storeLocationId1,\r\n" +
			"sl.STORE_LOCATION_ID2 as storeLocationId2 ,sl.ASCENT_LOCK_STATE as ascentLockState ,sl.LOCATION_INDEX as locationIndex ,sl.dept_num as deptNum from sx_store_location sl \r\n" +
			"inner join sx_store s on s.STORE_LOCATION_ID = sl.ID\r\n" +
			"inner join sx_store_location_group slg on slg.id = sl.STORE_LOCATION_GROUP_ID\r\n" +
			"where sl.STORE_LOCATION_GROUP_ID = #{locationGroupId} and  slg.entrance1_property1 = #{taskProperty1} and slg.entrance1_property2 = #{taskProperty2}\r\n" +
			"and sl.dept_num = 0 ORDER BY sl.location_index")
	List<SxStoreLocation> findByProperty1(@Param("locationGroupId") Integer locationGroupId,
                                          @Param("taskProperty1") String taskProperty1,
                                          @Param("taskProperty2") String taskProperty2);

	@Select("select sl.id,sl.STORE_LOCATION_GROUP_ID as storeLocationGroupId,sl.LAYER as layer ,sl.x,sl.y,sl.STORE_LOCATION_ID1 as storeLocationId1,\r\n" +
			"sl.STORE_LOCATION_ID2 as storeLocationId2 ,sl.ASCENT_LOCK_STATE as ascentLockState ,sl.LOCATION_INDEX as locationIndex ,sl.dept_num as deptNum from sx_store_location sl \r\n" +
			"inner join sx_store s on s.STORE_LOCATION_ID = sl.ID\r\n" +
			"inner join sx_store_location_group slg on slg.id = sl.STORE_LOCATION_GROUP_ID\r\n" +
			"where sl.STORE_LOCATION_GROUP_ID = #{locationGroupId} and  \r\n" +
			"slg.entrance2_property1 = #{taskProperty1} and slg.entrance2_property2 = #{taskProperty2} and sl.dept_num" +
            " = 0 ORDER BY sl.location_index desc" )
	List<SxStoreLocation> findByProperty2(@Param("locationGroupId") Integer locationGroupId, @Param("taskProperty1") String taskProperty1, @Param("taskProperty2") String taskProperty2);

	@Select("select sl.id,sl.STORE_LOCATION_GROUP_ID as storeLocationGroupId,sl.LAYER as layer ,sl.x,sl.y,sl.STORE_LOCATION_ID1 as storeLocationId1,\r\n" +
			"sl.STORE_LOCATION_ID2 as storeLocationId2 ,sl.ASCENT_LOCK_STATE as ascentLockState ,sl.LOCATION_INDEX as locationIndex ,sl.dept_num as deptNum \r\n" +
			"from sx_store_location sl\r\n" +
			"left join container_path_task t on t.source_area = sl.area_no and t.source_location = sl.store_no\r\n" +
			"where sl.STORE_LOCATION_GROUP_ID = #{storeLocationGroupId} and sl.dept_num = 0")
	List<SxStoreLocation> findStoreLocation(@Param("storeLocationGroupId") int id);

	@Select("select sx.id,sx.store_location_group_id as storeLocationGroupId , sx.layer ,sx.x,sx.y ,sx.store_location_id1 as storeLocationId1, \r\n" +
			"sx.store_location_id2 as storeLocationId2 ,sx.ascent_lock_state as ascentLockState, sx.location_index as locationIndex,sx.dept_num as deptNum, \r\n" +
			"sx.create_time as createTime,s.container_no as sourceContainerNo,ts.container_no as targetContainerNo\r\n" +
			"from sx_store_location l\r\n" +
			"left join container_path_task_detail s on s.source_area = l.area_no and l.store_no = s.source_location and s.task_state < #{startTaskState}\r\n" +
			"left join container_path_task_detail ts on ts.next_area = l.area_no and l.store_no = ts.next_location\r\n" +
			"where (s.id is not null or ts.id is not null) and l.store_location_group_id = #{sxStoreLocationGroupId} " +
            "order by sx.location_index")
	List<SxStoreLocationDto> findMinHaveStore(@Param("groupId") int groupId, @Param("startTaskState") int startTaskState);

	@Select("select sx.id,sx.store_location_group_id as storeLocationGroupId , sx.layer ,sx.x,sx.y ,sx.store_location_id1 as storeLocationId1, \r\n" +
			"sx.store_location_id2 as storeLocationId2 ,sx.ascent_lock_state as ascentLockState, sx.location_index as locationIndex,sx.dept_num as deptNum, \r\n" +
			"sx.create_time as createTime,s.container_no as sourceContainerNo,ts.container_no as targetContainerNo\r\n" +
			"from sx_store_location l\r\n" +
			"left join container_path_task_detail s on s.source_area = l.area_no and l.store_no = s.source_location and s.task_state < #{startTaskState}\r\n" +
			"left join container_path_task_detail ts on ts.next_area = l.area_no and l.store_no = ts.next_location\r\n" +
			"where (s.id is not null or ts.id is not null) and l.store_location_group_id = #{sxStoreLocationGroupId} " +
            "order by sx.location_index desc")
	List<SxStoreLocationDto> findMaxHaveStore(@Param("groupId") int groupId, @Param("startTaskState") int startTaskState);

	@Select("select sx.id,sx.store_location_group_id as storeLocationGroupId , sx.layer ,sx.x,sx.y ,sx.store_location_id1 as storeLocationId1, \r\n" +
			"sx.store_location_id2 as storeLocationId2 ,sx.ascent_lock_state as ascentLockState, sx.location_index as locationIndex,sx.dept_num as deptNum, \r\n" +
			"sx.create_time as createTime,s.container_no as sourceContainerNo,ts.container_no as targetContainerNo\r\n" +
			"from sx_store_location l\r\n" +
			"left join container_path_task_detail s on s.source_area = l.area_no and l.store_no = s.source_location and s.task_state < #{startTaskState}\r\n" +
			"left join container_path_task_detail ts on ts.next_area = l.area_no and l.store_no = ts.next_location\r\n" +
			"where (s.id is not null or ts.id is not null) and l.store_location_group_id = #{sxStoreLocationGroupId}")
	List<SxStoreLocationDto> findHaveStore(@Param("groupId") int groupId, @Param("startTaskState") int startTaskState);

	@Update("update sx_store_location t\r\n" +
			"set t.is_inBound_location = 0\r\n" +
			"where t.store_location_group_id = #{groupId} and t.location_index != #{haveStoreIndex}")
	void updateNotIsInboundLocation(@Param("groupId") int groupId, @Param("haveStoreIndex") int haveStoreIndex);

	@Update("update sx_store_location t\r\n" +
			"set t.is_inBound_location = 0\r\n" +
			"where t.store_location_group_id = #{groupId} and t.location_index != #{bigHaveStoreIndex} and t" +
            ".location_index != #{smallHaveStoreIndex}")
	void updateNotIsInboundLocationTwo(@Param("groupId") int groupId, @Param("bigHaveStoreIndex") int bigHaveStoreIndex, @Param("smallHaveStoreIndex") int smallHaveStoreIndex);
}
