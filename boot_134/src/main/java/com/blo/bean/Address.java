package com.blo.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    private String province;
    private String city;

}
