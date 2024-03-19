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
        Assert.assertEquals(1,a);
//        Assert.fail("测试失败");

    }
    @Test
    public void test3() {
        Account account = new Account();
        account.setId(1234+"");
        account.setName("张三");
        account.setBalance(1000.0);
        accountDao.insert(account);
    }

    @Test
    public void test2() {
        Account account1 = accountDao.selectById(1234);
        Assert.assertNotNull(account1);
    }

}
