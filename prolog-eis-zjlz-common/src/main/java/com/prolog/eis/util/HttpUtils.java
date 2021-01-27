package com.prolog.eis.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.prolog.framework.common.message.RestMessage;
import com.prolog.framework.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpUtils {

    @Autowired
	private RestTemplate restTemplate;

	private final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public HttpUtils(RestTemplate restTemplate){
		this.restTemplate = restTemplate;
	}

	public <T> EisRestMessage<T> postWms(String url, Map<String,Object> params, TypeReference<EisRestMessage<T>> typeReference) throws IOException {
		HttpEntity<Map<String, Object>> entity = this.parseWmsParams(params);
		System.out.println(entity.toString());
		String data = this.restTemplate.postForObject(url,entity,String.class);
        //String data = this.restTemplate.postForObject("http://10.0.2.135:20631/TaskDispatch/returnDo/v1.0",entity,String.class);
		logger.info("EIS <- WCS Resutl: {}",data);
		EisRestMessage<T> result = EisRestMessage.parseJsonString(data,typeReference);
		return result;
	}

	private HttpEntity<Map<String, Object>> parseWmsParams(Map<String,Object> params) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("Authorization",getToken());
//		headers.set("UserKey","wms");
		Map<String, Object> requestEntity =new HashMap<>();
		if(params!=null && params.size()>0){
			params.forEach((k,v) ->{requestEntity.put(k,v);});
			logger.info("EIS -> WMS Params: {}", JsonUtils.toString(params));
		}else{
			logger.info("EIS -> WMS Params: {}","{}");
		}
		HttpEntity<Map<String, Object>> entity =new HttpEntity<Map<String, Object>>(requestEntity, headers);
		return entity;
	}

	private HttpEntity<Map<String, Object>> parseParams(Map<String,Object> params) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> requestEntity =new HashMap<>();
		if(params!=null && params.size()>0){
			params.forEach((k,v) ->{requestEntity.put(k,v);});
			logger.info("EIS -> Wms Params: {}", JsonUtils.toString(params));
		}else{
			logger.info("EIS -> Wms Params: {}","{}");
		}
		HttpEntity<Map<String, Object>> entity =new HttpEntity<Map<String, Object>>(requestEntity, headers);
		return entity;
	}

	public <T> RestMessage<T> post(String url, Map<String,Object> params, TypeReference<RestMessage<T>> typeReference) throws IOException {
		HttpEntity<Map<String, Object>> entity = this.parseParams(params);
		System.out.println(entity.toString());
		String data = this.restTemplate.postForObject(url,entity,String.class);
		logger.info("EIS <- WCS Resutl: {}",data);
		RestMessage<T> result = RestMessage.parseJsonString(data,typeReference);
		return result;
	}


	public String getToken() {
		String url = "http://10.0.2.192:8800/oauth/token?grant_type=client_credentials&client_id=wms&client_secret" +
				"=wms";
		HashMap hashMap = restTemplate.postForObject(url, null, HashMap.class);
		String value = "Bearer "+hashMap.get("access_token");
		System.out.println(value);
		return value;
	}
}
