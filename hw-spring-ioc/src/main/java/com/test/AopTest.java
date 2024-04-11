package com.test;


import com.bloducspauter.config.IocConfig2;
import com.yc.spring.bbs.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = IocConfig2.class)
public class AopTest {
    @Resource
    UserDao userDao;

    @Test
    public void test1() {
        userDao.selectList(null,1,1);
        userDao.insert(null);

    }

    @Test
    public void test2() throws InterruptedException {
        userDao.update(null);
        userDao.insert(null);
    }



    @Test
    public void test3() throws InterruptedException {
        System.out.println("**********************************");
        userDao.insert(null);
        System.out.println("**********************************");
        userDao.update(null);
        System.out.println("**********************************");
        userDao.selectList(null,0,0);
        System.out.println("**********************************");
        userDao.selectOne(null);
        System.out.println("**********************************");
    }

    @Test
    public void test4(){
        System.out.println("**********************************");
        userDao.insert(null);
        System.out.println("**********************************");
        userDao.count(null);
        System.out.println("**********************************");
    }

    //@Autowired
    //@Qualifier("userDaoProxy1")
    @Resource
    UserDao userDaoProxy1;

    @Resource  //=> byName
    UserDao userDaoProxy2;

    @Test
    public void test5(){
        userDaoProxy1.insert(null);
        System.out.println("----------------------------------");
        userDaoProxy2.insert(null);
    }

    A a = new C();
    A a1 = new B();
    B b= new D();

//    C c=new B();

//    D d = new B();
}
//抽象主题
interface A {

}
//真实主题
class B implements A {
}

class C implements A {
    B b=new B();
}

class D extends B {
}
