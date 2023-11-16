package com.bs.controller;

import com.bs.biz.impl.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
@Slf4j
public class EmialController {
    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/sendEmail")
    public String sendEmail(HttpServletRequest request, HttpSession session) {
        String from = "3230695439@qq.com";//设置发件人地址
        String to = request.getParameter("email");
        Random rd = new Random();
        StringBuilder emailcode = new StringBuilder();
        int[] a = new int[6];
        for (int i = 0; i < a.length; i++) {
            a[i] = rd.nextInt(10);
            emailcode.append(a[i]);
        }
        String subject = "登录小萌神";//主题
        String text = to + "，你好!\n" +
                "\n" +
                "我们已收到你要求获得此帐户所用的一次性代码的申请。\n" +
                "\n" +
                "你的一次性代码为: " + emailcode + "\n" +
                "\n" +
                "如果你没有请求此代码，可放心忽略这封电子邮件。别人可能错误地键入了你的电子邮件地址。\n";//内容
        emailService.sendEmail(from, to, subject, text);
        session.setAttribute("emailcode", emailcode.toString());
        return "Email sent successfully!";
    }
}
