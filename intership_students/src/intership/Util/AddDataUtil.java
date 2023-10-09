package intership.Util;

import java.io.File;
import java.util.List;
import intership.dao.ImStudentDAO;
import intership.dao.LessonDAO;
import intership.dao.TeacherDAO;

public class AddDataUtil {
	ReadExcelUtil read = new ReadExcelUtil();
	ImStudentDAO stuDao = new ImStudentDAO();
	TeacherDAO tdao = new TeacherDAO();
	LessonDAO ldao = new LessonDAO();

	public void AddStudentUtil(File file) {
		List<String> list = read.readExcel(file);
		if (isNull(list)) {
			return;
		}
		for (String l : list) {
			String[] info = l.split("\\s");
			if (info.length != 6) {
				return;
			}
			try {
				info[0] = info[0].substring(0, 11);
				info[3] = info[3].substring(0, 10);
				info[4] = info[4].replaceAll("月", "");
				stuDao.insert(info[0], info[1], info[2], info[3], info[4], info[5]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void AddTeacherUtil(File file) {
		List<String> list = read.readExcel(file);
		if (isNull(list)) {
			return;
		}
		for (String l : list) {
			String[] info = l.split("\\s");
			if (info.length != 6) {
				continue;
			}
			try {
				info[0] = info[0].substring(0, 4);
				info[3] = info[3].replaceAll("月", "");
				info[4] = info[4].substring(0, 10);
				tdao.insert(info[0], info[1], info[2], info[3], info[4], info[5]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void AddCourseUtil(File file) {

		List<String> list = read.readExcel(file);
		if (isNull(list)) {
			return;
		}
		for (String l : list) {
			String[] info = l.split("\\s");
			if (info.length != 3) {
				continue;
			}
			try {
				ldao.insert(info[0], info[1], "否");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isNull(List<String> list) {
		return list == null || list.isEmpty();
	}
}
