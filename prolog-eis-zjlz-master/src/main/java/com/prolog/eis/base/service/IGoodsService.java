package com.prolog.eis.base.service;

import com.prolog.eis.model.base.Goods;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 11:12
 */
public interface IGoodsService {

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
}
