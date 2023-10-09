package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;

public class ImStudentDAO {
	public List<Map<String,Object>> selectAll(){
		String sql = "select * from tb_student";
		return JdbcTemplate.select(sql);
	}
	
	public void insert(String s_id,String s_name,String s_class,String phone,String date,String sex) {
		String sql = "insert into tb_student values(?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate.update(sql,s_id,s_name,s_class,"否","无",null,phone,phone.substring(phone.length()-6,phone.length()),date,sex);
	}

	public List<Map<String, Object>> searchStudentsBy(String sclass) {
		String sql="select * from tb_student where s_class=?";
		return JdbcTemplate.select(sql, sclass);
	}
	
	public int delete(String id) {
		String sql="delete tb_student where s_id=?";
		return JdbcTemplate.update(sql, id);
	}

	
	public int countSelected(String id) {
		List<Map<String,Object>>list=JdbcTemplate.select("select * from tb_student_courses where s_id=?",id);
		if(list.isEmpty()) {
			return 0;
		}
		String S_ID=list.get(0).get("S_ID").toString();
		String sql="select count(*) cnt from tb_student_courses where s_id=?";
		Object cnt=JdbcTemplate.select(sql, S_ID).get(0).get("CNT");
		int i=Integer.parseInt(cnt+"");
		return i;
	}
	

	
	
}
