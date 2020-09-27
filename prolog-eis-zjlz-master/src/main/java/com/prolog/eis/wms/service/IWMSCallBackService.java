package com.prolog.eis.wms.service;

import com.prolog.eis.dto.wms.UpProiorityDto;
import com.prolog.eis.dto.wms.WMSInboundTaskDto;
import com.prolog.eis.dto.wms.WMSOutboundTaskDto;

import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 13:59
 */
public interface IWMSCallBackService {

    /**
     * WMS下发入库任务
     * @param wmsInboundTaskDtos
     */
    void sendInboundTask(List<WMSInboundTaskDto> wmsInboundTaskDtos);

    /**
     * WMS下发出库任务
     * @param wmsOutboundTaskDtos
     * @throws Exception
     */
    void sendOutBoundTask(List<WMSOutboundTaskDto> wmsOutboundTaskDtos) throws Exception;

    /**
     * 提升订单优先级
     * @param upProiorityDto
     */
    void upOrderProiority(UpProiorityDto upProiorityDto);
}
