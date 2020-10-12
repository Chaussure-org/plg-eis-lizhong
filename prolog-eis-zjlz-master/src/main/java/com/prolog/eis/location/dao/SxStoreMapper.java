package com.prolog.eis.location.dao;

import com.prolog.eis.dto.location.sxk.SxStoreGroupDto;
import com.prolog.eis.dto.location.sxk.SxStoreLockDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SxStoreMapper {

	/**
     * 查询锁定状态
     * @param containerNo
     * @return
     */
    @Select("select \r\n" + 
    		"ss.id as storeId,\r\n" + 
    		"sl.id as locationId,\r\n" + 
    		"sl.ascent_lock_state as ascentLockState, \r\n" + 
    		"sslp.ascent_lock_state as ascentGroupLockState, \r\n" + 
    		"sslp.is_lock as isLock, \r\n" + 
    		"sl.dept_num as deptNum, \r\n" +
    		"sl.layer, \r\n" + 
    		"sl.x, \r\n" + 
    		"sl.y \r\n" + 
    		"from container_path_task ss\r\n" + 
    		"left join sx_store_location sl on \r\n" + 
    		"sl.store_no = ss.source_location and sl.area_no = ss.source_area\r\n" + 
    		"left join sx_store_location_group sslp on \r\n" + 
    		"sl.store_location_group_id = sslp.id\r\n" + 
    		"where ss.container_no = #{containerNo}")
    SxStoreLockDto findSxStoreLock(@Param("containerNo") String containerNo);
    
    
    @Select("select\r\n" + 
    		"	sl.store_no as storeNo,\r\n" + 
    		"	sl.ascent_lock_state as ascentLockState,\r\n" + 
    		"	sl.dept_num as deptNum,\r\n" + 
    		"	sl.layer,\r\n" + 
    		"	sl.location_index as locationIndex,\r\n" + 
    		"	sl.store_location_group_id as storeLocationGroupId,\r\n" + 
    		"	sl.store_location_id1 as storeLocationId1,\r\n" + 
    		"	sl.store_location_id2 as storeLocationId2,\r\n" + 
    		"	sl.x,\r\n" + 
    		"	sl.y,\r\n" + 
    		"	ss.container_no as containerNo,\r\n" + 
    		"	cs.owner_id as ownerId,\r\n" + 
    		"	cs.goods_id as goodsId,\r\n" + 
    		"	cs.lot_id as lotId,\r\n" + 
    		"	cs.goods_order_no as goodsOrderNo,\r\n" + 
    		"	ss.task_type as taskType,\r\n" + 
    		"	ss.id as sotreId\r\n" + 
    		"from\r\n" + 
    		"	sx_store_location sl\r\n" + 
    		"left join container_path_task ss on\r\n" + 
    		"	sl.store_no = ss.source_location and sl.area_no = ss.source_area and ss.task_state <= 20\r\n" + 
    		"left join container_store cs on ss.container_no = cs.container_no\r\n" + 
    		"where\r\n" + 
    		"	sl.store_location_group_id = #{groupId}")
    List<SxStoreGroupDto> findStoreGroup(@Param("groupId") int groupId);
}
