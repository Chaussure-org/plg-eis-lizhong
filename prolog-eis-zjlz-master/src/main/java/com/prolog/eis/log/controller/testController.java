package com.prolog.eis.log.controller;

import com.prolog.eis.dto.lzenginee.boxoutdto.OrderSortDto;
import com.prolog.eis.util.CompareStrSimUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:testController
 * Package:com.prolog.eis.log.controller
 * Description:
 *
 * @date:2020/10/20 19:58
 * @author:SunPP
 */
@RestController
@RequestMapping("test")
public class testController {

    @RequestMapping("repeat")
    public void testRepeat(){
        List<OrderSortDto> strList = new ArrayList<>();
        for (int x = 0; x < 500; x++) {
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < (int) Math.random() * 20; i++) {
                int id = (int) Math.random() * 10000;
                str.append(id+"@");
            }
            OrderSortDto orderSortDto=new OrderSortDto();
            orderSortDto.setOrderBillId(x);
            orderSortDto.setStrTest(str);
            strList.add(orderSortDto);
        }
        StringBuffer s1 = strList.get(0).getStrTest();

        for (OrderSortDto dto:strList){
            float ratio = CompareStrSimUtil.getSimilarityRatio(s1, dto.getStrTest(), true);
            dto.setRate(ratio);
        }
        List<OrderSortDto> sortList = strList.stream().sorted(Comparator.comparing(OrderSortDto::getRate).reversed()).collect(Collectors.toList());
    }


}
