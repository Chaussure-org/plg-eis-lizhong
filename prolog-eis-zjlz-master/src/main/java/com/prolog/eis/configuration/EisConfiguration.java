package com.prolog.eis.configuration;

import com.prolog.eis.util.HttpUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableConfigurationProperties({EisProperties.class})
public class EisConfiguration {



    @Bean
    public HttpUtils httpUtils(RestTemplate restTemplate){
        return new HttpUtils(restTemplate);
    }
}
