package com.yc.demo.d0903.tomcat;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * <h1>常用请求头参数</h1>
 * <table class="reference notranslate">
<tbody><tr><th style="width:30%">头信息</th><th>描述</th></tr>
<tr><td>Accept</td><td>这个头信息指定浏览器或其他客户端可以处理的 MIME 类型。值 <b>image/png</b> 或 <b>image/jpeg</b> 是最常见的两种可能值。</td></tr>
<tr><td>Accept-Charset</td><td>这个头信息指定浏览器可以用来显示信息的字符集。例如 ISO-8859-1。</td></tr>
<tr><td>Accept-Encoding</td><td>这个头信息指定浏览器知道如何处理的编码类型。值 <b>gzip</b> 或 <b>compress</b> 是最常见的两种可能值。</td></tr>
<tr><td>Accept-Language</td><td>这个头信息指定客户端的首选语言，在这种情况下，Servlet 会产生多种语言的结果。例如，en、en-us、ru 等。</td></tr>
<tr><td>Authorization</td><td>这个头信息用于客户端在访问受密码保护的网页时识别自己的身份。</td></tr>
<tr><td>Connection</td><td>这个头信息指示客户端是否可以处理持久 HTTP 连接。持久连接允许客户端或其他浏览器通过单个请求来检索多个文件。值 <b>Keep-Alive</b> 意味着使用了持续连接。</td></tr>
<tr><td>Content-Length</td><td>这个头信息只适用于 POST 请求，并给出 POST 数据的大小（以字节为单位）。</td></tr>
<tr><td>Cookie</td><td>这个头信息把之前发送到浏览器的 cookies 返回到服务器。</td></tr>
<tr><td>Host</td><td>这个头信息指定原始的 URL 中的主机和端口。</td></tr>
<tr><td>If-Modified-Since</td><td>这个头信息表示只有当页面在指定的日期后已更改时，客户端想要的页面。如果没有新的结果可以使用，服务器会发送一个 304 代码，表示 <b>Not Modified</b> 头信息。</td></tr>
<tr><td>If-Unmodified-Since</td><td>这个头信息是 If-Modified-Since 的对立面，它指定只有当文档早于指定日期时，操作才会成功。</td></tr>
<tr><td>Referer</td><td>这个头信息指示所指向的 Web 页的 URL。例如，如果您在网页 1，点击一个链接到网页 2，当浏览器请求网页 2 时，网页 1 的 URL 就会包含在 Referer 头信息中。</td></tr>
<tr><td>User-Agent</td><td>这个头信息识别发出请求的浏览器或其他客户端，并可以向不同类型的浏览器返回不同的内容。</td></tr>
</tbody></table>
 */
public interface HttpServletRequest {
	/**
	 * 获取输入流
	 * @return
	 */
	InputStream getInputStream();

	/**
	 * 返回请求的 HTTP 方法的名称，例如，GET、POST 或 PUT。
	 * @return
	 */
	String getMethod();

	/**
	 * 从协议名称直到 HTTP 请求的第一行的查询字符串中，返回该请求的 URL 的一部分。
	 * @return
	 */
	String getRequestURI();

	/**
	 * 返回请求协议的名称和版本。
	 * @return
	 */
	String getProtocol();

	/**
	 * 以字符串形式返回指定的请求头的值。
	 * @param name
	 * @return
	 */
	String getHeader(String name);

	/**
	 * 返回一个数组，包含客户端发送该请求的所有的 Cookie 对象。
	 * @return
	 */
	Cookie[] getCookies();

	String getRequestedSessionId();

	/**
	 * 返回与该请求关联的当前 session 会话，或者如果请求没有 session 会话，则创建一个。
	 */
	HttpSession getSession();

	/**
	 * 以字符串形式返回请求参数的值，或者如果参数不存在则返回 null。
	 * @param name
	 * @return
	 */
	String getParameter(String name);
	
	/**
	 * 返回一个字符串对象的数组，包含所有给定的请求参数的值，如果参数不存在则返回 null。
	 * @param name
	 * @return
	 */
	String[] getParameterValues(String name);


	/**
	 * 返回包含在路径后的请求 URL 中的查询字符串。
	 * @return
	 */
	String getQueryString();

	public void setCharacterEncoding(String env) throws UnsupportedEncodingException;

}
