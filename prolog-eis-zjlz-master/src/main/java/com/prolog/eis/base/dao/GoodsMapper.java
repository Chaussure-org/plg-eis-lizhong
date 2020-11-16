package com.prolog.eis.base.dao;

import com.prolog.eis.dto.page.GoodsInfoDto;
import com.prolog.eis.dto.page.GoodsQueryPageDto;
import com.prolog.eis.model.base.Goods;
import com.prolog.framework.dao.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 11:13
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 商品资料分页查询
     * @param queryPageDto
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "\tg.id AS goodsId,\n" +
            "\tg.goods_no AS goodsNo,\n" +
            "\tg.goods_name AS goodsName,\n" +
            "\tg.goods_one_type AS goodsOneType,\n" +
            "\tg.owner_drawn_no AS ownerDrawnNo,\n" +
            "\tg.surface_deal AS surfaceDeal,\n" +
            "\tg.goods_type AS goodsType,\n" +
            "\tg.weight AS weight,\n" +
            "\tg.package_number AS packageNumber,\n" +
            "\tg.past_label_flg AS pastLabelFlg,\n" +
            "\tg.create_time AS createTime,\n" +
            "\tg.update_time AS updateTime \n" +
            "FROM\n" +
            "\tgoods g \n" +
            "WHERE 1=1\n" +
            "<if test = 'queryPageDto.goodsOneType != null and queryPageDto.goodsOneType != \"\"'>\n" +
            "and g.goods_one_type like (concat('%',#{queryPageDto.goodsOneType},'%')\n" +
            "</if>\n" +
            "<if test = 'queryPageDto.goodsId != null and queryPageDto.goodsId != \"\"'>\n" +
            "and g.id like concat('%',#{queryPageDto.goodsId},'%')\n" +
            "</if>\n" +
            "<if test = 'queryPageDto.goodsName != null and queryPageDto.goodsName != \"\"'>\n" +
            "and g.goods_name like concat('%',#{queryPageDto.goodsName},'%')\n" +
            "</if>\n" +
            "<if test = 'queryPageDto.ownerDrawnNo != null and queryPageDto.ownerDrawnNo != \"\"'>\n" +
            "and g.owner_drawn_no like concat('%',#{queryPageDto.ownerDrawnNo},'%')\n" +
            "</if>\n" +
            "<if test = 'queryPageDto.goodsType != null and queryPageDto.goodsType != \"\"'>\n" +
            "and g.goods_type like concat('%',#{queryPageDto.goodsType},'%')\n" +
            "</if>\n" +
            "<if test = 'queryPageDto.pastLabelFlg != null and queryPageDto.pastLabelFlg != \"\" '>\n" +
            "\tand g.past_label_flg = #{queryPageDto.pastLabelFlg}\n" +
            "</if>\n" +
            "order by g.id asc" +
            "</script>")
    List<GoodsInfoDto> getGoodsPage(@Param("queryPageDto") GoodsQueryPageDto queryPageDto);
}
