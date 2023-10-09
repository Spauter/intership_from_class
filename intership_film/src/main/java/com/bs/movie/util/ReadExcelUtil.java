package com.bs.movie.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcelUtil {
    public static List<String> excel = new ArrayList<String>();
    public static String infos = "";

    /**
     * XSSFWorkbook读取xlsx表格，返回list
     *
     * @param file
     * @return
     */
    public static List<String> readXlsx(File file) {
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
                for (int row = 1; row <= maxRow; row++) {
                    // 获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
                    try {
                        int maxRol = sheet.getRow(row).getLastCellNum();
                        for (int rol = 0; rol < maxRol; rol++) {
                            XSSFCell info = sheet.getRow(row).getCell(rol);
                            infos = infos + info + "=";
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
     * HSSFWorkbook
     * 用于读取xls表格
     * @param file
     * @return
     */
    public static List<String> readXls(File file) {
        try {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
			int sheetNum = hssfWorkbook.getNumberOfSheets();
			System.out.println("该excel文件中总共有：" + sheetNum + "个sheet");
			for (int i = 0; i < sheetNum; i++) {
				HSSFSheet sheet = hssfWorkbook.getSheetAt(i);
				int maxRow = sheet.getLastRowNum();
				for (int row = 1; row <= maxRow; row++) {
					// 获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
					try {
						int maxRol = sheet.getRow(row).getLastCellNum();
						for (int rol = 0; rol < maxRol; rol++) {
							HSSFCell info = sheet.getRow(row).getCell(rol);
							infos = infos + info + "=";
						}
						excel.add(infos);
						infos = "";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
        return excel;
    }


    public static void main(String[] args) {
        File file = new File("C:\\users\\32306\\desktop\\1.xls");
        List<String>list=new ArrayList<>();
        if(file.getName().endsWith("xlsx")){
            list=readXlsx(file);
        }else {
            list=readXls(file);
        }
        for (String l : list) {
            System.out.println(l);
        }
    }
}
