package com.prolog.eis.util;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author SunPP
 * myMotto:三十功名尘与土，八千里路云和月
 * Description:
 * @return
 * @date:2020 /12/8 11:57
 */
@Configuration
public class RabbitMqConf {


    /**
     * 声明 推送给Eis的 消息队列
     *
     * @return
     */
    @Bean
    public Queue agvMove() {
        return new Queue("agvMove", true);
    }

    @Bean
    public Queue rcsMove() {
        return new Queue("rcsMove", true);
    }

    @Bean
    public Queue sasMove() {
        return new Queue("sasMove", true);
    }


    /**
     * 声明交换机 交换机类型
     * 采用直连交换机，不同的设备系统无关系
     * 后续可根据 需求设置主题 和 扇形等交换机
     */
    @Bean
    public DirectExchange mcsMoveExchange() {
        return new DirectExchange("mcsMoveExchange", true, false);
    }

    @Bean
    public DirectExchange rcsMoveExchange() {
        return new DirectExchange("rcsMoveExchange", true, false);
    }

    @Bean
    public DirectExchange sasMoveExchange() {
        return new DirectExchange("sasMoveExchange", true, false);
    }

    /**
     * 设置 队列和 交换机的bangding 关系，并给定路由key
     */

    @Bean
    Binding bind1() {
        return BindingBuilder.bind(agvMove()).to(mcsMoveExchange()).with("mcs");
    }

    @Bean
    Binding bind2() {
        return BindingBuilder.bind(rcsMove()).to(rcsMoveExchange()).with("rcs");
    }

    @Bean
    Binding bind3() {
        return BindingBuilder.bind(sasMove()).to(sasMoveExchange()).with("sas");
    }
}
