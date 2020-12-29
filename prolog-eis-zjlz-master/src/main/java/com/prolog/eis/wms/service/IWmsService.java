package com.prolog.eis.wms.service;

import com.prolog.eis.dto.wms.WmsInboundCallBackDto;
import com.prolog.eis.dto.wms.WmsInventoryCallBackDto;
import com.prolog.eis.dto.wms.WmsOutboundCallBackDto;
import com.prolog.eis.dto.wms.WmsStartOrderCallBackDto;
import com.prolog.eis.util.EisRestMessage;
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
     * @throws Exception
     */
    EisRestMessage<String> inboundTaskCallBack(WmsInboundCallBackDto wmsInboundCallBackDto) throws Exception;

    /**
     * 拣货完成回告wms或移库回告wms 移库回告taskId和容器号
     * @param wmsOutboundCallBackDto 回告实体
     * @return
     * @throws Exception
     */
    EisRestMessage<String> outboundTaskCallBack(WmsOutboundCallBackDto wmsOutboundCallBackDto) throws Exception;

    /**
     * 盘点完成回告wms
     * @param wmsInventoryCallBackDto 回告实体
     * @return
     * @throws Exception
     */
    EisRestMessage<String> inventoryTaskCallBack(WmsInventoryCallBackDto wmsInventoryCallBackDto) throws Exception;


    /**
     * 拣货开始回告wms
     * @param startOrderCallBackDto
     * @return
     */
    EisRestMessage<String>  startOrderCallBack(WmsStartOrderCallBackDto startOrderCallBackDto) throws Exception;
}
