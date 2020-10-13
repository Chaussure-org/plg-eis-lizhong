package com.prolog.eis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.prolog.eis.service.FeignService;
import com.prolog.eis.service.RcsRequestService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RcsRequestServiceImpl implements RcsRequestService {
	@Autowired
	private FeignService feignService;

	@Override
	@SneakyThrows(Exception.class)
	@Async
	public void sendTask(String reqCode, String taskCode) {
		Thread.sleep(5000);
		this.send(reqCode, reqCode, "outbin");
		Thread.sleep(5000);
		this.send(reqCode, reqCode, "end");

	}

	private void send(String reqCode, String taskCode, String method) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("reqCode", reqCode);
		jsonObject.put("taskCode", taskCode);
		jsonObject.put("method", method);

		feignService.agvCallback(jsonObject.toString());
	}
}
