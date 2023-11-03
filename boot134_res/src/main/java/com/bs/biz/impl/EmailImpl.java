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

//@Service
@Slf4j
public class EmailImpl {
    //    使用@Autowired注解将JavaMailSender注入到EmailService中
//    @Autowired
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
    //    发送HTML邮件
    public void sendHtmlEmail(String from,String to, String subject,String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//    一个布尔值，指示邮件消息是否为多部分消息。如果为 true，则邮件消息将被视为多部分消息，允许添加附件等内容。如果为 false，则邮件消息将被视为简单消息，仅包含文本内容。

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
//    使用MimeMessageHelper类创建了一个MimeMessage对象，并调用MimeMessageHelper.setText()方法将邮件内容设置为HTML格式。
//    helper.setText(text,true);
        helper.setText(buildContent("test"), true);
//    true表示这是个HTML邮件
        mailSender.send(message);
    }
    public String buildContent(String title) {
        //加载邮件html模板
        Resource resource = new ClassPathResource("template/Test.html");
        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            inputStream = resource.getInputStream();
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.info("发送邮件读取模板失败{}", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //替换html模板中的参数
        return MessageFormat.format(buffer.toString(), title);
    }
    //    发送带有附件的邮件
    public void sendFileMail(String from, String to, String subject, String text, File file){
        try {
            MimeMessage mm = mailSender.createMimeMessage();
            MimeMessageHelper mmh = new MimeMessageHelper(mm,true);
            mmh.setFrom(from);
            mmh.setTo(to);
            mmh.setSubject(subject);
            mmh.setText(text);
            mmh.addAttachment(file.getName(), file);//通过此方法添加附件
            mailSender.send(mm);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


