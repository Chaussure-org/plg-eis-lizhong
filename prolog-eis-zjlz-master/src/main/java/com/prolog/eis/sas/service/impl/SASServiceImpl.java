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
    @LogInfo(desci = "sas请求小车信息",direction = "eis->sas",type = LogDto.SAS_TYPE_GET_CARINFO,systemType = LogDto.SAS)
    public List<CarInfoDTO> getCarInfo() {
        String url = this.getUrl(properties.getWcs().getRequestCarUrl());
        logger.info("EIS -> SAS 请求小车信息:{}",url);
        try {
            RestMessage<CarListDTO> result = httpUtils.post(url, null, new TypeReference<RestMessage<CarListDTO>>() {});
            return result.getData().getCarryList();
        } catch (Exception e) {
            logger.warn("EIS -> SAS 请求小车信息异常",e);
            return null;
        }
    }

    /**
     * 获取三个提升机的信息
     *
     * @return
     */
    @Override
    @LogInfo(desci = "sas获取提升机信息",direction = "eis->sas",type = LogDto.SAS_TYPE_GET_HOISTERINFO,systemType =
            LogDto.SAS)
    public List<HoisterInfoDto> getHoisterInfoDto() {
        String url = this.getUrl(properties.getWcs().getRequestHoisterUrl());
        logger.info("EIS -> SAS 获取提升机信息:{}", url);

        try {
            String data = this.restTemplate.postForObject(url, null, String.class);
            logger.info("+++++++++++++++提升机:"+data+"++++++++++++++++++");
            PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(data);
            return helper.getObjectList("data", HoisterInfoDto.class);

        } catch (Exception e) {
            logger.warn("EIS -> SAS 提升机信息异常", e);
            return null;
        }
    }


    /**
     * 小车换层
     *
     * @param sasMoveCarDto 小车换层实体
     * @return
     */
    @Override
    @LogInfo(desci = "eis请求小车换层",direction = "eis->sas",type = LogDto.SAS_TYPE_CHANGE_LAYER,systemType = LogDto.SAS)
    public  RestMessage<String> moveCar(SasMoveCarDto sasMoveCarDto) {
        String url = this.getUrl(properties.getWcs().getMoveCarUrl());
        logger.info("EIS -> SAS 请求小车换层:{}",url);
        try {
            RestMessage<String> result = httpUtils.post(url, MapUtils.convertBean(sasMoveCarDto),
                    new TypeReference<RestMessage<String>>() {});
            return result;
        } catch (Exception e) {
            logger.warn("EIS -> SAS 请求小车换层异常",e);
            return RestMessage.newInstance(false,"500",e.getMessage(),null);
        }
    }


    /**
     * 出入库指令
     *
     * @param sasMoveTaskDto
     * @return
     */
    @Override
    @LogInfo(desci = "eis出入库任务",direction = "eis->sas",type = LogDto.SAS_TYPE_SEND_TASK,systemType =
            LogDto.SAS)
    public RestMessage<String> sendContainerTask(SasMoveTaskDto sasMoveTaskDto) {
        String url = this.getUrl(properties.getWcs().getTaskUrl());
        logger.info("EIS -> WCS 任务请求:{}",url);
        List<SasMoveTaskDto> list = new ArrayList<>();
        list.add(sasMoveTaskDto);
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
