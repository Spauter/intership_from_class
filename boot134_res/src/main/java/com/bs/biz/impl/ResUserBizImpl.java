package com.bs.biz.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.bean.ResUser;
import com.bs.biz.ResUserBiz;
import com.bs.mapper.ResUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(
        readOnly = true
)
@Slf4j
public class ResUserBizImpl implements ResUserBiz {
    @Autowired
    private ResUserMapper resUserMapper;


    @Override
    public ResUser findByNameAndPwd(String username, String pwd) {
        QueryWrapper<ResUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("pwd", pwd);
        ResUser resUser = resUserMapper.selectOne(wrapper);
        return resUser;
    }

    @Override
    public ResUser findByUID(Integer uid) {
        QueryWrapper<ResUser> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", uid);
        return resUserMapper.selectOne(wrapper);
    }
}
