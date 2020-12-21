package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.mcs.McsCarInfoDto;
import com.prolog.eis.dto.wms.WmsInboundCallBackDto;
import com.prolog.eis.dto.wms.WmsInventoryCallBackDto;
import com.prolog.eis.dto.wms.WmsOutboundCallBackDto;
import com.prolog.eis.dto.wms.WmsStartOrderCallBackDto;
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

    /**
     * 入库回告
     * @throws Exception
     */
    @Test
    public void testInbound() throws Exception {
        WmsInboundCallBackDto wmsInboundCallBackDto = new WmsInboundCallBackDto();
        wmsInboundCallBackDto.setSEQNO("0");
        wmsInboundCallBackDto.setITEMNAME("内接头总成");
        wmsInboundCallBackDto.setITEMID("0000002405");
        wmsInboundCallBackDto.setCONTAINERNO("600018");
        wmsInboundCallBackDto.setBILLTYPE(1);
        wmsInboundCallBackDto.setBILLNO("RCV0000004803");
        wmsInboundCallBackDto.setLINEID("21954703");
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
        wmsOutboundCallBackDto.setQTY(1.0);
        wmsOutboundCallBackDto.setTASKID("21955170");
        wmsOutboundCallBackDto.setBILLTYPE("2");
        wmsOutboundCallBackDto.setBILLDATE(new Date());
        wmsOutboundCallBackDto.setITEMID(EisStringUtils.getRemouldId(2842));
        wmsOutboundCallBackDto.setSTATUS(1);
        wmsOutboundCallBackDto.setSJC(new Date());
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
        WmsInventoryCallBackDto wmsInventoryCallBackDto = new WmsInventoryCallBackDto();
        wmsService.inventoryTaskCallBack(wmsInventoryCallBackDto);
    }
}
