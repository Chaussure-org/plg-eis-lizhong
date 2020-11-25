package com.prolog.eis.wms.service;

import com.prolog.eis.dto.mcs.McsCarInfoDto;
import com.prolog.eis.dto.wms.WmsInboundCallBackDto;
import com.prolog.eis.util.EisRestMessage;
import com.prolog.framework.common.message.RestMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/24 16:42
 */
@Component
@FeignClient(value="lizhong-wms-bc-entry")
public interface FeignService {


    /**
     * 入库任务回告
     * @param wmsInboundCallBackDto
     * @return
     */
    @PostMapping(value = "inTransferTask/eisTaskConfirm/v1.0")
    EisRestMessage<String> inboundTaskCallBack(@RequestBody WmsInboundCallBackDto wmsInboundCallBackDto);

    
}
