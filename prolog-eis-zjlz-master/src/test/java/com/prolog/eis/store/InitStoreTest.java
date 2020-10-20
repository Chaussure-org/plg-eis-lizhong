package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.store.InitStoreDto;
import com.prolog.eis.engin.service.FinishedProdOutEnginService;
import com.prolog.eis.engin.service.impl.FinishedProdOutEnginServiceImpl;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.store.service.IStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    @Test
    public void testComputeStore() {
//        Map<Integer, Integer> canBeUsedStore = finishedProdOutEnginService.getCanBeUsedStore();
//        System.out.println(canBeUsedStore);
    }

    @Test
    public void testRepeat() {
        List<String> strList = new ArrayList<>();

        for (int x = 0; x < 500; x++) {
            String str = new String();
            for (int i = 0; i < (int) Math.random() * 20; i++) {
                int id = (int) Math.random() * 10000;
                str += id;
            }
            strList.add(str);
        }
    }
}
