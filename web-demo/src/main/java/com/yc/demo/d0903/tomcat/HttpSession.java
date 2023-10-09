package com.yc.demo.d0903.tomcat;

/**
 * HTTP 是一种"无状态"协议，这意味着每次客户端检索网页时，客户端打开一个单独的连接到 Web 服务器，服务器会自动不保留之前客户端请求的任何记录。 
 */
public interface HttpSession {

	/**
	 * 该方法返回该 session 会话被创建的时间，自格林尼治标准时间 1970 年 1 月 1 日午夜算起，以毫秒为单位。
	 * @return
	 */
	public long getCreationTime();

	/**
	 * 该方法返回一个包含分配给该 session 会话的唯一标识符的字符串。
	 * @return
	 */
	public String getId();

	/**
	 * 该方法返回客户端最后一次发送与该 session 会话相关的请求的时间自格林尼治标准时间 1970 年 1 月 1 日午夜算起，以毫秒为单位。
	 * @return
	 */
	public long getLastAccessedTime();

	/**
	 * 该方法返回 Servlet 容器在客户端访问时保持 session 会话打开的最大时间间隔，以秒为单位。
	 * @return
	 */
	public int getMaxInactiveInterval();

	/**
	 * 该方法指示该 session 会话无效，并解除绑定到它上面的任何对象。
	 */
	public void invalidate();

	/**
	 * 该方法将从该 session 会话移除指定名称的对象。
	 * @param name
	 * @param value
	 */
	public void setAttribute(String name, Object value);

	/**
	 * 该方法返回在该 session 会话中具有指定名称的对象，如果没有指定名称的对象，则返回 null。
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name);

	/**
	 * 该方法使用指定的名称绑定一个对象到该 session 会话。
	 * @param name
	 */
	public void removeAttribute(String name);

	void setLastAccessedTime(long l);
}
