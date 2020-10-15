package com.prolog.eis.store.dao;

import com.prolog.eis.dto.store.AgvContainerStoreDto;
import com.prolog.eis.dto.store.ContainerStoreInfoDto;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerStoreMapper extends EisBaseMapper<ContainerStore> {

	@Select("select s.container_no as containerNo,\n"
			+ "       case\n"
			+ "         when s.task_type = 0 and p.task_type = 0 then\n"
			+ "          1\n"
			+ "         else\n"
			+ "          2\n"
			+ "       end as containerState,\n"
			+ "       g.owner_id as ownerId,\n"
			+ "       g.goods_code as itemId,\n"
			+ "       g.lot_id as lotId,\n"
			+ "       g.goods_order_no as itemOrderNo,\n"
			+ "       g.chinese_name as chineseName,\n"
			+ "       s.qty as qty,\n"
			+ "       sg.abc_type as abcType\n"
			+ "  from container_store s\n"
			+ "  left join goods_info g\n"
			+ "    on s.goods_id = g.id\n"
			+ "  left join container_store_goods sg\n"
			+ "    on s.container_no = sg.container_no\n"
			+ "  left join container_path_task p\n"
			+ "    on s.container_no = p.container_no\n"
			+ "  left join store_area a\n"
			+ "    on p.target_area = a.area_no\n"
			+ " where a.area_type = 10\n"
			+ "   and a.device_system = 'RCS'\n")
	List<AgvContainerStoreDto> getAgvContainerStore();

	@Select("<script>"
			+ "select s.container_no   as containerNo,\n"
			+ "       s.container_type as containerType,\n"
			+ "       s.task_type      as taskType,\n"
			+ "       s.work_count     as workCount,\n"
			+ "       g.goods_code     as goodsCode,\n"
			+ "       g.owner_id       as ownerId,\n"
			+ "       g.lot_id         as lotId,\n"
			+ "       g.goods_order_no as goodsOrderNo,\n"
			+ "       g.goods_barcode  as goodsBarcode,\n"
			+ "       g.chinese_name   as chineseName,\n"
			+ "       s.qty            as qty,\n"
			+ "       sg.case_specs    as caseSpecs,\n"
			+ "       sg.box_specs     as boxSpecs,\n"
			+ "       sg.abc_type      as abcType,\n"
			+ "       p.target_area    as targetArea,\n"
			+ "       p.pallet_no      as palletNo,\n"
			+ "       p.actual_height  as actualHeight\n"
			+ "  from container_store s\n"
			+ "  left join goods_info g\n"
			+ "    on s.goods_id = g.id\n"
			+ "  left join container_store_goods sg\n"
			+ "    on s.container_no = sg.container_no\n"
			+ "  left join container_path_task p\n"
			+ "    on s.container_no = p.container_no\n"
			+ " where 1 = 1\n"
			+ "<if test='containerNo != null and productId !=\"\"'>"
			+ "   and s.container_no like '%${containerNo}%'\n"
			+ "</if>"
			+ "</script>")
	List<ContainerStoreInfoDto> getContainerStoreInfo(@Param("containerNo") String containerNo);

	@Update("update container_store set container_type = -1, goods_id = null, qty = 0 where container_no = #{containerNo}")
	void setContainerStoreEmpty(@Param("containerNo") String containerNo);


	@Select("select * from container_store cs\n" +
			"join container_path_task cpt on cs.container_no = cpt.container_no\n" +
			"join sx_store_location sl on cpt.source_location = sl.id\n" +
			"where cs.task_type=0 #and cs.goods_id = #{goodsId}\n" +
			"order by sl.y,cs.qty asc")
    List<ContainerStore> findBestContainerSeq(@Param("goodsId") Integer goodsId);
}
