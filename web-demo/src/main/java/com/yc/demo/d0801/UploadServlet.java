package com.yc.demo.d0801;

import com.yc.demo.util.Utils;
import com.yc.demo.vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.UUID;
@WebServlet(name = "UploadServlet",value = "/upload.s")

@MultipartConfig(maxFileSize = 1024*1024*10)
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        final Part image=req.getPart("image");
        image.getSubmittedFileName();
        image.getSize();

        image.getContentType();

        String fileName =image.getSubmittedFileName();

        String suffix =fileName.replaceAll(".+(\\.\\w+)","$1");

        String prefix = UUID.randomUUID().toString();

        fileName =prefix+suffix;
        System.out.println(fileName);
        image.write("C:\\users\\32306\\desktop\\"+fileName);


        String  webPath ="upload/"+fileName;

        System.out.println("webPath = " + webPath);


        Result result=new Result(1,"文件上传成功",webPath);
        Utils.result(resp,result);

    }
}
