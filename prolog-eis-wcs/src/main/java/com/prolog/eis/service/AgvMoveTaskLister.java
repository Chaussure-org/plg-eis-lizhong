package com.prolog.eis.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author SunPP
 * myMotto:三十功名尘与土，八千里路云和月
 * Description:
 * @return
 * @date:2020/12/9 11:06
 */
@Component
public class AgvMoveTaskLister {

    @RabbitListener(queues = "rcsMove")
    public String handleAgvMove(@Payload String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        try {
            System.out.println(message);
            channel.basicAck(deliveryTag, false);
            return "成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
