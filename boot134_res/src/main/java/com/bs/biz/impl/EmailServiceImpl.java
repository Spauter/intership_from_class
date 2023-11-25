package com.bs.biz.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.MessageFormat;

@Service
@Slf4j
public class EmailServiceImpl {
    //    使用@Autowired注解将JavaMailSender注入到EmailService中
    @Autowired
    private JavaMailSender mailSender;
    //发送普通邮件
    public void sendEmail(String from,String to,String subject,String text){
//        sendEmail()方法接收四个参数：from表示发件人地址、to表示收件人邮箱地址，subject表示邮件主题，text表示邮件正文。我们使用SimpleMailMessage类创建了一个邮件对象，并调用JavaMailSender.send()方法来发送邮件。
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}


