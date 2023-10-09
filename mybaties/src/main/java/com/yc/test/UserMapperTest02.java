package com.yc.test;

import com.yc.entity.User;
import com.yc.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class UserMapperTest02 {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory factory=getFacatory("mybatis-config.xml");
        System.out.println(factory);
        SqlSession session=factory.openSession(true);



        UserMapper mapper=session.getMapper(UserMapper.class);
        User user=new User();
        user.setuname("OldFu");
        user.setUpwd("213");
        user.setStatus(1);
        user.setU_role("叼毛");
        int result=mapper.add(user);
        System.out.println(result);
        session.close();
    }

    public static SqlSessionFactory getFacatory(String config)throws IOException{
        InputStream in= Resources.getResourceAsStream(config);
        SqlSessionFactoryBuilder builder =new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
        return factory;
    }
}
