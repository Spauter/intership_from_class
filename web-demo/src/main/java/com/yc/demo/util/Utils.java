package com.yc.demo.util;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Utils {
    public static void result(HttpServletResponse resp, Object result) throws IOException{
        resp.setContentType("application/json;charset=utf-8");
        final PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        final String json = gson.toJson(result);
        out.append(json);
    }
}
