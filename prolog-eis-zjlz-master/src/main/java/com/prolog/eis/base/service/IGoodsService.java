package com.prolog.eis.base.service;

import com.prolog.eis.model.base.Goods;

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
}
