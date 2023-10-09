package com.yc.demo.d0903;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import static com.yc.demo.d0903.SpiderDemo.download;

public class DownloadHtml {
    public static void main(String[] args) throws IOException {

    }

    public static void downloadHTML(String
                                            webpath, String dir) throws IOException {
        System.out.println("开始下载:" + webpath);
        final URL url = new URL(webpath);
        final Document doc = Jsoup.parse(url, 5000);
        final Elements elements = doc.select("link[type=\"text/css\"]");
        final Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()) {
            final Element link = iterator.next();
            String href = link.attr("href");
            String filepath = dir + href; // d:/a/css/index.css
            String newHrnef = webpath + href;// => http://www.duganqs.net/css/index.cSS


            File parentDir = new File(filepath).getParentFile();
            parentDir.mkdirs();
            downloadFile(newHrnef, dir);
            link.attr("href", href.substring(1));

        }

        final String html = doc.toString();
        String filename = url.getFile();
        if ("/".equals(filename)) {
            filename = "/index.html";
        }
        try (FileOutputStream fos = new FileOutputStream(dir + filename)) {
            fos.write(html.getBytes());
            System.out.println("下载完成:" + dir + filename);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    public static void downloadFile(String webpath,String dir) throws IOException {
        final URL url = new URL(webpath);
        final URLConnection urlConnection = url.openConnection();
        final InputStream in = urlConnection.getInputStream();
        String filename = url.getFile();
        if ("/".equals(filename)){
            filename = "/index.html";
        }
        System.out.println("开始下载:"+ webpath + " => " + dir + filename);
        try (FileOutputStream fos = new FileOutputStream( dir + filename);) {
            byte[] bytes = new byte[100];
            int count;
            while ((count = in.read(bytes)) >0){
                fos.write(bytes,  0, count);
            }
            in.close();
            System.out.println("下载完成:" +dir + filename);
        }
    }

    public static void searchMovie( )throws IOException{
        final URL url = new URL(  "http://www.dygangs.net");
        final Document doc = Jsoup.parse(url, 5000);
        final Elements as = doc.select("a");
        as.forEach(a->{

            if (a.attr("href") .matches(  "/l\\w+/Nld+/l1d+l1.htm")){
                final String href = a.attr(  "href");
                try {
                    download(href);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

}
