package com.prolog.eis.mcs.service.impl;

import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.mcs.McsResultDto;
import com.prolog.eis.dto.mcs.McsSendTaskDto;
import com.prolog.eis.mcs.service.IMCSService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologApiJsonHelper;
import com.prolog.eis.util.PrologHttpUtils;
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
public class MCSServiceImpl implements IMCSService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EisProperties eisProperties;

    private String getUrl(){
        return String.format("http://%s:%s%s",eisProperties.getMcs().getHost(),eisProperties.getMcs().getPort(),eisProperties.getMcs().getTaskUrl());
    }

    @Override
    @LogInfo(desci = "eis发起托盘移动任务",direction = "eis->mcs",type = LogDto.MCS_TYPE_CONTIANER_MOVE,systemType = LogDto.MCS)
    public McsResultDto mcsContainerMove(McsMoveTaskDto mcsMoveTaskDto) throws Exception {
        List<McsMoveTaskDto> mcsSendTaskDtos = new ArrayList<McsMoveTaskDto>();
//        McsSendTaskDto mcsSendTaskDto = new McsSendTaskDto();
//        mcsSendTaskDto.setTaskId(taskId);
//        mcsSendTaskDto.setType(type);
//        mcsSendTaskDto.setBankId(1);
//        mcsSendTaskDto.setContainerNo(containerNo);
//        mcsSendTaskDto.setAddress(address);
//        mcsSendTaskDto.setTarget(target);
//        mcsSendTaskDto.setPriority(priority);
//        mcsSendTaskDto.setWeight("");
//        mcsSendTaskDto.setStatus(0);
        mcsSendTaskDtos.add(mcsMoveTaskDto);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("carryList", mcsSendTaskDtos);

        String data = PrologApiJsonHelper.toJson(map);
        String restJson = "";
        String postUrl = this.getUrl();
        restJson = restTemplate.postForObject(postUrl, PrologHttpUtils.getRequestEntity(data), String.class);
        PrologApiJsonHelper helper = PrologApiJsonHelper.createHelper(restJson);
        Boolean success = helper.getBoolean("ret");
        String message = helper.getString("msg");

        McsResultDto result = new McsResultDto();
        result.setRet(success);
        result.setMsg(message);

        return result;
    }
}
