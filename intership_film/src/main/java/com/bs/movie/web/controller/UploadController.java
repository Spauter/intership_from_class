package com.bs.movie.web.controller;

//import com.bs.movie.biz.MovieBiz;
import com.google.gson.Gson;
import com.ly.common.bean.Result;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet(name="UploadController",value = "/upload/*")
public class UploadController extends BaseServlet{
//    MovieBiz biz=new MovieBiz();

    /**
     * 长传文件，用于电影上传
     * 支持Excel表格上传
     * @param response
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void upload(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        final Part file = request.getPart("datafile");
        String path=System.getenv("APPDATA")+file.getSubmittedFileName();
        File file1=new File(path);
        Result result=null;
        if(file1.exists()){
            result=Result.failure("文件已经上传",null);
            send(response,result);
        }else {
            file.write(path);
//            biz.save(path);
        }
        final  String json=new Gson().toJson(result);
        response.getWriter().append(json);
        send(response,Result.success("文件上传成功",null));
    }

    /**
     * 系统环境变量路径测试部分
     * 请忽略
     */
    @Test
    public void test(){
        System.out.println(System.getenv("JAVA_HOME")
        +"\n" +
        System.getenv("APPDATA"));
    }

}
