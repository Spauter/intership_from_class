package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;

public class StudentGradeDao {
	/**
	 * 
	 * @param grade
	 * @param s_id
	 * @param c_name
	 * @param c_id
	 * @return
	 */
	public List<Map<String, Object>> selectById(String grade, String s_id, String c_name, String c_id){
		String sql = "select a.grade"
				+ "  from tb_grades a"
				+ "  join tb_student b on a.s_id = b.s_id"
				+ "  join tb_courses c on a.c_name=c.c_name"
				+ "  where c.c_id=?";
		return JdbcTemplate.select(sql,grade,s_id,c_name,c_id);
	}

}
