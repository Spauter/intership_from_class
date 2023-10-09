package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;

public class TeacherDAO {
	public List<Map<String,Object>> selectAll(){
		String sql = "select * from tb_teacher";
		return JdbcTemplate.select(sql);
	}
	
	public List<Map<String,Object>> selectById(String id){
		String sql = "select * from tb_teacher where t_id=?";
		return JdbcTemplate.select(sql,id);
	}
	
	public void insert(String t_id, String t_name,String pro,String date,String tel,String sex) {
		String sql = "insert into tb_teacher values(?,?,?,?,?,?,?,?)";
		JdbcTemplate.update(sql, t_id,t_name,null,pro,date,tel,tel.substring(tel.length()-6,tel.length()),sex);
	}

	public List<Map<String,Object>>select(String course){
		String sql="select * from tb_teacher where t_cname=?";
		return JdbcTemplate.select(sql, course);
	}
	
	public int delete(String id) {
		String sql="delete tb_teacher where t_id=?";
		return JdbcTemplate.update(sql, id);
	}
	
	public int update(String id,String course) {
		String sql="update tb_teacher set t_cname=? where t_id=?";
		return JdbcTemplate.update(sql, course,id);
	}
	
	public int countSelected(String id) {
		List<Map<String,Object>>list=JdbcTemplate.select("select * from tb_student_courses a join tb_teacher b on a.t_name=b.t_name where t_id=?",id);
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
