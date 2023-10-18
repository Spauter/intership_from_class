package com.blo.bean;

import com.blo.Myapp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= Myapp.class)
public class StudentTest {
    @Autowired
    private Student student;
    @Test
    public void testHello(){
        student.setSname("hello");
        Assert.notNull(student);
    }
}