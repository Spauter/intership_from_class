package com.bs.biz.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.bs.bean.ResUser;
import com.bs.biz.ResUserBiz;
import com.bs.excption.BizException;
import com.bs.mapper.ResUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
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

    @Override
    public int insert(ResUser resUser) throws BizException {
        QueryWrapper<ResUser>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",resUser.getUsername());
        ResUser registeredUser= resUserMapper.selectOne(queryWrapper);
        if(registeredUser!=null){
            throw new BizException("This user has registered");
        }
        return resUserMapper.insert(resUser);
    }
}
