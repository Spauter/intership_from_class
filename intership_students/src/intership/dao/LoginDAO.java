package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;

public class LoginDAO {
	public  List<Map<String,Object>>list;

	
	public List<Map<String,Object>> selectStudent(String id, String password){
		String sql = "select * from tb_student where s_id=? and s_password=?";
		return JdbcTemplate.select(sql,id,password);
	}
	
	public List<Map<String,Object>> selectTeacher(String id, String password){
		String sql = "select * from tb_teacher where t_id=? and t_pwd=?";
		return JdbcTemplate.select(sql,id,password);
	}
	
	public List<Map<String,Object>>selectAdmin(String name,String password){
		String sql="select * from tb_admin where aname=? and pwd=?";
		return JdbcTemplate.select(sql, name,password);
	}
}
