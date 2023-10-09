package blo.spau.excel;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


interface ReadXlsx extends Read {
    /**
     * 该方法通过输入文件读取xlsx文件，返回List集合
     * @param file
     * @return list
     * @throws IOException
     */
    List<Map<String, Object>> readXlsx(File file) throws IOException;

    /**
     * 该方法通过文件路径读取xlsx文件，返回lList集合
     *
     * @param path
     * @return list
     * @throws IOException
     */
    List<Map<String, Object>> readXlsx(String path) throws IOException;

    /**
     * 该方法通过输入文件读取xlsx文件，返回一个二维数组
     * 注意返回的数据不会包含表头（标题栏）
     *
     * @param file
     * @return Object[][]
     * @throws IOException
     */
    Object[][] readLsx(File file) throws IOException;

    /**
     * 该方法通过文件路径读取xlsx文件，返回一个二维数组
     * 注意返回的数据不会包含表头（标题栏）
     *
     * @param Path
     * @return Object[][]
     * @throws IOException
     */
    Object[][] readLsx(String Path) throws IOException;

}