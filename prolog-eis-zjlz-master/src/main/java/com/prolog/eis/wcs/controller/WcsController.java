package com.prolog.eis.wcs.controller;

import com.prolog.eis.dto.wcs.*;
import com.prolog.eis.wcs.service.IWcsCallbackService;
import com.prolog.eis.wcs.service.IWcsService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author wangkang
 * @Description wcs服务
 * @CreateTime 2020-11-02 9:18
 */
@RestController
@Api(tags = "WCS回调接口文档(输送线)")
@RequestMapping("/wcs")
public class WcsController {

    private final Logger logger = LoggerFactory.getLogger(WcsController.class);
    @Autowired
    private IWcsCallbackService wcsService;
    @Autowired
    private IWcsService service;

    @ApiOperation(value = "任务回告", notes = "此接口包含输送线行走任务回告")
    @PostMapping("/task/callback")
    public RestMessage<String> taskCallback(@RequestBody TaskCallbackDTO taskCallbackDTO) throws Exception {
        logger.info("wcs任务回告,{}", JsonUtils.toString(taskCallbackDTO));
        return wcsService.executeTaskCallback(taskCallbackDTO);
    }

    @ApiOperation(value = "bcr请求", notes = "请求类型 1-入库 2-出库 3-料箱进站 ")
    @PostMapping("/bcr")
    public RestMessage<String> bcrCallback(@RequestBody BCRDataDTO bcrDataDTO) throws Exception {
//        logger.info("bcr请求,{}", JsonUtils.toString(bcrDataDTO));
        return wcsService.executeBcrCallback(bcrDataDTO);
    }


    @ApiOperation(value = "拆盘机入口信息回告", notes = "拆码盘设备空闲时，WCS上传空闲信号")
    @PostMapping("/openDisk/in/callback")
    public RestMessage<String> openDiskEntrance(@RequestBody OpenDiskDto openDiskDto) throws Exception {
        logger.info("拆盘机入口信息回告,{}", JsonUtils.toString(openDiskDto));
        return wcsService.openDiskEntranceCallback(openDiskDto);
    }

    @ApiOperation(value = "拆盘机出口信息回告", notes = "拆码盘完成后，托盘到达agv接泊位，WCS上传托盘到位信号")
    @PostMapping("openDisk/out/callback")
    public RestMessage<String> openDiskOut(@RequestBody OpenDiskFinishDto openDiskDto) throws Exception {
        logger.info("拆盘机出口信息回告,{}", JsonUtils.toString(openDiskDto));
        return wcsService.openDiskOuTCallback(openDiskDto);
    }


    @ApiOperation(value = "拣选站料箱放行", notes = "拣选完成，上层料箱通过按钮放行")
    @PostMapping("station/leave")
    public RestMessage<String> stationLeave(@RequestBody ContainerLeaveDto containerLeaveDto) throws Exception {
        logger.info("拣选站料箱放行,{}", JsonUtils.toString(containerLeaveDto));
        return wcsService.containerLeave(containerLeaveDto);
    }

}
