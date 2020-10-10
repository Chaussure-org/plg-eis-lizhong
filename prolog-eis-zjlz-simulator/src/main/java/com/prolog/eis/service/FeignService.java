package com.prolog.eis.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: wuxl
 * @create: 2020-09-28 16:43
 * @Version: V1.0
 */
@Component
@FeignClient(value="service-ai-eis-zjlz-master")
public interface FeignService {

    @PostMapping(value = "/api/v1/agv/agvCallbackService/agvCallback")
    String agvCallback(@RequestBody String json);
}
