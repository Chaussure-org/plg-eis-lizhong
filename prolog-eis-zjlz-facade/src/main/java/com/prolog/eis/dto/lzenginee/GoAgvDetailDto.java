package com.prolog.eis.dto.lzenginee;

import lombok.Data;

/**
 * ClassName:GoAgvDetailDto
 * Package:com.prolog.eis.dto.lzenginee
 * Description:
 *
 * @date:2020/9/29 17:16
 * @author:SunPP
 */
@Data
public class GoAgvDetailDto {
    private int orderBillId;
    private int orderPriority;
    private int detailId;
    private int goodsId;
    private int qty;
}
