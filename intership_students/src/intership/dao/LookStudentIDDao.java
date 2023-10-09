package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;

public class LookStudentIDDao {
	
	public List<Map<String,Object>> select(String id){
		String sql = "select * from tb_student where s_id =?";
		return JdbcTemplate.select(sql,id);
	}
	
	public void update(String id, String s_is_member, String s_class_position, String s_phone_number) {
		String sql = "update tb_student set ,s_is_member=?,"
				+"s_class_position=?,s_phone_number=? where s_id=?";
		JdbcTemplate.update(sql, s_is_member,s_class_position,s_phone_number,id);
	}

	public int updateImg(String id,Object image ) {
		String sql="update tb_student set s_image=? where s_id=?";
		return JdbcTemplate.update(sql, image,id);
	}
}
