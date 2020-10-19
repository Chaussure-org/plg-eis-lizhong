package com.prolog.eis.dto.lzenginee;

import lombok.Data;

/**
 * ClassName:LayerGoodsCountDto
 * Package:com.prolog.eis.dto.lzenginee
 * Description:
 *
 * @date:2020/9/29 12:13
 * @author:SunPP
 */
@Data
public class LayerGoodsCountDto {

    private int goodsId;
    private int layer;
    private int qty;
    private Double rate;
    private String containerNo;
    private String storeLocation;
    private int deptNum;

    //非查询数据
    private int outCount;
    private int inCount;


}
