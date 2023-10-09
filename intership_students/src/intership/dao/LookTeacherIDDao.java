package intership.dao;

import java.util.List;
import java.util.Map;

import intership.Util.UI_Util;
import oldFu.shopping.dao.JdbcTemplate;

public class  LookTeacherIDDao{
	public int update(String t_phone_number, String position, int t_id) {
		String sql = "update tb_teacher set t_phone_number=?, position=? where t_id=?";
		return JdbcTemplate.update(sql,t_phone_number, position,t_id);
	}

	public List<Map<String, Object>> select(String id) {
		String sql="select * from tb_teacher where t_id=?";
		return JdbcTemplate.select(sql, id);
	}

	public int updateImg(String id,String image ) {
		String imagePath=UI_Util.stringToHexString(image);
		String sql="update tb_teacher set image=? where t_id=?";
		return JdbcTemplate.update(sql, imagePath,id);
	}
	
	
	public int updateTel(String id,String tel) {
		String sql="update tb_teacher set t_phone_number=? where t_id=?";
		return JdbcTemplate.update(sql, tel,id);
	}
	
	
	
	public int updatePro(String id,String work) {
		String sql="update tb_teacher set position=? where t_id=?";
		return JdbcTemplate.update(sql, work,id);
	}
	
	
	
}
