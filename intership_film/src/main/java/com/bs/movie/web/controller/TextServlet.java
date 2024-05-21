package com.bs.movie.web.controller;



import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Company 源辰信息
 * text/plain 处理
 * @author navy
 * @date 2024/3/27
 * Email haijunzhou@hnit.edu.cn
 */
@WebServlet("/text/*")
public class TextServlet extends BaseController{
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletInputStream sis = request.getInputStream();
        int len = request.getContentLength();
        byte[] saveUploadData = new byte[len]; // 用来存储上传的数据信息
        int totalRead = 0; // 已经读取到的数据的长度
        int readLength = 0; // 记录当前读到的数据的长度
        for (; totalRead < len; totalRead += readLength) {
            readLength = sis.read(saveUploadData, totalRead, len - totalRead);
        }
        String str = new String(saveUploadData, StandardCharsets.UTF_8);
        String[] arrs = str.split("\r\n");
        String[] temp = null;
        for (String data : arrs) {
            temp = data.split("=");
            if (temp.length == 1) {
                System.out.println(temp[0] + " : ");
                continue;
            }
            System.out.println(temp[0] + " : " + temp[1]);
        }
        this.send(response, 200, "成功");
    }
}
