package com.prolog.eis.base.service.impl;

import com.prolog.eis.base.dao.GoodsMapper;
import com.prolog.eis.base.service.IGoodsService;
import com.prolog.eis.model.base.Goods;
import com.prolog.framework.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-27 11:12
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Goods getGoodsByCode(String itemCode) throws Exception {
        List<Goods> goodsList = goodsMapper.findByMap(MapUtils.put("goodsNo", itemCode).getMap(), Goods.class);
        if (goodsList.size()== 0) {
            throw new Exception("没有找到"+itemCode+"该条码对应的商品信息,请确认");
        }
        return goodsList.get(0);
    }
}
