package com.prolog.eis.dto.lzenginee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName:OutContainerDto
 * Package:com.prolog.eis.dto.lzenginee
 * Description:
 *
 * @date:2020/9/30 10:42
 * @author:SunPP
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutContainerDto {
    private String containerNo;
    private String storeLocation;
    private int goodsId;
    private int qty;
    //明细id 数量
    private List<OutDetailDto> detailList=new ArrayList<>();
}
