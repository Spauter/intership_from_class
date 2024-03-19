package com.yc.spring.bank.bean;


/**
 * 银行账户
 * @author Administrator
 *
 */
public class Account {
	
	private String id;		// 主键
	private String name;	// 姓名
	private Double balance;	// 余额

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static Account getSingleAccount() {
		return singleAccount;
	}

	public static void setSingleAccount(Account singleAccount) {
		Account.singleAccount = singleAccount;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	} 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	/**
	 * 这是一个不完整的单例模式，少了私有的构造方法
	 */
	private static Account singleAccount; 
	public static Account getInstance() {
		if(singleAccount == null) {
			singleAccount = new Account();
		}
		return singleAccount;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", money=" + balance + "]";
	}

}
