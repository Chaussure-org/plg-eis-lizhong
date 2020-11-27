package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.model.wcs.OpenDisk;
import com.prolog.eis.pick.service.IOrderTrayService;
import com.prolog.eis.wcs.dao.OpenDiskMapper;
import com.prolog.eis.wcs.service.IOpenDiskService;
import com.prolog.framework.utils.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/18 16:52
 */
@SpringBootTest(classes = ZjlzApplication.class)
@RunWith(SpringRunner.class)
public class OpenDiskTest {
    @Autowired
    private IOrderTrayService orderTrayService;

    @Autowired
    private IOpenDiskService openDiskService;
    @Test
    public void testOrderTray() throws Exception {
        orderTrayService.requestOrderTray();

    }
    @Test
    public void testOpen(){
        List<OpenDisk> openDisks = openDiskService.findOpenDiskByMap(MapUtils.put("openDiskId", OpenDisk.OPEN_DISK_OUT).put("taskStatus", OpenDisk.TASK_STATUS_ARRIVE).getMap());
        if (openDisks.size() == 0){
            return;
        }
        OpenDisk openDisk = openDisks.get(0);
        openDisk.setTaskStatus(OpenDisk.TASK_STATUS_NOT);
        openDiskService.updateOpenDisk(openDisk);
    }

    @Test
    public void testIron() throws Exception {
        orderTrayService.requestIronTray();
    }

    public static void main(String[] args) {
        
    }
}
