package com.prolog.eis.service;

import com.prolog.eis.dto.station.StationInfoDto;
import com.prolog.eis.dto.wms.WmsOutboundTaskDto;
import com.prolog.eis.util.EisRestMessage;
import com.prolog.framework.common.message.RestMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: wuxl
 * @create: 2020-09-28 16:43
 * @Version: V1.0
 */
@Component
@FeignClient(value="service-ai-eis-zjlz-master-wk")
public interface FeignService {

    @PostMapping(value = "/api/v1/agv/agvCallbackService/agvCallback")
    String agvCallback(@RequestBody String json);


    @PostMapping(value = "api/v1/master/station/updateType")
    RestMessage<String> updateStation(@RequestBody @Validated StationInfoDto stationInfoDto);



}
