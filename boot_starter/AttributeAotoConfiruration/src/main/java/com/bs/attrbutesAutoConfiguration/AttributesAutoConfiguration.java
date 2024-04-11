package com.bs.attrbutesAutoConfiguration;

import com.bs.attrbutesAutoConfiguration.demo.AttributesProperties;
import com.bs.attrbutesAutoConfiguration.demo.BsAttrbutesFunction;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(BsAttrbutesFunction.class)
@EnableConfigurationProperties({AttributesProperties.class})
public class AttributesAutoConfiguration {
    @Bean
    public BsAttrbutesFunction bsAttrbutesFunction(){return new BsAttrbutesFunction();}
}
