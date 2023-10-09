package com.bs.movie.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtil {
    private static SqlSessionFactory factory = null;

    static {
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            factory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取session，不会自动提交事务
     *
     * @return
     */
    public static SqlSession getSession() {
        SqlSession session = null;
        if (factory != null) {
            session = factory.openSession();
        }
        return session;
    }

    /**
     * 获取一个带事务提交的session
     *
     * @param isAutoCommit
     * @return
     */
    public static SqlSession getSession(boolean isAutoCommit) {
        SqlSession session = null;
        if (factory != null) {
            session = factory.openSession(isAutoCommit);
        }
        return session;
    }

    /**
     * 关闭session
     *
     * @param session
     */
    public static void close(SqlSession session) {
        if (session != null) {
            session.close();
        }
    }
}
