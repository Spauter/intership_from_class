package com.bs.contriller;

import com.bs.dateFormatAutoConfiguration.demo.BsTimeFunction;
import com.bs.jdbcTemplate.config.JdbcTemplate;
import com.bs.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bs.attrbutesAutoConfiguration.demo.BsAttrbutesFunction;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    private BsTimeFunction bsTimeFunction;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BsAttrbutesFunction bsAttributesFunction;

    @RequestMapping("/")
    public String home(@RequestParam String name){return bsTimeFunction.showtime(name);}

    @RequestMapping("/all")

    public List<Map<String,Object>> all(){
        log.info("as");
        return jdbcTemplate.select("select * from sp_movie");}

    @RequestMapping("/get")
    public String getSession(HttpServletRequest request){
        return bsAttributesFunction.getSession(request);
    }

    @RequestMapping("/set")
    public Map<String,Object> setSession(HttpServletRequest request, @RequestParam("attributes")String attributes){
       return bsAttributesFunction.setSession(request,attributes);
    }

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendEmail")
    public String sendEmail(){
        String from = "3230695439@qq.com";//设置发件人地址
        String to =   "3234547382@qq.com";//设置收件人地址
        String subject = "测试";//主题
        String text = "这是一则测试文件";//内容
        emailService.sendEmail(from,to,subject,text);
        return "Email sent successfully!";
    }

    @GetMapping("/sendHtmlEmail")
    public String sendHtmlEmail() throws MessagingException {
        String from = "3230695439@qq.com";//设置发件人地址
        String to = "3230695439@qq.com";//设置收件人地址
        String subject = "测试";//主题
        String text = "<h1 style='color: red'>这是一个测试文件</h1>";
        emailService.sendHtmlEmail(from,to,subject,text);
        return "Email sent successfully!";
    }

    @GetMapping("/sendAttachmentEmail")
    public String sendAttachmentEmail(){
        String from = "3230695439@qq.com";//设置发件人地址
        String to = "3230695439@qq.com";//设置收件人地址
        String text = "这是一个测试文件";
        String subject = "测试";//主题
        emailService.sendFileMail(from,to,subject,text,new File("D:\\test\\测试文档.docx"));
        return "Email sent successfully!";
    }



}
