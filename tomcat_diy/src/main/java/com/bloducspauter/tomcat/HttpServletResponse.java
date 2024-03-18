package com.bloducspauter.tomcat;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;

/**
<h1>常用响应头参数</h1>
<table class="reference notranslate">
<tbody><tr><th style="width:30%">头信息</th><th>描述</th></tr>
<tr><td>Content-Type</td><td>这个头信息提供了响应文档的 MIME（Multipurpose Internet Mail Extension）类型。</td></tr>
<tr><td>Location</td><td>这个头信息应被包含在所有的带有状态码的响应中。在 300s 内，这会通知浏览器文档的地址。浏览器会自动重新连接到这个位置，并获取新的文档。</td></tr>
<tr><td>Set-Cookie</td><td>这个头信息指定一个与页面关联的 cookie。</td></tr>
<tr><td>Allow</td><td>这个头信息指定服务器支持的请求方法（GET、POST 等）。</td></tr>
<tr><td>Cache-Control</td><td>这个头信息指定响应文档在何种情况下可以安全地缓存。可能的值有：<b>public、private</b> 或 <b>no-cache</b> 等。Public 意味着文档是可缓存，Private 意味着文档是单个用户私用文档，且只能存储在私有（非共享）缓存中，no-cache 意味着文档不应被缓存。</td></tr>
<tr><td>Connection</td><td>这个头信息指示浏览器是否使用持久 HTTP 连接。值 <b>close</b> 指示浏览器不使用持久 HTTP 连接，值 <b>keep-alive</b> 意味着使用持久连接。</td></tr>
<tr><td>Content-Disposition</td><td>这个头信息可以让您请求浏览器要求用户以给定名称的文件把响应保存到磁盘。</td></tr>
<tr><td>Content-Encoding</td><td>在传输过程中，这个头信息指定页面的编码方式。</td></tr>
<tr><td>Content-Language</td><td>这个头信息表示文档编写所使用的语言。例如，en、en-us、ru 等。</td></tr>
<tr><td>Content-Length</td><td>这个头信息指示响应中的字节数。只有当浏览器使用持久（keep-alive）HTTP 连接时才需要这些信息。</td></tr>
<tr><td>Expires</td><td>这个头信息指定内容过期的时间，在这之后内容不再被缓存。</td></tr>
<tr><td>Last-Modified</td><td>这个头信息指示文档的最后修改时间。然后，客户端可以缓存文件，并在以后的请求中通过 <b>If-Modified-Since</b> 请求头信息提供一个日期。
</td></tr>
<tr><td>Refresh</td><td>这个头信息指定浏览器应该如何尽快请求更新的页面。您可以指定页面刷新的秒数。</td></tr>
<tr><td>Retry-After</td><td>这个头信息可以与 503（Service Unavailable 服务不可用）响应配合使用，这会告诉客户端多久就可以重复它的请求。</td></tr>
</tbody></table>
 */
public interface HttpServletResponse {

	/**
	 * 获取字符号输出流
	 * @return
	 */
	PrintWriter getWriter();

	/**
	 * 获取字节输出流
	 * @return
	 */
	OutputStream getOutputStream();

	/**
	 * 如果响应还未被提交，设置被发送到客户端的响应的内容类型。<br>
	 * 参考: <a href="https://tool.oschina.net/commons">HTTP Content-type 对照表</a>
	 * @param type
	 */
	void setContentType(String type);

	/**
	 * 设置响应设置状态码。
	 * @param status
	 * @param msg
	 */
	void setStatus(int status, String msg);

	/**
	 * 设置一个带有给定的名称和值的响应报头。
	 * @param name
	 * @param value
	 */
	void setHeader(String name, String value);

	/**
	 * 强制任何在缓冲区中的内容被写入到客户端。
	 */
	void flushBuffer() throws IOException;

	/**
	 * 使用指定的重定向位置 URL 发送临时重定向响应到客户端。
	 * @param location
	 */
	void sendRedirect(String location);
	
	/**
	 * 把指定的 cookie 添加到响应。
	 * @param cookie
	 */
	void addCookie(Cookie cookie);

}
