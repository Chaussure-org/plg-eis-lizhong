package com.prolog.eis.dao.enginee;

import com.prolog.eis.dto.enginee.*;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EngineGetInitMapper {

	@Select("select mx.goods_id as spId, (sum(mx.plan_num) - sum(mx.actual_num)) as num \n" +
			"from order_mx mx \n" +
			"INNER JOIN order_hz hz on hz.id = mx.order_hz_id\n" +
			"INNER JOIN pick_order po on po.id = hz.picking_order_id GROUP BY mx.goods_id")
	List<KuCunTotalDto> findAllUsedKuCun();

	@Select("select csi.commodity_id as spId ,sum(csi.commodity_num) as num from container_sub csi\n" +
			"INNER JOIN sx_store sx on csi.container_no = sx.CONTAINER_NO\n" +
			"INNER JOIN sx_store_location sl on sl.id = sx.STORE_LOCATION_ID\n" +
			"INNER JOIN sx_store_location_group slg on slg.id = sl.store_location_group_id\n" +
			"where sx.STORE_STATE = 20 and slg.IS_LOCK = 0 and slg.ASCENT_LOCK_STATE = 0  and csi.commodity_id is not null GROUP BY csi.commodity_id")
	List<KuCunTotalDto> findAllXkKuCun();

	@Select("select csi.commodity_id as spId ,sum(csi.commodity_num) as num from container_sub csi\n" +
			"INNER JOIN zt_store zt on zt.CONTAINER_NO = csi.container_no\n" +
			"where zt.task_type = 20 GROUP BY csi.commodity_id")
	List<KuCunTotalDto> findAllztKuCun();

	@Select("select s.id as zhanTaiId ,s.is_lock as isLock ,s.is_claim as isClaim ,s.lx_max_count as maxLxCacheCount \n" +
			"from station s where s.station_task_type = 20  order by s.id")
	List<ZhanTaiDto> findAllStation();

	@Select("select cbhz.station_id as zhanTaiId ,count(*) as count from sx_store sx \r\n" +
			"inner join container_binding_hz cbhz  on cbhz.xk_store_id = sx.id group by cbhz.station_id")
	List<ZhanTaiLXNo> findChuKuLxCount();

	@Select("select zt.station_id as zhanTaiId ,count(*) as count from zt_store zt \r\n" + 
			"where zt.task_type = 20 and status in (1,2) GROUP BY zt.station_id")
	List<ZhanTaiLXNo> findArriveLxCount();

	@Select("select bmx.container_sub_no as huoGeNo, bmx.order_mx_id as dingDanMxId , SUM(bmx.binding_num) as num  from container_sub_binding_mx bmx\r\n" + 
			"where bmx.is_finish = 0 \r\n" + 
			"GROUP BY bmx.container_sub_no,bmx.order_mx_id")
	List<HuoGeDingDanDto> findHuoGeDingDan();

	@Select("select po.id as jxdId , po.station_id as zhanTaiId,po.is_all_arrive as isAllLiaoXiangArrive from pick_order po")
	List<JianXuanDanDto> findAllJianXuanDan();

	@Select("select hz.picking_order_id as jxdId ,hz.xk_store_id as xkKuCunId ,hz.zt_store_id as ztKuCunId,\r\n" + 
			"hz.station_id as stationId ,hz.container_no as liaoXiangNo\r\n" + 
			"from container_binding_hz hz INNER JOIN pick_order po on po.id = hz.picking_order_id where po.is_all_arrive = 0")
	List<LiaoXiangDto> findAllLiaoXiang();

	@Select("select si.container_sub_no as huoGeNo , si.commodity_id as spId ,si.commodity_num as spCount ,si.container_no as liaoxiang\r\n" + 
			"from container_sub si INNER JOIN container_sub_binding_mx sbm on sbm.container_sub_no = si.container_sub_no")
	List<HuoGeDto> findHuoGe();

	@Select("select hz.id ,hz.picking_order_id as jxdId,hz.expect_time as shiXiaoTime ,hz.priority as priority  from order_hz hz\r\n" +
			"inner join pick_order po on po.id = hz.picking_order_id\r\n" +
			"where hz.picking_order_id is not null and po.is_all_arrive = 0")
	List<DingDanDto> findAllDingDan();

	@Select("\n" +
			"SELECT\n" +
			"\tmx.id,\n" +
			"\tmx.order_hz_id AS orderHzId,\n" +
			"\tmx.goods_id AS spId,\n" +
			"\tmx.plan_num AS spCount,\n" +
			"\tmx.actual_num AS boZhongCount \n" +
			"FROM\n" +
			"\torder_mx mx\n" +
			"\tINNER JOIN order_hz hz ON mx.order_hz_id = hz.id\n" +
			"\tINNER JOIN pick_order po ON po.id = hz.picking_order_id \n" +
			"WHERE\n" +
			"\thz.picking_order_id IS NOT NULL \n" +
			"\tAND po.is_all_arrive = 0")
	List<DingDanMxDto> findAllDingDanMx();

	@Select("SELECT\n" +
			"\tcsbm.order_mx_id AS id,\n" +
			"\tsum( csbm.binding_num ) AS bindingCount \n" +
			"FROM\n" +
			"\tcontainer_sub_binding_mx csbm\n" +
			"\tINNER JOIN container_binding_hz hz ON hz.container_no = csbm.container_no\n" +
			"\tINNER JOIN pick_order po ON po.id = hz.picking_order_id \n" +
			"WHERE\n" +
			"\thz.picking_order_id IS NOT NULL \n" +
			"\tAND po.is_all_arrive = 0 \n" +
			"GROUP BY\n" +
			"\tcsbm.order_mx_id")
	List<DingDanMxDto> findBindingNum();
}
