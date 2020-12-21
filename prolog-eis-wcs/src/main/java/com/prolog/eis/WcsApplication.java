package com.prolog.eis;

import com.prolog.framework.microservice.annotation.EnablePrologService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;

/**
 * @Author SunPP
 * myMotto:三十功名尘与土，八千里路云和月
 * Description:
 * @return
 * @date:2020/12/8 11:50
 */
@EnablePrologService
@EnableRabbit
public class WcsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WcsApplication.class, args);
    }


}
