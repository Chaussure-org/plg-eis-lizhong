package com.prolog.eis.wcs.controller;

import com.prolog.eis.dto.wcs.*;
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

@RestController
@RequestMapping("/wcs")
@Api(tags = "WCS回调接口文档")
public class WCSController {

    private final Logger logger = LoggerFactory.getLogger(WCSController.class);
    @Autowired
    private IWCSCallbackService wcsService;
    @Autowired
    private IWCSService service;

    @ApiOperation(value="任务回告",notes="此接口包含入库任务回告、出库任务回告、移库任务回告、小车换层回告、输送线行走任务回告")
    @PostMapping("/task/callback")
    public RestMessage<String> taskCallback(@RequestBody TaskCallbackDTO taskCallbackDTO) throws Exception{
        logger.info("接收任务回告,{}",JsonUtils.toString(taskCallbackDTO));
        return wcsService.executeTaskCallback(taskCallbackDTO);
    }

    @ApiOperation(value="bcr请求",notes="此接口包含料箱进站请求、订单框进站请求、体积检测请求、入库口请求")
    @PostMapping("/bcr")
    public RestMessage<String> bcrCallback(@RequestBody BCRDataDTO bcrDataDTO) throws Exception{
        logger.info("bcr请求,{}",JsonUtils.toString(bcrDataDTO));
        return wcsService.executeBCRCallback(bcrDataDTO);

    }

    @ApiOperation(value="箱到位请求",notes="此接口包含料箱到位、订单框到位")
    @PostMapping("/box/arrive")
    public RestMessage<String> boxArriveCallback(@RequestBody BoxCallbackDTO boxCallbackDTO) throws Exception{
        logger.info("箱到位请求,{}",JsonUtils.toString(boxCallbackDTO));
        return wcsService.executeBoxArriveCallback(boxCallbackDTO);
    }

    @ApiOperation(value="料箱弹出完成回告",notes="料箱弹出完成时，请求此接口")
    @PostMapping("/box/complete")
    public RestMessage<String> boxCompleteCallback(@RequestBody BoxCompletedDTO boxCompletedDTO) throws Exception{
        logger.info("料箱弹出完成回告,{}",JsonUtils.toString(boxCompletedDTO));
        return wcsService.executeCompleteBoxCallback(boxCompletedDTO);
    }

    @ApiOperation(value="拍灯回告",notes="拍灯完成时，WCS请求此接口")
    @PostMapping("/light")
    public RestMessage<String> lightCallback(@RequestBody LightDTO lightDTO) throws Exception{
        logger.info("拍灯回告,{}",JsonUtils.toString(lightDTO));
        return wcsService.executeLightCallback(lightDTO);
    }

    @ApiOperation(value="安全门控制",notes="安全门控制")
    @PostMapping("/door")
    public RestMessage<String> door(@RequestBody DoorDTO doorDTO) throws Exception{
        logger.info("安全门回告,{}",JsonUtils.toString(doorDTO));
        try {
            service.openDoor(doorDTO.getDoorNo(),doorDTO.isOpen());
            return RestMessage.newInstance(true,"操作成功",null);
        }catch (Exception e){
            return RestMessage.newInstance(false,"500","操作失败，"+e.getMessage(),null);
        }
    }
}
