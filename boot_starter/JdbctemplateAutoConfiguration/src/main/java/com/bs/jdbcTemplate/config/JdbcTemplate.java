package com.bs.jdbcTemplate.config;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;


public class JdbcTemplate {
    @Autowired
    private JdbcTemplateProperties properties;
    static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    static String uname = "scott";
    static String pwd = "Spauter";
    static String driver;
    static {
        try {

            ClassLoader cl = JdbcTemplate.class.getClassLoader();
            InputStream in = cl.getResourceAsStream("db.properties");
            if (in == null) {
                System.out.println("db.properties does not exists!");

            } else {
                Properties prop = new Properties();
                prop.load(in);
                url = prop.getProperty("url");
                uname = prop.getProperty("username");
                pwd = prop.getProperty("password");
                driver =prop.getProperty("driver");
            }
            Class.forName(driver);
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            throw new RuntimeException("数据驱动加载失败", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, uname, pwd);
        } catch (Exception e) {
            throw new RuntimeException("数据连接失败", e);
        }
    }

    public static Object execute(SqlExcutor executor) {
        Connection con = getConnection();
        try {
            con.setAutoCommit(false);
            Object ret = executor.execute(con);
            con.commit();
            return ret;
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception e1) {
                throw new RuntimeException("数据回滚失败", e);
            }
            throw new RuntimeException("SQL执行滚失败", e);
        } finally {
            try {
                con.close();
            } catch (Exception e1) {
                throw new RuntimeException("关闭链接失败", e1);
            }
        }

    }

    public static interface SqlExcutor {
        Object execute(Connection con) throws Exception;
    }

    public static int update(String sql, Object... params) {
        return (int) execute(con -> {
            System.out.println("SQL:" + sql);
            System.out.println("参数" + Arrays.toString(params));

            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        });

    }

    public static PreparedStatement prepareStatement(Connection con, String sql, Object... params) throws SQLException {
        System.out.println("SQL:" + sql);
        System.out.println("参数" + Arrays.toString(params));

        PreparedStatement ps = con.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        return ps;
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> select(String sql, Object... params) {
        return (List<Map<String, Object>>) execute(con -> {
            List<Map<String, Object>> list = new ArrayList<>();

            PreparedStatement ps = prepareStatement(con, sql, params);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 0; i < md.getColumnCount(); i++) {
                    String colunmName = md.getColumnName(i + 1);
                    colunmName.toLowerCase();
                    Object colunmValue = rs.getObject(i + 1);
                    row.put(colunmName, colunmValue);
                }
                list.add(row);
            }
            return list;
        });
    }

    public static void main(String[] args) {
        List<?> list1 = select( "select* from emp");
        list1.forEach(System.out::println);
    }
}
