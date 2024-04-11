package com.bs.dateFormatAutoConfiguration;

import com.bs.dateFormatAutoConfiguration.demo.BsTimeFunction;
import com.bs.dateFormatAutoConfiguration.demo.TimeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(BsTimeFunction.class)
@EnableConfigurationProperties({TimeProperties.class})
public class DateFormatAutoConfigration {
    @Bean
    public BsTimeFunction bsTimeFunction(){
        return new BsTimeFunction();
    }
}
