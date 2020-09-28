package com.prolog.eis.dao.enginee;

import com.prolog.eis.dto.enginee.AllHuoWeiDto;
import com.prolog.eis.dto.enginee.CengLxTaskDto;
import com.prolog.eis.dto.enginee.OutStoreHuoWeiDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface EngineOutboundMapper {

	@Select("select sl.layer as ceng ,count(*) as taskcount from sx_store sx " +
			"inner join sx_store_location sl on sl.id = sx.store_location_id " +
			"where sx.store_state = 30 " +
			"group by sl.layer")
	List<CengLxTaskDto> findCkCengLxTaskDtos();

	@Select("select sl.layer as ceng ,count(*) as taskcount from sx_store sx  " +
			"inner join sx_store_location sl on sl.id = sx.store_location_id " +
			"where sx.store_state = 10 " +
			"group by sl.layer")
	List<CengLxTaskDto> findRkCengLxTaskDtos();

	@Select("select sl.layer as ceng ,count(*) as lxcount from sx_store sx  " +
			"inner join container_sub si on si.container_no = sx.container_no " +
			"inner join sx_store_location sl on sl.id = sx.store_location_id " +
			"inner join sx_store_location_group slg on slg.id = sl.store_location_group_id " +
			"where sx.store_state = 20 and si.commodity_id  = #{spId} and si.sp_disable = 0 and si.commodity_num > 0 " +
			"and slg.ascent_lock_state = 0 and slg.is_lock = 0 and slg.ready_out_lock = 0 " +
			"and sl.id not in (select ssr2.location_child_id from sx_store_location_relation ssr2 where ssr2.location_lock = 1) " +
			"group by sl.layer")
	List<AllHuoWeiDto> findAllHuoWeiDto(@Param("spId")int spId);


    @Select("select sl.layer as ceng ,count(*) as taskcount from sx_store sx  " +
            "inner join sx_store_location sl on sl.id = sx.store_location_id " +
            "where sl.layer = #{ceng} and (sx.store_state = 30 or sx.store_state = 10 or sx.store_state = 40)")
    CengLxTaskDto findTotalTaskByCeng(@Param("ceng")int ceng);

    @Select("select sl.layer as ceng ,count(*) as taskcount from sx_store sx  " +
            "inner join sx_store_location sl on sl.id = sx.store_location_id " +
            "where sx.store_state = 30 or sx.store_state = 10 or sx.store_state = 40 group by sl.layer")
    List<CengLxTaskDto> findTotalTask();

	@Select("SELECT\n" +
			"sx.id AS storeid,\n" +
			"sx.container_no AS containerno,\n" +
			"sx.store_location_id AS huoweiid,\n" +
			"sl.x AS x,\n" +
			"sl.y AS y,\n" +
			"si.expiry_date AS expiry\n" +
			"FROM\n" +
			"sx_store sx\n" +
			"INNER JOIN container_sub si ON si.container_no = sx.container_no\n" +
			"INNER JOIN sx_store_location sl ON sl.id = sx.store_location_id\n" +
			"INNER JOIN sx_store_location_group slg ON slg.id = sl.store_location_group_id\n" +
			"WHERE\n" +
			"sx.store_state = 20\n" +
			"AND si.commodity_id = #{spId} AND si.commodity_num > 0 AND sl.layer = #{ceng}\n" +
			"AND slg.ascent_lock_state = 0\n" +
			"AND slg.is_lock = 0\n" +
			"AND slg.ready_out_lock = 0\n" +
			"AND sl.id NOT IN (\n" +
			"SELECT\n" +
			"location_child_id\n" +
			"FROM\n" +
			"sx_store_location_relation\n" +
			"WHERE\n" +
			"location_lock = 1\n" +
			")")
	List<OutStoreHuoWeiDto> findAllOutboundCargoBox(@Param("spId")Integer spId, @Param("ceng")int ceng);

}
