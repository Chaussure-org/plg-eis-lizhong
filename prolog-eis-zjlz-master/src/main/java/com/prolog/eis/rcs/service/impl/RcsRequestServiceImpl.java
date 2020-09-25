package com.prolog.eis.rcs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prolog.eis.configuration.EisProperties;
import com.prolog.eis.dto.rcs.RcsRequestResultDto;
import com.prolog.eis.rcs.service.IRcsRequestService;
import com.prolog.eis.util.PrologHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RcsRequestServiceImpl implements IRcsRequestService {

	@Autowired
	private EisProperties properties;

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public RcsRequestResultDto sendTask(String reqCode,String containerNo,String startPosition,String endPosition,String taskTyp,String priority) {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("reqCode", reqCode);
		jsonObject.put("reqTime", (new SimpleDateFormat("yyyy MM dd HH:mm:ss")).format(new Date()));
		jsonObject.put("clientCode", "");
		jsonObject.put("tokenCode", "");
		jsonObject.put("interfaceName", "genAgvSchedulingTask");

		jsonObject.put("taskTyp", taskTyp);
		jsonObject.put("wbCode", "");

		JSONArray jsonArray = new JSONArray();

		JSONObject startPositionJson = new JSONObject();
		startPositionJson.put("positionCode", startPosition);
		startPositionJson.put("type", "00");
		jsonArray.add(startPositionJson);

		JSONObject endPositionJson = new JSONObject();
		endPositionJson.put("positionCode", endPosition);
		endPositionJson.put("type", "00");

		jsonArray.add(endPositionJson);

		jsonObject.put("positionCodePath", jsonArray);
		//盲举全部不传货架号
		jsonObject.put("podCode", "");
		jsonObject.put("podDir", "");
		jsonObject.put("priority", priority);
		jsonObject.put("agvCode", "");
		jsonObject.put("taskCode", reqCode);
		jsonObject.put("data", "");

		String data = jsonObject.toString();
		String postUrl = String.format("http://%s:%s%s", properties.getRcs().getHost(), properties.getRcs().getPort(),properties.getRcs().getSendTaskUrl());
		String result = restTemplate.postForObject(postUrl, PrologHttpUtils.getRequestEntity(data), String.class);
		//LogServices.logRcs(postUrl,data,"",result);
		RcsRequestResultDto resultObj = JSONObject.parseObject(result, RcsRequestResultDto.class);
		return resultObj;
	}
}
