package com.bloducspauter.tomcat;

public interface Cookie {

	/**
	 * 该方法设置 cookie 适用的域，例如 runoob.com。
	 * @return
	 */
	public void setDomain(String pattern);

	/**
	 * 该方法设置 cookie 过期的时间（以秒为单位）。如果不这样设置，cookie 只会在当前 session 会话中持续有效。
	 * @param expiry
	 */
	public void setMaxAge(int expiry);

	/**
	 * 该方法设置 cookie 适用的路径。如果您不指定路径，与当前页面相同目录下的（包括子目录下的）所有 URL 都会返回 cookie。
	 * @param uri
	 */
	public void setPath(String uri);

	/**
	 * 该方法返回 cookie 的名称。名称在创建后不能改变。
	 * @return
	 */
	public String getName();

	/**
	 * 该方法获取与 cookie 关联的值。
	 * @return
	 */
	public String getValue();

    void add(Cookie cookie);
}
