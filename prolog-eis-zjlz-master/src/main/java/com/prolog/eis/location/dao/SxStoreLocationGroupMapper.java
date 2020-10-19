package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.InStoreLocationGroupDto;
import com.prolog.eis.dto.location.sxk.StoreLocationGroupDto;
import com.prolog.eis.model.store.SxStoreLocationGroup;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 货位组Mapper
 */
@Repository
public interface SxStoreLocationGroupMapper extends EisBaseMapper<SxStoreLocationGroup> {

	/**
	 * 通过区域找满足条件的货位组
	 * @param area
	 * @param weight
	 * @return
	 */
	@Select("select slg.id as storeLocationGroupId,count(s.id) as containerCount,slg.IN_OUT_NUM as inOutNum ,slg.x,slg.y,slg.reserved_location as reservedLocation,slg.entrance1_property1 as entrance1Property1 ,slg.entrance1_property2 as entrance1Property2 ,slg.entrance2_property1 as entrance2Property1 ,slg.entrance2_property2 as  entrance2Property2  \r\n" + 
			"from sx_store_location sl \r\n" + 
			"left join sx_store_location_group slg on slg.id = sl.STORE_LOCATION_GROUP_ID \r\n" + 
			"left join container_path_task s on s.source_area = sl.area_no and s.source_location = sl.store_no \r\n" + 
			"where slg.IS_LOCK = 0 and slg.ASCENT_LOCK_STATE = 0 \r\n" + 
			"and slg.location_num > (select count(*) from sx_store_location sl inner join container_path_task s on \r\n" + 
			"s.source_location = sl.store_no where sl.STORE_LOCATION_GROUP_ID = slg.ID) \r\n" + 
			"and sl.area_no = #{area} and EXISTS (select sl2.id from sx_store_location sl2 where sl2.limit_weight > #{weight} and sl2.STORE_LOCATION_GROUP_ID = slg.ID and sl2.is_inBound_location = 1) \r\n" + 
			"GROUP BY slg.ID ,slg.IN_OUT_NUM,slg.IN_OUT_NUM,slg.x,slg.y,slg.reserved_location,slg.entrance1_property1,slg.entrance1_property2,slg.entrance2_property1,slg.entrance2_property2")
	List<InStoreLocationGroupDto> findStoreLocationGroupByArea(@Param("area")String area, @Param("weight") Double weight);

	@Select("select g.ID\r\n" + 
			"from sx_store_location_group g\r\n" + 
			"left join sx_store_location l on g.id = l.store_location_group_id\r\n" + 
			"left join container_path_task s on s.source_area = l.area_no and s.source_location = l.store_no and s.task_state = #{startTaskState}\r\n" +
			"left join container_path_task ts on ts.target_area = l.area_no and ts.target_location = l.store_no and ts.task_state >= #{startTaskState}\r\n" +
			"where g.id = #{groupId} and (s.id is not null or ts.id is not null)")
	Integer checkGroupLock(@Param("groupId")int groupId,@Param("startTaskState")int startTaskState);


	/**
	 * 删除货位货位组
	 */
	@Delete("delete from SX_STORE_LOCATION_GROUP")
	void deleteAll();


	/**
	 * 批量新增，并返回ID
	 * @param storeLocationGroupDtos
	 */
	@Insert("<script>insert into SX_STORE_LOCATION_GROUP " +
			"(belong_area,ENTRANCE,GROUP_NO,IN_OUT_NUM,layer," +
			"location_num,reserved_location,x,y,ASCENT_LOCK_STATE,is_lock,READY_OUT_LOCK) values " +
			"<foreach collection='list' item='c' separator=','>" +
			"(#{c.belongArea},#{c.entrance},#{c.groupNo}" +
			",#{c.inOutNum},#{c.layer},#{c.locationNum},#{c.reservedLocation}" +
			",#{c.x},#{c.y},0,0,0)" +
			"</foreach></script>")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void saveBatchReturnKey(@Param("list") List<StoreLocationGroupDto> storeLocationGroupDtos);

	/**
	 * 根据编号找货位组
	 *
	 * @param groupNosStr
	 * @return
	 */
	@Select("select t.id as id,t.GROUP_NO as groupNo,t.location_num as locationNum from SX_STORE_LOCATION_GROUP t" +
			" where FIND_IN_SET(t.GROUP_NO,#{groupNosStr})")
	List<StoreLocationGroupDto> findByGroupNos(@Param("groupNosStr") String groupNosStr);


	/**
	 * "t.x=t2.x,t.y=t2.y,t.ENTRANCE=t2.entrance,t.IN_OUT_NUM=t2.inOutNum" +
	 * t.belong_area=t2.belongArea,
	 *
	 * @param list
	 */
	@Update("<script>UPDATE SX_STORE_LOCATION_GROUP t," +
			"(<foreach collection = \"list\" separator = \"union all\" item = \"item\">\n" +
			"     select #{item.id} as id," +
			"            #{item.x} as x," +
			"            #{item.y} as y," +
			"            #{item.belongArea} as belongArea," +
			"            #{item.entrance} as entrance," +
			"            #{item.inOutNum} as inOutNum," +
			"            #{item.reservedLocation} as reservedLocation" +
			"  </foreach>) t2" +
			" set t.reserved_location = t2.reservedLocation" +
			" where t.id = t2.id</script>")
	void batchUpdateById(@Param("list") List<StoreLocationGroupDto> list);
}
