package com.bs.config;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

//@Service
//@Component
public class QuartzConfig {
    @Scheduled(cron = "0/60 * * * * ?")
    public void  test() throws IOException {
        URL url = new URL("http://127.0.0.1:81/sendEmail");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
// 设置其他请求头...
        int responseCode = connection.getResponseCode();
        InputStream inputStream = (responseCode >= 200 && responseCode < 300) ? connection.getInputStream() : connection.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        String responseBody = response.toString();
        System.out.println(responseBody);
    }


}
