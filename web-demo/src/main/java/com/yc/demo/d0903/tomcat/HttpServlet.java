package com.yc.demo.d0903.tomcat;

import java.io.IOException;

public interface HttpServlet {

	/**
	 * 每次服务器接收到一个 Servlet 请求时，服务器会产生一个新的线程并调用服务。service() 方法检查 HTTP 请求类型（GET、POST、PUT、DELETE 等），并在适当的时候调用 doGet、doPost、doPut，doDelete 等方法
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	void doTrace(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

	/**
	 * 初始化
	 */
	void init();

	/**
	 * 销毁
	 */
	void destroy();
}
