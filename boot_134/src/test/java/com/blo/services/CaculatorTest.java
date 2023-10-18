package com.blo.services;

import com.blo.Myapp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Myapp.class)
public class CaculatorTest {

    @Autowired
    private Caculator caculator;
    @Test
    public void add() {
        int x=caculator.add(1,2);
        Assert.assertEquals(x,3);

    }
}