package com.yc.spring.bank.dao.impl;


import com.yc.spring.bank.bean.Account;
import com.yc.spring.bank.dao.AccountDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AccountDaoImpl implements AccountDao {
    @Resource
    JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Account t) {
        String sql = "insert into bank_account values(?,?,?,?)";
        jdbcTemplate.update(sql, t.getId(), null, t.getBalance(),t.getName());

    }

    @Override
    public int update(Account t) {
        String sql = "update bank_account set balance=? where id=?";
        int a;
        a= jdbcTemplate.update(sql, t.getBalance(), t.getId());
        return  a;
    }

    @Override
    public Account selectById(Integer id) {
        String sql = "select * from bank_account where id= "+id;
        //        return jdbcTemplate.execute(sql);
        Account account=jdbcTemplate.queryForObject(sql, new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
                Account result=new Account();
                result.setId(rs.getString("id"));
                result.setBalance(rs.getDouble("balance"));
                result.setName(rs.getString("name"));
                return result;
            }
        });
        return account;
    }

    @Override
    public int delete(Integer id) {
        String sql = "delete from bank_account where id=?";
        return jdbcTemplate.update(sql, id);
    }
}
