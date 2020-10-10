package com.prolog.eis.dto.lzenginee;

import lombok.Data;

/**
 * ClassName:OutContainerDto
 * Package:com.prolog.eis.dto.lzenginee
 * Description:
 *
 * @date:2020/9/30 10:42
 * @author:SunPP
 */
@Data
public class OutContainerDto {
    private String containerNo;
    private String storeLocation;
    private int goodsId;
    private int qty;
}
