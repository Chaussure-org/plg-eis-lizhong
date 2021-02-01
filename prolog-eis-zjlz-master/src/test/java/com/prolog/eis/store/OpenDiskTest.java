package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.lzenginee.OutContainerDto;
import com.prolog.eis.dto.lzenginee.boxoutdto.LayerTaskDto;
import com.prolog.eis.engin.service.BoxOutEnginService;
import com.prolog.eis.model.wcs.OpenDisk;
import com.prolog.eis.pick.service.IOrderTrayService;
import com.prolog.eis.wcs.dao.OpenDiskMapper;
import com.prolog.eis.wcs.service.IOpenDiskService;
import com.prolog.framework.dao.util.PageUtils;
import com.prolog.framework.utils.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private BoxOutEnginService boxOutEnginService;
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

    @Test
    public void testBoxOut() throws Exception {
        List<OutContainerDto> a = boxOutEnginService.outByGoodsId(7446, 100);
        System.out.println("aaa");

    }


    @Test
    public void testList(){

        LayerTaskDto layerTaskDto1 = new LayerTaskDto(1,1,0);
        LayerTaskDto layerTaskDto2 = new LayerTaskDto(2,1,0);
        LayerTaskDto layerTaskDto3 = new LayerTaskDto(3,0,1);
        LayerTaskDto layerTaskDto4 = new LayerTaskDto(2,0,1);
        List<LayerTaskDto> list1 = Arrays.asList(layerTaskDto1,layerTaskDto2);
        List<LayerTaskDto> list2 = Arrays.asList(layerTaskDto3,layerTaskDto4);
        List<LayerTaskDto> list3 = new ArrayList<>();
        list3.addAll(list1);
        list3.addAll(list2);
        List<LayerTaskDto> collect = list3.stream()
                .collect(Collectors.toMap(LayerTaskDto::getLayer, a -> a, (o1, o2) -> {
                    o1.setInCount(o1.getInCount() + o2.getInCount());
                    o1.setOutCount(o1.getOutCount() + o2.getOutCount());
                    return o1;
                })).values().stream().collect(Collectors.toList());
        System.out.println("aaaa");
    }

}
