package com.yc.spring.bbs.dao;


import com.yc.spring.bbs.bean.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class UserDaoImpl implements UserDao {

    public void insert(User user) {
//        异常方法
        int i=1/0;
        System.out.println("Insert");
    }


    public void update(User user) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Update");
    }


    public void delete(User user) {
        System.out.println("Delete");
    }


    public void selectOne(Object id) {
        System.out.println("SelectOne");
    }


    public List<User> selectList(User user, Integer page, Integer rows) {
        System.out.println("SelectList");
        return new ArrayList<>();
    }


    public long count(User user) {
        System.out.println("Count");
        return 0;
    }
}
