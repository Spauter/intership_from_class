package com.blo.bean;

import com.blo.Myapp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= Myapp.class)
public class ProductTest {
    @Autowired
    private Product product;
    @Test
    public void  testProduct(){
        Assert.assertNotNull(product);
        Assert.assertEquals("Apple",product.getPname());
        Assert.assertEquals(20.0F,product.getPrice(),0.01);
        Assert.assertEquals("Red",product.getAttributes().get("color"));




    }

}