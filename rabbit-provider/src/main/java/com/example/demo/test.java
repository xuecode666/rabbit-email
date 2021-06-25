package com.example.demo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @GetMapping("/sendDirect")
    public String sendDirect(){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentEncoding("UTF-8");
        Message message = new Message("你好,这是发给test1队列的消息".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("DirectExchange","DirectRouter",message);
        return "direct ok";
    }
}
