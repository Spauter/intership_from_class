package blo.spau.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelUtil implements ReadXlsx, ReadXls,OutputExcel {
    final String suffix1 = "xlsx";
    final String suffix2 = "xls";
    private final Map<Integer, String> titles = new HashMap<>();
    private Map<String, Object> map;
    private List<Map<String, Object>> list=new ArrayList<>();
    ToolImpl fileValidation = new ToolImpl();
    private int maxRow = 0;
    private int maxCol = 0;

    private List<Map<String, Object>> readXlsImpl(File file) throws IOException{
        fileValidation.Check_file(file);
        fileValidation.Check_IsDirectory(file);
        fileValidation.Ckeck_suffix(file, suffix2);
        // 创建工作簿对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        // 获取工作簿下sheet的个数
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        // 获取最后一行的num，即总行数。此处从0开始
        this.maxRow = sheet.getLastRowNum();
        for (int row = 1; row < maxRow; row++) {
            map=new HashMap<>();
            this.maxCol = sheet.getRow(row).getLastCellNum();
            for (int rol = 0; rol < maxCol; rol++) {
                HSSFCell title = sheet.getRow(0).getCell(rol);
                String titleInfo = String.valueOf(title);
                if (row == 1) {
                    this.titles.put(rol, titleInfo);
                }
                HSSFCell info = sheet.getRow(row).getCell(rol);
                if (titleInfo.isEmpty() || String.valueOf(info).isEmpty()) {
                    String infos = fileValidation.PrintInfo("WARRING: 存在合并单元格或者空单元格", 31, 0);
                    System.out.println(infos);
                }
                map.put(titleInfo, info);
            }
            list.add(map);
        }
        for (Map<String, Object> m : list) {
            System.out.println(m);
        }

        return list;
    }


    private   List<Map<String, Object>> readXlsxImpl(File file) throws IOException {
        fileValidation.Check_file(file);
        fileValidation.Check_IsDirectory(file);
        fileValidation.Ckeck_suffix(file, suffix1);
        // 创建工作簿对象
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(file));
        // 遍历工作簿中的所有数据
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        // 获取最后一行的num，即总行数。此处从0开始
        this.maxRow = sheet.getLastRowNum();
        for (int row = 1; row < maxRow; row++) {
            this.maxCol = sheet.getRow(row).getLastCellNum();
            map=new HashMap<>();
            for (int rol = 0; rol < maxCol; rol++) {
                XSSFCell title = sheet.getRow(0).getCell(rol);
                String titleInfo = String.valueOf(title);
                XSSFCell info = sheet.getRow(row).getCell(rol);
                if (row == 1) {
                    this.titles.put(rol, titleInfo);
                }
                if (titleInfo.isEmpty() || String.valueOf(info).isEmpty()) {
                    String infos = fileValidation.PrintInfo("WARRING: 存在合并单元格或者空单元格", 31, 0);
                    System.out.println(infos);
                }
                map.put(titleInfo, info);
            }
            list.add(map);
        }
        return list;
    }

    private String[] getTitleImpl(File file) throws IOException{
        String[] title;
        if(file==null && list!=null){
            if(list.isEmpty()){
                String infos = fileValidation.PrintInfo("WARRING: 未成功获取标题信息", 31, 0);
                System.out.println(infos);
            }
            title = new String[titles.size()];
            for (int i = 0; i < titles.size(); i++) {
                title[i] = titles.get(i);
            }
            return title;
        }
        assert file != null;
        if (file.getName().endsWith("xlsx")) {
            list = readXlsx(file);
        } else if (file.getName().endsWith("xls")) {
            list = readXls(file);
        } else {
            throw new IllegalArgumentException("不支持文件格式，需要 \".xlsx\" 或者 \".xls\".");
        }
        if(list==null || list.isEmpty()){
            String infos = fileValidation.PrintInfo("WARRING: 未成功获取标题信息", 31, 0);
            System.out.println(infos);
        }
        title = new String[titles.size()];
        for (int i = 0; i < titles.size(); i++) {
            title[i] = titles.get(i);
        }
        return title;
    }

    private void outPutImpl(String sheetName, Object[][] obj, String[] title, File file) throws IOException {
        fileValidation.Check_OutputPath(file);
        fileValidation.conformity(obj,title);
        Workbook wb;
        if (file.getName().endsWith(".xls")) {
            wb = new HSSFWorkbook();
        } else  {
            wb = new XSSFWorkbook();

        }
        Sheet sheet = wb.createSheet(sheetName);
        Row row = sheet.createRow(0);
        Cell cell;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        for (int i = 0; i < obj.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < obj[i].length; j++) {
                row.createCell(j).setCellValue(obj[i][j].toString());
            }
        }
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("无效路径.");
        }
        try {
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            throw new IOException("导出失败.");
        }
        System.out.println("文件导出成功，已保存到"+file.getAbsolutePath());
    }


    @Override
    public List<Map<String, Object>> readXls(File file) throws IOException {
        return readXlsImpl(file);
    }

    @Override
    public List<Map<String, Object>> readXlsx(File file) throws IOException {
        return readXlsxImpl(file);
    }

    @Override
    public List<Map<String, Object>> readXls(String Path) throws IOException {
        File file = fileValidation.conformity(Path);
        return readXlsImpl(file);
    }

    @Override
    public Object[][] readLs(File file) throws IOException {
        list = readXlsImpl(file);
        return fileValidation.conformity(list, titles);
    }

    @Override
    public Object[][] readLs(String Path) throws IOException {
        File file = fileValidation.conformity(Path);
        return readLs(file);
    }


    @Override
    public List<Map<String, Object>> readXlsx(String Path) throws IOException {
        File file = fileValidation.conformity(Path);
        return readXlsxImpl(file);
    }

    @Override
    public Object[][] readLsx(File file) throws IOException {
        list = readXlsxImpl(file);
        return fileValidation.conformity(list, titles);
    }

    @Override
    public Object[][] readLsx(String Path) throws IOException {
        File file = fileValidation.conformity(Path);
        return readLsx(file);
    }

    @Override
    public String[] getTitle(File file) throws IOException {
        return getTitleImpl(file);
    }

    @Override
    public String[] getTitle(List<Map<String, Object>> list) {
        String[] title=new String[list.get(0).size()];
        int i=0;
        for(Map<String,Object> map:list){
            for (String key : map.keySet()) {
                if(i<title.length) {
                    title[i] = key;
                    titles.put(i,key);
                    i++;
                }else {
                    break;
                }
            }
        }
        return title;
    }

    public String[] getTitle(String Path) throws IOException {
        File file = fileValidation.conformity(Path);
        return getTitleImpl(file);
    }

    public String[] getTitle() throws IOException {
        return getTitleImpl(null);
    }

    public int getMaxRows() {
        return maxRow;
    }

    public int getMaxCols() {
        return maxCol;
    }

    @Override
    public void outPut(String sheetName, Object[][] obj, String[] title, File file) throws IOException {
        outPutImpl(sheetName,obj,title,file);
    }

    @Override
    public void outPut(String sheetName, Object[][] obj, String[] title, String Path) throws IOException {
        File file=fileValidation.conformity(Path);
        outPutImpl(sheetName,obj,title,file);
    }


    @Override
    public void outPut(String sheetName, List<Map<String, Object>> list, String Path) throws IOException {
        File file=fileValidation.conformity(Path);
        String[] title=getTitle(list);
        Object[][] obj=fileValidation.conformity(list,titles);
        outPutImpl(sheetName,obj,title,file);
    }

    @Override
    public void outPut(String sheetName, List<Map<String, Object>> list, File file) throws IOException {
        String[] title=getTitle(list);
        Object[][] obj=fileValidation.conformity(list,titles);
        outPutImpl(sheetName,obj,title,file);
    }

    @Override
    public void outPut(List<Map<String, Object>> list, String Path) throws IOException {
        File file=fileValidation.conformity(Path);
        String[] title=getTitle(list);
        Object[][] obj=fileValidation.conformity(list,titles);
        outPutImpl("Sheet1",obj,title,file);
    }

    @Override
    public void outPut(List<Map<String, Object>> list, File file) throws IOException {
        String[] title=getTitle(list);
        Object[][] obj=fileValidation.conformity(list,titles);
        outPutImpl("Sheet1",obj,title,file);
    }

    @Override
    public void outPut(Object[][] obj, String[] title, String Path) throws IOException {
        File file=fileValidation.conformity(Path);
        outPutImpl("Sheet1",obj,title,file);
    }

    @Override
    public void outPut(Object[][] obj, String[] title, File file) throws IOException {
        outPutImpl("Sheet1",obj,title,file);

    }
}
