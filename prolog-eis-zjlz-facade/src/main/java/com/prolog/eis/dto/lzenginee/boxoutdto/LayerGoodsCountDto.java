package com.prolog.eis.dto.lzenginee.boxoutdto;

import lombok.Data;

/**
 * ClassName:LayerGoodsCountDto
 * Package:com.prolog.eis.dto.lzenginee.boxoutdto
 * Description:
 *
 * @date:2020/10/10 9:49
 * @author:SunPP
 */
@Data
public class LayerGoodsCountDto {
    private int detailId;
    private int goodsId;
    private int layer;
    private int qty;
    private Double rate;
    private String containerNo;

    private int taskCount;
    private int deptNum;
    private String storeLocation;
}
