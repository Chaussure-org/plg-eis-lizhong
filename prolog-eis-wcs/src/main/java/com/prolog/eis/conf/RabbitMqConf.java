package com.prolog.eis.conf;


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
    public Queue mcsCallback() {
        return new Queue("mcsCallback", true);
    }

    @Bean
    public Queue rcsCallback() {
        return new Queue("rcsCallback", true);
    }

    @Bean
    public Queue sasCallback() {
        return new Queue("sasCallback", true);
    }
//===============================================================================================
  /*  *//**
     * 监听 队列
     *//*
    @Bean
    public Queue agvMove() {
        return new Queue("agvMove", true);
    }*/

    /**
     * 声明交换机 交换机类型
     * 采用直连交换机，不同的设备系统无关系
     * 后续可根据 需求设置主题 和 扇形等交换机
     */
    @Bean
    public DirectExchange mcsExchange() {
        return new DirectExchange("mcsExchange", true, false);
    }

    @Bean
    public DirectExchange rcsExchange() {
        return new DirectExchange("rcsExchange", true, false);
    }

    @Bean
    public DirectExchange sasExchange() {
        return new DirectExchange("sasExchange", true, false);
    }

    /**
     * 设置 队列和 交换机的bangding 关系，并给定路由key
     */

    @Bean
    Binding bind1() {
        return BindingBuilder.bind(mcsCallback()).to(mcsExchange()).with("mcsback");
    }

    @Bean
    Binding bind2() {
        return BindingBuilder.bind(rcsCallback()).to(rcsExchange()).with("rcsback");
    }

    @Bean
    Binding bind3() {
        return BindingBuilder.bind(sasCallback()).to(sasExchange()).with("sasback");
    }
}
