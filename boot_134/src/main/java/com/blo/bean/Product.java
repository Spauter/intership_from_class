package com.blo.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Data
@Component
@ConfigurationProperties(prefix = "product")
public class Product {
    private String pname;
    private Float price;
    private Date manDate;
    private Boolean isUsed;
    private Map<String,String> attributes;

    private Address address;
    private List<String> types;
}
