package com.bs.movie.web.controller;



import com.bs.movie.core.Part;
import com.bs.movie.core.RequestParam;
import com.bs.movie.core.RequestParameterUtil;
import com.bs.movie.util.ConstantInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Company 源辰信息
 * @author navy
 * @date 2024/3/4
 * @Email haijunzhou@hnit.edu.cn
 */
@WebServlet("/test/*")
public class TestServlet extends BaseController {
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestParam req = RequestParameterUtil.parseRequest(request);
        if (req == null) {
            this.send(response, 500, "失败");
            return;
        }
        System.out.println(req.getParameter("account"));
        System.out.println(req.getParameter("pwd"));
        System.out.println(req.getParameter("code"));
        Part part = req.getPart("photo");
        String path = null;
        if (part != null) {
            System.out.println(part);
            path = ConstantInfo.uploadpath + "/" + System.currentTimeMillis() + "_" + part.getFileName();
            part.write(ConstantInfo.basepath + "/" + path);
        }

        List<Part> parts = req.getParts("pics");
        for (Part pt : parts) {
            if (pt != null) {
                System.out.println(pt);
                path = ConstantInfo.uploadpath + "/" + System.currentTimeMillis() + "_" + pt.getFileName();
                pt.write(ConstantInfo.basepath + "/" + path);
            }
        }
        this.send(response, 200, "成功");
    }
}
