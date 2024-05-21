package com.bs.movie.web.controller;



import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BaseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI(); 
		String path = url.substring(url.lastIndexOf("/") + 1);
		
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(path)) { // 激活这个方法处理请求
				try {
					method.invoke(this, req, resp);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				return;
			}
		}
		this.error(req, resp);
	}

	private void error(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print("<script>alert('您请求的路径不支持...');location.href='" + req.getContextPath() + "/index.html';</script>");
		out.flush();
		out.close();
	}

	protected void send(HttpServletResponse response, int code, Object data) throws IOException {
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", data);
		out.print(mapper.writeValueAsString(map));
		out.flush();
		out.close();
	}

	protected void send(HttpServletResponse response, Object data) throws IOException {
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		out.print(mapper.writeValueAsString(data));
		out.flush();
		out.close();
	}
}
