package com.yc.test;

import com.yc.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class UserMapperTest {
    public static void main(String[] args) throws IOException {
        //读取配置文件
        String resource= "mybatis-config.xml";
        InputStream in= Resources.getResourceAsStream(resource);
        //获取SQl
        SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(in);
        //获取绘画
        SqlSession session =factory.openSession();
        System.out.println(session);

        User user=session.selectOne("com.yc.mapper.UserMapper.findById",1);
        System.out.println(user);
       //关闭会话
        session.close();
    }
}
