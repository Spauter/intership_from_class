package com.bs.attrbutesAutoConfiguration.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bs.attributes")
public class AttributesProperties {
    private String attributes;

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
}
