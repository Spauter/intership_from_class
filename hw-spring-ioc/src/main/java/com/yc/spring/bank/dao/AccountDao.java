package com.yc.spring.bank.dao;

import com.yc.spring.bank.bean.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountDao {

	void insert(Account t);

	int update(Account t);

	Account selectById(Integer id);

	int delete(Integer id);

}
