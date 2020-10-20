package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.lzenginee.boxoutdto.OrderSortDto;
import com.prolog.eis.engin.service.FinishedProdOutEnginService;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.store.service.IStoreService;
import com.prolog.eis.util.CompareStrSimUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wangkang
 * @Description 初始化货位测试
 * @CreateTime 2020-10-10 12:31
 */
@SpringBootTest(classes = ZjlzApplication.class)
@RunWith(SpringRunner.class)
public class InitStoreTest {

    @Autowired
    private IStoreService storeService;
    @Autowired
    private AgvStoragelocationMapper agvStoragelocationMapper;

    @Autowired
    private FinishedProdOutEnginService finishedProdOutEnginService;

    @Test
    public void testComRepeat() {
        List<OrderSortDto> strList = new ArrayList<>();
        for (int x = 0; x < 500; x++) {

            int count = (int) (Math.random() * 20);
            if (count == 0) {
                continue;
            }
            List<Integer> ids = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                int id = (int) (Math.random() * 100);
                ids.add(id);
            }
            Collections.sort(ids);
            StringBuffer str = new StringBuffer();
            for (Integer integer :ids){
                str.append(integer+"@");
            }
            OrderSortDto orderSortDto = new OrderSortDto();
            orderSortDto.setOrderBillId(x);
            orderSortDto.setStrTest(str);
            strList.add(orderSortDto);
        }
        StringBuffer s1 = strList.get(0).getStrTest();

        for (OrderSortDto dto : strList) {
            float ratio = CompareStrSimUtil.getSimilarityRatio(s1, dto.getStrTest(), true);
            dto.setRate(ratio);
        }
        List<OrderSortDto> sortList = strList.stream().sorted(Comparator.comparing(OrderSortDto::getRate).reversed()).collect(Collectors.toList());
    }

    //@Test
    public void testInit() throws Exception {
        //storeService.initStore(28,0,1,281,2,null,"A");
        //storeService.initStore(18,1,5,137,2,null,"B");
//        InitStoreDto initStoreDto = new InitStoreDto();
//        initStoreDto.setLayerCount(13);
//        initStoreDto.setxStart(5);
//        initStoreDto.setxCount(6);
//        initStoreDto.setyCount(108);
//        initStoreDto.setAscent(1);
//        initStoreDto.setExList(null);
//        initStoreDto.setAreaNo("C");
//        storeService.initStore(initStoreDto);
        //storeService.initStore(13,5,6,108,1,null,"C");
    }

    // @Test
    public void testComputeStore() {
//        Map<Integer, Integer> canBeUsedStore = finishedProdOutEnginService.getCanBeUsedStore();
//        System.out.println(canBeUsedStore);
    }


}
