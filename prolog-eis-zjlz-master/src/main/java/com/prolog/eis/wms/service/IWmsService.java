package com.prolog.eis.wms.service;

import com.prolog.eis.dto.wms.WmsInboundCallBackDto;
import com.prolog.eis.dto.wms.WmsInventoryCallBackDto;
import com.prolog.eis.dto.wms.WmsOutboundCallBackDto;
import com.prolog.framework.common.message.RestMessage;

import java.util.Date;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 13:57
 */
public interface IWmsService {

    /**
     * 上架完成回告wms
     * @param wmsInboundCallBackDto 回告实体
     * @return
     */
    RestMessage<String> inboundTaskCallBack(WmsInboundCallBackDto wmsInboundCallBackDto) throws Exception;

    /**
     * 拣货完成回告wms
     * @param wmsOutboundCallBackDto 回告实体
     * @return
     */
    RestMessage<String> outboundTaskCallBack(WmsOutboundCallBackDto wmsOutboundCallBackDto) throws Exception;

    /**
     * 盘点完成回告wms
     * @param wmsInventoryCallBackDto 回告实体
     * @return
     */
    RestMessage<String> inventoryTaskCallBack(WmsInventoryCallBackDto wmsInventoryCallBackDto) throws Exception;
}
