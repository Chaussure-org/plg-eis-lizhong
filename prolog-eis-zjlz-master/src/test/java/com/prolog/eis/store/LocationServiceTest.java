package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.inventory.InventoryGoodsDto;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.inventory.dao.InventoryTaskDetailMapper;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.LocationService;
import com.prolog.eis.location.service.PathSchedulingService;
import com.prolog.framework.utils.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-16 15:40
 */
@SpringBootTest(classes = ZjlzApplication.class)
@RunWith(SpringRunner.class)
public class LocationServiceTest {

    @Autowired
    private PathSchedulingService pathSchedulingService;

    @Autowired
    private AgvLocationService agvLocationService;
    @Autowired
    private InventoryTaskDetailMapper mapper;

    @Test
    public void doTask() throws Exception {
        pathSchedulingService.containerMoveTask("1","RCS01",null);
    }

    @Test
    public void testPd(){
        List<InventoryGoodsDto> goodsId = mapper.getInventoryGoods(MapUtils.put("goodsId", 1).getMap());
        return;
    }

}
