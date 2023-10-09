package intership.Util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OutPutExcelUtil {

	/**
	 * 输出excel表
	 * @param sheetName
	 * @param title
	 * @param values
	 * @param filePath
	 */
	public static void createExcel(String sheetName, String[] title, String[][] values, String filePath) {

        Workbook wb;
        if (filePath.endsWith(".xls")) {
            wb = new HSSFWorkbook();
        } else if (filePath.endsWith(".xlsx")) {
            wb = new XSSFWorkbook();
        } else {
            throw new IllegalArgumentException("Invalid file extension. Only .xls and .xlsx are supported.");
        }
        Sheet sheet = wb.createSheet(sheetName);

        Row row = sheet.createRow(0);

        Cell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                row.createCell(j).setCellValue(values[i][j]);
            }
        }

        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid file path.");
        }

        try {
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file.");
        }
	}

}