package com.prolog.eis.location.dao;

import com.prolog.eis.dto.store.SxStoreLocationDto;
import com.prolog.eis.model.store.SxStoreLocation;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SxStoreLocationMapper extends EisBaseMapper<SxStoreLocation> {
	
	@Select("select sl.id,sl.STORE_LOCATION_GROUP_ID as storeLocationGroupId,sl.LAYER as layer ,sl.x,sl.y,sl.STORE_LOCATION_ID1 as storeLocationId1,\r\n" + 
			"sl.STORE_LOCATION_ID2 as storeLocationId2 ,sl.ASCENT_LOCK_STATE as ascentLockState ,sl.LOCATION_INDEX as locationIndex ,sl.dept_num as deptNum from sx_store_location sl \r\n" + 
			"inner join container_path_task s on s.source_area = sl.area_no and s.source_location = sl.store_no\r\n" +
			"inner join sx_store_location_group slg on slg.id = sl.STORE_LOCATION_GROUP_ID\r\n" + 
			"where sl.STORE_LOCATION_GROUP_ID = #{locationGroupId} and  slg.entrance1_property1 = #{taskProperty1} and slg.entrance1_property2 = #{taskProperty2}\r\n" + 
			"and sl.dept_num = 0 ORDER BY sl.location_index")
	List<SxStoreLocation> findByProperty1(@Param("locationGroupId")Integer locationGroupId, @Param("taskProperty1")String taskProperty1, @Param("taskProperty2") String taskProperty2);

	@Select("select sl.id,sl.STORE_LOCATION_GROUP_ID as storeLocationGroupId,sl.LAYER as layer ,sl.x,sl.y,sl.STORE_LOCATION_ID1 as storeLocationId1,\r\n" + 
			"sl.STORE_LOCATION_ID2 as storeLocationId2 ,sl.ASCENT_LOCK_STATE as ascentLockState ,sl.LOCATION_INDEX as locationIndex ,sl.dept_num as deptNum from sx_store_location sl \r\n" + 
			"inner join container_path_task s on s.source_area = sl.area_no and s.source_location = sl.store_no\r\n" +
			"inner join sx_store_location_group slg on slg.id = sl.STORE_LOCATION_GROUP_ID\r\n" + 
			"where sl.STORE_LOCATION_GROUP_ID = #{locationGroupId} and  \r\n" + 
			"slg.entrance2_property1 = #{taskProperty1} and slg.entrance2_property2 = #{taskProperty2} and sl.dept_num = 0 ORDER BY sl.location_index desc" )
	List<SxStoreLocation> findByProperty2(@Param("locationGroupId")Integer locationGroupId, @Param("taskProperty1")String taskProperty1, @Param("taskProperty2") String taskProperty2);
	
	@Select("select sl.id,sl.STORE_LOCATION_GROUP_ID as storeLocationGroupId,sl.LAYER as layer ,sl.x,sl.y,sl.STORE_LOCATION_ID1 as storeLocationId1,\r\n" + 
			"sl.STORE_LOCATION_ID2 as storeLocationId2 ,sl.ASCENT_LOCK_STATE as ascentLockState ,sl.LOCATION_INDEX as locationIndex ,sl.dept_num as deptNum \r\n" + 
			"from sx_store_location sl\r\n" + 
			"inner join container_path_task t on t.source_area = sl.area_no and t.source_location = sl.store_no\r\n" +
			"where sl.STORE_LOCATION_GROUP_ID = #{storeLocationGroupId} and sl.dept_num = 0")
	List<SxStoreLocation> findStoreLocation(@Param("storeLocationGroupId")int storeLocationGroupId);
	
	@Select("select sx.id,sx.store_location_group_id as storeLocationGroupId , sx.layer ,sx.x,sx.y ,sx.store_location_id1 as storeLocationId1, \r\n" + 
			"sx.store_location_id2 as storeLocationId2 ,sx.ascent_lock_state as ascentLockState, sx.location_index as locationIndex,sx.dept_num as deptNum, \r\n" + 
			"sx.create_time as createTime,s.container_no as sourceContainerNo,ts.container_no as targetContainerNo\r\n" + 
			"from sx_store_location sx\r\n" +
			"left join container_path_task s on s.source_area = sx.area_no and sx.store_no = s.source_location and s.task_state < #{startTaskState}\n" +
			"left join container_path_task_detail ts on ts.next_area = sx.area_no and sx.store_no = ts.next_location and ts.task_state >= #{startDetailTaskState}\n" +
			"where (s.id is not null or ts.id is not null) and sx.store_location_group_id = #{groupId} order by sx.location_index")
	List<SxStoreLocationDto> findMinHaveStore(@Param("groupId")int groupId, @Param("startTaskState")int startTaskState, @Param("startDetailTaskState")int startDetailTaskState);
	
	@Select("select sx.id,sx.store_location_group_id as storeLocationGroupId , sx.layer ,sx.x,sx.y ,sx.store_location_id1 as storeLocationId1, \r\n" + 
			"sx.store_location_id2 as storeLocationId2 ,sx.ascent_lock_state as ascentLockState, sx.location_index as locationIndex,sx.dept_num as deptNum, \r\n" + 
			"sx.create_time as createTime,s.container_no as sourceContainerNo,ts.container_no as targetContainerNo\r\n" + 
			"from sx_store_location sx\r\n" +
			"left join container_path_task s on s.source_area = sx.area_no and sx.store_no = s.source_location and s.task_state < #{startTaskState}\n" +
			"left join container_path_task_detail ts on ts.next_area = sx.area_no and sx.store_no = ts.next_location and ts.task_state >= #{startDetailTaskState}\n" +
			"where (s.id is not null or ts.id is not null) and sx.store_location_group_id = #{groupId} order by sx.location_index desc")
	List<SxStoreLocationDto> findMaxHaveStore(@Param("groupId")int groupId, @Param("startTaskState")int startTaskState, @Param("startDetailTaskState")int startDetailTaskState);
	
	@Select("select sx.id,sx.store_location_group_id as storeLocationGroupId , sx.layer ,sx.x,sx.y ,sx.store_location_id1 as storeLocationId1,  \n" +
			"sx.store_location_id2 as storeLocationId2 ,sx.ascent_lock_state as ascentLockState, sx.location_index as locationIndex,sx.dept_num as deptNum,  \n" +
			"sx.create_time as createTime,s.container_no as sourceContainerNo,ts.container_no as targetContainerNo \n" +
			"from sx_store_location sx\n" +
			"left join container_path_task s on s.source_area = sx.area_no and sx.store_no = s.source_location and s.task_state < #{startTaskState}\n" +
			"left join container_path_task_detail ts on ts.next_area = sx.area_no and sx.store_no = ts.next_location and ts.task_state >= #{startDetailTaskState}\n" +
			"where (s.id is not null or ts.id is not null) and sx.store_location_group_id = #{groupId}")
	List<SxStoreLocationDto> findHaveStore(@Param("groupId")int groupId, @Param("startTaskState")int startTaskState, @Param("startDetailTaskState")int startDetailTaskState);
	
	@Update("update sx_store_location t\r\n" + 
			"set t.is_inBound_location = 0\r\n" + 
			"where t.store_location_group_id = #{groupId} and t.location_index != #{haveStoreIndex}")
	void updateNotIsInboundLocation(@Param("groupId")int groupId,@Param("haveStoreIndex")int haveStoreIndex);
	
	@Update("update sx_store_location t\r\n" + 
			"set t.is_inBound_location = 0\r\n" + 
			"where t.store_location_group_id = #{groupId} and t.location_index != #{bigHaveStoreIndex} and t.location_index != #{smallHaveStoreIndex}")
	void updateNotIsInboundLocationTwo(@Param("groupId")int groupId,@Param("bigHaveStoreIndex")int bigHaveStoreIndex,@Param("smallHaveStoreIndex")int smallHaveStoreIndex);


	@Insert("<script>insert into SX_STORE_LOCATION " +
			"(ascent_lock_state,layer,location_index,store_location_group_id,store_location_id1,store_location_id2,x,y,depth" +
			",store_no,dept_num) values " +
			"<foreach collection='list' item='c' separator=','>" +
			"(0,#{c.layer}" +
			",#{c.locationIndex},#{c.storeLocationGroupId},#{c.storeLocationId1},#{c.storeLocationId2}" +
			",#{c.x},#{c.y},#{c.depth},#{c.storeNo},0)" +
			"</foreach></script>")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void saveBatchReturnKey(@Param("list") List<SxStoreLocation> storeLocationDtos);

	@Update("<script>UPDATE SX_STORE_LOCATION t," +
			"(<foreach collection = \"list\" separator = \"union all\" item = \"item\">\n" +
			"     select #{item.id} as id," +
			"            #{item.storeLocationId1} as storeLocationId1," +
			"            #{item.storeLocationId2} as storeLocationId2" +
			"  </foreach>) t2"+
			" set t.store_location_id1=t2.storeLocationId1,t.store_location_id2 = t2.storeLocationId2" +
			" where t.id = t2.id</script>")
	void updateAdjacentStore(@Param("list") List<SxStoreLocation> storeLocationDtos);

	@Update("<script>UPDATE SX_STORE_LOCATION t," +
			"(<foreach collection = \"list\" separator = \"union all\" item = \"item\">\n" +
			"     select #{item.storeLocationGroupId} as storeLocationGroupId," +
			"            #{item.x} as x," +
			"            #{item.y} as y," +
			"            #{item.locationIndex} as locationIndex" +
			"  </foreach>) t2"+
			" set t.x=t2.x,t.y=t2.y" +
			" where t.STORE_LOCATION_GROUP_ID = t2.storeLocationGroupId AND t.LOCATION_INDEX = t2.locationIndex</script>")
	void batchUpdateById(@Param("list") List<SxStoreLocation> list);

	@Update("<script>UPDATE SX_STORE_LOCATION t," +
			"(<foreach collection = \"list\" separator = \"union all\" item = \"item\">\n" +
			"     select #{item.id} as id," +
			" 			 #{item.wmsStoreNo} as wmsStoreNo" +
			"  </foreach>) t2"+
			" set t.wms_store_no=t2.wmsStoreNo" +
			" where t.id = t2.id</script>")
	void batchUpdateWmsHuoWei(@Param("list") List<SxStoreLocation> list);

	@Update("update sx_store_location sx set sx.dept_num = (sx.dept_num + 1) where sx.dept_num > 0 and sx.store_location_group_id = #{groupId}")
	void updateYKLocation(@Param("groupId")int groupId);

	@Select("select max(sx.dept_num) from sx_store_location sx where sx.store_location_group_id =#{sxStoreLocationGroupId}")
	Integer findMaxYkNum(@Param("sxStoreLocationGroupId")int sxStoreLocationGroupId);

	@Select("select count(*) from sx_store_location t where t.area_no = #{areaNo}")
	int getAreaLocationCount(@Param("areaNo")String areaNo);
}
