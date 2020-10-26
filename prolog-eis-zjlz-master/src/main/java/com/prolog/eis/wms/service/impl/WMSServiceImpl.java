package com.prolog.eis.wms.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.wms.WmsInboundCallBackDto;
import com.prolog.eis.dto.wms.WmsInventoryCallBackDto;
import com.prolog.eis.dto.wms.WmsOutboundCallBackDto;
import com.prolog.eis.util.HttpUtils;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.wms.service.IWMSService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 13:58
 */
@Service
public class WMSServiceImpl implements IWMSService {

    private static final Logger logger = LoggerFactory.getLogger(WMSServiceImpl.class);

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private EisProperties properties;

    /**
     * 获取完整路径
     * @param path
     * @return
     */
    private String getUrl(String path){
        return String.format("http://%s:%s%s",properties.getWms().getHost(),properties.getWms().getPort(),path);
    }

    /**
     * 上架任务回告wms
     * @param wmsInboundCallBackDto
     * @return
     */
    @Override
    @LogInfo(desci = "EIS入库任务回告",direction = "eis->wms",type = LogDto.WMS_TYPE_INBOUND_CALLBACK,systemType = LogDto.WMS)
    public RestMessage<String> inboundTaskCallBack(WmsInboundCallBackDto wmsInboundCallBackDto) throws Exception {
        String url = this.getUrl(properties.getWcs().getLineMoveUrl());
        logger.info("EIS -> WMS 入库任务回告:{}",url);
            RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(wmsInboundCallBackDto),new TypeReference<RestMessage<String>>() {});
            return result;
    }

    /**
     * 拣选任务回告wms
     * @param wmsOutboundCallBackDto 回告实体
     * @return
     */
    @Override
    @LogInfo(desci = "Eis出库任务完成回告",direction = "eis->wms",type = LogDto.WMS_TYPE_OUTBOUND_CALLBACK,systemType =
            LogDto.WMS)
    public RestMessage<String> outboundTaskCallBack(WmsOutboundCallBackDto wmsOutboundCallBackDto) throws Exception {
        String url = this.getUrl(properties.getWcs().getLineMoveUrl());
        logger.info("EIS -> WCS 出库任务完成回告:{}",url);
            RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(wmsOutboundCallBackDto),new TypeReference<RestMessage<String>>() {});
            return result;
    }

    @Override
    @LogInfo(desci = "eis盘点任务回告",direction = "eis->wms",type = LogDto.WMS_TYPE_INVENTORY_CALLBACK,systemType = LogDto.WMS)
    public RestMessage<String> inventoryTaskCallBack(WmsInventoryCallBackDto wmsInventoryCallBackDto) throws Exception {
        String url = this.getUrl(properties.getWcs().getLineMoveUrl());
        logger.info("EIS -> WCS 出库任务完成回告:{}",url);
            RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(wmsInventoryCallBackDto),new TypeReference<RestMessage<String>>() {});
            return result;
    }
}
