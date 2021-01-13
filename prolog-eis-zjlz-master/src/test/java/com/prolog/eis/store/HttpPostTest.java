package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.inventory.InventoryOutDto;
import com.prolog.eis.dto.rcs.RcsTaskDto;
import com.prolog.eis.dto.wms.WmsGoodsDto;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.rcs.service.IRcsService;
import com.prolog.eis.store.dao.StoreLocationMapper;
import com.prolog.eis.util.PrologApiJsonHelper;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.framework.core.restriction.Criteria;
import com.prolog.framework.core.restriction.Restrictions;
import com.prolog.framework.utils.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/19 11:47
 */
@SpringBootTest(classes = ZjlzApplication.class)
@RunWith(SpringRunner.class)
public class HttpPostTest {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AgvStoragelocationMapper agvStoragelocationMapper;

    @Autowired
    private StoreLocationMapper storeLocationMapper;

    @Autowired
    private IRcsService rcsService;

    @Test
    public void mcsMove() {
        List<AgvStoragelocation> list = agvStoragelocationMapper.findByMap(MapUtils.put("areaNo", "RCS01")
                .put("taskLock", 0).getMap(), AgvStoragelocation.class);

        int s = (int) (Math.random() * 100);
        AgvStoragelocation start = list.get(s);
        int e = (int) (Math.random() * 100);
        AgvStoragelocation end = list.get(e);
        RcsTaskDto rcsTaskDto = new RcsTaskDto();
        String id = PrologStringUtils.newGUID();
        rcsTaskDto.setTaskType("F01");
        rcsTaskDto.setPriority("99");
        rcsTaskDto.setReqCode(id);
        rcsTaskDto.setContainerNo(id.substring(1, 6));
        rcsTaskDto.setStartPosition(start.getLocationNo());
        rcsTaskDto.setEndPosition(end.getLocationNo());
        //发送 agv 任务
        rcsService.sendTask(rcsTaskDto);
        //锁定货位
        Criteria ctr = Criteria.forClass(AgvStoragelocation.class);
        ctr.setRestriction(Restrictions.eq("locationNo", end.getLocationNo()));
        agvStoragelocationMapper.updateMapByCriteria(MapUtils.put("taskLock", 1).getMap(), ctr);
    }

    @Test
    public void testRCS() {
        List<String> locations = storeLocationMapper.find();
        for (String str : locations) {
            String x = str.substring(1, 6);
            String y = str.substring(10, 15);
            // storeLocationMapper.testSave(str,x,y);
        }


    }

    //@Test
    public void testSend() {
        String url = "http://192.168.41.191:20310/wms/goods/sync";
        WmsGoodsDto wmsGoodsDto = new WmsGoodsDto();
        wmsGoodsDto.setITEMID("11111111111111");
        wmsGoodsDto.setGATEGORYID("aaaaaaaaaaa");
        wmsGoodsDto.setWEIGHT(1.111);
        wmsGoodsDto.setJZS(1.000);
        wmsGoodsDto.setITEMBARCODE("111111");
        String s = restTemplate.postForObject(url, "[\n" +
                "  {\n" +
                "    \"jzs\": 1.0,\n" +
                "    \"itembarcode\": \"0\",\n" +
                "    \"itemtype\": \"1\",\n" +
                "    \"weight\": 1.11,\n" +
                "    \"itemid\": \"111\",\n" +
                "    \"itemname\": \"底盘件计划部\"\n" +
                "  }\n" +
                "]", String.class);
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(s);
        Boolean ret = helper.getBoolean("success");
        if (ret) {
            System.out.println("发送成功");
        } else {
            System.out.println("发送失败");
        }
    }
}
