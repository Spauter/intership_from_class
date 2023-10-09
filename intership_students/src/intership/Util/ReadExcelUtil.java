package intership.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取Ececal表格，仅仅支持xlsx表格读取
 * 
 * @author Bloduc_Spauter
 *
 */
public class ReadExcelUtil {
	public static List<String> excel = new ArrayList<String>();
	public static String infos = "";
	/**
	 * 读取excel表格，返回list
	 * 
	 * @param file
	 * @return
	 */
	public List<String> readExcel(File file) {
		try {
			// 创建工作簿对象
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(file));
			// 获取工作簿下sheet的个数
			int sheetNum = xssfWorkbook.getNumberOfSheets();
			System.out.println("该excel文件中总共有：" + sheetNum + "个sheet");
			// 遍历工作簿中的所有数据
			for (int i = 0; i < sheetNum; i++) {
				// 读取第i个工作表
				System.out.println("读取第" + (i + 1) + "个sheet");
				XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
				// 获取最后一行的num，即总行数。此处从0开始
				int maxRow = sheet.getLastRowNum();
				for (int row = 0; row <= maxRow; row++) {
					// 获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
					try {
						int maxRol = sheet.getRow(row).getLastCellNum();
						for (int rol = 0; rol < maxRol; rol++) {
							XSSFCell info = sheet.getRow(row).getCell(rol);
							infos = infos + isNumber(info) + "\t";
						}
						excel.add(infos);
						infos = "";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
		return excel;
	}

	/**
	 * 由于POI读取短数字会转化为double类型（比如114.0),长数字时会自动转化成科学计数法数字；所以需中再次转化;
	 * 
	 * @param cell
	 * @return
	 */
	public String isNumber(XSSFCell cell) {
		String info = cell.toString();
		// 替换E
		info = info.replaceAll("[Ee]+", "");
		// 去除小数点
		info = info.replaceAll("[.]", "");
		// 检查是否为数字
			return info;
	}

	public static void main(String[] args) {
		File file = new File("C:\\users\\22\\desktop\\1.xlsx");
		ReadExcelUtil read = new ReadExcelUtil();
		List<String> list = read.readExcel(file);
		for (String l : list) {
			System.out.println(l);
		}
	}
}
