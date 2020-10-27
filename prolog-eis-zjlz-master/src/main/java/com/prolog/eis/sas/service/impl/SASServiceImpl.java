package com.prolog.eis.sas.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.sas.SasMoveTaskDto;
import com.prolog.eis.dto.wcs.CarInfoDTO;
import com.prolog.eis.dto.wcs.CarListDTO;
import com.prolog.eis.dto.wcs.HoisterInfoDto;
import com.prolog.eis.dto.wcs.SasMoveCarDto;
import com.prolog.eis.sas.service.ISASService;
import com.prolog.eis.util.HttpUtils;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologApiJsonHelper;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
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
     *
     * @param path
     * @return
     */
    private String getUrl(String path) {
        return String.format("http://%s%s", properties.getSas().getHost(), path);
    }

    /**
     * 获取每层小车信息
     *
     * @return
     */
    @Override
    @LogInfo(desci = "sas请求小车信息", direction = "eis->sas", type = LogDto.SAS_TYPE_GET_CARINFO, systemType = LogDto.SAS)
    public List<CarInfoDTO> getCarInfo() throws IOException {
        String url = this.getUrl(properties.getSas().getGetCarInfoUrl());
        logger.info("EIS -> SAS 请求小车信息:{}", url);
        RestMessage<CarListDTO> result = httpUtils.post(url, null, new TypeReference<RestMessage<CarListDTO>>() {
        });
        return result.getData().getCarryList();
    }

    /**
     * 获取三个提升机的信息
     *
     * @return
     */
    @Override
    @LogInfo(desci = "sas获取提升机信息", direction = "eis->sas", type = LogDto.SAS_TYPE_GET_HOISTERINFO, systemType =
            LogDto.SAS)
    public List<HoisterInfoDto> getHoisterInfoDto() throws Exception {
        String url = this.getUrl(properties.getSas().getGetHoisterInfoDtoUrl());
        logger.info("EIS -> SAS 获取提升机信息:{}", url);
        String data = this.restTemplate.postForObject(url, null, String.class);
        logger.info("+++++++++++++++提升机:" + data + "++++++++++++++++++");
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(data);
        return helper.getObjectList("data", HoisterInfoDto.class);
    }


    /**
     * 小车换层
     *
     * @param sasMoveCarDto 小车换层实体
     * @return
     */
    @Override
    @LogInfo(desci = "eis请求小车换层", direction = "eis->sas", type = LogDto.SAS_TYPE_CHANGE_LAYER, systemType = LogDto.SAS)
    public RestMessage<String> moveCar(SasMoveCarDto sasMoveCarDto) throws Exception {
        String url = this.getUrl(properties.getSas().getGetCarInfoUrl());
        logger.info("EIS -> SAS 请求小车换层:{}", url);
        RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(sasMoveCarDto), new TypeReference<RestMessage<String>>() {});
        return result;
    }


    /**
     * 出入库指令
     *
     * @param sasMoveTaskDto
     * @return
     */
    @Override
    @LogInfo(desci = "eis出入库任务", direction = "eis->sas", type = LogDto.SAS_TYPE_SEND_TASK, systemType =
            LogDto.SAS)
    public RestMessage<String> sendContainerTask(SasMoveTaskDto sasMoveTaskDto) throws Exception {
        String url = this.getUrl(properties.getSas().getSendContainerTaskUrl());
        logger.info("EIS -> WCS 任务请求:{}", url);
        RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(sasMoveTaskDto),
                new TypeReference<RestMessage<String>>() {
                });
        return result;
    }


    @Override
    public RestMessage<String> deleteAbnormalContainerNo(String taskId, String containerNo) throws Exception {
//        String url = this.getUrl(properties.getWcs().getRequestBankTaskUrl());
//        logger.info("EIS -> WCS 删除异常入库料箱:{}", url);
//
//        RestMessage<String> result = httpUtils.post(url, MapUtils.put("taskId", taskId).put("containerNo",
//                containerNo).getMap(), new TypeReference<RestMessage<String>>() {
//        });
//        return result;
        return null;
    }
}
