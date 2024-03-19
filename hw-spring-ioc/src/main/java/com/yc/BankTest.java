package com.yc;

import com.bloducspauter.config.JdbcConfig;
import com.yc.spring.bank.bean.Account;
import com.yc.spring.bank.dao.AccountDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JdbcConfig.class)
@Component
public class BankTest {

    @Autowired
    @Qualifier("accountDaoImpl")
    AccountDao accountDao;


    @Test
    public void test1() {
        int a = accountDao.delete(1234);
        Assert.assertEquals(1, a);
    }

    //当readOnly设置为true时,不能进行增删改操作,所以此操作不会通过
    @Transactional(readOnly = true)
    @Test
    public void test1_1(){
        int a = accountDao.delete(1234);
        Assert.assertEquals(1, a);
        Assert.fail();
    }

    @Test
    public void test2() {
        Account account = new Account();
        account.setId(1234 + "");
        account.setName("张三");
        account.setBalance(1000.0);
        accountDao.insert(account);
    }

    @Test
    public void test3() {
        Account account1 = accountDao.selectById(1234);
        Assert.assertNotNull(account1);
    }

    @Test
    public void test4() {
        Account account = accountDao.selectById(1234);
        account.setBalance(114514.0);
        int a=accountDao.update(account);
        Assert.assertEquals(1,a);
        Account newAccount = accountDao.selectById(1234);
        System.out.println(account);
        System.out.println(newAccount);
        Assert.assertEquals(account.getBalance(),newAccount.getBalance(),0.01);
    }

    //由于出现异常,bank_account中balance最终没有修改成114514
    @Transactional
    @Test
    public void Transactional() {
        test4();
        opus();
    }

    //用来模拟异常的
    public void opus(){
       int a=1/0;
    }
}
