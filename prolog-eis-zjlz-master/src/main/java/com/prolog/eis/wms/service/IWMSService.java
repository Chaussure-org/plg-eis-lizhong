package com.prolog.eis.wms.service;

import com.prolog.eis.dto.wms.InboundCallBackDto;
import com.prolog.eis.dto.wms.WMSInboundTaskDto;
import com.prolog.eis.dto.wms.WMSOutboundTaskDto;
import com.prolog.framework.common.message.RestMessage;

import java.util.Date;
import java.util.List;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 13:57
 */
public interface IWMSService {

    /**
     * 上架完成回告wms
     * @param inboundCallBackDto
     * @return
     */
    RestMessage<String> inboundTaskCallBack(InboundCallBackDto inboundCallBackDto);

    /**
     * 拣货完成回告wms
     * @param billNo 清单号
     * @param billDate  清单时间
     * @param status 状态
     * @param sjc 时间戳
     * @param billType 单据类型
     * @return
     */
    RestMessage<String> outboundTaskCallBack(String billNo, Date billDate,Integer status,Date sjc,Integer billType);
}
