package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;

public class WatchCourseDao {
	public List<Map<String,Object>>showAllChoosed(String tname){
		String sql="select a.s_id,b.s_name,b.s_class,b.s_phone_number,a.t_name from  tb_student_courses a join tb_student b  on a.s_id =b.s_id where t_name=?";
		return JdbcTemplate.select(sql, tname);
	}
	
	public List<Map<String,Object>>showStudentID(String s_name){
		String sql="select a.s_id,b.s_name,b.s_class,b.s_phone_number,a.t_name from  tb_student_courses a join tb_student b  on a.s_id =b.s_id where s_name=?";
		return JdbcTemplate.select(sql, s_name);
	}
	
	public List<Map<String,Object>>selectStudentsClass(String className){
		String sql="select a.s_id,b.s_name,b.s_class,b.s_phone_number,a.t_name from  tb_student_courses a join tb_student b  on a.s_id =b.s_id where s_class=?";
		return JdbcTemplate.select(sql, className);
	}
	
	public List<Map<String,Object>>select(String s_id){
		String sql="select * from tb_student where s_id=?";
		return JdbcTemplate.select(sql, s_id);
	}

}
