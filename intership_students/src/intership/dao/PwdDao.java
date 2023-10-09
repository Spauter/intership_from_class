package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;

public class PwdDao {
	public int modifyStu(String s_id,String pwd){
		String sql="update tb_student set s_password=? where s_id=?";
		return JdbcTemplate.update(sql, pwd,s_id);
	}
	
	public List<Map<String,Object>>selectStu(String s_id,String pwd){
		String sql="select * from tb_student where s_id=? and  s_password=?";
		return JdbcTemplate.select(sql, s_id,pwd);
	}
}
