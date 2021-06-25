package com.example.demo.service.imp;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MailService mailService;
    @Override
    public void register(User user) {
        userMapper.register(user);
        String code = user.getCode();
        System.out.println("code:"+code);
        //主题
        String subject = "来自xxx网站的激活邮件";
        //user/checkCode?code=code(激活码)是我们点击邮件链接之后根据激活码查询用户，如果存在说明一致，将用户状态修改为“1”激活
        //上面的激活码发送到用户注册邮箱
        String context = "<a href=\"/user/checkCode?code="+code+"\">激活请点击:"+code+"</a>";
        //发送激活邮件
        mailService.sendMail (user.getEmail(),subject,context);
    }

    @Override
    public User checkCode(String code) {
        return userMapper.checkCode(code);
    }

    @Override
    public void updateUserStatus(User user) {
        userMapper.updateUserStatus(user);
    }

    @Override
    public User loginUser(User user) {
        return userMapper.loginUser(user);
    }
}
