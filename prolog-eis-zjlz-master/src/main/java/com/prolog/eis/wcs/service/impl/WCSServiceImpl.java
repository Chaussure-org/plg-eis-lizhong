package com.prolog.eis.wcs.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;
import com.prolog.eis.util.HttpUtils;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.wcs.service.IWCSService;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WCSServiceImpl implements IWCSService {
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private EisProperties properties;
    @Autowired
    private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(WCSServiceImpl.class);

    /**
     * 获取完整路径
     * @param path
     * @return
     */
    private String getUrl(String path){
        return String.format("http://%s%s",properties.getWcs().getHost(),path);
    }

    /**
     * 输送线行走
     *
     * @param wcsLineMoveDto 输送线行走实体
     * @return
     */
    @Override
    @LogInfo(desci = "eis发送输送线行走命令",direction = "eis->wcs",type = LogDto.WCS_TYPE_LINE_MOVE,systemType = LogDto.WCS)
    public RestMessage<String> lineMove(WcsLineMoveDto wcsLineMoveDto) {
        String url = this.getUrl(properties.getWcs().getLineMoveUrl());
        logger.info("EIS -> WCS 输送线行走:{}",url);
        try {
            RestMessage<String> result = httpUtils.post(url,MapUtils.convertBean(wcsLineMoveDto),
                    new TypeReference<RestMessage<String>>() {});
            return result;
        } catch (Exception e) {
            logger.warn("EIS -> WCS 请求输送线行走异常",e);
            return RestMessage.newInstance(false,"500",e.getMessage(),null);
        }
    }

//    /**
//     * 请求订单箱
//     *
//     * @param taskId
//     * @param address
//     * @return
//     */
//    @Override
//    public RestMessage<String> requestOrderBox(String taskId, String address) {
//        String url = this.getUrl(properties.getWcs().getOrderBoxReqUrl());
//        logger.info("EIS -> WCS 订单框请求:{}",url);
//        try {
//            RestMessage<String> result = httpUtils.post(url,MapUtils.put("taskId",taskId).put("address",address).getMap(),new TypeReference<RestMessage<String>>() {});
//            return result;
//        } catch (Exception e) {
//            logger.warn("EIS -> WCS 请求订单框异常",e);
//            return RestMessage.newInstance(false,"500",e.getMessage(),null);
//        }
//    }
//
//    /**
//     * 亮灯
//     *
//     * @param pickStationNo
//     * @param lights
//     * @return
//     */
//    @Override
//    public RestMessage<String> light(String pickStationNo, String[] lights) {
//        String url = this.getUrl(properties.getWcs().getLightControlUrl());
//        logger.info("EIS -> WCS 灯光控制请求:{}",url);
//        try {
//            RestMessage<String> result = httpUtils.post(url,MapUtils.put("stationNo",pickStationNo).put("lights",lights).getMap(),new TypeReference<RestMessage<String>>() {});
//            return result;
//        } catch (Exception e) {
//            logger.warn("EIS -> WCS 请求灯光异常",e);
//            return RestMessage.newInstance(false,"500",e.getMessage(),null);
//        }
//    }
//
//
//    @Override
//    public void openDoor(String doorNo, boolean open) throws Exception {
//
//    }

}
