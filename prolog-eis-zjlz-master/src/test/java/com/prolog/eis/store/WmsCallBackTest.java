package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.mcs.McsCarInfoDto;
import com.prolog.eis.dto.wms.WmsInboundCallBackDto;
import com.prolog.eis.dto.wms.WmsInventoryCallBackDto;
import com.prolog.eis.dto.wms.WmsOutboundCallBackDto;
import com.prolog.eis.dto.wms.WmsStartOrderCallBackDto;
import com.prolog.eis.inventory.dao.InventoryTaskMapper;
import com.prolog.eis.util.EisRestMessage;
import com.prolog.eis.util.EisStringUtils;
import com.prolog.eis.wms.service.FeignService;
import com.prolog.eis.wms.service.IWmsService;
import com.prolog.framework.common.message.RestMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/23 17:37
 */
@SpringBootTest(classes = ZjlzApplication.class)
@RunWith(SpringRunner.class)
public class WmsCallBackTest {
    @Autowired
    private IWmsService wmsService;
    @Autowired
    private FeignService feignService;
    @Autowired
    private InventoryTaskMapper taskMapper;

    /**
     * 入库回告
     * @throws Exception
     */
    @Test
    public void testInbound() throws Exception {
        WmsInboundCallBackDto wmsInboundCallBackDto = new WmsInboundCallBackDto();
        wmsInboundCallBackDto.setSEQNO("21954998");
        wmsInboundCallBackDto.setITEMNAME("悬挂球头总成");
        wmsInboundCallBackDto.setITEMID("0000002207");
        wmsInboundCallBackDto.setCONTAINERNO("11051");
        wmsInboundCallBackDto.setBILLTYPE(1);
        wmsInboundCallBackDto.setBILLNO("0000043763");
        wmsInboundCallBackDto.setLINEID("21954998");
        EisRestMessage<String> restMessage = wmsService.inboundTaskCallBack(wmsInboundCallBackDto);
        System.out.println("aaaa");
    }

    /**
     * 拣选完成回告
     * @throws Exception
     */
    @Test
    public void testSeed() throws Exception {
        WmsOutboundCallBackDto wmsOutboundCallBackDto = new WmsOutboundCallBackDto();
//        wmsOutboundCallBackDto.setQTY(1.0);
        wmsOutboundCallBackDto.setTASKID("21954995");
//        wmsOutboundCallBackDto.setBILLTYPE("3");
//        wmsOutboundCallBackDto.setBILLDATE(new Date());
//        wmsOutboundCallBackDto.setITEMID(EisStringUtils.getRemouldId(2207));
//        wmsOutboundCallBackDto.setSTATUS(1);
//        wmsOutboundCallBackDto.setSJC(new Date());
        wmsOutboundCallBackDto.setCONTAINERNO("11051");
//        wmsOutboundCallBackDto.setLOTID();
        EisRestMessage<String> restMessage = wmsService.outboundTaskCallBack(wmsOutboundCallBackDto);
        System.out.println("aaa");

    }

    /**
     * 开始拣选回告
     */
    @Test
    public void testStartSeed() throws Exception {
        WmsStartOrderCallBackDto wmsStartOrderCallBackDto = new WmsStartOrderCallBackDto();
        wmsStartOrderCallBackDto.setBILLNO("DO20201207001501");
        wmsStartOrderCallBackDto.setSTATUS("1");
        EisRestMessage<String> restMessage = wmsService.startOrderCallBack(wmsStartOrderCallBackDto);
        System.out.println("aaaa");
    }

    /**
     * 盘点任务回告
     * @throws Exception
     */
    @Test
    public void testInventory() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Date date = new Date();
        date = df.parse("2020-12-30");
        WmsInventoryCallBackDto wmsInventoryCallBackDto = new WmsInventoryCallBackDto();

        wmsInventoryCallBackDto.setITEMID("0000002207");
        wmsInventoryCallBackDto.setBILLNO("PD202012300451");
        wmsInventoryCallBackDto.setBILLDATE(date);
        wmsInventoryCallBackDto.setAFFQTY(10.0);
        wmsInventoryCallBackDto.setTASKID("PDD202012301026");
        wmsInventoryCallBackDto.setBRANCHAREA("LTK");
        wmsInventoryCallBackDto.setITEMTYPE("CP");
        wmsInventoryCallBackDto.setCONTAINERNO("11112");
        wmsInventoryCallBackDto.setBRANCHCODE("C001");
        wmsInventoryCallBackDto.setSEQNO("PDD202012301026");
        wmsInventoryCallBackDto.setSJZ(new Date());
        wmsService.inventoryTaskCallBack(wmsInventoryCallBackDto);

    }
}
