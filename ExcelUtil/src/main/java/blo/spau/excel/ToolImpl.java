package blo.spau.excel;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用继承的方式实现的
 */
class ToolImpl extends Tool {
    private Object[][] objects;
    private List<Map<String, Object>> list=new ArrayList<>();

    private Map<String, Object> map;


    @Override
    void Ckeck_suffix(File file, String check) {
        String suffix=file.getName().split("\\.")[1];
        if (!suffix.equals(check)) {
            throw new IllegalArgumentException("不支持的文件格式。" + "需要的文件格式：\"" + check + "\",提供的文件格式:\"" + suffix + "\".");
        }

    }

    @Override
    void Check_file(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("文件或路径不存在:" + file.getName() + ".");
        }
    }

    @Override
    void Check_IsDirectory(File file) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("不能对文件夹进行读写操作.");
        }
    }

    @Override
    void Check_OutputPath(File file) {
        Check_IsDirectory(file);
        String suffix=file.getName().split("\\.")[1];
        if (suffix.equals("xlsx")) {

        }
        else if(suffix.equals("xls")){

        }else {
            throw new IllegalArgumentException("不支持文件格式，需要 \".xlsx\" 或者 \".xls\",提供的格式: ."+suffix);
        }
        if(file.exists()){
            throw new IllegalArgumentException("存在重复文件");
        }
    }


    @Override
    String PrintInfo(String content, int color, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", color, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", color, type, content);
        }

    }

    @Override
    Object[][] conformity(List<Map<String, Object>> list, Map<Integer, String> titles) {
        objects = new Object[list.size()][titles.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < titles.size(); j++) {
                objects[i][j] = list.get(i).get(titles.get(j));
            }
        }
        return objects;
    }


    @Override
    File conformity(String path) {
        File file = new File(path);
        return file;
    }

    @Override
    List<Map<String, Object>> conformity(Object[][] obj, String[] title) throws IndexOutOfBoundsException, NullPointerException {
        if (obj == null || obj.length == 0 || title == null || title.length == 0) {
            throw new NullPointerException("无法载入空数据");
        }
        int lenx = obj[0].length;
        int leny = obj.length;
        if (title.length < lenx) {
            throw new IndexOutOfBoundsException("标题长度与内容不匹配");
        }
        for (int i = 0; i < leny; i++) {
            map = new HashMap<>();
            for (int j = 0; j < title.length; j++) {
                if(j<obj[0].length) {
                    map.put(title[j], obj[i][j]);
                }else {
                    System.out.println(PrintInfo("WARRING: 第"+(i+1)+"行 "+"第"+j+"列 数据为空，将用空字符代替", 31, 0));
                    map.put(title[j],"");
                }
            }
            list.add(map);
        }
        return list;
    }

}
