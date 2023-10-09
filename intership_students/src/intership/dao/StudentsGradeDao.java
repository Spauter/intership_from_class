package intership.dao;

import java.util.List;
import java.util.Map;

import oldFu.shopping.dao.JdbcTemplate;




public class StudentsGradeDao {
	public List<Map<String,Object>> selectAll(){
		String sql = "select * from tb_grades";
		return JdbcTemplate.select(sql);
	}
	
	public List<Map<String, Object>> selectById(String s_name, int s_sex, String s_phone_number,
			String c_name, String grade){
		String sql = "select a.s_name,a.s_sex,s_phone_number,b.c_name,b.grade"
				+ "  from tb_student a"
				+ "  join tb_grades b on a.s_id=b.s_Id"
				+ "  where a.s_id=?";
		return JdbcTemplate.select(sql,s_name,s_sex,s_phone_number,c_name,grade);
	}
	//添加成绩
	public void insert(int s_id,  String grades) {
		String sql = "update tb_grades set grades=? where s_id=?";
		JdbcTemplate.update(sql, s_id, grades);
	}
	//升序
	public void asc() {
		String sql = "select * from tb_grades order by grade ASC";
		JdbcTemplate.select(sql);
	}
	
	public List<Map<String,Object>> select(String s_id, String c_name, String s_name, String grade){
		String sql = "select s_id, c_name, s_name, grade"
				+ "    case"
				+ "        when grade >= 90 then '优秀'"
				+ "        when grade >= 80 then '良好'"
				+ "        then grade >= 70 then '中等'"
				+ "        then grade >= 60 then '及格'"
				+ "        else '不及格'"
				+ "    end as grade_category"
				+ "from tb_grades";
		return JdbcTemplate.select(sql,s_id,c_name,s_name,grade);
	}
	//平均分
	public void avg() {
		String sql = "select avg(grade) as average_grade from tb_grades";
		JdbcTemplate.select(sql);
	}

}
