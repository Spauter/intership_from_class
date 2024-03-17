package com.yc.spring.bbs.dao;

import com.yc.spring.bbs.bean.User;
//@Repository
public interface UserDao extends BaseDao<User> {
    void update(User user) throws InterruptedException;
}
