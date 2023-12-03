package blo.spau.tool;


import blo.spau.FileReadAndOutPutUtil;
import blo.spau.exception.UnsupportedSuffixException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用继承的方式实现的
 */
public class ToolImpl extends Tool implements FileReadAndOutPutUtil {
    private final List<Map<String, Object>> list = new ArrayList<>();


    public void Ckeck_suffix(File file) throws UnsupportedSuffixException, IOException {
        String suffix = file.getName().split("\\.")[1];
        if (!(suffix.equals(suffix1) || (suffix.equals(suffix2)))) {
            throw new UnsupportedSuffixException("Unsupported suffix.We need 'xls' or 'xlsx' file,but you provide a '" + suffix + "' file");
        }

    }

    @Override
    public void Check_file(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("The file is not found:" + file.getName() + ".");
        }
    }

    @Override
    public void Check_IsDirectory(File file) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("The folder cannot be read or written.");
        }
    }


    @Override
    public String PrintInfo(String content, int color, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", color, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", color, type, content);
        }

    }

    @Override
    public Object[][] conformity(List<Map<String, Object>> list, Map<Integer, String> titles) {
        Object[][] objects = new Object[list.size()][titles.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < titles.size(); j++) {
                objects[i][j] = list.get(i).get(titles.get(j));
            }
        }
        return objects;
    }


    @Override
    public File conformity(String path) {
        return new File(path);
    }

    @Override
    public List<Map<String, Object>> conformity(Object[][] obj, String[] title) throws IndexOutOfBoundsException, NullPointerException {
        if (obj == null || obj.length == 0 || title == null || title.length == 0) {
            throw new NullPointerException("无法载入空数据");
        }
        int lenx = obj[0].length;
        int leny = obj.length;
        if (title.length < lenx) {
            throw new IndexOutOfBoundsException("标题长度与内容不匹配");
        }
        for (int i = 0; i < leny; i++) {
            Map<String, Object> map = new HashMap<>();
            for (int j = 0; j < title.length; j++) {
                if (j < obj[0].length) {
                    map.put(title[j], obj[i][j]);
                } else {
                    System.out.println(PrintInfo("WARRING: 第" + (i + 1) + "行 " + "第" + (j + 1) + "列 数据为空，将用空字符代替", 31, 0));
                    map.put(title[j], "");
                }
            }
            list.add(map);
        }
        return list;
    }

}
