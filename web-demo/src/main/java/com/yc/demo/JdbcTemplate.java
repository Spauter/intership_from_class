package com.yc.demo;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcTemplate {
	
	static String url ;
	static String user;
	static String password ;
    static String driver;
	
	static {
		try {
			ClassLoader cl = JdbcTemplate.class.getClassLoader();
			InputStream in = cl.getResourceAsStream("db.properties");
			if(in ==null) {
				System.out.println("db.properties不存在");
			}else {
				Properties props = new Properties();
				props.load(in);
				url = props.getProperty("url");
				user = props.getProperty("username");
				password = props.getProperty("password");
				driver = props.getProperty("driver");
			}

			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e) {
			throw new RuntimeException("数据库驱动加载失败",e);
		}
	}
 
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(url,user,password);
		}catch(Exception e) {
			throw new RuntimeException("创建连接失败",e);
		}
	}
	
	public static Object execute(SqlExecutor executor) {
		
		Connection conn = getConnection();
		try{
			conn.setAutoCommit(false);
			Object ret = executor.execute(conn);
			conn.commit();
			return ret;
		}catch(Exception e) {
			try {
				conn.rollback();
			}catch(Exception e1) {
				throw new RuntimeException("回滚失败",e1);
			}
			throw new RuntimeException("SQL执行失败",e);
		}finally{
			try {
				conn.close();
			}catch(Exception e1) {
				throw new RuntimeException("关闭连接失败",e1);
			}
		}
	}

	public static interface SqlExecutor{
		Object execute(Connection conn)throws Exception;
	}
	
	public static int update(String sql, Object...params) {
		return (int) execute( conn -> {
			PreparedStatement ps = prepareStatement(conn,sql,params);
			return ps.executeUpdate();
		} );
	}
	
	public static PreparedStatement prepareStatement(
			Connection conn, String sql, Object... params) throws SQLException{
		System.out.println("SQL:"+sql);
		System.out.println("参数:"+Arrays.toString(params));
		PreparedStatement ps = conn.prepareStatement(sql);
		for(int i =0;i<params.length;i++) {
			ps.setObject(i+1, params[i]);
		}
		return ps;
	}
		
	public static PreparedStatement prepareInsertStatement(
			Connection conn, String sql, String keyName, Object... params) throws SQLException{
		System.out.println("SQL:"+sql);
		System.out.println("参数:"+Arrays.toString(params));
		PreparedStatement ps = conn.prepareStatement(sql,new String[] {keyName});
		for(int i =0;i<params.length;i++) {
			ps.setObject(i+1, params[i]);
		}
		return ps;
	}
	
	public static long insert(String sql, String keyName, Object...params) {
		return (long) execute( conn -> {
			PreparedStatement ps = prepareInsertStatement(conn, sql, keyName, params);
			if( ps.executeUpdate() > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				return rs.getLong(1);
			}else {
				return 0;
			}
		} );
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> select(String sql,Object...params){
		return (List<Map<String,Object>>) execute( conn -> {
			List<Map<String,Object>> list = new ArrayList<>();
			
			PreparedStatement ps = prepareStatement(conn,sql,params);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			while(rs.next()) {
				Map<String, Object> row = new LinkedHashMap<>();
				for(int i = 0; i<md.getColumnCount();i++) {
					String columnName = md.getColumnName(i+1);
					columnName = columnName.toLowerCase();
					Object columnValue = rs.getObject(i+1);
					row.put(columnName, columnValue);
				}
				list.add(row);
			}
			return list;
		} );
		
	}
	
	public static Map<String,Object> selectPage(String sql,
			int page, int size, Object...params){
		Map<String,Object> ret = new HashMap<>();
	    
		int total = 0;
		List<Map<String,Object>> data = null;
		
		String sql1 = "select * from"
				+ "(select a.*, rownum rn from ("+sql+") a where rownum <= ?) "
				+ "where rn >= ?";
		
		int begin = (page-1) * size + 1;
		int end = page * size;
		
		Object[] newParams = new Object[params.length+2];
		
		System.arraycopy(params, 0, newParams, 0, params.length);
		newParams[newParams.length-2] = end;
		newParams[newParams.length-1] = begin;
		data = JdbcTemplate.select(sql1, newParams);

		String sql2 = "select count(*) cnt from ("+sql+")";
		
		List<Map<String,Object>> totalList = JdbcTemplate.select(sql2, params);
		total = Integer.parseInt(totalList.get(0).get("cnt") + "");
		
		ret.put("total", total);
		ret.put("data", data);
		return ret;
	}
	
	public static void main(String[] args) {
		//update("insert into dept values(?,?,?)",13,"test","hy");
		//update("update dept set loc = ? where deptno =?","衡阳",13);
	    List<?> list1 = select("select * from emp");
	    list1.forEach(System.out::println);
	    
	    List<?> list2 = select("select * from emp where deptno =? and sal > ?",10,1000);
	    list2.forEach(System.out::println);
	    
	    System.out.println("====================================");
	    Map<String,Object> page = selectPage("select * from emp",1 , 5);
	    List<?> list3 = (List<?>) page.get("data");
	    list3.forEach(System.out::println);
	    int total = (int) page.get("total");
	    System.out.println("总行数:"+ total);
	    
	    
	    System.out.println("====================================");
	    Map<String,Object> page1 = selectPage("select * from emp where sal > ?",3 , 3, 100);
	    List<?> list4 = (List<?>) page1.get("data");
	    list4.forEach(System.out::println);
	    int total1 = (int) page1.get("total");
	    System.out.println("总行数:"+ total1);
	}
}	
	
