package com.example.demo.service.imp;

import com.example.demo.service.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImp implements MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImp.class);
    @Autowired
    JavaMailSender mailSender;
    @Value("${spring.mail.from}")
    private String from;
    @Override
    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("邮件已经发送。");

        } catch (Exception e) {

            logger.error("发送邮件异常",e);
        }


    }
}
