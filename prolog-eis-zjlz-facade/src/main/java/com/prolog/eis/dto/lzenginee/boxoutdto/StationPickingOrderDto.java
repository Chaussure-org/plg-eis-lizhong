package com.prolog.eis.dto.lzenginee.boxoutdto;

import lombok.Data;

/**
 * ClassName:StationPickingOrderDto
 * Package:com.prolog.eis.dto.lzenginee.boxoutdto
 * Description:
 *
 * @date:2020/10/12 12:27
 * @author:SunPP
 */
@Data
public class StationPickingOrderDto {
    private int pickingOrderId;
    private int stationId;
    private int orderBillId;
    private String locationNo;
}
