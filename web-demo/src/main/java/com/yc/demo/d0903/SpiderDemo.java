package com.yc.demo.d0903;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="http://www.dygangs.net/">XX 电影网</a>
 */
public class SpiderDemo {
    /*
     * _ooOoo_
     * o8888888o
     * 88" . "88
     * (| -_- |)
     *  O\ = /O
     * ___/`---'\____
     * .   ' \\| |// `.
     * / \\||| : |||// \
     * / _||||| -:- |||||- \
     * | | \\\ - /// | |
     * | \_| ''\---/'' | |
     * \ .-\__ `-` ___/-. /
     * ___`. .' /--.--\ `. . __
     * ."" '< `.___\_<|>_/___.' >'"".
     * | | : `- \`.;`\ _ /`;.`/ - ` : | |
     * \ \ `-. \_ __\ /__ _/ .-` / /
     * ======`-.____`-.___\_____/___.-`____.-'======
     * `=---='
     *          .............................................
     *           佛曰：bug泛滥，我已瘫痪！
     */

    public static void main(String[] args) throws IOException {
        final URL url = new URL("http://www.dygangs.net");
        final Document doc = Jsoup.parse(url, 5000);
        final Elements a = doc.select("a");
        a.forEach(a1 -> {
            if (a1.attr("href").matches("/\\w+/\\d+/\\d+\\.htm")) {
                String herf = a1.attr("href");
                try {
//                    downMovieInfo(herf);
                    download(herf);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    public static void downMovieInfo(String id) throws IOException {
        final URL url = new URL("http://www.dygangs.net"+id);
        final Document doc = Jsoup.parse(url, 5000);
        Element element=doc.selectFirst("#dede_content>p:nth-child(2)");
        System.out.println(element);


    }

    public static void download(String href) throws IOException {
        final URL url = new URL("http://www.dygangs.net" + href);
        final Document doc = Jsoup.parse(url, 5000);
        System.out.println(doc);
        final Elements element = doc.select("#dede_content>p");
        System.out.println(element.get(0).text());
        String id = href.replaceAll("/\\w+/\\d+/(\\d+)\\+.htm", "$1");
        final List<Map<String, Object>> list = JdbcTemplate.select("select count(*) cnt from movie where id=?", id);
        final int i = Integer.parseInt("" + list.get(0).get("cnt"));
        if (i == 0) {
            return;
        }
        if (element.get(0).children().size() == 0) {
            return;
        }
        final String image = element.get(0).child(0).attr("src");
        final String intro = element.get(3).html();
        final String text = element.get(1).text();

        String replacement = "$2";
        System.out.println("text = " + text);
        replacement = java.util.regex.Matcher.quoteReplacement(replacement);

        //此处替换无效，正则表达式无法匹配当前结果



        final String name = replaceAll(text,".*◎(片　*名|标　*题)([^◎]+)◎.+", replacement);
        final String cate = replaceAll( text,".*◎(类　*别)([^◎]+)◎.+", replacement);
        final String natu = replaceAll(text,".*◎(产　*地)([^◎]+)◎.+", replacement);
        final String date = replaceAll( text,".*◎(上映日期)([^◎]+)◎.+", replacement);
        if(name!=null && !"$2".equals(name)) {
            System.out.println("name=" + name);
            System.out.println("cate=" + cate);
            System.out.println("natu=" + natu);
            System.out.println("date=" + date);
        }

        if (null == name) {
            return;
        }
        String sql = "insert into movie values(?,?，?，?，?,?，?)";
        try {
            JdbcTemplate.update(sql, id, name, cate, null, natu, image, intro);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static String replaceAll(String string, String regex, String text) {
        String res = string.replaceAll(regex, text);
        if (res.equals(string)) {
            return null;
        }
        return res;
    }

    @Test
    public void replaceTest(){
        String regex = "s";
        String replacement = "$f";
        String str = "test str replace.";
        replacement = java.util.regex.Matcher.quoteReplacement(replacement);
        String str1 = str.replaceAll(regex, replacement);
        System.out.println(str1);
    }


}
