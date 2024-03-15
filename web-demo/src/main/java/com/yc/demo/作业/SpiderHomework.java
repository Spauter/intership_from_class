package com.yc.demo.作业;


import blo.spau.excel.ExcelUtil;
import blo.spau.excel.output.OutputExcel;
import blo.spau.excel.read.ReadExcel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author spauter
 * @see <a href="https://www.0734zpw.com">...</a>
 */
public class SpiderHomework {
    static Set<Map<String, Object>> set = new HashSet<>();

    public static void main(String[] args) throws IOException {
        final URL url = new URL("https://www.0734zpw.com");
        for (int i = 1; i <= 2; i++) {
            Document document = Jsoup.parse(new URL(url + "/offer-" + i), 5000);
            final Elements a = document.select("a");
            a.forEach(a1 -> {
                String href = a1.attr("href");
                if (href.matches("/c\\d+\\.html")) {
                    try {
                        getInfo(href);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        for (Map<String, Object> m : set) {
            System.out.println(m);
        }
        List<Map<String, Object>> list = new ArrayList<>(set);
        System.out.println(list.size());
        OutputExcel output = new ExcelUtil();
        File file = new File(System.getenv("TEMP") + "\\1.xlsx");
        output.outPut(list, file);
        ReadExcel excel = new ExcelUtil();
        excel.getTitle();
    }

    public static void getInfo(String id) throws IOException {
        URL url = new URL("https://www.0734zpw.com" + id);
        Document document = Jsoup.parse(url, 5000);
        Elements p = document.select(".Xqcominfo");
        String info = p.get(0).text();
        String cname = info.split("关注")[0];
        String cinfo = info.split("关注")[1];
        String[] cinfos = cinfo.split("\\s");
        Map<String, Object> map = new HashMap<>();
        map.put("公司名称", cname);
        for (String c : cinfos) {
            String[] items = c.split("：");
            if (items.length == 2 && items[0].length() != 1) {
                map.put(items[0], items[1]);

            }
        }
        set.add(map);

    }


}
