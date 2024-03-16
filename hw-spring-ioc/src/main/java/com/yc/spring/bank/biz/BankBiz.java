package com.yc.spring.bank.biz;

import com.yc.spring.bank.bean.Account;
import com.yc.spring.bank.bean.Record;
import java.util.List;

public interface BankBiz {

	// 开户
	void register(Account account);

	// 存款
	void deposit(Integer id, double money);

	// 取款
	void withdraw(Integer id, double money);

	// 转账
	void transfer(Integer id1, Integer id2, double money);

	// 查询交易明细
	List<Record> query(Integer id);
}
