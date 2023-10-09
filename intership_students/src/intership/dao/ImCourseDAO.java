package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;

public class ImCourseDAO {
	public List<Map<String,Object>> selectAll(){
		String sql = "select * from tb_courses";
		return JdbcTemplate.select(sql);
	}
	
	public void insert(String c_id, String c_name, String is_course_open) {
		String sql = "insert into tb_course values(?,?,?)";
		JdbcTemplate.update(sql, c_id, c_name, is_course_open);
	}

	public List<Map<String,Object>> selectByName(String name){
		String sql = "select * from tb_courses where c_name=?";
		return JdbcTemplate.select(sql,name);
	}
}
