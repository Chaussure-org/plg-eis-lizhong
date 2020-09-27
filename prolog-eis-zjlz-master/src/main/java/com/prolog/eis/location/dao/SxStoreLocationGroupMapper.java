package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.InStoreLocationGroupDto;
import com.prolog.eis.model.location.sxk.SxStoreLocationGroup;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SxStoreLocationGroupMapper extends EisBaseMapper<SxStoreLocationGroup> {

	@Select("select slg.id as storeLocationGroupId,count(s.id) as containerCount,slg.IN_OUT_NUM as inOutNum ,slg.x,slg.y,slg.reserved_location as reservedLocation,slg.entrance1_property1 as entrance1Property1 ,slg.entrance1_property2 as entrance1Property2 ,slg.entrance2_property1 as entrance2Property1 ,slg.entrance2_property2 as  entrance2Property2  \r\n" + 
			"from sx_store_location sl \r\n" + 
			"left join sx_store_location_group slg on slg.id = sl.STORE_LOCATION_GROUP_ID \r\n" + 
			"left join container_path_task s on s.source_area = sl.area_no and s.source_location = sl.store_no \r\n" + 
			"where slg.IS_LOCK = 0 and slg.ASCENT_LOCK_STATE = 0 \r\n" + 
			"and slg.location_num > (select count(*) from sx_store_location sl inner join container_path_task s on \r\n" + 
			"s.source_location = sl.store_no where sl.STORE_LOCATION_GROUP_ID = slg.ID) \r\n" + 
			"and sl.area_no = #{area} and EXISTS (select sl2.id from sx_store_location sl2 where sl2.limit_weight > #{weight} and sl2.STORE_LOCATION_GROUP_ID = slg.ID and sl2.is_inBound_location = 1) \r\n" + 
			"GROUP BY slg.ID ,slg.IN_OUT_NUM,slg.IN_OUT_NUM,slg.x,slg.y,slg.reserved_location,slg.entrance1_property1,slg.entrance1_property2,slg.entrance2_property1,slg.entrance2_property2")
	List<InStoreLocationGroupDto> findStoreLocationGroupByArea(@Param("area") String area, @Param("weight") Double weight);

	@Select("select g.ID\r\n" +
			"from sx_store_location_group g\r\n" +
			"left join sx_store_location l on g.id = l.store_location_group_id\r\n" +
			"left join container_path_task_detail s on s.source_area = l.area_no and s.source_location = l.store_no and s.task_state < #{startTaskState}\r\n" +
			"left join container_path_task_detail ts on ts.next_area = l.area_no and ts.next_location = l.store_no\r\n" +
			"where g.id = #{groupId} and g.ASCENT_LOCK_STATE = 1 and (s.id is not null or ts.id is not null)")
	Integer checkGroupLock(@Param("groupId") int groupId, @Param("startTaskState") int startTaskState);
}
