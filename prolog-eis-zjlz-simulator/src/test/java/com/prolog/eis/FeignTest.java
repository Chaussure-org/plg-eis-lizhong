package com.prolog.eis;

import com.prolog.eis.service.impl.McsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/24 15:23
 */
@SpringBootTest(classes = SimulatorApplication.class)
@RunWith(SpringRunner.class)
public class FeignTest {

    @Autowired
    private McsServiceImpl mcsService;

    @Test
    public void testFeign(){
        mcsService.testFeign();
    }

}
