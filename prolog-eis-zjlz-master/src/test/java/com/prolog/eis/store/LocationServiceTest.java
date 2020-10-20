package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.store.StationTrayDTO;
import com.prolog.eis.location.service.AgvLocationService;
import com.prolog.eis.location.service.LocationService;
import com.prolog.eis.location.service.PathSchedulingService;
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

    @Test
    public void doTask() throws Exception {
        pathSchedulingService.containerMoveTask("1","RCS01",null);
    }
    @Test
    public void testTrayTask(){
        System.out.println("aaaa");
        List<Integer> list = Arrays.asList(1,2,3);
        List<StationTrayDTO> stations = agvLocationService.findTrayTaskStation(list);
        return;
    }
}
