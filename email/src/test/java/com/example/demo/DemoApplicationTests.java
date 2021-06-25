package com.example.demo;

import com.example.demo.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    MailService mailService;
    @Test
    void contextLoads() {
        mailService.sendMail("3424757297@qq.com","注册用的","123456");
    }

}
