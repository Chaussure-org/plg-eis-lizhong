package com.prolog.eis.mcs.service.impl;

import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.mcs.McsCarInfoDto;
import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.mcs.McsResultDto;
import com.prolog.eis.mcs.service.IMcsService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologApiJsonHelper;
import com.prolog.eis.util.PrologHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-09-22 15:56
 */
@Service
public class McsServiceImpl implements IMcsService {

    private static final Logger logger = LoggerFactory.getLogger(McsServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EisProperties eisProperties;

    private String getUrl(String url){
        return String.format("http://%s%s",eisProperties.getMcs().getHost(),url);
    }

    @Override
    @LogInfo(desci = "eis发起托盘移动任务",direction = "eis->mcs",type = LogDto.MCS_TYPE_CONTIANER_MOVE,systemType = LogDto.MCS)
    public McsResultDto mcsContainerMove(McsMoveTaskDto mcsMoveTaskDto) throws Exception {
        List<McsMoveTaskDto> mcsSendTaskDtos = new ArrayList<McsMoveTaskDto>();
        mcsSendTaskDtos.add(mcsMoveTaskDto);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("carryList", mcsSendTaskDtos);

        String data = PrologApiJsonHelper.toJson(map);
        String restJson = "";
        String postUrl = this.getUrl(eisProperties.getMcs().getMcsContainerMoveUrl());
        logger.info("EIS -> MCS 发起托盘移动任务:{}", postUrl);
        restJson = restTemplate.postForObject(postUrl, PrologHttpUtils.getRequestEntity(data), String.class);
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(restJson);
        Boolean success = helper.getBoolean("success");
        String message = helper.getString("message");

        McsResultDto result = new McsResultDto();
        result.setRet(success);
        result.setMsg(message);
        return result;
    }

    @Override
    @LogInfo(desci = "eis查询堆垛机信息",direction = "eis-mcs",type = LogDto.MCS_TYPE_GETCATINFO,systemType = LogDto.MCS)
    public List<McsCarInfoDto> getMcsCarInfo() throws Exception {
        String url = this.getUrl(eisProperties.getMcs().getGetMcsCarInfoUrl());
        logger.info("EIS -> MCS 获取堆垛机信息:{}", url);
        String data = this.restTemplate.postForObject(url, null, String.class);
        logger.info("+++++++++++++++堆垛机:" + data + "++++++++++++++++++");
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(data);
        return helper.getObjectList("data", McsCarInfoDto.class);
    }


}
