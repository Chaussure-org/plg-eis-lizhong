package com.prolog.eis.base.service;

import com.prolog.eis.dto.page.GoodsInfoDto;
import com.prolog.eis.dto.page.GoodsQueryPageDto;
import com.prolog.eis.model.base.Goods;
import com.prolog.framework.core.pojo.Page;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 11:12
 */
public interface IGoodsService {
    Goods getGoodsByCode(String itemCode) throws Exception;

    /**
     * 根据id查询
     * @param goodsId
     * @return
     */
    Goods findGoodsById(int goodsId);

    /**
     * 根据商品id查商品
     * @param goodId 商品id
     * @return
     */
    Goods getGoodsByGoodId(Integer goodId);

    /**
     * 根据list进行添加和修改商品
     * @param newGoods
     * @param updateGoods
     */
    void saveAndUpdateGoods(List<Goods> newGoods, List<Goods> updateGoods);

    /**
     * 分页查询商品
     * @param queryPageDto
     * @return
     */
    Page<GoodsInfoDto> getGoodsPage(GoodsQueryPageDto queryPageDto);
}
