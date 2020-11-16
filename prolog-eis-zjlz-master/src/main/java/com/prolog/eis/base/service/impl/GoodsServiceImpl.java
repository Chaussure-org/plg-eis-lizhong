package com.prolog.eis.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.prolog.eis.base.dao.GoodsMapper;
import com.prolog.eis.base.service.IGoodsService;
import com.prolog.eis.dto.page.GoodsInfoDto;
import com.prolog.eis.dto.page.GoodsQueryPageDto;
import com.prolog.eis.model.base.Goods;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
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
    public Goods getGoodsByGoodId(Integer goodId) {
        List<Goods> goods = goodsMapper.findByMap(MapUtils.put("id", goodId).getMap(), Goods.class);
        if (goods !=null && goods.size()>0){
            return goods.get(0);
        }
        return null;
    }

    @Override
    public void saveAndUpdateGoods(List<Goods> newGoods, List<Goods> updateGoods) {
        if (newGoods != null){
            goodsMapper.saveBatch(newGoods);
        }
        for (Goods updateGood : updateGoods) {
            goodsMapper.update(updateGood);
        }
    }

    @Override
    public Page<GoodsInfoDto> getGoodsPage(GoodsQueryPageDto queryPageDto) {
        PageHelper.startPage(queryPageDto.getPageNum(),queryPageDto.getPageSize());
        List<GoodsInfoDto> goodsPage = goodsMapper.getGoodsPage(queryPageDto);
        Page<GoodsInfoDto> page = PageUtils.getPage(goodsPage);
        return page;
    }

    @Override
    public Goods getGoodsByCode(String itemCode) throws Exception {
        return null;
    }

    @Override
    public Goods findGoodsById(int goodsId) {
        return goodsMapper.findById(goodsId, Goods.class);
    }
}
