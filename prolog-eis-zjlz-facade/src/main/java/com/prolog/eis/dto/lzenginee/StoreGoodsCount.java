package com.prolog.eis.dto.lzenginee;

import lombok.Data;

/**
 * ClassName:StoreGoodsCount
 * Package:com.prolog.eis.dto.lzenginee
 * Description:
 *
 * @date:2020/9/28 11:21
 * @author:SunPP
 */
@Data
public class StoreGoodsCount {
    /**
     * 商品id
     */
    private int goodsId;
    /**
     * 路径任务类型
     */
    private int taskType;
    /**
     * 商品库存数量
     */
    private int qty;

    /**
     *起始区域
     */
    private String sourceArea;
}
