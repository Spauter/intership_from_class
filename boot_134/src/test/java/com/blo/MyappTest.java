package com.blo;
import com.blo.bean.ProductTest;
import com.blo.bean.StudentTest;
import com.blo.services.CaculatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({StudentTest.class, CaculatorTest.class, ProductTest.class})
public class MyappTest {
    
}