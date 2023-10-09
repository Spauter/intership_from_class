package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;


public class LessonDAO {
	public List<Map<String,Object>> selectAll(){
		String sql = "select * from tb_courses";
		return JdbcTemplate.select(sql);
	}
	
	public List<Map<String, Object>> selectById(Object c_id){
		String sql = "select * from tb_courses where id=?";
		return JdbcTemplate.select(sql,c_id);
	}
	
	
	public int insert(String id,String name,String isOpen) {
		String sql="insert into tb_courses values(?,?,?)";
		return JdbcTemplate.update(sql, id,name,isOpen);
	}
	
	public int delete(String id) {
		String sql="delete tb_courses where c_id=?";
		return JdbcTemplate.update(sql, id);
	}
}
