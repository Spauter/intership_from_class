package com.bs.jdbcTemplate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("bs.jdbctemplate")
public class JdbcTemplateProperties {
    private String url;
    private String driverClassName;
    private String username;
    private String pwd;
}
