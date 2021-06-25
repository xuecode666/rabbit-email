package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {


    @Bean
    public Queue DirectQueie(){
        return new Queue("DirectQueue");
    }
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange("DirectExchange");

    }
    @Bean
    Binding binding(){
        return BindingBuilder.bind(DirectQueie()).to(directExchange()).with("DirectRouter");
    }
}
