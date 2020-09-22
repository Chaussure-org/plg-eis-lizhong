package com.prolog.eis.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
	private RestTemplate restTemplate;
	private final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public HttpUtils(RestTemplate restTemplate){
		this.restTemplate = restTemplate;
	}

	public <T> RestMessage<T> post(String url, Map<String,Object> params, TypeReference<RestMessage<T>> typeReference) throws IOException {
		HttpEntity<Map<String, Object>> entity = this.parseParams(params);
		System.out.println(entity.toString());
		String data = this.restTemplate.postForObject(url,entity,String.class);
		logger.info("EIS <- WCS Resutl: {}",data);
		RestMessage<T> result = RestMessage.parseJsonString(data,typeReference);
		return result;
	}

	private HttpEntity<Map<String, Object>> parseParams(Map<String,Object> params) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> requestEntity =new HashMap<>();
		if(params!=null && params.size()>0){
			params.forEach((k,v) ->{requestEntity.put(k,v);});
			logger.info("EIS -> WCS Params: {}", JsonUtils.toString(params));
		}else{
			logger.info("EIS -> WCS Params: {}","{}");
		}
		HttpEntity<Map<String, Object>> entity =new HttpEntity<Map<String, Object>>(requestEntity, headers);
		return entity;
	}
}
