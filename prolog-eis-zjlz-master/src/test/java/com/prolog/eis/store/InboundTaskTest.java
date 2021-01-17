package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.location.service.SxMoveStoreService;
import com.prolog.eis.location.service.SxkLocationService;
import com.prolog.eis.model.GoodsInfo;
import com.prolog.eis.model.store.SxStoreLocation;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.EisStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author dengj
 * @Date 2021/1/14 14:37
 * @Version 1.0
 */
@SpringBootTest(classes = ZjlzApplication.class)
@RunWith(SpringRunner.class)
public class InboundTaskTest {

    @Autowired
    private IContainerStoreService containerStoreService;

    @Autowired
    private SxkLocationService sxkLocationService;

    @Test
    public void testInbound() throws Exception {
        GoodsInfo goodsInfo =
                containerStoreService.findContainerStockInfo("C-002615");
        //获取商品id
        String taskProperty1 = null;
        String taskProperty2 = null;
        if (goodsInfo != null) {
            taskProperty1 = containerStoreService.buildTaskProperty1(goodsInfo);
            taskProperty2 = containerStoreService.buildTaskProperty2(goodsInfo);
        }
        long start = System.currentTimeMillis();
        SxStoreLocation targetSxStoreLocation =
                sxkLocationService.findLoacationByArea("MCS04",
                        0, 0, 0, 0, taskProperty1, taskProperty2);
        long cost = (System.currentTimeMillis() - start) ;
        System.out.println("------------------分配货位耗时" + cost + "毫秒------------------------------");

    }


    @Test
    public void testSpsStore(){
        String mcsPoint = EisStringUtils.getMcsPoint("0800380019");
        System.out.println(mcsPoint);
    }
}
