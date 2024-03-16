package com.yc.spring.bank.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yc.spring.bank.bean.Record;

public interface RecordDao{
	void insert(Record t);

	List<Record> selectByAccountId(Integer id);
}
