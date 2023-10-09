package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;




public class GradeDAO {
	public List<Map<String,Object>> selectAll(){
		String sql = "select *  from tb_grades";
		return JdbcTemplate.select(sql);
	}
	
	public List<Map<String,Object>>showGrade(String c_name){
		String sql="select * from tb_grades where c_name=?";
		return JdbcTemplate.select(sql, c_name);
	}
	
	public List<Map<String,Object>>select(String s_class){
		String sql="select *  from tb_grades a join tb_student b on a.s_id=b.s_id where s_class=?";
		return JdbcTemplate.select(sql, s_class);
	}
	
	public List<Map<String, Object>> selectById(String c_id){
		String sql = "select * from tb_grades where s_id=?";
		return JdbcTemplate.select(sql,c_id);
	}
	
	public List<Map<String, Object>> selectBySId(String s_id){
		String sql = "select * from tb_grades where s_id=?";
		return JdbcTemplate.select(sql,s_id);
	}
	
	public int insert(String s_id,String c_name,String t_name,  String grades) {
		String sql = "insert into tb_grades values(?,?,?,?)";
		return JdbcTemplate.update(sql,s_id, c_name,t_name,grades);
		 
	}
	

	public int update(String s_id,String c_name,String score) {
		String sql="update tb_grades set grade=? where s_id=? and c_name=?";
		return JdbcTemplate.update(sql, score,s_id,c_name);
	}
	
	
	public List<Map<String,Object>>showStudentgrade(String tname){
		String sql="select c.s_id,c.s_name,c.s_sex,c.s_class,c.s_phone_number,d.t_name,d.t_cname from tb_teacher d"
				+ "       join  "
				+ "       (select a.s_id,a.s_name,a.s_class,b.c_id,b.t_name,a.s_phone_number,a.s_sex from tb_student a join tb_student_courses b on a.s_id=b.s_id) c "
				+ "       on d.t_name =c.t_name"
				+ " where d.t_name=?";
		return JdbcTemplate.select(sql, tname);
	}
	
	public List<Map<String,Object>> select1(String name){
		String sql = "SELECT"
				+ "    COUNT(*) AS total_students,"
				+ "    SUM(CASE WHEN grade >= 90 THEN 1 ELSE 0 END) AS A,"
				+ "    SUM(CASE WHEN grade >= 80 AND grade < 90 THEN 1 ELSE 0 END) AS B,"
				+ "    SUM(CASE WHEN grade >= 70 AND grade < 80 THEN 1 ELSE 0 END) AS C,"
				+ "    SUM(CASE WHEN grade >= 60 AND grade < 70 THEN 1 ELSE 0 END) AS D,"
				+ "    SUM(CASE WHEN grade < 60 THEN 1 ELSE 0 END) AS E,"
				+ "    AVG(grade) AS average_grade"
				+ " FROM tb_grades where s_name=?";
		return JdbcTemplate.select(sql,name);
	}
}

