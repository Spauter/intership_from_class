package com.bs.jdbcTemplate;

import com.bs.jdbcTemplate.config.JdbcTemplate;
import com.bs.jdbcTemplate.config.JdbcTemplateProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Driver;

@Configuration
@ConditionalOnClass({Driver.class, JdbcTemplate.class})
@EnableConfigurationProperties({JdbcTemplateProperties.class})
public class JdbcTemplateAutoConfiguration {
    @Bean
    public JdbcTemplate jdbctemplate(){
        return new JdbcTemplate();
    }
}

