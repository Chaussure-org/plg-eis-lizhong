package com.prolog.eis;

import com.prolog.framework.authority.annotation.EnablePrologEmptySecurityServer;
import com.prolog.framework.microservice.annotation.EnablePrologService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@EnableScheduling
@EnablePrologEmptySecurityServer
@MapperScan({"com.prolog.eis.dao","com.prolog.eis.*.dao"})
@EnableAsync
@EnablePrologService
@EnableCaching
public class SimulatorApplication {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectionRequestTimeout(30000);
		httpRequestFactory.setConnectTimeout(30000);
		httpRequestFactory.setReadTimeout(30000);
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 支持中文编码
		return restTemplate;
	}


	public static void main( String[] args )
    {
    	SpringApplication.run(SimulatorApplication.class, args);
    }
}
