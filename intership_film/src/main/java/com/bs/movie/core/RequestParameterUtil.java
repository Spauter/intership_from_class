package com.bs.movie.core;



import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Company 源辰信息
 * 读取解析数据
 * @author navy
 * @date 2024/3/4
 * @Email haijunzhou@hnit.edu.cn
 */
public class RequestParameterUtil {
    private byte[] saveUploadData; // 存放读取到的数据
    private int currentReadIndex = 0; // 当前读取到的索引位置
    private int contentLength; // 上传的数据总长度


    public static RequestParam parseRequest(HttpServletRequest request) throws IOException {
        return new RequestParameterUtil().parse(request);
    }

    /**
     * 1、从请求中读取数据
     * 2、判断参数的编码类型
     * 3、解析对应的编码类型
     * @param request 用户请求
     * @return 解析好的请求对象
     * @throws IOException
     */
    private RequestParam parse(HttpServletRequest request) throws IOException {
        // 1、读取数据

        // 2、判断数据类型

        return null;
    }

    /**
     * 从请求中读取用户传过来的数据
     * @param request 用户请求
     * @return 用户传给服务器的数据
     */
    private byte[] readData(HttpServletRequest request) throws IOException {

        return null;
    }

    /**
     * 处理multipart/form-data编码格式的请求
     * 1、获取数据之间的分隔符，即第一行数据
     * 2、循环读取解析每一个表单项的数据：
     *  （1）、解析获取表单文本框的name，即分隔符之后，空行之前的数据
     *  （2）、解析获取表单文本框的值，即空行之后，下一个分隔符之前的数据
     * @return
     */
    private RequestParam parseMultipart() {
        Map<String, List<Part>> partMap = new Hashtable<>(); // 存文件数据
        Map<String, String> parameters = new Hashtable<>(); // 存普通表单数据
        List<Part> parts = null; // 存放文件


        return new RequestParam(parameters, partMap);
    }

    /**
     * 读取分隔符。即请求参数中的第一行
     * @return 数据分隔符
     */
    private String readBoundary() {
        return "";
    }

    /**
     * 读取解析表单元素信息
     * 即Content-Disposition: form-data; name="account"\r\n\r\n
     * 或Content-Disposition: form-data; name="photo"; filename="head.png"\r\nContent-Type: image/png\r\n\r\n
     * @return 返回表单元素信息字符串
     */
    private String readFormName() {
        return ""; // 取到是表单数据信息
    }

    /**
     * 读取表单项的数据部分，读到什么时候说明数据部分结束呢？ 读到下一个分隔符结束
     * 这个数据可能是普通的表单数据如：账号：yc  密码：123
     * 也可能是文件的字节码数据（图片、音频、视频等）
     * @return
     */
    private byte[] readFormData(String boundStr) {
        return null;
    }

    /**
     * 处理文件表单数据
     * @param formInfo 表单元素信息
     *                 Content-Disposition: form-data; name="photo"; filename="head.png"
     *                 Content-Type: image/png
     * @param name 表单元素名，即表单中name属性的属性值
     * @param data 数据信息
     * @return
     */
    private Part parsePart(String formInfo, String name, byte[] data) {
        return null;
    }

    /**
     * 提取表单项信息中的name属性的值
     * @param str 表单项信息  Content-Disposition: form-data; name="account"
     * @return
     */
    private String getName(String str) {
        return "";
    }

    /**
     * 解析文件名
     * @param str Content-Disposition: form-data; name="photo"; filename="head.png"
     *            Content-Type: image/png
     * @return
     */
    private String getFileName(String str) {
        return "";
    }

    /**
     * 解析文件类型信息
     * @param str Content-Disposition: form-data; name="photo"; filename="head.png"
     *            Content-Type: image/png
     * @return
     */
    private String getTypeName(String str) {
        return "";
    }

    /**
     * 处理application/x-www-form-urlencoded编码格式的请求
     * 直接转成字符串，然后截取。此时的数据的数据格式为company=yc&pwd=123&code=
     * @return 解析好的请求对象
     */
    private RequestParam parseEncoded() {

        return null;
    }

    /**
     * 处理text/plain编码格式的数据
     * 直接转成字符串，然后截取。此时的数据的数据格式为：company=yc\r\npwd=123\r\ncode=
     * @return 解析好的请求对象
     */
    private RequestParam parseTextPlain() {
        return null;
    }

    /**
     * 右下角图片水印
     * 1. 获取原始图片大小
     * 2.创建图片缓存对象   高度、宽度和图片颜色
     * 3.创建Java绘图工具对象
     * 4.使用绘图工具对象将原图绘制到缓存图片对象
     * 5.添加图片水印
     *  (1)获取水印图片路径
     *  (2)创建文件对象，去指向这个文件
     *  (3)使用Image这个类去操作这个文件
     *  (4)计算图片的显示位置
     *  (5)设置透明度
     *  (6)绘制图片水印
     *  (7)释放工具
     * 6.返回处理好的图片字节数据
     * @param bt 要添加水印的图片数据
     * @param logopath logo图片路径
     */
    private static byte[] addImageWaterMark(byte[] bt, String logopath) {
        return null;
    }
}
