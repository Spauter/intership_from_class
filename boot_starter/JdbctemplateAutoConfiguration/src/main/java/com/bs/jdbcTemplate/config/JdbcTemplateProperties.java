package com.bs.jdbcTemplate.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bs.jdbctemplate")
public class JdbcTemplateProperties {
    private String url;
    private String driverClassName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private String username;
    private String pwd;
}
