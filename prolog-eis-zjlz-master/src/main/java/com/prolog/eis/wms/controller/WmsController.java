package com.prolog.eis.wms.controller;

import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.mcs.McsResultDto;
import com.prolog.eis.dto.wms.*;
import com.prolog.eis.mcs.service.IMcsService;
import com.prolog.eis.model.ContainerStore;
import com.prolog.eis.model.wms.WmsInboundTask;
import com.prolog.eis.store.service.IContainerStoreService;
import com.prolog.eis.util.EisRestMessage;
import com.prolog.eis.util.PrologStringUtils;
import com.prolog.eis.warehousing.dao.WareHousingMapper;
import com.prolog.eis.wms.service.IWmsCallBackService;
import com.prolog.eis.wms.service.IWmsService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.JsonUtils;
import com.prolog.framework.utils.MapUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    //-------测试

    @Autowired
    private WareHousingMapper wareHousingMapper;

    @Autowired
    private IMcsService mcsService;

    @Autowired
    private IContainerStoreService containerStoreService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("test")
    public String test(@RequestParam("address") String address, @RequestParam("target") String target, @RequestParam("type") int type) throws Exception {
        Map map = new HashMap();
        map.put("0100360022", 1);
        map.put("0100360023", 1);
        map.put("0100360024", 1);
        map.put("0100360025", 1);
        map.put("0100360026", 1);
        map.put("0100360027", 1);
        map.put("0100360028", 1);
        map.put("0100360029", 1);
        map.put("0100360030", 1);

        map.put("0100360031", 0);
        map.put("0100360032", 0);
        map.put("0100360033", 0);
        map.put("0100360034", 0);
        map.put("0100360035", 0);
        map.put("0100360036", 0);
        map.put("0100360037", 0);
        map.put("0100360038", 0);
        map.put("0100360039", 0);
        map.put("0100360040", 0);
        map.put("0100360041", 0);
        map.put("0100360042", 0);
        map.put("0100360043", 0);
        map.put("0100360044", 0);
        map.put("0100360045", 0);
        redisTemplate.opsForValue().set("testIn", map);

        return "";

        /*McsMoveTaskDto mcsMoveTaskDto = new McsMoveTaskDto();
        String taskId = PrologStringUtils.newGUID();
        ;
        mcsMoveTaskDto.setTaskId(taskId);
        mcsMoveTaskDto.setAddress(address);
        mcsMoveTaskDto.setTarget(target);
        mcsMoveTaskDto.setBankId(1);
        mcsMoveTaskDto.setType(type);
        mcsMoveTaskDto.setPriority("99");
        mcsMoveTaskDto.setStatus(0);
        mcsMoveTaskDto.setWeight("10");
        List<WmsInboundTask> list = wareHousingMapper.findByMap(null, WmsInboundTask.class);
        if (list.size() == 0) {
            return "数据库 没找到入库任务";
        }
        mcsMoveTaskDto.setContainerNo(list.get(0).getContainerNo());
        WmsInboundTask wareHousing = list.get(0);
        //发送任务
        McsResultDto mcsResultDto = mcsService.mcsContainerMove(mcsMoveTaskDto);
        if (!mcsResultDto.isRet()) {
            return "调用堆垛机 移动指令 失败";
        }
        //生成库存
        ContainerStore containerStore = new ContainerStore();
        containerStore.setContainerNo(wareHousing.getContainerNo());
        containerStore.setTaskType(10);
        containerStore.setTaskStatus(10);
        containerStore.setWorkCount(0);
        containerStore.setGoodsId(Integer.valueOf(wareHousing.getGoodsId()));
        containerStore.setQty(wareHousing.getQty());
        containerStore.setCreateTime(new Date());
        containerStore.setUpdateTime(new Date());
        containerStore.setTaskType(ContainerStore.TASK_TYPE_INBOUND);
        containerStoreService.saveContainerStore(containerStore);
        return "测试流程已走通";*/
    }


    @ApiOperation(value = "入库任务下发", notes = "入库任务下发")
    @PostMapping("/task/sendInbountTask")
    public EisRestMessage<String> sendInbountTask(@Validated @RequestBody List<WmsInboundTaskDto> wmsInboundTaskDtos) throws Exception {
        logger.info("wms入库任务下发,{}", JsonUtils.toString(wmsInboundTaskDtos));

        try {
            wmsCallBackService.sendInboundTask(wmsInboundTaskDtos);
            return EisRestMessage.newInstance(true, "上架任务下发成功");
        } catch (Exception e) {
            return EisRestMessage.newInstance(false, "500", "上架任务下发失败" + e.getMessage(), null);
        }
    }

    @ApiOperation(value = "出库任务下发", notes = "出库任务下发")
    @PostMapping("/task/sendOutboundTask")
    public EisRestMessage<String> sendOutBountTask(@Validated @RequestBody List<WmsOutboundTaskDto> wmsOutboundTaskDtos) throws Exception {
        logger.info("wms出库任务下发,{}", JsonUtils.toString(wmsOutboundTaskDtos));
        try {
            wmsCallBackService.sendOutBoundTask(wmsOutboundTaskDtos);
            return EisRestMessage.newInstance(true, "出库任务下发成功");
        } catch (Exception e) {
            return EisRestMessage.newInstance(false, "出库任务下发失败" + e.getMessage());
        }
    }

    @ApiOperation(value = "提升订单优先级", notes = "提升订单优先级")
    @PostMapping("order/upOrderPriority")
    public EisRestMessage<String> upOrderProiority(@Validated @RequestBody WmsUpProiorityDto wmsUpProiorityDto) throws Exception {
        logger.info("wms提升订单优先级,{}", JsonUtils.toString(wmsUpProiorityDto));
        try {
            wmsCallBackService.upOrderProiority(wmsUpProiorityDto);
            return EisRestMessage.newInstance(true, "订单优先级提升成功");
        } catch (Exception e) {
            return EisRestMessage.newInstance(false, "订单优先级提升失败" + e.getMessage());
        }
    }

    @ApiOperation(value = "商品资料同步", notes = "商品资料同步")
    @PostMapping("/goods/sync")
    public EisRestMessage<String> goodsSync(@Validated @RequestBody List<WmsGoodsDto> goodsDtos) throws Exception {
        logger.info("wms商品资料同步,{}", JsonUtils.toString(goodsDtos));
        try {
            wmsCallBackService.goodsSync(goodsDtos);
            return EisRestMessage.newInstance(true, "商品资料同步成功");
        } catch (Exception e) {
            return EisRestMessage.newInstance(false, "商品资料同步失败" + e.getMessage());
        }
    }

    @ApiOperation(value = "盘点计划下发", notes = "盘点计划下发")
    @PostMapping("/task/sendInventoryTask")
    public EisRestMessage<String> sendInventoryTask(@Validated @RequestBody List<WmsInventoryTaskDto> wmsInventoryTasks) throws Exception {
        logger.info("wms盘点计划下发,{}", JsonUtils.toString(wmsInventoryTasks));
        try {
            wmsCallBackService.sendInventoryTask(wmsInventoryTasks);
            return EisRestMessage.newInstance(true, "盘点计划下发成功");
        } catch (Exception e) {
            return EisRestMessage.newInstance(false, "盘点计划下发失败" + e.getMessage());
        }
    }

}
