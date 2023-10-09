package com.yc.demo;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(value = {"/jd.s","/pic.s"},
            loadOnStartup = 2)
public class PicServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("============== QUeryUserServlet init ==============");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        resp.setContentType("image/jpg");
        final ServletOutputStream out = resp.getOutputStream();
        try(FileInputStream fis = new FileInputStream( "d:/backImg.jpg")){
            byte[] bytes = new byte[1024];
            int count;
            while((count = fis.read(bytes)) > 0){
                out.write(bytes,0,count);
            }

        }
    }
}
