package com.bs.dateFormatAutoConfiguration.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BsTimeFunction {
    @Autowired
    private TimeProperties properties;

    public String showtime(String name){
        Date date = new Date();
        DateFormat dateFormat=new SimpleDateFormat(properties.getFormat());
        String s= dateFormat.format(date);
        return "Hello"+name+"!It's "+s;
    }
}
