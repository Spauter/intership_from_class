package com.yc.test;

import com.yc.entity.User;
import com.yc.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;


import java.io.IOException;

/**
 * mybatis缓存机制：
 * 两级缓存：
 * 一级缓存：（局部缓存），SqlSession级别缓存。一级缓存一致开启的
 * SqlSession级别的一个Map数据库同一会话期间查询到数据都会放到本地缓存中
 * 以后如果需要获取相同的数据，直接从缓存中获取，没有必要去数据库中查询
 * 一级缓存失效情况:
 * 1.SqlSession不同
 * 2.SqlSession相同，但还没有缓存该数据
 * 3.SqlSession相同，两次查询之间执行更新操作（insert delete update）
 * 4.SqlSession相同，手动清除了缓存
 * <p>
 * 二级缓存：（全局缓存）基于namespace级别的缓存，一个名称空间对应这一个二级缓存
 * 工作机制：
 * 1.一个会话查询一条数据，这个数据保存到一级缓存中
 * 2.如果关闭了会话，一级缓存数据保存到二级缓存中；新的会话对象查询数据时，就可以参照二级缓存中
 * 注意：查询的结果到二级缓存中，该结果对应实体类对象需要实现序列化接口
 * 3.不同的namespace查询出的数据都会放到自己对应的缓存中（Map）
 * 注意： 从二级缓存中获取的数据都会保存自己对应的一级缓存中。
 * 会话的提交或关闭后，将一级缓存数据保存到二级缓存中
 * <p>
 * 如何开启二级缓存：
 * 1.开启二级缓存的全局配置mybatis-config.xml
 * <setting name="cacheEnabled" value="true"/>  系统默认开启二级缓存
 * 1.针对某一个名称空间namespace
 * 在xxx.Mapper.xml文件中 <catch/>
 * <p>
 * 参照帮助文档
 */
public class UserCatchTest {


    @Test
    public void testCatch01() {
        try (SqlSession session = getFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            User user = mapper.findById(1);
            System.out.println(user);

//            User user3 = new User();
//            user3.setUname("test20");
//            user3.setUpwd("123");
//            user3.setStatus(1);
//            user3.setURole("普通用户");
//            //两次查询操作之间，更新操作
//            mapper.add(user3);  //进行更新操作时，一级缓存失败

            //查看相同数据时，不从缓存中获取
            // session.clearCache();//清空缓存

            //查看相同的数据，直接从缓存获取
            User user2 = mapper.findById(1);
            System.out.println(user2);

        } catch (Exception e) {
            e.printStackTrace();

        }


    }


    @Test
    public void testCach02() {
        SqlSessionFactory factory = getFactory();
        SqlSession session01 = factory.openSession();
        UserMapper mapper = session01.getMapper(UserMapper.class);

        User user = mapper.findById(1);
        System.out.println(user);
        session01.close();  //关闭会话1
        SqlSession session02 = factory.openSession();
        UserMapper mapper2 = session02.getMapper(UserMapper.class);
        User user2 = mapper2.findById(1);//会话2从二级缓存中获取到数据
        System.out.println(user2);

        User u = mapper2.findById(1);
        System.out.println(u);

    }


    public SqlSessionFactory getFactory() {
        String res = "mybatis-config.xml";

        try {
            return new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
