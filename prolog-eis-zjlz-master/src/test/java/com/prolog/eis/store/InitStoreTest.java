package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.lzenginee.boxoutdto.OrderSortDto;
import com.prolog.eis.engin.service.FinishedProdOutEnginService;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.store.service.IStoreService;
import com.prolog.eis.util.CompareStrSimUtil;
import org.apache.poi.util.Internal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
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
    public void test10() {
        StringBuffer s1 = new StringBuffer().append("1@2@3");
        StringBuffer s2 = new StringBuffer().append("1@4@5@6");//3
        StringBuffer s3 = new StringBuffer().append("1@@2@3@5@6@7");//1
        StringBuffer s4 = new StringBuffer().append("1@2@4");//2
        //float ratio = CompareStrSimUtil.getSimilarityRatio(s1, s2, true);
    }

    @Test
    public void testComRepeat() {


        List<OrderSortDto> strList = new ArrayList<>(100000);
        for (int x = 0; x < 1000000; x++) {
            int count = (int) (Math.random() * 20);
            if (count == 0) {
                continue;
            }
            List<Integer> ids = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                int id = (int) (Math.random() * 100);
                if (ids.contains(id) || id == 0) {
                    continue;
                }
                ids.add(id);
            }

            Collections.sort(ids);
            OrderSortDto orderSortDto = new OrderSortDto();
            orderSortDto.setIds(ids);
            orderSortDto.setOrderBillId(x);
            //orderSortDto.setStrTest(str);
            strList.add(orderSortDto);
        }
        long start=System.currentTimeMillis();
        //商品的品种数最大的
        //大于 0 是 按照剩余排列的 交换位置
        Collections.sort(strList, new Comparator<OrderSortDto>() {
            @Override
            public int compare(OrderSortDto o1, OrderSortDto o2) {
                if (o2.getIds().size() - o1.getIds().size() == 0) {
                    return 0;
                } else {
                    return o2.getIds().size() - o1.getIds().size() > 0 ? 1 : -1;
                }
            }
        });

        int[] s1 = strList.get(0).getIds().stream().mapToInt(Integer::valueOf).toArray();
        for (OrderSortDto dto : strList) {
            int[] s2 = dto.getIds().stream().mapToInt(Integer::valueOf).toArray();
            int sameCount = 0;
            for (int i = 0; i < s1.length; i++) {
                for (int j = 0; j < s2.length; j++) {
                    if (s1[i] == s2[j]) {
                        sameCount++;
                    }
                }
            }
            float ratio = CompareStrSimUtil.getSimilarityRatio(s1, s2, true);
            dto.setLevenCount(ratio);
            dto.setSameCount(sameCount);
        }
        //先 相同数最多，然后是不同数最少
        List<OrderSortDto> sortList = strList.stream().sorted(Comparator.comparing(OrderSortDto::getSameCount, Comparator.reverseOrder()).
                thenComparing(OrderSortDto::getLevenCount)).collect(Collectors.toList());
        long end=System.currentTimeMillis();

        System.out.println("程序运行时间： "+(end-start)+"ms");
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
