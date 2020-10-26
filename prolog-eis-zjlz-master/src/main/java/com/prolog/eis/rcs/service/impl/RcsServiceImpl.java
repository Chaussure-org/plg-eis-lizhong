package com.prolog.eis.rcs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.dto.rcs.RcsRequestResultDto;
import com.prolog.eis.dto.rcs.RcsTaskDto;
import com.prolog.eis.rcs.service.IRCSService;
import com.prolog.eis.util.LogInfo;
import com.prolog.eis.util.PrologHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RcsServiceImpl implements IRCSService {

	@Autowired
	private EisProperties properties;

	@Autowired
	private RestTemplate restTemplate;

	private String getUrl(String url){
		return String.format("http://%s%s",properties.getRcs().getHost(),url);
	}


	@Override
	@LogInfo(desci = "eis发起rcs任务",direction = "eis->rcs",type = LogDto.RCS_TYPE_SEND_TASK,systemType = LogDto.RCS)
	public RcsRequestResultDto sendTask(RcsTaskDto rcsTaskDto) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("reqCode", rcsTaskDto.getReqCode());
		jsonObject.put("reqTime", (new SimpleDateFormat("yyyy MM dd HH:mm:ss")).format(new Date()));
		jsonObject.put("clientCode", "");
		jsonObject.put("tokenCode", "");
		jsonObject.put("interfaceName", "genAgvSchedulingTask");

		jsonObject.put("taskTyp", rcsTaskDto.getTaskType());
		jsonObject.put("wbCode", "");

		JSONArray jsonArray = new JSONArray();

		JSONObject startPositionJson = new JSONObject();
		startPositionJson.put("positionCode", rcsTaskDto.getStartPosition());
		startPositionJson.put("type", "00");
		jsonArray.add(startPositionJson);

		JSONObject endPositionJson = new JSONObject();
		endPositionJson.put("positionCode", rcsTaskDto.getEndPosition());
		endPositionJson.put("type", "00");

		jsonArray.add(endPositionJson);

		jsonObject.put("positionCodePath", jsonArray);
		//盲举全部不传货架号
		jsonObject.put("podCode", "");
		jsonObject.put("podDir", "");
		jsonObject.put("priority", rcsTaskDto.getPriority());
		jsonObject.put("agvCode", "");
		jsonObject.put("taskCode", rcsTaskDto.getReqCode());
		jsonObject.put("data", "");

		String data = jsonObject.toString();
		//	TODO 先改为模拟器方式
		//String postUrl = String.format("http://%s:%s%s", properties.getRcs().getHost(), properties.getRcs().getPort(),properties.getRcs().getSendTaskUrl());
		String postUrl = getUrl(properties.getRcs().getAgvmoveUrl());
		String result = restTemplate.postForObject(postUrl, PrologHttpUtils.getRequestEntity(data), String.class);
		//LogServices.logRcs(postUrl,data,"",result);
		RcsRequestResultDto resultObj = JSONObject.parseObject(result, RcsRequestResultDto.class);
		return resultObj;
	}
}
