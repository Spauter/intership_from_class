package com.yc.spring.bank.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yc.spring.bank.bean.Account;

public interface AccountDao {

	void insert(Account t);

	void update(Account t);

	Account selectById(Integer id);

}
