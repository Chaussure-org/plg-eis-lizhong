package com.prolog.eis;

import com.prolog.framework.authority.annotation.EnablePrologEmptySecurityServer;
import com.prolog.framework.authority.annotation.EnablePrologResourceServer;
import com.prolog.framework.microservice.annotation.EnablePrologService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@EnableScheduling
//@EnablePrologResourceServer
@EnablePrologEmptySecurityServer //无权限控制
@MapperScan(basePackages={"com.prolog.eis.*.dao"})
@EnableAsync
@EnablePrologService
@EnableFeignClients
public class  ZjlzApplication {
	public static void main( String[] args )
    {
    	SpringApplication.run(ZjlzApplication.class, args);
    }
}
