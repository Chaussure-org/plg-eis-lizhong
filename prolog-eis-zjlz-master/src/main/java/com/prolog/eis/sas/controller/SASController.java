package com.prolog.eis.sas.controller;

import com.prolog.eis.dto.wcs.*;
import com.prolog.eis.sas.service.ISASCallbackService;
import com.prolog.eis.wcs.controller.WCSController;
import com.prolog.eis.wcs.service.IWCSCallbackService;
import com.prolog.eis.wcs.service.IWCSService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangkang
 */
@RestController
@RequestMapping("/sas")
@Api(tags = "SAS回调接口文档(两向车及提升机)")
public class SASController {

    private final Logger logger = LoggerFactory.getLogger(SASController.class);
    @Autowired
    private ISASCallbackService sasService;

    @ApiOperation(value="任务回告",notes="此接口包含入库任务回告、出库任务回告、移库任务回告、小车换层回告")
    @PostMapping("/task/callback")
    public RestMessage<String> taskCallback(@RequestBody TaskCallbackDTO taskCallbackDTO) throws Exception{
        logger.info("接收任务回告,{}",JsonUtils.toString(taskCallbackDTO));
        return sasService.executeTaskCallback(taskCallbackDTO);
    }

}
