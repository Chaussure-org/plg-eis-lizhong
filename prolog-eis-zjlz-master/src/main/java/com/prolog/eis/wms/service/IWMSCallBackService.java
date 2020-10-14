package com.prolog.eis.wms.service;

import com.prolog.eis.dto.wms.*;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 13:59
 */
public interface IWMSCallBackService {

    /**
     * WMS下发入库任务
     * @param wmsInboundTaskDtos 入库任务
     */
    void sendInboundTask(List<WmsInboundTaskDto> wmsInboundTaskDtos);

    /**
     * WMS下发出库任务
     * @param wmsOutboundTaskDtos 出库任务
     * @throws Exception
     */
    void sendOutBoundTask(List<WmsOutboundTaskDto> wmsOutboundTaskDtos) throws Exception;

    /**
     * 提升订单优先级
     * @param wmsUpProiorityDto 优先级
     */
    void upOrderProiority(WmsUpProiorityDto wmsUpProiorityDto) throws Exception;

    /**
     * 商品资料同步
     * @param goodsDtos 商品参数
     */
    void goodsSync(List<WmsGoodsDto> goodsDtos);

    /**
     * 盘点计划下发
     * @param wmsInventoryTasks 盘点任务
     */
    void sendInventoryTask(List<WmsInventoryTaskDto> wmsInventoryTasks) throws Exception;
}
