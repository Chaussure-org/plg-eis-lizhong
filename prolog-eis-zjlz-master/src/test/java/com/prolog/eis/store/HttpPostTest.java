package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.inventory.InventoryOutDto;
import com.prolog.eis.dto.wms.WmsGoodsDto;
import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import com.prolog.eis.model.location.AgvStoragelocation;
import com.prolog.eis.store.dao.StoreLocationMapper;
import com.prolog.eis.util.PrologApiJsonHelper;
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

    @Test
    public void testRCS() {
        List<String> locations = storeLocationMapper.find();
        for (String str : locations) {
            String x = str.substring(1, 6);
            //String y = str.substring();
        }

        List<AgvStoragelocation> list = agvStoragelocationMapper.findByMap(MapUtils.put("areaNo", "RCS01")
                .put("taskLock", 0).getMap(), AgvStoragelocation.class);


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
