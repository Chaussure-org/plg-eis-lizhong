package com.prolog.eis.dto.lzenginee;

import lombok.Data;

/**
 * ClassName:RoadWayGoodsCountDto
 * Package:com.prolog.eis.dto.lzenginee
 * Description:
 *
 * @date:2020/9/29 14:10
 * @author:SunPP
 */
@Data
public class RoadWayGoodsCountDto {
    private int detailId;
    private int goodsId;
    private int roadWay;
    private int qty;
    private Double rate;
    private String ContainerNo;

    private int taskCount;
    private int deptNum;
    private String storeLocation;
}
