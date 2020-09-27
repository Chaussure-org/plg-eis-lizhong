package com.prolog.eis.wms.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.wms.InboundCallBackDto;
import com.prolog.eis.util.HttpUtils;
import com.prolog.eis.wms.service.IWMSService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * @param inboundCallBackDto
     * @return
     */
    @Override
    public RestMessage<String> inboundTaskCallBack(InboundCallBackDto inboundCallBackDto) {
        String url = this.getUrl(properties.getWcs().getLineMoveUrl());
        logger.info("EIS -> WCS 输送线行走:{}",url);
        try {
            RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(inboundCallBackDto),new TypeReference<RestMessage<String>>() {});
            return result;
        } catch (Exception e) {
            logger.warn("EIS -> WCS 请求输送线行走异常", e);
            return RestMessage.newInstance(false, "500", e.getMessage(), null);
        }
    }

    /**
     * 拣选任务回告wms
     * @param billNo 清单号
     * @param billDate  清单时间
     * @param status 状态
     * @param sjc 时间戳
     * @param billType 单据类型
     * @return
     */
    @Override
    public RestMessage<String> outboundTaskCallBack(String billNo, Date billDate, Integer status, Date sjc,
                                                    Integer billType) {
        String url = this.getUrl(properties.getWcs().getLineMoveUrl());
        logger.info("EIS -> WCS 输送线行走:{}",url);
        try {
            Map<String, Object> params =
                    MapUtils.put("billNo", billNo).put("billDate", billDate).put("status", status).put("sjc", sjc).put("billType", billType).getMap();
            RestMessage<String> result = httpUtils.post(url, params,new TypeReference<RestMessage<String>>() {});
            return result;
        } catch (Exception e) {
            logger.warn("EIS -> WCS 请求输送线行走异常", e);
            return RestMessage.newInstance(false, "500", e.getMessage(), null);
        }
    }
}
