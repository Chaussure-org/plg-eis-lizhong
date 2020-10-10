package com.prolog.eis.dto.lzenginee.boxoutdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:OrderSortDto
 * Package:com.prolog.eis.dto.lzenginee.boxoutdto
 * Description:
 *
 * @date:2020/10/9 17:24
 * @author:SunPP
 */
@Data
@NoArgsConstructor                 //无参构造
@AllArgsConstructor                //有参构造
public class OrderSortDto {
    private Integer orderBillId;
    private float rate;
}
