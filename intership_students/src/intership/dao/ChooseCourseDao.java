package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;

public class ChooseCourseDao {
	public List<Map<String, Object>> selectAll() {
		String sql = "select c_id,c.c_name,t.t_name,is_course_open from tb_courses c join tb_teacher t on t.t_cname=c.c_name";
		return JdbcTemplate.select(sql);
	}
	
	public List<Map<String, Object>> select(String name) {
		String sql = "select c_id,c.c_name,t.t_name,is_course_open from tb_courses c join tb_teacher t on t.t_cname=c.c_name where t.t_name=?";
		return JdbcTemplate.select(sql,name);
	}
	
	public int  insert(String s_id,String c_id,String t_name) {
		String sql="insert into tb_student_courses values(?,?,?)";
		return JdbcTemplate.update(sql, s_id,c_id,t_name);
	}
	
	public  List<Map<String,Object>>selectChoose(String id){
		String sql="select * from tb_student_courses where s_id=?";
		return JdbcTemplate.select(sql, id);
	}
	
	public int delete(String c_id) {
		String sql="delete tb_student_courses where c_id=?";
		return JdbcTemplate.update(sql, c_id);
	}
	
	public boolean isSelected(String s_id,String c_id) {
		String sql="select * from tb_student_courses where s_id=? and c_id=?";
		List<Map<String,Object>>list=JdbcTemplate.select(sql, s_id,c_id);
		return list.size()==1?true:false;
	}
	
	public int countChoosed(String s_id) {
		List<Map<String,Object>>list=JdbcTemplate.select("select * from tb_student_courses where s_id=?",s_id);
		if(list.isEmpty()) {
			return 0;
		}
		String c_id=list.get(0).get("S_ID").toString();
		String sql="select count(*) cnt from tb_student_courses where S_ID=?";
		Object cnt=JdbcTemplate.select(sql, c_id).get(0).get("CNT");
		int i=Integer.parseInt(cnt+"");
		return i;
	}
	
	
	public int countChoosed1(String t_name) {
		List<Map<String,Object>>list=JdbcTemplate.select("select * from tb_student_courses where t_name=?",t_name);
		if(list.isEmpty()) {
			return 0;
		}
		String T_NAME=list.get(0).get("T_NAME").toString();
		String sql="select count(*) cnt from tb_student_courses where T_NAME=?";
		Object cnt=JdbcTemplate.select(sql, T_NAME).get(0).get("CNT");
		int i=Integer.parseInt(cnt+"");
		return i;
	}
	
}
