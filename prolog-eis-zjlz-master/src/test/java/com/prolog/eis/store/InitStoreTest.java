package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.store.InitStoreDto;
import com.prolog.eis.store.service.IStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testInit() throws Exception{
        //storeService.initStore(28,0,1,281,2,null,"A");
        //storeService.initStore(18,1,5,137,2,null,"B");
        InitStoreDto initStoreDto = new InitStoreDto();
        initStoreDto.setLayerCount(13);
        initStoreDto.setxStart(5);
        initStoreDto.setxCount(6);
        initStoreDto.setyCount(108);
        initStoreDto.setAscent(1);
        initStoreDto.setExList(null);
        initStoreDto.setAreaNo("C");
        storeService.initStore(initStoreDto);
        //storeService.initStore(13,5,6,108,1,null,"C");
    }
}
