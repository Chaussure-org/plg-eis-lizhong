package com.prolog.eis.sas.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.dto.wcs.CarListDTO;
import com.prolog.eis.dto.wcs.HoisterInfoDto;
import com.prolog.eis.sas.service.ISASService;
import com.prolog.eis.util.HttpUtils;
import com.prolog.eis.util.PrologApiJsonHelper;
import com.prolog.eis.wcs.service.IWCSService;
import com.prolog.eis.wcs.service.impl.WCSServiceImpl;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SASServiceImpl implements ISASService {
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private EisProperties properties;
    @Autowired
    private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(SASServiceImpl.class);

    /**
     * 获取完整路径
     * @param path
     * @return
     */
    private String getUrl(String path){
        return String.format("http://%s:%s%s",properties.getSas().getHost(),properties.getSas().getPort(),path);
    }
    /**
     * 获取每层小车信息
     *
     * @return
     */
    @Override
    public List<CarInfoDTO> getCarInfo() {
        String url = this.getUrl(properties.getWcs().getRequestCarUrl());
        logger.info("EIS -> WCS 请求小车信息:{}",url);
        try {
            RestMessage<CarListDTO> result = httpUtils.post(url, null, new TypeReference<RestMessage<CarListDTO>>() {});
            return result.getData().getCarryList();
        } catch (Exception e) {
            logger.warn("EIS -> WCS 请求小车信息异常",e);
            return null;
        }
    }

    /**
     * 获取三个提升机的信息
     *
     * @return
     */
    @Override
    public List<HoisterInfoDto> getHoisterInfoDto() {
        String url = this.getUrl(properties.getWcs().getRequestHoisterUrl());
        logger.info("EIS -> WCS 获取提升机信息:{}", url);

        try {
            String data = this.restTemplate.postForObject(url, null, String.class);
            logger.info("+++++++++++++++提升机:"+data+"++++++++++++++++++");
            PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(data);
            return helper.getObjectList("data", HoisterInfoDto.class);

        } catch (Exception e) {
            logger.warn("EIS -> WCS 提升机信息异常", e);
            return null;
        }
    }


    /**
     * 小车换层
     *
     * @param taskId
     * @param carId
     * @param sourceLayer
     * @param targetLayer
     * @return
     */
    @Override
    public  RestMessage<String> moveCar(String taskId, int carId, int sourceLayer, int targetLayer) {
        String url = this.getUrl(properties.getWcs().getMoveCarUrl());
        logger.info("EIS -> WCS 请求小车换层:{}",url);
        try {
            RestMessage<String> result = httpUtils.post(url, MapUtils.put("taskId", taskId).put("source", sourceLayer).put("target", targetLayer).put("bankId", properties.getWcs().getBankId()).getMap(), new TypeReference<RestMessage<String>>() {});
            return result;
        } catch (Exception e) {
            logger.warn("EIS -> WCS 请求小车换层异常",e);
            return RestMessage.newInstance(false,"500",e.getMessage(),null);
        }
    }


    /**
     * 出入库指令
     *
     * @param taskId
     * @param type
     * @param containerNo
     * @param address
     * @param target
     * @param weight
     * @param priority
     * @return
     */
    @Override
    public RestMessage<String> sendContainerTask(String taskId, int type, String containerNo, String address, String target, String weight, String priority,int status) {
        String url = this.getUrl(properties.getWcs().getTaskUrl());
        logger.info("EIS -> WCS 任务请求:{}",url);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = MapUtils.put("taskId", taskId)
                .put("type", type)
                .put("bankId", properties.getWcs().getBankId())
                .put("containerNo", containerNo)
                .put("address", address)
                .put("target", target)
                .put("weight", weight)
                .put("priority", priority)
                .put("status",status)
                .getMap();
        list.add(map);
        try {
            RestMessage<String> result = httpUtils.post(url,MapUtils.put("carryList",list.toArray()).getMap(),new TypeReference<RestMessage<String>>() {});
            return result;
        } catch (Exception e) {
            logger.warn("EIS -> WCS 任务请求异常",e);

            return RestMessage.newInstance(false,"500",e.getMessage(),null);
        }
    }



    @Override
    public RestMessage<String> deleteAbnormalContainerNo(String taskId, String containerNo) throws Exception {
        String url = this.getUrl(properties.getWcs().getRequestBankTaskUrl());
        logger.info("EIS -> WCS 删除异常入库料箱:{}", url);

        try {
            RestMessage<String> result = httpUtils.post(url,MapUtils.put("taskId",taskId).put("containerNo",containerNo).getMap(),new TypeReference<RestMessage<String>>() {});
            return result;

        } catch (Exception e) {
            logger.warn("EIS -> WCS 删除异常入库料箱", e);
            return null;
        }
    }
}
