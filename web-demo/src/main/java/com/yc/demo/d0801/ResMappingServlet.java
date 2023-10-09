package com.yc.demo.d0801;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
@WebServlet(name = "ResMappingServlet" ,value = "/upload/*")
public class ResMappingServlet extends HttpServlet {
    public static  final  String UPLOAD_DIR="C://users//32306//desktop//";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

        //获取文件路径
        String requestURI =req.getRequestURI();
        //上下文对象
        final ServletContext servletContext=req.getServletContext();
//        去掉上下文路径
        final String contextPath=servletContext.getContextPath();

        requestURI =requestURI.substring(contextPath.length());
        System.out.println("requestURI = " + requestURI);
//        去掉upload
        String resPath =requestURI.substring("/upload/".length());

        System.out.println(resPath);
//        拼接本地磁盘路径
        String diskPath =UPLOAD_DIR + resPath;

        System.out.println(diskPath);

        String contentType =servletContext.getMimeType(resPath);
        resp.setContentType(contentType);
        final ServletOutputStream out=resp.getOutputStream();
        try(FileInputStream fis =new FileInputStream(diskPath)){
            byte[] bytes =new byte[1024];
            int count;
            while ((count =fis.read(bytes))>0){
                out.write(bytes,0,count);
            }

        }
    }
}
