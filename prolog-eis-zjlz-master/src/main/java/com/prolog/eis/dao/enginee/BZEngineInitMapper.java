package com.prolog.eis.dao.enginee;

import com.prolog.eis.dto.enginee.*;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BZEngineInitMapper {

    @Select("select s.id           as zhanTaiId,\n" +
            "       s.is_lock      as isLock,\n" +
            "       s.pallet_max_cache_qty as maxLxCacheCount\n" +
            "  from station s\n" +
            " where s.Type = 20\n" +
            " order by s.id")
    List<ZhanTaiDto> findAllStation();

    @Select(" select s.id as zhanTaiId, count(*) as count\n" +
            "   from  CONTAINER_PATH_TASK cpt\n" +
            "  inner join STATION s on cpt.target_area=s.area_no group by s.id")
    List<ZhanTaiLXNo> findChuKuLxCount();

    @Select("select po.id as jxdId , po.station_id as zhanTaiId,po.is_all_arrive as is_all_arrived from pick_order po")
    List<JianXuanDanDto> findAllJianXuanDan();

    @Select(" select hz.id ,hz.picking_order_id as jxdId,hz.order_priority as priority  from ORDER_BILL hz\n" +
            "            inner join PICKING_ORDER po on po.id = hz.picking_order_id\n" +
            "            where hz.picking_order_id is not null and po.is_all_arrived = 0")
    List<DingDanDto> findAllDingDan();
    @Select("select conotd.container_no as containerNo,conotd.order_detail_id as orderDetailId,conotd.order_detail_qty as orderDetailQty\n" +
            "from container_order_task_detail conotd")
    List<LxOrderMxCountDto>findLxOrderMxCount();

    @Select("select bb.Picking_Order_Id as jxdId,cons.id as xkKuCunId,bb.station_id as stationId,bb.box_code as liaoXiangNo,\n" +
            "cons.goods_id as spId,cons.qty as spCount from box_binding  bb \n" +
            " left join container_store cons on bb.container_code=cons.container_no")
    List<LiaoXiangDto>findAllLx();
}
