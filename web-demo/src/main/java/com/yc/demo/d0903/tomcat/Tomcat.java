package com.yc.demo.d0903.tomcat;

import java.io.IOException;
import java.net.Socket;

public interface Tomcat {

	/**
	 * 启动服务器
	 */
	void start(int port, String webRoot);

	/**
	 * 构建请求对象
	 * @param socket
	 * @return
	 */
	HttpServletRequest buildRequest(Socket socket) throws IOException;;

	/**
	 * 构建响应对象
	 * @param socket
	 * @return
	 */
	HttpServletResponse buildResponse(Socket socket, HttpServletRequest request) throws IOException;;

	/**
	 * 判断是否是静态请求
	 * @param request
	 * @param response
	 * @return
	 */
	boolean isStaticRequest(HttpServletRequest request);

	/**
	 * 判断是否是动态(Servlet)请求
	 * @param request
	 * @param response
	 * @return
	 */
	boolean isServletRequest(HttpServletRequest request);

	/**
	 * 处理静态请求
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	void processStaticRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 处理动态(Servlet)请求
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	void processServletRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException;
			
	/**
	 * 根据文件名返回ContentType
	 */
	String getMimeType(String path);

	/**
	 * 监听停止服务指令
	 */
	void listenShutdown();

}
