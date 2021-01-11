package com.prolog.eis.store.dao;

import com.prolog.eis.dto.lzenginee.LayerGoodsCountDto;
import com.prolog.eis.dto.store.AgvContainerStoreDto;
import com.prolog.eis.dto.store.ContainerInfoDto;
import com.prolog.eis.dto.page.ContainerQueryDto;
import com.prolog.eis.dto.store.ContainerStoreInfoDto;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.util.mapper.EisBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    @Select("select cs.id as id,\n" +
            "cs.container_no as containerNo,\n" +
            "cs.container_type as containerType,\n" +
            "cs.task_type as taskType,\n" +
            "cs.work_count as workCount,\n" +
            "cs.owner_id as ownerId,\n" +
            "cs.goods_id AS goodsId,\n" +
            "cs.lot_id as lotId,\n" +
            "cs.goods_order_no as goodsOrderNo,\n" +
            "cs.qty as qty,\n" +
            "cs.create_time as createTime,\n" +
            "cs.update_time as updateTime\n" +
            "\n" +
            "from container_store cs \n" +
            "\t\t\tjoin container_path_task cpt on cs.container_no = cpt.container_no \n" +
            "\t\t\tjoin sx_store_location sl on cpt.source_location = sl.id \n" +
            "\t\t\twhere cs.task_type=0 and cs.goods_id = #{goodsId}\n" +
            "\t\t\torder by sl.y,cs.qty asc")
    List<ContainerStore> findBestContainerSeq(@Param("goodsId") Integer goodsId);

    @Update("UPDATE container_store c set c.task_type=#{taskType},c.task_status=#{taskStatus} WHERE FIND_IN_SET(c.container_no,#{strContainers})")
    void updateContainerStatus(@Param("strContainers") String strContainers, @Param("taskType") int taskType, @Param("taskStatus") int taskStatus);

    /*@Select("SELECT LEFT(cpt.source_location,2) AS layer ,COUNT(*) as outCount from container_store c LEFT JOIN container_path_task cpt on c.container_no=cpt.container_no\n" +
            "WHERE  FIND_IN_SET(c.task_type,'20,21,22,23') AND cpt.source_area='SAS01' GROUP BY LEFT(cpt.source_location,2)")*/
    @Select("SELECT LEFT\n" +
            "\t( d.next_location, 2 ) AS layer,\n" +
            "\tCOUNT(*) AS outCount \n" +
            "FROM\n" +
            "\tcontainer_store c\n" +
            "\tLEFT JOIN container_path_task_detail d ON c.container_no = d.container_no \n" +
            "WHERE\n" +
            "\tFIND_IN_SET( c.task_type, '10' ) \n" +
            "\tAND d.next_area = 'SAS01' \n" +
            "GROUP BY\n" +
            "\tLEFT (\n" +
            "\td.next_location,\n" +
            "\t2);")
    List<LayerGoodsCountDto> findOutContainers();

    @Select("select container_no as containerNo from container_store")
    List<String> findAllStoreContainers();


    /**
     * 根据条件查询容器资料
     * @param containerDto
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "\tcs.container_no AS containerNo,\n" +
            "\tg.goods_name AS goodsName,\n" +
            "\tg.owner_drawn_no AS ownerDrawnNo,\n" +
            "\tcs.lot_id AS lotId,\n" +
            "\tcs.create_time as createTime,\n" +
            "\tcs.qty AS qty \n" +
            "FROM\n" +
            "\tcontainer_store cs\n" +
            "\tJOIN goods g ON g.id = cs.goods_id\n" +
            "\twhere 1 = 1\n" +
            "<if test = 'containerDto.containerNo != null and containerDto.containerNo != \"\"'>\n" +
            "\t\tand cs.container_no like '%${containerDto.containerNo}%'\n" +
            "</if>\n" +
            "<if test = 'containerDto.goodsName != null and containerDto.goodsName != \"\"'>\n" +
            "\t\tand g.goods_name like '%${containerDto.goodsName}%'\n" +
            "</if>\n" +
            "<if test = 'containerDto.ownerDrawnNo != null and containerDto.ownerDrawnNo != \"\"'>\n" +
            "\t\tand g.owner_drawn_no like '%${containerDto.ownerDrawnNo}%'\n" +
            "</if>\n" +
            "<if test = 'containerDto.lotId != null and containerDto.lotId != \"\"'>\n" +
            "\t\tand cs.lot_id like '%${containerDto.lotId}%'\n" +
            "</if>\n" +
            "<if test = 'containerDto.startTime != null '>\n" +
            "\t\tand cs.create_time >= #{containerDto.startTime}\n" +
            "</if>\n" +
            "<if test = 'containerDto.endTime != null '>\n" +
            "\t\tand cs.create_time &lt;= #{containerDto.endTime}\n" +
            "</if>" +
            "order by cs.container_no asc " +
            "</script>")
    List<ContainerInfoDto> queryContainer(@Param("containerDto") ContainerQueryDto containerDto);
}
