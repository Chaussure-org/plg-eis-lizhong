package com.prolog.eis.wms.controller;

import com.prolog.eis.dto.wms.*;
import com.prolog.eis.util.EisRestMessage;
import com.prolog.eis.wms.service.IWmsCallBackService;
import com.prolog.eis.wms.service.IWmsService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 13:57
 */
@RestController
@RequestMapping("/wms")
@Api(tags = "WMS回调接口文档")
public class WmsController {

    private static final Logger logger = LoggerFactory.getLogger(WmsController.class);

    @Autowired
    private IWmsCallBackService wmsCallBackService;

    @Autowired
    private IWmsService wmsService;

    @PostMapping("test")
    public String test() throws Exception {
        WmsInboundCallBackDto wmsInboundCallBackDto = new WmsInboundCallBackDto();
        wmsInboundCallBackDto.setSEQNO("0");
        wmsInboundCallBackDto.setITEMNAME("内接头总成");
        wmsInboundCallBackDto.setITEMID("0000002405");
        wmsInboundCallBackDto.setCONTAINERNO("600018");
        wmsInboundCallBackDto.setBILLTYPE(1);
        wmsInboundCallBackDto.setBILLNO("RCV0000004803");
        wmsInboundCallBackDto.setLINEID("21954703");
        EisRestMessage<String> restMessage = wmsService.inboundTaskCallBack(wmsInboundCallBackDto);
        return "发送成功";
    }
    @ApiOperation(value = "入库任务下发",notes = "入库任务下发")
    @PostMapping("/task/sendInbountTask")
    public EisRestMessage<String> sendInbountTask(@Validated @RequestBody List<WmsInboundTaskDto> wmsInboundTaskDtos) throws Exception {
        logger.info("wms入库任务下发,{}", JsonUtils.toString(wmsInboundTaskDtos));

        try {
            wmsCallBackService.sendInboundTask(wmsInboundTaskDtos);
            return EisRestMessage.newInstance(true,"上架任务下发成功");
        } catch (Exception e) {
            return EisRestMessage.newInstance(false,"500","上架任务下发失败"+e.getMessage(),null);
        }
    }

    @ApiOperation(value = "出库任务下发",notes = "出库任务下发")
    @PostMapping("/task/sendOutboundTask")
    public EisRestMessage<String> sendOutBountTask(@Validated @RequestBody List<WmsOutboundTaskDto> wmsOutboundTaskDtos) throws Exception {
        logger.info("wms出库任务下发,{}", JsonUtils.toString(wmsOutboundTaskDtos));
        try {
            wmsCallBackService.sendOutBoundTask(wmsOutboundTaskDtos);
            return EisRestMessage.newInstance(true,"出库任务下发成功");
        } catch (Exception e) {
            return EisRestMessage.newInstance(false,"出库任务下发失败"+e.getMessage());
        }
    }

    @ApiOperation(value = "提升订单优先级",notes = "提升订单优先级")
    @PostMapping("order/upOrderPriority")
    public EisRestMessage<String> upOrderProiority(@Validated @RequestBody WmsUpProiorityDto wmsUpProiorityDto) throws Exception{
        logger.info("wms提升订单优先级,{}", JsonUtils.toString(wmsUpProiorityDto));
        try {
            wmsCallBackService.upOrderProiority(wmsUpProiorityDto);
            return EisRestMessage.newInstance(true,"订单优先级提升成功");
        }catch (Exception e){
            return EisRestMessage.newInstance(false,"订单优先级提升失败"+e.getMessage());
        }
    }

    @ApiOperation(value = "商品资料同步",notes = "商品资料同步")
    @PostMapping("/goods/sync")
    public EisRestMessage<String> goodsSync(@Validated @RequestBody List<WmsGoodsDto> goodsDtos) throws Exception{
        logger.info("wms商品资料同步,{}", JsonUtils.toString(goodsDtos));
        try {
            wmsCallBackService.goodsSync(goodsDtos);
            return EisRestMessage.newInstance(true,"商品资料同步成功");
        }catch (Exception e){
            return EisRestMessage.newInstance(false,"商品资料同步失败"+e.getMessage());
        }
    }

    @ApiOperation(value = "盘点计划下发",notes = "盘点计划下发")
    @PostMapping("/task/sendInventoryTask")
    public EisRestMessage<String> sendInventoryTask(@Validated @RequestBody List<WmsInventoryTaskDto> wmsInventoryTasks) throws Exception{
        logger.info("wms盘点计划下发,{}", JsonUtils.toString(wmsInventoryTasks));
        try {
            wmsCallBackService.sendInventoryTask(wmsInventoryTasks);
            return EisRestMessage.newInstance(true,"盘点计划下发成功");
        }catch (Exception e){
            return EisRestMessage.newInstance(false,"盘点计划下发失败"+e.getMessage());
        }
    }

}
