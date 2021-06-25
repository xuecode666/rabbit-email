package com.example.demo;

import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class DirectReceiver implements ChannelAwareMessageListener {
    @Autowired
    UserService userService;
    @Override
    @RabbitListener(queues = "DirectQueue")
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            ObjectMapper mapper=new ObjectMapper();

            String s = new String(message.getBody());
            User user = mapper.readValue(s.getBytes("utf-8"), User.class);
            userService.register(user);
            log.info("接收到的数据是:{}",s);
            //为true表示确认之前的所有消息  false表示只来处理着当前的消息
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.info("处理消息时显示异常,异常是:{},现拒绝消费当前消息且不再放回队列",e.getMessage());
            //为true会重新放回队列
            channel.basicReject(deliveryTag, false);
        }
    }

}
