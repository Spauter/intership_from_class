package com.yc.spring.bbs.biz;

import com.yc.spring.bbs.bean.User;
import com.yc.spring.bbs.dao.UserDao;

public class UserBiz {


	private UserDao uDao;

	public void create(User user) {
		uDao.insert(user);
	}

	public void modify(User user) throws InterruptedException {
		uDao.update(user);
	}

	public void remove(User user) {
		uDao.delete(user);
	}

	public long count(User user) {
		try {
			Thread.sleep((long) (Math.random() * 2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("======= UserDaoImpl.count ========");
		return 0;
	}

}
