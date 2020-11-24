package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.wms.WmsInboundCallBackDto;
import com.prolog.eis.wms.service.IWmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    @Test
    public void testInbound() throws Exception {
        WmsInboundCallBackDto wmsInboundCallBackDto = new WmsInboundCallBackDto();
        wmsInboundCallBackDto.setSEQNO("0");
        wmsInboundCallBackDto.setITEMNAME("10#圆钢");
        wmsInboundCallBackDto.setLOTNO("PH000000000022789");
        wmsInboundCallBackDto.setITEMID("0000002206");
        wmsInboundCallBackDto.setCONTAINERNO("18");
        wmsInboundCallBackDto.setBILLTYPE(1);
        wmsInboundCallBackDto.setBILLNO("RCV0000004718");
        wmsInboundCallBackDto.setLINEID("21954522");
        wmsService.inboundTaskCallBack(wmsInboundCallBackDto);
    }
}
