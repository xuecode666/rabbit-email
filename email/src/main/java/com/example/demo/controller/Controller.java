package com.example.demo.controller;


import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@org.springframework.stereotype.Controller
@RequestMapping("/user")

public class Controller {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    UserService userService;
    @RequestMapping("/registerUser")
    public String regist(User user) throws JsonProcessingException {
        user.setState(0);
        String code = UUID.randomUUID().toString();
        user.setCode(code);


        //序列化对象
        ObjectMapper mapper=new ObjectMapper();
        String messaged=mapper.writeValueAsString(user);

        rabbitTemplate.convertAndSend("DirectExchange","DirectRouter",messaged.getBytes());
        System.out.println("发送消息到队列中");
//        userService.register(user);
        return "success";
    }
    @RequestMapping(value = "/checkCode")
    public String check(String code){
        User user = userService.checkCode(code);
        if (user !=null){
            user.setState(1);
            //把code验证码清空，已经不需要了
            user.setCode("");
            System.out.println(user);
            userService.updateUserStatus(user);
        }
        return "login";
    }
    @RequestMapping(value = "/loginPage")
    public String login(){
        return "login";
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/loginUser")
    public String login(User user, Model model){
        User u = userService.loginUser(user);
        if (u !=null){
            return "welcome";
        }
        return "login";
    }


}
