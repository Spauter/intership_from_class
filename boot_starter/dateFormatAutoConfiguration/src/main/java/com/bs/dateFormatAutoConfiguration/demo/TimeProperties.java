package com.bs.dateFormatAutoConfiguration.demo;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bs.time")
public class TimeProperties {
    private String format="yyyy-MM-dd HH:mm:ss";

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
